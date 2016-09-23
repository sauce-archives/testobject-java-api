package org.testobject.rest.api.resource;

import org.testobject.rest.api.model.TestReportWithDevice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("users/{user}/projects/{project}/reports")
public interface TestReportResource {
	@GET
	TestReportWithDevice getTestReport(@PathParam("user") String user, @PathParam("project") String project,
			@PathParam("reportId") long reportId);
}
