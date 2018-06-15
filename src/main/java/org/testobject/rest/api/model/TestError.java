package org.testobject.rest.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestError {

	private final String file;
	private final Integer lineNumber;
	private final String error;

	@JsonCreator
	public TestError(@JsonProperty("file") String file, @JsonProperty("lineNumber") Integer lineNumber,
			@JsonProperty("error") String error) {
		this.file = file;
		this.lineNumber = lineNumber;
		this.error = error;
	}

	public TestError(String error) {
		this(null, null, error);
	}

	public String getFile() {
		return file;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public String getError() {
		return error;
	}
}
