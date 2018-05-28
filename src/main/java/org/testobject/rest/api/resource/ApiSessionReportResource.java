package org.testobject.rest.api.resource;

import org.testobject.rest.api.model.PaginationObject;
import org.testobject.rest.api.model.SessionReport;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import static java.util.Base64.getEncoder;

public class ApiSessionReportResource {

	private final WebTarget target;

	public ApiSessionReportResource(WebTarget target) {
		this.target = target;
	}

	public PaginationObject<SessionReport> getSessionReports(String userId, long offset, int limit, int lastDays, String apiKey) {
		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());

		return target
				.path("v1")
				.path("devices")
				.path("reports")
				.queryParam("userId", userId)
				.queryParam("offset", offset)
				.queryParam("limit", limit)
				.queryParam("lastDays", lastDays)
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", apiKeyHeader)
				.get(new GenericType<PaginationObject<SessionReport>>() {
				});
	}

}
