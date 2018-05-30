package org.testobject.rest.api.appium.common;

import java.net.MalformedURLException;
import java.net.URL;

public final class TestObjectCapabilities {

	public static final URL TESTOBJECT_API_ENDPOINT = getURL("https://app.testobject.com/api");
	public static final URL TESTOBJECT_APPIUM_ENDPOINT_US = getURL("https://us1.appium.testobject.com/wd/hub");
	public static final URL TESTOBJECT_APPIUM_ENDPOINT_EU = getURL("https://eu1.appium.testobject.com/wd/hub");

	/**
	 *
	 * @deprecated Devices are now available in seperate US and EU data centers.
	 *   Please use either TESTOBJECT_APPIUM_ENDPOINT_US or TESTOBJECT_APPIUM_ENDPOINT_EU accordingly.
	 */
	@Deprecated
	public static final URL TESTOBJECT_APPIUM_ENDPOINT = TESTOBJECT_APPIUM_ENDPOINT_EU;

	public static final String TESTOBJECT_API_KEY = "testobject_api_key";
	public static final String TESTOBJECT_TEST_REPORT_ID = "testobject_test_report_id";
	public static final String TESTOBJECT_TEST_REPORT_URL = "testobject_test_report_url";
	public static final String TESTOBJECT_TEST_LIVE_VIEW_URL = "testobject_test_live_view_url";
	public static final String TESTOBJECT_FRAMEWORK_APP_ID = "testobject_framework_app_id";
	public static final String TESTOBJECT_APP_ID = "testobject_app_id";
	public static final String TESTOBJECT_DEVICE = "testobject_device";
	public static final String TESTOBJECT_DEVICE_SESSION_ID = "testobject_device_session_id";
	public static final String TESTOBJECT_SUITE_NAME = "testobject_suite_name";
	public static final String TESTOBJECT_TEST_NAME = "testobject_test_name";
	public static final String TESTOBJECT_APPIUM_VERSION = "testobject_appium_version";
	public static final String TESTOBJECT_DISABLE_VALIDATION = "testobject_disable_validation";
	public static final String TESTOBJECT_NO_RESET = "testobject_no_reset";
	public static final String TESTOBJECT_CACHE_DEVICE = "testobject_cache_device";
	public static final String TESTOBJECT_REINSTALL_APP = "testobject_reinstall_app";
	public static final String TESTOBJECT_REINSTALL_FRAMEWORK_APP = "testobject_reinstall_framework_app";
	public static final String TESTOBJECT_SESSION_CREATION_RETRY = "testobject_session_creation_retry";
	public static final String TESTOBJECT_SESSION_CREATION_TIMEOUT = "testobject_session_creation_timeout";
	public static final String TESTOBJECT_DEVICE_ALLOCATION_SLEEP_TIME = "testobject_device_session_sleep_time";
	public static final String TESTOBJECT_USER_ID = "testobject_user_id";
	public static final String TESTOBJECT_PROJECT_ID = "testobject_project_id";



	private TestObjectCapabilities() {
	}

	private static URL getURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
