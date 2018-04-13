package org.testobject.api.v2;

import org.testobject.rest.api.model.InstrumentationRequestData;
import org.testobject.rest.api.model.StartInstrumentationResponse;
import org.testobject.rest.api.model.TestSuiteReport;
import org.testobject.rest.api.model.InstrumentationReport;
import org.testobject.rest.api.resource.v2.TestSuiteResourceV2.InstrumentationTestSuiteRequest;

import java.io.Closeable;
import java.io.File;
import java.util.concurrent.TimeoutException;

public interface TestObjectClientV2 extends Closeable {

	long uploadRunnerIpa(String apikey, File ipa) throws InvalidUserInputServerException;

	long uploadAppIpa(String apikey, File ipa) throws InvalidUserInputServerException;

	long uploadRunnerApk(String apikey, File apk) throws InvalidUserInputServerException;

	long uploadAppApk(String apikey, File apk) throws InvalidUserInputServerException;

	StartInstrumentationResponse startXcuiTestSuite(String apiKey, InstrumentationRequestData requestData);

	StartInstrumentationResponse startAndroidSuite(String apiKey, InstrumentationRequestData requestData);

	String readJunitReport(String apiKey, long testReportId);

	InstrumentationReport waitForInstrumentationReport(String apiKey,
	                                                   long testSuiteReportId,
	                                                   long waitTimeoutMs,
	                                                   long sleepTimeMs) throws TimeoutException;

	void updateInstrumentationTestSuite(long testSuite, File appApk, File instrumentationAPK,
			InstrumentationTestSuiteRequest request, String apiKey);

	long startInstrumentationTestSuite(long testSuite, String apiKey);

	TestSuiteReport waitForSuiteReport(final long testSuiteReportId, long waitTimeoutMs,
			long sleepTimeMs, String apiKey);

	String readTestSuiteXMLReport(final long testSuiteReportId, String apiKey);

	void close();

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

		public static TestObjectClientV2 create() {
			return create(BASE_URL, null);
		}

		public static TestObjectClientV2 create(String baseUrl) {
			return create(baseUrl, null);
		}

		public static TestObjectClientV2 create(String baseUrl, ProxySettings proxySettings) {
			return new TestObjectRemoteClientV2(baseUrl, proxySettings);
		}

	}

}
