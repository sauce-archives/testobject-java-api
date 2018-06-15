package org.testobject.rest.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCase {

	private final String methodName;
	private final String className;
	private final long startTime;
	private final long duration;
	private final String status;
	private final TestError testError;

	@JsonCreator
	public TestCase(
			@JsonProperty("className") String className,
			@JsonProperty("methodName") String methodName,
			@JsonProperty("startTime") long startTime,
			@JsonProperty("duration") long duration,
			@JsonProperty("status") String status,
			@JsonProperty("testError") TestError testError) {
		this.className = className;
		this.methodName = methodName;
		this.startTime = startTime;
		this.duration = duration;
		this.status = status;
		this.testError = testError;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getDuration() {
		return duration;
	}

	public String getStatus() {
		return status;
	}

	public TestError getTestError() {
		return testError;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TestCase testCase = (TestCase) o;

		if (startTime != testCase.startTime)
			return false;
		if (duration != testCase.duration)
			return false;
		if (methodName != null ? !methodName.equals(testCase.methodName) : testCase.methodName != null)
			return false;
		if (className != null ? !className.equals(testCase.className) : testCase.className != null)
			return false;
		return status == testCase.status;
	}

	@Override
	public int hashCode() {
		int result = methodName != null ? methodName.hashCode() : 0;
		result = 31 * result + (className != null ? className.hashCode() : 0);
		result = 31 * result + (int) (startTime ^ (startTime >>> 32));
		result = 31 * result + (int) (duration ^ (duration >>> 32));
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}

}

