package org.testobject.rest.api.model;

import java.util.Map;

public class InstrumentationRequestData {

	private final String testName;
	private final String testSpecAppId;
	private final String appUnderTestAppId;
	private final String dataCenterId;
	private final String deviceName;
	private final String platformVersion;
	private final Boolean privateDevicesOnly;
	private final Boolean tabletOnly;
	private final Boolean phoneOnly;
	private final String tunnelIdentifier;
	private final Map<String, String> testOptions;

	public InstrumentationRequestData(String testName, String testSpecAppId, String appUnderTestAppId, String dataCenterId,
			String deviceName, String platformVersion, Boolean privateDevicesOnly, Boolean tabletOnly, Boolean phoneOnly,
			String tunnelIdentifier, Map<String, String> testOptions) {
		this.testName = testName;
		this.testSpecAppId = testSpecAppId;
		this.appUnderTestAppId = appUnderTestAppId;
		this.dataCenterId = dataCenterId;
		this.deviceName = deviceName;
		this.platformVersion = platformVersion;
		this.privateDevicesOnly = privateDevicesOnly;
		this.tabletOnly = tabletOnly;
		this.phoneOnly = phoneOnly;

		this.tunnelIdentifier = tunnelIdentifier;
		this.testOptions = testOptions;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public Boolean getPrivateDevicesOnly() {
		return privateDevicesOnly;
	}

	public Boolean getTabletOnly() {
		return tabletOnly;
	}

	public Boolean getPhoneOnly() {
		return phoneOnly;
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
