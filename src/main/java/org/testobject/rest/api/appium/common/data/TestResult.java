package org.testobject.rest.api.appium.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResult {

	private final boolean passed;

	@JsonCreator
	public TestResult(@JsonProperty("passed") boolean passed) {
		this.passed = passed;
	}

	public boolean isPassed() {
		return passed;
	}

}
