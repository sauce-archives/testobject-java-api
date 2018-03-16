package org.testobject.rest.api.model;

public class TestCase {

	private final String methodName;
	private final String className;
	private final long startTime;
	private final long duration;
	private final String status;

	public TestCase(String className, String methodName, long startTime, long duration, String status) {
		this.className = className;
		this.methodName = methodName;
		this.startTime = startTime;
		this.duration = duration;
		this.status = status;
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

