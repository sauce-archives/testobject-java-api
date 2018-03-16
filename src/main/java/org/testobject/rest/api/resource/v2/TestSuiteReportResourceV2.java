package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.model.TestSuiteReport;

import javax.ws.rs.*;

@Produces({"application/json"})
@Consumes({"application/json"})
@Path("v2/batchReports")
public interface TestSuiteReportResourceV2 {

	@GET
	@Produces({"application/json"})
	@Consumes({"application/json"})
	@Path("{testSuiteReport}")
	TestSuiteReport getReport(@PathParam("testSuiteReport") long batchReport, @HeaderParam("Accept") String mediatype, String apiKey);

    String getXMLReport(@PathParam("testSuiteReport") long batchReport, String apiKey);

}
