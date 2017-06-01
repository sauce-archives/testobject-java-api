package org.testobject.rest.api.appium.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestReport {

	public static class Id extends org.testobject.rest.api.appium.common.data.Id<Integer> {
		public Id(Integer value) {
			super(value);
		}
	}

	private final Id id;
	private final Test test;

	@JsonCreator
	public TestReport(@JsonProperty("id") Id id, @JsonProperty("test") Test test) {
		this.id = id;
		this.test = test;
	}

	public Id getId() {
		return id;
	}

	public Test getTest() {
		return test;
	}

}
