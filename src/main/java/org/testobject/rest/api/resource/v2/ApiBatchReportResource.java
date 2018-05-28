package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.model.TestSuiteReport;

import javax.ws.rs.client.WebTarget;

import static java.util.Base64.getEncoder;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

public class ApiBatchReportResource {

	private final WebTarget target;

	public ApiBatchReportResource(WebTarget target) {
		this.target = target;
	}

	public TestSuiteReport getReport(long suiteReportId, String apiKey) {
		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());

		return target
				.path("v2")
				.path("batchReports").path(Long.toString(suiteReportId))
				.request(APPLICATION_JSON)
				.header("Authorization", apiKeyHeader)
				.get(TestSuiteReport.class);
	}

	public String getXMLReport(long suiteReportId, String apiKey) {
		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());

		return target
				.path("v2")
				.path("batchReports").path(Long.toString(suiteReportId))
				.path("xml")
				.request(APPLICATION_XML)
				.header("Authorization", apiKeyHeader)
				.get(String.class);
	}
}
