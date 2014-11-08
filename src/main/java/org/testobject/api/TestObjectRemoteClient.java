package org.testobject.api;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import org.testobject.rest.api.BatchReportResource;
import org.testobject.rest.api.BatchReportResource.BatchReport;
import org.testobject.rest.api.BatchResource;
import org.testobject.rest.api.UploadResource;
import org.testobject.rest.api.UserResource;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;

public class TestObjectRemoteClient implements TestObjectClient {

	private final UserResource user;
	private final BatchResource batch;
	private final UploadResource upload;
	private final BatchReportResource batchReport;

	private final Client client;

	public TestObjectRemoteClient(String baseUrl) {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.connectorProvider(new ApacheConnectorProvider());
		clientConfig.register(JacksonFeature.class);
		clientConfig.register(MultiPartFeature.class);

		client = ClientBuilder.newClient(clientConfig);

		WebTarget target = client.target(baseUrl);

		user = WebResourceFactory.newResource(UserResource.class, target);
		batch = WebResourceFactory.newResource(BatchResource.class, target);
		upload = WebResourceFactory.newResource(UploadResource.class, target);
		batchReport = WebResourceFactory.newResource(BatchReportResource.class, target);
	}

	public void login(String username, String password) {
		Response response = user.login(username, password);
		Preconditions.checkState(Response.Status.OK.getStatusCode() == response.getStatus());
	}

	public void updateInstrumentationBatch(String user, String project, long batch, InputStream appApk, InputStream testApk) {
		String appUploadId = uploadFile(user, project, appApk).replace("\"", "");
		String testUploadId = uploadFile(user, project, testApk).replace("\"", "");

		Response response = this.batch.updateInstrumentationBatch(user, project, batch, new BatchResource.UpdateInstrumentationBatchRequest(appUploadId, testUploadId));
		Preconditions.checkState(Response.Status.NO_CONTENT.getStatusCode() == response.getStatus());
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

	public long runInstrumentationBatch(String user, String project, long batch) {
		return this.batch.runInstrumentationBatch(user, project, batch);
	}

	public BatchReport waitForBatchReport(final String user, final String project, final long batchReportId) {
		Callable<BatchReport> callable = new Callable<BatchReport>() {
		    public BatchReport call() throws Exception {
		    	BatchReport batchReport = TestObjectRemoteClient.this.batchReport.getReport(user, project, batchReportId);
		        return batchReport.isRunning() == false ? batchReport : null;
		    }
		};

		Retryer<BatchReport> retryer = RetryerBuilder.<BatchReport>newBuilder()
		        .retryIfResult(Predicates.<BatchReport>isNull())
		        .withWaitStrategy(WaitStrategies.exponentialWait(30, TimeUnit.MINUTES))
		        .build();
		try {
		    return retryer.call(callable);
		} catch (RetryException e) {
		    throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
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
