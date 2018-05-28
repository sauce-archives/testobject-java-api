package org.testobject.api;

import org.testobject.rest.api.appium.common.data.*;
import org.testobject.rest.api.model.*;
import org.testobject.rest.api.resource.v2.ApiBatchResource.InstrumentationTestSuiteRequest;

import java.io.Closeable;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
	// ApiUploadResource
	//

	String uploadFile(String apiKey, File apk);

	//
	// AppiumReportResource
	//

	SuiteReport startAppiumSuite(long suiteId, Optional<String> appId, Set<Test> tests);

	SuiteReport finishAppiumSuite(long suiteId, SuiteReport.Id suiteReportId);

	TestReport finishAppiumTestReport(long suiteId, SuiteReport.Id suiteReportId, TestReport.Id testReportId, TestResult testResult);

	//
	// AppiumSessionResource
	//

	void updateTestReportStatus(String sessionId, boolean passed);

	void updateTestReportName(String sessionId, String suiteName, String testName);

	//
	// AppiumSuiteResource
	//

	Set<DataCenterSuite> readSuiteDeviceDescriptorIds(long suiteId);

	Suite updateSuite(long suiteId, Suite suite);

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

	//
	// QualityReportResource
	//

	long startQualityReport(String userId, String projectId, String apiKey);

	//
	// TestReportResource
	//

	TestReportWithDevice getTestReport(String userId, String projectId, long reportId, String apiKey);

	//
	// VideoResource
	//

	File saveScreenRecording(String userId, String projectId, String videoId, String apiKey, File savePath);

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
