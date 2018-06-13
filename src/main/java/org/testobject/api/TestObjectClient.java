package org.testobject.api;

import org.testobject.rest.api.model.*;
import org.testobject.rest.api.resource.v2.ApiBatchResource.InstrumentationTestSuiteRequest;

import java.io.Closeable;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface TestObjectClient extends Closeable {

	// ---------------------------------------------------------------- //
	// --------------------------  V2 folder  ------------------------- //
	// ---------------------------------------------------------------- //

	//
	// ApiBatchReportResource (SuiteReportResource)
	//

	TestSuiteReport waitForSuiteReport(long suiteReportId, long waitTimeoutMs, long sleepTimeMs, String apiKey);

	TestSuiteReport waitForSuiteReport(long suiteReportId, String apiKey);

	String readTestSuiteXMLReport(long suiteReportId, String apiKey);

	//
	// ApiBatchResource (InstrumentationSuiteResource)
	//

	long startInstrumentationTestSuite(long suiteId, String apiKey);

	void updateInstrumentationTestSuite(long suiteId, File appApk, File instrumentationApk, InstrumentationTestSuiteRequest request,
			String apiKey);

	//
	// ApiTestReportResource
	//

	TestReportWithDevice getTestReport(long reportId, String apiKey);

	//
	// ApiUploadResource
	//

	String uploadFile(String apiKey, File apk);

	//
	// ApiVideoResource
	//

	File saveScreenRecording(String videoId, String apiKey, File savePath);

	//
	// AppStorageResource
	//

	long uploadRunnerIpa(String apiKey, File ipa) throws InvalidUserInputServerException;

	long uploadAppIpa(String apiKey, File ipa) throws InvalidUserInputServerException;

	long uploadRunnerApk(String apiKey, File apk) throws InvalidUserInputServerException;

	long uploadAppApk(String apiKey, File apk) throws InvalidUserInputServerException;

	//
	// InstrumentationResource
	//

	StartInstrumentationResponse startXcuiTestSuite(String apiKey, StaticInstrumentationRequestData requestData);

	StartInstrumentationResponse startXcuiTestSuite(String apiKey, DynamicInstrumentationRequestData requestData);

	StartInstrumentationResponse startAndroidSuite(String apiKey, StaticInstrumentationRequestData requestData);

	StartInstrumentationResponse startAndroidSuite(String apiKey, DynamicInstrumentationRequestData requestData);

	String readJunitReport(String apiKey, long testReportId);

	String readJunitReport(String apiKey, List<Long> testReportIds);

	InstrumentationReport waitForInstrumentationReport(String apiKey, long testSuiteReportId, long waitTimeoutMs, long sleepTimeMs)
			throws TimeoutException;

	// ---------------------------------------------------------------- //
	// ---------------------------------------------------------------- //
	// ---------------------------------------------------------------- //

	//
	// ApiSessionReportResource
	//

	PaginationObject<SessionReport> getSessionReports(String userId, long offset, int limit, int lastDays, String apiKey);

	//
	// DeviceDescriptorsResource
	//

	List<DeviceDescriptor> getAvailableDeviceDescriptors();

	List<String> getAvailableDeviceDescriptorIds();

	void close();

	final class Factory {

		private static final String BASE_URL = "https://appium.testobject.com/api/rest";

		public static TestObjectClient create() {
			return create(BASE_URL, null);
		}

		public static TestObjectClient create(String baseUrl) {
			return create(baseUrl, null);
		}

		public static TestObjectClient create(String baseUrl, ProxySettings proxySettings) {
			return new TestObjectClientImpl(baseUrl, proxySettings);
		}

	}

}
