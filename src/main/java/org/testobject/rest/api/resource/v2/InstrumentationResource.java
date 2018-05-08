package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.model.DynamicInstrumentationRequestData;
import org.testobject.rest.api.model.StaticInstrumentationRequestData;
import org.testobject.rest.api.model.StartInstrumentationResponse;
import org.testobject.rest.api.model.InstrumentationReport;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("v2/instrumentation")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface InstrumentationResource {

	@POST
	@Path("xcuitest")
	StartInstrumentationResponse createAndStartXCUITestInstrumentation(@HeaderParam("Authorization") String apiKey,
			StaticInstrumentationRequestData requestData);

	@POST
	@Path("xcuitest/dynamic")
	StartInstrumentationResponse createAndStartXCUITestInstrumentation(String apiKey, DynamicInstrumentationRequestData requestData);

	@POST
	@Path("android")
	StartInstrumentationResponse createAndStartAndroidInstrumentation(@HeaderParam("Authorization") String apiKey,
			StaticInstrumentationRequestData requestData);

	@GET
	@Path("{testReportId}/junitreport")
	@Produces(MediaType.APPLICATION_XML)
	String getJUnitReport(@HeaderParam("Authorization") String authorizationHeader,
			@PathParam("testReportId") long reportId);

	@GET
	@Path("testreport/{testReportId}")
	InstrumentationReport getTestReport(@HeaderParam("Authorization") String authorizationHeader,
			@PathParam("testReportId") long reportId);
}
