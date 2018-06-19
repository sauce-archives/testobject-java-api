package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.model.AppiumTestReport;

import javax.ws.rs.client.WebTarget;

import static java.util.Base64.getEncoder;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class ApiTestReportResource {

	private final WebTarget target;

	public ApiTestReportResource(WebTarget target) {
		this.target = target;
	}

	public AppiumTestReport getTestReport(long reportId, String apiKey) {
		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());
		return target
				.path("v2")
				.path("reports").path(Long.toString(reportId))
				.request(APPLICATION_JSON_TYPE)
				.header("Authorization", apiKeyHeader)
				.get(AppiumTestReport.class);
	}
}
