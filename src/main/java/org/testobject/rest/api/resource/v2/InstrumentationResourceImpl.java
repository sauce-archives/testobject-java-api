package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.model.DynamicInstrumentationRequestData;
import org.testobject.rest.api.model.StaticInstrumentationRequestData;
import org.testobject.rest.api.model.StartInstrumentationResponse;
import org.testobject.rest.api.model.InstrumentationReport;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class InstrumentationResourceImpl implements InstrumentationResource {

	private final WebTarget target;

	public InstrumentationResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public StartInstrumentationResponse createAndStartXCUITestInstrumentation(String apiKey, StaticInstrumentationRequestData requestData) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());

		return target
				.path("v2").path("instrumentation").path("xcuitest")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue)
				.post(Entity.json(requestData), StartInstrumentationResponse.class);
	}

	@Override
	public StartInstrumentationResponse createAndStartXCUITestInstrumentation(String apiKey, DynamicInstrumentationRequestData requestData) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());

		return target
				.path("v2").path("instrumentation").path("xcuitest").path("dynamic")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue)
				.post(Entity.json(requestData), StartInstrumentationResponse.class);
	}

	@Override
	public StartInstrumentationResponse createAndStartAndroidInstrumentation(String apiKey, StaticInstrumentationRequestData requestData) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());

		return target
				.path("v2").path("instrumentation").path("android")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue)
				.post(Entity.json(requestData), StartInstrumentationResponse.class);
	}

	@Override
	public String getJUnitReport(String apiKey, long reportId) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());

		return target
				.path("v2").path("instrumentation").path("testreport").path(Long.toString(reportId)).path("junitreport")
				.request(MediaType.APPLICATION_XML)
				.header("Authorization", authorizationHeaderValue)
				.get(String.class);
	}

	@Override
	public InstrumentationReport getTestReport(String apiKey, long reportId) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());
		return target
				.path("v2").path("instrumentation").path("testreport").path(Long.toString(reportId))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue)
				.get(InstrumentationReport.class);
	}

}
