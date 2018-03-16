package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.model.TestSuiteReport;
import org.testobject.rest.api.resource.v2.TestSuiteReportResourceV2;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class TestSuiteReportResourceImplV2 implements TestSuiteReportResourceV2 {

	private final WebTarget target;

	public TestSuiteReportResourceImplV2(WebTarget target) {
		this.target = target;
	}

	@Override
	public TestSuiteReport getReport(long batchReport, String mediatype, String apiKey) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString((":" + apiKey).getBytes());

		return target
				.path("v2").path("batchReports").path(Long.toString(batchReport))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", authorizationHeaderValue)
				.get(TestSuiteReport.class);
	}

	@Override
	public String getXMLReport(long batchReport, String apiKey) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString((":" + apiKey).getBytes());

		return target
				.path("v2").path("batchReports").path(Long.toString(batchReport)).path("xml")
				.request(MediaType.APPLICATION_XML)
				.header("Authorization", authorizationHeaderValue)
				.get(String.class);
	}
}
