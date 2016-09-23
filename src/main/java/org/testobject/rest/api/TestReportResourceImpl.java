package org.testobject.rest.api;

import org.testobject.rest.api.model.TestReportWithDevice;
import org.testobject.rest.api.resource.TestReportResource;

import javax.ws.rs.PathParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class TestReportResourceImpl implements TestReportResource {

	private final WebTarget target;

	public TestReportResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public TestReportWithDevice getTestReport(@PathParam("user") String user, @PathParam("project") String project,
			@PathParam("reportId") long reportId) {
		return target
				.path("users").path(user)
				.path("projects").path(project)
				.path("reports").path(Long.toString(reportId))
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get(TestReportWithDevice.class);
	}
}
