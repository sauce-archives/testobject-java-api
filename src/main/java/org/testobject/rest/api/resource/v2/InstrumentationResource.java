package org.testobject.rest.api.resource.v2;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testobject.rest.api.model.DynamicInstrumentationRequestData;
import org.testobject.rest.api.model.InstrumentationReport;
import org.testobject.rest.api.model.StartInstrumentationResponse;
import org.testobject.rest.api.model.StaticInstrumentationRequestData;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

public class InstrumentationResource {

	private final WebTarget target;

	public InstrumentationResource(WebTarget target) {
		this.target = target;
	}

	public StartInstrumentationResponse createAndStartXCUITestInstrumentation(String apiKey, StaticInstrumentationRequestData requestData) {
		return target
				.path("v2")
				.path("instrumentation")
				.path("xcuitest")
				.request(APPLICATION_JSON)
				.header("Authorization", getApiKeyHeader(apiKey))
				.post(Entity.json(requestData), StartInstrumentationResponse.class);
	}

	public StartInstrumentationResponse createAndStartXCUITestInstrumentation(String apiKey,
			DynamicInstrumentationRequestData requestData) {
		return target
				.path("v2")
				.path("instrumentation")
				.path("xcuitest")
				.path("dynamic")
				.request(APPLICATION_JSON)
				.header("Authorization", getApiKeyHeader(apiKey))
				.post(Entity.json(requestData), StartInstrumentationResponse.class);
	}

	public StartInstrumentationResponse createAndStartAndroidInstrumentation(String apiKey, StaticInstrumentationRequestData requestData) {
		return target
				.path("v2")
				.path("instrumentation")
				.path("android")
				.request(APPLICATION_JSON)
				.header("Authorization", getApiKeyHeader(apiKey))
				.post(Entity.json(requestData), StartInstrumentationResponse.class);
	}

	public StartInstrumentationResponse createAndStartAndroidInstrumentation(String apiKey, DynamicInstrumentationRequestData requestData) {
		return target
				.path("v2")
				.path("instrumentation")
				.path("android")
				.path("dynamic")
				.request(APPLICATION_JSON)
				.header("Authorization", getApiKeyHeader(apiKey))
				.post(Entity.json(requestData), StartInstrumentationResponse.class);
	}

	public String getJUnitReport(String apiKey, long reportId) {
		return target
				.path("v2")
				.path("instrumentation")
				.path("testreport").path(Long.toString(reportId))
				.path("junitreport")
				.request(APPLICATION_XML)
				.header("Authorization", getApiKeyHeader(apiKey))
				.get(String.class);
	}

	public String getJUnitReport(String apiKey, List<Long> reportIds) {
		return target
				.path("v2")
				.path("instrumentation")
				.path("junitreport")
				.request(APPLICATION_XML)
				.header("Authorization", getApiKeyHeader(apiKey))
				.post(Entity.json(reportIds), String.class);
	}

	public InstrumentationReport getTestReport(String apiKey, long reportId) {
		return target
				.path("v2")
				.path("instrumentation")
				.path("testreport").path(Long.toString(reportId))
				.request(APPLICATION_JSON)
				.header("Authorization", getApiKeyHeader(apiKey))
				.get(InstrumentationReport.class);
	}

	private String getApiKeyHeader(String apiKey) {
		return "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());
	}

}
