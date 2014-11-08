package org.testobject.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("users/{user}/projects/{project}/batches")
public interface BatchResource {

	@XmlRootElement
	public class UpdateInstrumentationBatchRequest {

		@XmlElement
		public final String appUploadId;
		
		@XmlElement
		public final String testUploadId;

		public UpdateInstrumentationBatchRequest(String appUploadId, String testUploadId) {
			this.appUploadId = appUploadId;
			this.testUploadId = testUploadId;
		}

	}

	@PUT
	@Path("instrumentation/{batch}")
	public Response updateInstrumentationBatch(@PathParam("user") String user, @PathParam("project") String project,
			@PathParam("batch") long batch,
			UpdateInstrumentationBatchRequest request);

	@POST
	@Path("instrumentation/{batch}/replay")
	public long runInstrumentationBatch(@PathParam("user") String user, @PathParam("project") String project, @PathParam("batch") long batch);

}
