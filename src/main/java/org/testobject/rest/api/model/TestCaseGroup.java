package org.testobject.rest.api.model;

import java.util.List;

public class TestCaseGroup {

	public final String className;
	public final List<TestCase> testCases;

	public TestCaseGroup(String className, List<TestCase> testCases) {
		this.className = className;
		this.testCases = testCases;
	}

}
