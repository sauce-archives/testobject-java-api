package org.testobject.rest.api.model;

import java.util.Objects;

public class TestToRun {

	private final String testClass;
	private final String testMethod;

	public TestToRun(String testClass, String testMethod) {
		this.testClass = testClass;
		this.testMethod = testMethod;
	}

	public TestToRun(String testClass) {
		this.testClass = testClass;
		this.testMethod = null;
	}

	public String getTestClass() {
		return testClass;
	}

	public String getTestMethod() {
		return testMethod;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TestToRun testToRun = (TestToRun) o;
		return Objects.equals(testClass, testToRun.testClass) &&
				Objects.equals(testMethod, testToRun.testMethod);
	}

	@Override
	public int hashCode() {

		return Objects.hash(testClass, testMethod);
	}

	@Override
	public String toString() {
		return "TestToRun{" +
				"testClass='" + testClass + '\'' +
				", testMethod='" + testMethod + '\'' +
				'}';
	}
}
