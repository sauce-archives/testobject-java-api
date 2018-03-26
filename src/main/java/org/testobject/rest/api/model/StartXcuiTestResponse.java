package org.testobject.rest.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StartXcuiTestResponse {

	private final long testReportId;
	private final String testReportUrl;

	public StartXcuiTestResponse(
			@JsonProperty("testReportId") long testReportId,
			@JsonProperty("testReportUrl") String testReportUrl
	) {
		this.testReportId = testReportId;
		this.testReportUrl = testReportUrl;
	}

	public long getTestReportId() {
		return testReportId;
	}

	public String getTestReportUrl() {
		return testReportUrl;
	}

}
