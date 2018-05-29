package org.testobject.rest.api.model;

import java.util.List;
import java.util.Map;

public class DynamicInstrumentationRequestData {
	private final String testName;
	private final String testSpecAppId;
	private final String appUnderTestAppId;
	private final String dataCenterId;
	private final DeviceNameQuery deviceNameQuery;
	private final String platformVersion;
	private final Boolean privateDevicesOnly;
	private final Boolean tabletOnly;
	private final Boolean phoneOnly;
	private final String tunnelIdentifier;
	private final Map<String, String> testOptions;
	private final List<String> testsToRun;

	public DynamicInstrumentationRequestData(String testName, String testSpecAppId, String appUnderTestAppId, String dataCenterId,
			DeviceNameQuery deviceNameQuery, String platformVersion, Boolean privateDevicesOnly,
			Boolean tabletOnly, Boolean phoneOnly, String tunnelIdentifier,
			Map<String, String> testOptions, List<String> testsToRun) {
		this.testName = testName;
		this.testSpecAppId = testSpecAppId;
		this.appUnderTestAppId = appUnderTestAppId;
		this.dataCenterId = dataCenterId;
		this.deviceNameQuery = deviceNameQuery;
		this.platformVersion = platformVersion;
		this.privateDevicesOnly = privateDevicesOnly;
		this.tabletOnly = tabletOnly;
		this.phoneOnly = phoneOnly;

		this.tunnelIdentifier = tunnelIdentifier;
		this.testOptions = testOptions;
		this.testsToRun = testsToRun;
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

	public DeviceNameQuery getDeviceNameQuery() {
		return deviceNameQuery;
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

	public String getTunnelIdentifier() {
		return tunnelIdentifier;
	}

	public String getTestName() {

		return testName;
	}

	public Map<String, String> getTestOptions() {
		return testOptions;
	}

	public List<String> getTestsToRun() {
		return testsToRun;
	}
}
