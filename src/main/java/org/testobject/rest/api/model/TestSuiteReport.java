package org.testobject.rest.api.model;


import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuiteReport {

	public enum Status {
		SKIPPED(-2), UNKNOWN(-1), SUCCESS(0), WARNING(1), FAILURE(2);

		private final int level;

		private Status(int level) {
			this.level = level;
		}

		public boolean isSevere(Status other) {
			return other.level > this.level;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class DeviceView {

		private final String deviceId;
		private final String name;

		@JsonCreator
		public DeviceView(@JsonProperty("deviceId") String deviceId, @JsonProperty("name") String name) {
			this.deviceId = deviceId;
			this.name = name;
		}

		public String getDeviceId() {
			return deviceId;
		}

		public String getName() {
			return name;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class TestView {

		private final long testId;
		private final String name;

		@JsonCreator
		public TestView(@JsonProperty("testId") long testId, @JsonProperty("name") String name) {
			this.testId = testId;
			this.name = name;
		}

		public long getTestId() {
			return testId;
		}

		public String getName() {
			return name;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class ReportKey {

		private final long testId;
		private final String deviceId;

		@JsonCreator
		public ReportKey(@JsonProperty("testId") long testId, @JsonProperty("deviceId") String deviceId) {
			this.testId = testId;
			this.deviceId = deviceId;
		}

		public long getTestId() {
			return testId;
		}

		public String getDeviceId() {
			return deviceId;
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class ReportView {

		private final long reportId;
		private Status status;

		@JsonCreator
		public ReportView(@JsonProperty("reportId") long reportId, @JsonProperty("status") Status status) {
			this.reportId = reportId;
			this.status = status;
		}

		public long getReportId() {
			return reportId;
		}

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}

	}

	public static class ReportEntry {

		private final ReportKey key;
		private final ReportView view;

		@JsonCreator
		public ReportEntry(@JsonProperty("key") ReportKey key, @JsonProperty("view") ReportView view) {
			this.key = key;
			this.view = view;
		}

		public ReportKey getKey() {
			return key;
		}

		public ReportView getView() {
			return view;
		}
	}

	private final long id;

	private final long testSuiteId;
	private final String name;
	private final List<TestView> tests;
	private final List<DeviceView> devices;
	private final List<ReportEntry> reports;
	private final String networkSpeed;

	private final long startTime;

	private long duration = 0;
	private Status status;

	private boolean running;

	public TestSuiteReport(long id, long testSuiteId, String name, long startTime, long duration, Status status,
			List<TestView> tests, List<DeviceView> devices,
			List<ReportEntry> reports, String networkSpeed) {
		this.id = id;
		this.testSuiteId = testSuiteId;
		this.name = name;
		this.startTime = startTime;
		this.duration = duration;
		this.status = status;
		this.tests = tests;
		this.devices = devices;
		this.networkSpeed = networkSpeed;
		this.reports = reports;
	}

	@JsonCreator
	public TestSuiteReport(@JsonProperty("batchId") long testSuiteId, @JsonProperty("name") String name,
			@JsonProperty("startTime") long startTime, @JsonProperty("tests") List<TestView> tests,
			@JsonProperty("devices") List<DeviceView> devices, @JsonProperty("reports") List<ReportEntry> reports,
			@JsonProperty("networkSpeed") String networkSpeed, @JsonProperty("running") boolean running) {
		this(-1, testSuiteId, name, startTime, 0, Status.UNKNOWN, tests, devices, reports, networkSpeed);
		this.running = running;
	}

	public long getId() {
		return id;
	}

	public long getTestSuiteId() {
		return testSuiteId;
	}

	public String getName() {
		return name;
	}

	public List<TestView> getTests() {
		return tests;
	}

	public List<DeviceView> getDevices() {
		return devices;
	}

	public String getNetworkSpeed() {
		return networkSpeed;
	}

	public List<ReportEntry> getReports() {
		return reports;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getStartTime() {
		return startTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isRunning() {
		return running;
	}

}