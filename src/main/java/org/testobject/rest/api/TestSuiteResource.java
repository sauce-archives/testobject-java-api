package org.testobject.rest.api;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("users/{user}/projects/{project}/batches")
public interface TestSuiteResource {

	class InstrumentationTestSuiteRequest {

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

	@PUT
	@Path("instrumentation/{testSuite}")
	public void updateInstrumentationTestSuite(@PathParam("user") String user, @PathParam("project") String project,
			@PathParam("testSuite") long testSuite,
			InstrumentationTestSuiteRequest request);

	@POST
	@Path("instrumentation/{testSuite}/replay")
	public long runInstrumentationTestSuite(@PathParam("user") String user, @PathParam("project") String project,
			@PathParam("testSuite") long testSuite);

	@PUT
	@Path("instrumentation/newSuite/{testSuite}")
	public Long createInstrumentationTestSuite(@PathParam("user") String user, @PathParam("project") String project,
			@PathParam("testSuite") long testSuite,
			InstrumentationTestSuiteRequest request);
}
