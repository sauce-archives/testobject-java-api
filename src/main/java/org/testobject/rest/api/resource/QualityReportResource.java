package org.testobject.rest.api.resource;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import static java.util.Base64.getEncoder;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class QualityReportResource {

	private final WebTarget target;

	public QualityReportResource(WebTarget target) {
		this.target = target;
	}

	public long startQualityReport(String userId, String projectId, String apiKey) {
		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());

		return target
				.path("users").path(userId)
				.path("projects").path(projectId)
				.path("qualityReports")
				.path("run")
				.request(APPLICATION_JSON)
				.header("Authorization", apiKeyHeader)
				.post(Entity.json(null), Long.class);
	}

}
