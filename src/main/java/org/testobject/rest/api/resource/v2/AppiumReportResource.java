package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.appium.common.data.SuiteReport;
import org.testobject.rest.api.appium.common.data.Test;
import org.testobject.rest.api.appium.common.data.TestReport;
import org.testobject.rest.api.appium.common.data.TestResult;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.util.Optional;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class AppiumReportResource {

	private final WebTarget target;

	public AppiumReportResource(WebTarget target) {
		this.target = target;
	}

	/**
	 * Start a new suite execution including its test executions
	 */
	public SuiteReport startAppiumSuite(long suiteId, Optional<String> appId, Set<Test> tests) {
		WebTarget target = this.target
				.path("v2")
				.path("appium")
				.path("suites").path(Long.toString(suiteId))
				.path("reports")
				.path("start");

		if (appId.isPresent()) {
			target = target.queryParam("appId", appId.get());
		}

		return target
				.request(APPLICATION_JSON_TYPE)
				.post(Entity.json(tests), SuiteReport.class);
	}

	/**
	 * Marks all test executions contained in the specified suite execution as finished
	 */
	public SuiteReport finishAppiumSuite(long suiteId, SuiteReport.Id suiteReportId) {
		return target
				.path("v2")
				.path("appium")
				.path("suites").path(Long.toString(suiteId))
				.path("reports").path(Long.toString(suiteReportId.value()))
				.path("finish")
				.request(APPLICATION_JSON_TYPE)
				.put(Entity.json("ignored"), SuiteReport.class);
	}

	/**
	 * Sets the status of the specific test execution and marks it as finished
	 */
	public TestReport finishAppiumTestReport(long suiteId, SuiteReport.Id batchReportId, TestReport.Id testReportId,
			TestResult testResult) {
		return target
				.path("v2")
				.path("appium")
				.path("suites").path(Long.toString(suiteId))
				.path("reports").path(Long.toString(batchReportId.value()))
				.path("results").path(Integer.toString(testReportId.value()))
				.path("finish")
				.request(APPLICATION_JSON_TYPE)
				.put(Entity.json(testResult), TestReport.class);
	}

}
