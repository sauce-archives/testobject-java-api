package org.testobject.rest.api.appium.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SuiteReport {

	public static class Id extends org.testobject.rest.api.appium.common.data.Id<Long> {
		public Id(Long value) {
			super(value);
		}
	}

	private final Id id;
	private final Set<TestReport> testReports;

	@JsonCreator
	public SuiteReport(@JsonProperty("id") Id id, @JsonProperty("testReports") Set<TestReport> testReports) {
		this.id = id;
		this.testReports = testReports;
	}

	public Id getId() {
		return id;
	}

	public Optional<TestReport.Id> getTestReportId(Test test) {
		for (TestReport testReport : testReports) {
			if (testReport.getTest().equals(test)) {
				return Optional.of(testReport.getId());
			}
		}

		return Optional.empty();
	}

	public Optional<String> getTestDeviceId(Test test) {
		for (TestReport testReport : testReports) {
			if (testReport.getTest().equals(test)) {
				return Optional.of(testReport.getTest().getDeviceId());
			}
		}

		return Optional.empty();
	}

}
