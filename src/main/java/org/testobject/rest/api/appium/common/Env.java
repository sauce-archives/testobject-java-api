package org.testobject.rest.api.appium.common;

import java.util.Optional;

public class Env {

	public static final String TESTOBJECT_TEST_LOCALLY = "TESTOBJECT_TEST_LOCALLY";
	public static final String TESTOBJECT_API_ENDPOINT = "TESTOBJECT_API_ENDPOINT";
	public static final String TESTOBJECT_API_KEY = "TESTOBJECT_API_KEY";
	public static final String TESTOBJECT_SUITE_ID = "TESTOBJECT_SUITE_ID";
	public static final String TESTOBJECT_APP_ID = "TESTOBJECT_APP_ID";
	public static final String TESTOBJECT_DEVICE_IDS = "TESTOBJECT_DEVICE_IDS";
	public static final String TESTOBJECT_TIMEOUT = "TESTOBJECT_TIMEOUT";

	public static Optional<String> isTestLocally() {
		return getenv(TESTOBJECT_TEST_LOCALLY);
	}

	public static Optional<String> getApiEndpoint() {
		return getenv(TESTOBJECT_API_ENDPOINT);
	}

	public static Optional<String> getApiKey() {
		return getenv(TESTOBJECT_API_KEY);
	}

	public static Optional<String> getSuiteId() {
		return getenv(TESTOBJECT_SUITE_ID);
	}

	public static Optional<String> getAppId() {
		return getenv(TESTOBJECT_APP_ID);
	}

	public static Optional<String> getDevicesIds() {
		return getenv(TESTOBJECT_DEVICE_IDS);
	}

	public static Optional<String> getTimeout() {
		return getenv(TESTOBJECT_TIMEOUT);
	}


	private static Optional<String> getenv(String key){
		return Optional.ofNullable(System.getenv(key));
	}
}

