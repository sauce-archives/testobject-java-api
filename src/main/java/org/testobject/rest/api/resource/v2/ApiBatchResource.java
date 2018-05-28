package org.testobject.rest.api.resource.v2;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Base64.getEncoder;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

public class ApiBatchResource {

	private final WebTarget target;

	public ApiBatchResource(WebTarget target) {
		this.target = target;
	}

	public long startInstrumentationSuite(long testSuite, String apiKey) {

		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());

		return target
				.path("v2")
				.path("batches")
				.path("instrumentation")
				.path(Long.toString(testSuite))
				.path("replay")
				.request(APPLICATION_JSON)
				.header("Authorization", apiKeyHeader)
				.post(Entity.json(null), Long.class);
	}

	public void updateInstrumentationSuite(long testSuite, InstrumentationTestSuiteRequest request, String apiKey) {

		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());

		Response response = target
				.path("v2")
				.path("batches")
				.path("instrumentation")
				.path(Long.toString(testSuite))
				.request(APPLICATION_JSON)
				.header("Authorization", apiKeyHeader)
				.put(Entity.entity(request, APPLICATION_JSON));

		if (NO_CONTENT.getStatusCode() != response.getStatus()) {
			throw new IllegalStateException("expected status " + NO_CONTENT + " but was " + response.getStatus());
		}
	}

	public class InstrumentationTestSuiteRequest {

		@JsonProperty
		public String appUploadId;

		@JsonProperty
		public String testUploadId;

		@JsonProperty
		public List<String> methodsToRun;

		@JsonProperty
		public List<String> classesToRun;

		@JsonProperty
		public List<String> annotationsToRun;

		@JsonProperty
		public List<String> sizesToRun;

		@JsonProperty
		public String tunnelIdentifier;

		@JsonProperty
		public boolean runAsPackage;

		@SuppressWarnings("unused")
		private InstrumentationTestSuiteRequest() {
		}

		public InstrumentationTestSuiteRequest(String appUploadId, String testUploadId) {
			this.appUploadId = appUploadId;
			this.testUploadId = testUploadId;
		}

		public InstrumentationTestSuiteRequest(boolean runAsPackage) {
			this.runAsPackage = runAsPackage;

		}

	}

}
