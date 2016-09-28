package org.testobject.rest.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestReportWithDevice {
	private final String deviceContextId;
	private final AppiumTestReport report;

	@JsonCreator
	public TestReportWithDevice(@JsonProperty("deviceContextId") String deviceContextId, @JsonProperty("report") AppiumTestReport report) {
		this.deviceContextId = deviceContextId;
		this.report = report;
	}

	public String getDeviceContextId() {
		return deviceContextId;
	}

	public AppiumTestReport getReport() {
		return report;
	}
}
