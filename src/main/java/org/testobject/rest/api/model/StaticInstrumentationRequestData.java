package org.testobject.rest.api.model;

import java.util.Map;

public class StaticInstrumentationRequestData {

	private final String testName;
	private final String testSpecAppId;
	private final String appUnderTestAppId;
	private final String dataCenterId;
	private final String deviceName;
	private final String tunnelIdentifier;
	private final Map<String, String> testOptions;

	public StaticInstrumentationRequestData(String testName, String testSpecAppId, String appUnderTestAppId, String dataCenterId,
											String deviceName, String tunnelIdentifier, Map<String, String> testOptions) {
		this.testName = testName;
		this.testSpecAppId = testSpecAppId;
		this.appUnderTestAppId = appUnderTestAppId;
		this.dataCenterId = dataCenterId;
		this.deviceName = deviceName;

		this.tunnelIdentifier = tunnelIdentifier;
		this.testOptions = testOptions;
	}

	public String getTestSpecAppId() {
		return testSpecAppId;
	}

	public String getAppUnderTestAppId() {
		return appUnderTestAppId;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getTunnelIdentifier() {
		return tunnelIdentifier;
	}

	public String getTestName() {

		return testName;
	}

	public Map<String, String> getTestOptions() {
		return testOptions;
	}
}
