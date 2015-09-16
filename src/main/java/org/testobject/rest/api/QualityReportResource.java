package org.testobject.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("users/{user}/projects/{project}/qualityReports")
public interface QualityReportResource {

	@POST
	@Path("start")
	long startQualityReport(@PathParam("user") String user, @PathParam("project") String project);

}
