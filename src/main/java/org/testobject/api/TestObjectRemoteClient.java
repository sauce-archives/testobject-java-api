package org.testobject.api;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import org.testobject.rest.api.TestSuiteReport;
import org.testobject.rest.api.TestSuiteReportResource;
import org.testobject.rest.api.TestSuiteResource;
import org.testobject.rest.api.UploadResource;
import org.testobject.rest.api.UserResource;

public class TestObjectRemoteClient implements TestObjectClient {

	private final UserResource user;
	private final UploadResource upload;
	private final TestSuiteResource testSuite;
	private final TestSuiteReportResource testSuiteReport;

	private final Client client;

	public TestObjectRemoteClient(String baseUrl, ProxySettings proxySettings) {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.connectorProvider(new ApacheConnectorProvider());
		clientConfig.register(JacksonFeature.class);
		clientConfig.register(MultiPartFeature.class);

		if (proxySettings != null) {
			clientConfig.getProperties().put(ClientProperties.PROXY_URI,
					"http://" + proxySettings.getHost() + ":" + proxySettings.getPort());
			if (proxySettings.getUsername() != null) {
				clientConfig.getProperties().put(ClientProperties.PROXY_USERNAME, proxySettings.getUsername());
				clientConfig.getProperties().put(ClientProperties.PROXY_PASSWORD, proxySettings.getPassword());
			}
		}

		client = ClientBuilder.newClient(clientConfig);

		WebTarget target = client.target(baseUrl);

		user = WebResourceFactory.newResource(UserResource.class, target);
		upload = WebResourceFactory.newResource(UploadResource.class, target);
		testSuite = WebResourceFactory.newResource(TestSuiteResource.class, target);
		testSuiteReport = WebResourceFactory.newResource(TestSuiteReportResource.class, target);
	}

	public void login(String username, String password) {
		Response response = user.login(username, password);
		
		if(Response.Status.OK.getStatusCode() == response.getStatus()){
			throw new IllegalStateException("expected status " + Response.Status.OK + " but was " + response.getStatus());
		}
	}

	public void updateInstrumentationTestSuite(String user, String project, long testSuite, InputStream appApk, InputStream testApk) {
		String appUploadId = uploadFile(user, project, appApk).replace("\"", "");
		String testUploadId = uploadFile(user, project, testApk).replace("\"", "");

		Response response = this.testSuite.updateInstrumentationTestSuite(user, project, testSuite,
				new TestSuiteResource.UpdateInstrumentationTestSuiteRequest(appUploadId, testUploadId));
		
		if(Response.Status.NO_CONTENT.getStatusCode() == response.getStatus()){
			throw new IllegalStateException("expected status " + Response.Status.NO_CONTENT + " but was " + response.getStatus());
		}
	}

	private String uploadFile(String user, String project, InputStream appApk) {
		FormDataMultiPart form = new FormDataMultiPart();
		try {
			form.field("userId", user);
			form.field("projectId", project);
			form.bodyPart(new StreamDataBodyPart("file", appApk));

			return upload.uploadFile(form);
		} finally {
			close(form);
		}
	}

	public long startInstrumentationTestSuite(String user, String project, long testSuite) {
		return this.testSuite.runInstrumentationTestSuite(user, project, testSuite);
	}

	public TestSuiteReport waitForSuiteReport(final String user, final String project, final long testSuiteReportId) {
		long start = now();
		while(TimeUnit.MILLISECONDS.toMinutes(now() - start) < 60){
			TestSuiteReport testSuiteReport = TestObjectRemoteClient.this.testSuiteReport.getReport(user, project, testSuiteReportId,
					MediaType.APPLICATION_JSON);
			if(testSuiteReport.isRunning() == false){
				return testSuiteReport;
			}
			
			sleep(TimeUnit.SECONDS.toMillis(30));
		}

		throw new IllegalStateException("unable to get test suite report result after 60min");
	}

	private void sleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private long now() {
		return System.currentTimeMillis();
	}

	public void close() {
		client.close();
	}

	private static void close(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
