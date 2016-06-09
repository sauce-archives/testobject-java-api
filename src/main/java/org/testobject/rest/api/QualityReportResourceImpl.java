package org.testobject.rest.api;

import org.testobject.rest.api.resource.QualityReportResource;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class QualityReportResourceImpl implements QualityReportResource {

	private final WebTarget target;

	public QualityReportResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public long startQualityReport(String user, String project) {
		return target
				.path("users").path(user).path("projects").path(project).path("qualityReports").path("run")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(null), Long.class);
	}

}
