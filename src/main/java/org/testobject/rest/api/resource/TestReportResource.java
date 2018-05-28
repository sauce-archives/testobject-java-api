package org.testobject.rest.api.resource;

import org.testobject.rest.api.model.TestReportWithDevice;

import javax.ws.rs.client.WebTarget;

import static java.util.Base64.getEncoder;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class TestReportResource {

	private final WebTarget target;

	public TestReportResource(WebTarget target) {
		this.target = target;
	}

	public TestReportWithDevice getTestReport(String userId, String projectId, long reportId, String apiKey) {
		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());
		return target
				.path("users").path(userId)
				.path("projects").path(projectId)
				.path("reports").path(Long.toString(reportId))
				.request(APPLICATION_JSON_TYPE)
				.header("Authorization", apiKeyHeader)
				.get(TestReportWithDevice.class);
	}
}
