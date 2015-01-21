package org.testobject.rest.api;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.WebResource;

public class TestSuiteReportResourceImpl implements TestSuiteReportResource {

	private final WebResource resource;

	public TestSuiteReportResourceImpl(WebResource resource) {
		this.resource = resource;
	}

	@Override
	public TestSuiteReport getReport(String user, String project, long batchReport, String mediatype) {
		return resource
				.path("users").path(user).path("projects").path(project).path("batchReports").path(Long.toString(batchReport))
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.get(TestSuiteReport.class);
	}

}
