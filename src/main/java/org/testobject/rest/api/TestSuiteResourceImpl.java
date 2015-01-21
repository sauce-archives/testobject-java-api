package org.testobject.rest.api;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TestSuiteResourceImpl implements TestSuiteResource {

	private final WebResource resource;

	public TestSuiteResourceImpl(WebResource resource) {
		this.resource = resource;
	}

	@Override
	public void updateInstrumentationTestSuite(String user, String project, long testSuite,
			UpdateInstrumentationTestSuiteRequest request) {
		ClientResponse response = resource
				.path("users").path(user).path("projects").path(project).path("batches").path("instrumentation").path(Long.toString(testSuite))
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, request);
		
		if(Response.Status.NO_CONTENT.getStatusCode() != response.getStatus()){
			throw new IllegalStateException("expected status " + Response.Status.NO_CONTENT + " but was " + response.getStatus());
		}
	}

	@Override
	public long runInstrumentationTestSuite(String user, String project, long testSuite) {
		return resource
				.path("users").path(user).path("projects").path(project).path("batches").path("instrumentation").path(Long.toString(testSuite)).path("replay")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(long.class);
	}

}
