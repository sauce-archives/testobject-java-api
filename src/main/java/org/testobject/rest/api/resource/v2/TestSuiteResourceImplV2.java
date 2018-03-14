package org.testobject.rest.api.resource.v2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TestSuiteResourceImplV2 implements TestSuiteResourceV2 {

	private final WebTarget target;

	public TestSuiteResourceImplV2(WebTarget target) {
		this.target = target;
	}

	@Override
	public void updateInstrumentationTestSuite(long testSuite, InstrumentationTestSuiteRequest request, String apikey) {

		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apikey).getBytes());

		Response response = target
				.path("v2").path("batches").path("instrumentation").path(Long.toString(testSuite))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue)
				.put(Entity.entity(request, MediaType.APPLICATION_JSON));

		if (Response.Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
			throw new IllegalStateException("expected status " + Response.Status.NO_CONTENT + " but was " + response.getStatus());
		}
	}

	@Override
	public long runInstrumentationTestSuite(long testSuite, String apikey) {

		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apikey).getBytes());

		return target
				.path("v2").path("batches").path("instrumentation").path(Long.toString(testSuite)).path("replay")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue)
				.post(Entity.json(null), Long.class);
	}

}
