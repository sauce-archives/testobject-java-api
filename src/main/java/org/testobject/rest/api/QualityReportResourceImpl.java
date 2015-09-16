package org.testobject.rest.api;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class QualityReportResourceImpl implements QualityReportResource {

	private final WebResource resource;

	public QualityReportResourceImpl(WebResource resource) {
		this.resource = resource;
	}

	@Override
	public long startQualityReport(String user, String project) {
		return resource
				.path("users").path(user).path("projects").path(project).path("qualityReports").path("run")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(long.class);
	}

}
