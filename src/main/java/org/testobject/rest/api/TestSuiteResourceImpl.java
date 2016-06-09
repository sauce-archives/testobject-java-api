package org.testobject.rest.api;

import org.testobject.rest.api.resource.TestSuiteResource;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TestSuiteResourceImpl implements TestSuiteResource {

	private final WebTarget target;

	public TestSuiteResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public void updateInstrumentationTestSuite(String user, String project, long testSuite,
			InstrumentationTestSuiteRequest request) {
		Response response = target
				.path("users").path(user).path("projects").path(project).path("batches").path("instrumentation").path(Long.toString(testSuite))
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(request,MediaType.APPLICATION_JSON));

		if(Response.Status.NO_CONTENT.getStatusCode() != response.getStatus()){
			throw new IllegalStateException("expected status " + Response.Status.NO_CONTENT + " but was " + response.getStatus());
		}
	}

    @Override
    public Long createInstrumentationTestSuite(String user, String project, long testSuite,
                                               InstrumentationTestSuiteRequest request) {
        request.toString();
        Response response = target
                .path("users").path(user).path("projects").path(project).path("batches").path("instrumentation").path("newSuite").path(Long.toString(testSuite))
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(request,MediaType.APPLICATION_JSON));

        if(Response.Status.OK.getStatusCode() != response.getStatus()){
            throw new IllegalStateException("expected status " + Response.Status.OK + " but was " + response.getStatus());
        }
        return  new Long(response.readEntity(String.class));
    }

	@Override
	public long runInstrumentationTestSuite(String user, String project, long testSuite) {
		return target
				.path("users").path(user).path("projects").path(project).path("batches").path("instrumentation").path(Long.toString(testSuite)).path("replay")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(null),Long.class);
	}

}
