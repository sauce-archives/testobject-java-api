package org.testobject.rest.api.appium.common;

import java.net.MalformedURLException;
import java.net.URL;

public final class TestObjectCapabilities {

	public static final String TESTOBJECT_API_ENDPOINT = "https://appium.testobject.com:443/api";

	public static final URL TESTOBJECT_APPIUM_ENDPOINT = toAppiumEndpointURL(TESTOBJECT_API_ENDPOINT);

	public static final String TESTOBJECT_API_KEY = "testobject_api_key";
	public static final String TESTOBJECT_TEST_REPORT_ID = "testobject_test_report_id";

	public static final String TESTOBJECT_APP_ID = "testobject_app_id";
	public static final String TESTOBJECT_DEVICE = "testobject_device";
	public static final String TESTOBJECT_SUITE_NAME = "testobject_suite_name";
	public static final String TESTOBJECT_TEST_NAME = "testobject_test_name";
	public static final String TESTOBJECT_APPIUM_VERSION = "testobject_appium_version";

	private TestObjectCapabilities() {
	}

	public static URL toAppiumEndpointURL(String url) {
		try {
			return new URL(url + "/appium/wd/hub");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
