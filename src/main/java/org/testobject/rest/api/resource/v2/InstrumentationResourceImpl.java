package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.model.InstrumentationRequestData;
import org.testobject.rest.api.model.XcuiTestReport;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class InstrumentationResourceImpl implements InstrumentationResource {

	private final WebTarget target;

	public InstrumentationResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public long createAndStartXCUITestInstrumentation(String userName, String apiKey, InstrumentationRequestData requestData) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString((userName + ":" + apiKey).getBytes());

		return target
				.path("v2").path("instrumentation").path("xcuitest")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue)
				.post(Entity.json(requestData), Long.class);
	}

	@Override
	public String getJUnitReport(String userName, String apiKey, long reportId) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString((userName + ":" + apiKey).getBytes());

		return target
				.path("v2").path("instrumentation").path("testreport").path(Long.toString(reportId)).path("junitreport")
				.request(MediaType.APPLICATION_XML)
				.header("Authorization", authorizationHeaderValue)
				.get(String.class);
	}

	@Override
	public XcuiTestReport getTestReport(String userName, String apiKey, long reportId) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString((userName + ":" + apiKey).getBytes());
		return target
				.path("v2").path("instrumentation").path("testreport").path(Long.toString(reportId))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue)
				.get(XcuiTestReport.class);
	}

}
