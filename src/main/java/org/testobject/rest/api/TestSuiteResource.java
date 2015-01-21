package org.testobject.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("users/{user}/projects/{project}/batches")
public interface TestSuiteResource {

	@XmlRootElement
	public class UpdateInstrumentationTestSuiteRequest {

		@XmlElement
		public String appUploadId;
		
		@XmlElement
		public String testUploadId;
		
		@SuppressWarnings("unused")
		private UpdateInstrumentationTestSuiteRequest(){ }

		public UpdateInstrumentationTestSuiteRequest(String appUploadId, String testUploadId) {
			this.appUploadId = appUploadId;
			this.testUploadId = testUploadId;
		}

	}

	@PUT
	@Path("instrumentation/{testSuite}")
	public void updateInstrumentationTestSuite(@PathParam("user") String user, @PathParam("project") String project,
			@PathParam("testSuite") long testSuite,
			UpdateInstrumentationTestSuiteRequest request);

	@POST
	@Path("instrumentation/{testSuite}/replay")
	public long runInstrumentationTestSuite(@PathParam("user") String user, @PathParam("project") String project, @PathParam("testSuite") long testSuite);

}
