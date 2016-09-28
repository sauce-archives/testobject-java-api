package org.testobject.rest.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppiumTestReport {

	public static class ProjectPath {
		private final String first;
		private final String second;
		@JsonCreator
		public ProjectPath(@JsonProperty("first") String first, @JsonProperty("second") String second) {
			this.first = first;
			this.second = second;
		}

		public String getFirst() {
			return first;
		}

		public String getSecond() {
			return second;
		}
	}

	@JsonCreator
	public AppiumTestReport(
			@JsonProperty("projectPath") ProjectPath projectPath,
			@JsonProperty("id") long id,
			@JsonProperty("batchName") String batchName,
			@JsonProperty("testName") String testName,
			@JsonProperty("appVersionId") int appVersionId,
			@JsonProperty("appVersionName") String appVersionName,
			@JsonProperty("startTime") long startTime,
			@JsonProperty("duration") int duration,
			@JsonProperty("deviceDescriptor") DeviceDescriptor.DeviceContainer device,
			@JsonProperty("videoId") String videoId,
			@JsonProperty("running") boolean isRunning,
			@JsonProperty("status") String status) {
		this.projectPath = projectPath;
		this.id = id;
		this.batchName = batchName;
		this.testName = testName;
		this.appVersionId = appVersionId;
		this.appVersionName = appVersionName;
		this.startTime = startTime;
		this.duration = duration;
		this.device = device;
		this.videoId = videoId;
		this.isRunning = isRunning;
		this.status = status;
	}

	private final ProjectPath projectPath;
	private final long id;
	private final String batchName;
	private final String testName;
	private final int appVersionId;
	private final String appVersionName;
	private final long startTime;
	private final int duration;
	private final DeviceDescriptor.DeviceContainer device;
	private final String videoId;
	private final boolean isRunning;
	private final String status;

	public ProjectPath getProjectPath() {
		return projectPath;
	}

	public long getId() {
		return id;
	}

	public String getBatchName() {
		return batchName;
	}

	public String getTestName() {
		return testName;
	}

	public int getAppVersionId() {
		return appVersionId;
	}

	public String getAppVersionName() {
		return appVersionName;
	}

	public long getStartTime() {
		return startTime;
	}

	public int getDuration() {
		return duration;
	}

	public DeviceDescriptor.DeviceContainer getDevice() {
		return device;
	}

	public String getVideoId() {
		return videoId;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public String getStatus() {
		return status;
	}
}
