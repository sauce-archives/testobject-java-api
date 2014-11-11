package org.testobject.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Produces({"application/json"})
@Consumes({"application/json"})
@Path("users/{user}/projects/{project}/batchReports")
public interface TestSuiteReportResource {
	
	@GET
	@Produces({"application/json"})
	@Consumes({"application/json"})
	@Path("{testSuiteReport}")
	TestSuiteReport getReport(@PathParam("user") String user, @PathParam("project") String project, @PathParam("testSuiteReport") long batchReport, @HeaderParam("Accept") String mediatype);

}
