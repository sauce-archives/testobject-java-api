package org.testobject.api;

import org.testobject.rest.api.model.*;
import org.testobject.rest.api.resource.TestSuiteResource;

import java.io.Closeable;
import java.io.File;
import java.util.List;

public interface TestObjectClient extends Closeable {

	final class ProxySettings {

		private final String host;

		private final int port;
		private final String username;
		private final String password;

		public ProxySettings(String host, int port, String username, String password) {
			this.host = host;
			this.port = port;
			this.username = username;
			this.password = password;
		}

		public String getHost() {
			return host;
		}

		public int getPort() {
			return port;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

	}

	final class Factory {

		private static final String BASE_URL = "https://appium.testobject.com/api/rest";

		public static TestObjectClient create() {
			return create(BASE_URL, null);
		}

		public static TestObjectClient create(String baseUrl) {
			return create(baseUrl, null);
		}

		public static TestObjectClient create(String baseUrl, ProxySettings proxySettings) {
			return new TestObjectRemoteClient(baseUrl, proxySettings);
		}

	}

	void updateInstrumentationTestSuite(String user, String project, long testSuite, File appApk, File instrumentationAPK,
			TestSuiteResource.InstrumentationTestSuiteRequest request);

	long startInstrumentationTestSuite(String user, String project, long testSuite);

	Long createInstrumentationTestSuite(String user, String project, long testSuite, File appApk, File testApk,
			TestSuiteResource.InstrumentationTestSuiteRequest instrumentationTestSuiteRequest);

	TestSuiteReport waitForSuiteReport(String user, String project, long testSuiteReport);

	TestSuiteReport waitForSuiteReport(final String user, final String project, final long testSuiteReportId, long waitTimeoutMs,
			long sleepTimeMs);

	String readTestSuiteXMLReport(final String user, final String project, final long testSuiteReportId);

	void createAppVersion(String user, String project, File appApk);

	long startQualityReport(String user, String project);

	PaginationObject<SessionReport> getSessionReport(String user);

	PaginationObject<SessionReport> getSessionReport(String user, String userId, long offset, int limit, int lastDays);

	List<DeviceDescriptor> listDevices();

	AppiumTestReport getTestReport(String user, String project, long reportId);

	File saveVideo(String user, String project, String videoId, File output);

	void close();

}
