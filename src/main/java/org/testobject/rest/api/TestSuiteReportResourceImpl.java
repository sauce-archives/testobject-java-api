package org.testobject.rest.api;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class TestSuiteReportResourceImpl implements TestSuiteReportResource {

	private final WebTarget target;

	public TestSuiteReportResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public TestSuiteReport getReport(String user, String project, long batchReport, String mediatype) {
		return target
				.path("users").path(user).path("projects").path(project).path("batchReports").path(Long.toString(batchReport))
				.request(MediaType.APPLICATION_JSON)
				.get(TestSuiteReport.class);
	}

    @Override
    public String getXMLReport(String user, String project, long batchReport) {
        return target
                .path("users").path(user).path("projects").path(project).path("batchReports").path(Long.toString(batchReport)).path("xml")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);
    }
}
