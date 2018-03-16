package org.testobject.rest.api.model;

public class InstrumentationRequestData {

	private final String testName;
	private final String testSpecAppId;
	private final String appUnderTestAppId;
	private final String dataCenterId;
	private final String deviceName;
	private final String tunnelIdentifier;

	public InstrumentationRequestData(String testName, String testSpecAppId, String appUnderTestAppId, String dataCenterId,
			String deviceName, String tunnelIdentifier) {
		this.testName = testName;
		this.testSpecAppId = testSpecAppId;
		this.appUnderTestAppId = appUnderTestAppId;
		this.dataCenterId = dataCenterId;
		this.deviceName = deviceName;
		this.tunnelIdentifier = tunnelIdentifier;
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
}
