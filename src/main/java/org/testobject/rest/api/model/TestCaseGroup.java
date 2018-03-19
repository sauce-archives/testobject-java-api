package org.testobject.rest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TestCaseGroup {

	public final String className;
	public final List<TestCase> testCases;

	public TestCaseGroup(
			@JsonProperty("className") String className,
			@JsonProperty("testCases") List<TestCase> testCases) {
		this.className = className;
		this.testCases = testCases;
	}

}
