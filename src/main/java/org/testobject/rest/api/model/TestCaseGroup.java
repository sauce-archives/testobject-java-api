package org.testobject.rest.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseGroup {

	private final String className;
	private final List<TestCase> testCases;

	@JsonCreator
	public TestCaseGroup(
			@JsonProperty("className") String className,
			@JsonProperty("testCases") List<TestCase> testCases) {
		this.className = className;
		this.testCases = testCases;
	}

	public String getClassName() {
		return className;
	}

	public List<TestCase> getTestCases() {
		return testCases;
	}
}
