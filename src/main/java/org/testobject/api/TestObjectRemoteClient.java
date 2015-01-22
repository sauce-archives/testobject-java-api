package org.testobject.api;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.testobject.rest.api.TestSuiteReport;
import org.testobject.rest.api.TestSuiteReportResource;
import org.testobject.rest.api.TestSuiteReportResourceImpl;
import org.testobject.rest.api.TestSuiteResource;
import org.testobject.rest.api.TestSuiteResourceImpl;
import org.testobject.rest.api.UploadResource;
import org.testobject.rest.api.UploadResourceImpl;
import org.testobject.rest.api.UserResource;
import org.testobject.rest.api.UserResourceImpl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

public class TestObjectRemoteClient implements TestObjectClient {

	private final UserResource user;
	private final UploadResource upload;
	private final TestSuiteResource testSuite;
	private final TestSuiteReportResource testSuiteReport;

	private final Client client;

	public TestObjectRemoteClient(String baseUrl, ProxySettings proxySettings) {
		ApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
		config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);
		
		if (proxySettings != null) {
			config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_PROXY_URI, "http://" + proxySettings.getHost() + ":" + proxySettings.getPort());
			if (proxySettings.getUsername() != null) {
				config.getState().getHttpState().setProxyCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxySettings.getUsername(), proxySettings.getPassword()));
			}
		}
		
		client = ApacheHttpClient.create(config);
		
		WebResource resource = client.resource(baseUrl);

		user = new UserResourceImpl(resource);
		upload = new UploadResourceImpl(resource);
		testSuite = new TestSuiteResourceImpl(resource);
		testSuiteReport = new TestSuiteReportResourceImpl(resource);
	}

	public void login(String username, String password) {
		user.login(username, password);
	}

	public void updateInstrumentationTestSuite(String user, String project, long testSuite, File appApk, File testApk) {
		String appUploadId = upload.uploadFile(user, project, appApk).replace("\"", "");
		String testUploadId = upload.uploadFile(user, project, testApk).replace("\"", "");

		this.testSuite.updateInstrumentationTestSuite(user, project, testSuite,
				new TestSuiteResource.UpdateInstrumentationTestSuiteRequest(appUploadId, testUploadId));
	}

	public long startInstrumentationTestSuite(String user, String project, long testSuite) {
		return this.testSuite.runInstrumentationTestSuite(user, project, testSuite);
	}
	
	public TestSuiteReport waitForSuiteReport(final String user, final String project, final long testSuiteReportId) {
		return waitForSuiteReport(user, project, testSuiteReportId, TimeUnit.MINUTES.toMillis(60), TimeUnit.SECONDS.toMillis(30));
	}


	public TestSuiteReport waitForSuiteReport(final String user, final String project, final long testSuiteReportId, long waitTimeoutMs, long sleepTimeMs) {
		long start = now();
		while((now() - start) < waitTimeoutMs){
			TestSuiteReport testSuiteReport = TestObjectRemoteClient.this.testSuiteReport.getReport(user, project, testSuiteReportId,
					MediaType.APPLICATION_JSON);
			if(testSuiteReport.isRunning() == false){
				return testSuiteReport;
			}
			
			sleep(sleepTimeMs);
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
		client.destroy();
	}

}
