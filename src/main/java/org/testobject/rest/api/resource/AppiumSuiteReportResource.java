package org.testobject.rest.api.resource;

import org.testobject.rest.api.RestClient;
import org.testobject.rest.api.appium.common.data.SuiteReport;
import org.testobject.rest.api.appium.common.data.Test;
import org.testobject.rest.api.appium.common.data.TestReport;
import org.testobject.rest.api.appium.common.data.TestResult;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.Set;

public class AppiumSuiteReportResource {

	private final RestClient client;

	public AppiumSuiteReportResource(RestClient client) {
		this.client = client;
	}

	public SuiteReport startSuiteReport(long suiteId, Optional<String> appId, Set<Test> tests) {
		WebTarget target = client
				.path("suites").path(Long.toString(suiteId))
				.path("reports")
				.path("start");
		if (appId.isPresent()) {
			target = target.queryParam("appId", appId.get());
		}

		return target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(tests), SuiteReport.class);
	}

	public SuiteReport finishSuiteReport(long suiteId, SuiteReport.Id suiteReportId) {
		return client
				.path("suites").path(Long.toString(suiteId))
				.path("reports").path(Long.toString(suiteReportId.value()))
				.path("finish")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.json("ignored"), SuiteReport.class);
	}

	public TestReport finishTestReport(long suiteId, SuiteReport.Id suiteReportId, TestReport.Id testReportId, TestResult testResult) {
		return client
				.path("suites").path(Long.toString(suiteId))
				.path("reports").path(Long.toString(suiteReportId.value()))
				.path("results").path(Integer.toString(testReportId.value()))
				.path("finish")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.json(testResult), TestReport.class);
	}

	private class SuiteResult {
		public final boolean passed;

		private SuiteResult(boolean passed) {
			this.passed = passed;
		}
	}

}
