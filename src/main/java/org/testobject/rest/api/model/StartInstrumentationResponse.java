package org.testobject.rest.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StartInstrumentationResponse {

	private final long testReportId;
	private final String testReportUrl;

	@JsonCreator
	public StartInstrumentationResponse(
			@JsonProperty("testReportId") long testReportId,
			@JsonProperty("testReportURL") String testReportURL
	) {
		this.testReportId = testReportId;
		this.testReportUrl = testReportURL;
	}

	public long getTestReportId() {
		return testReportId;
	}

	public String getTestReportUrl() {
		return testReportUrl;
	}

}
