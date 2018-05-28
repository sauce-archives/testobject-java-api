package org.testobject.api;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.testobject.rest.api.model.AppiumTestReport;
import org.testobject.rest.api.model.DeviceDescriptor;
import org.testobject.rest.api.model.SessionReport;
import org.testobject.rest.api.model.TestSuiteReport;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestObjectClientTest {

	private final String USER_ID = "USER_ID";
	private final String PROJECT_ID = "PROJECT_ID";
	private final String API_KEY = "API_KEY";

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private File APP_APK = new File(TestObjectClientTest.class.getResource("calculator-debug-unaligned.apk").getPath());
	private File INSTRUMENTATION_APK = new File(TestObjectClientTest.class.getResource("calculator-debug-test-unaligned.apk").getPath());
	private File IPA_INVALID_BAD_ZIP = new File(TestObjectClientTest.class.getResource("ipa-invalid-bad-zip-archive.ipa").getPath());

	private TestObjectClient client;

	@Before
	public void setup() {
		client = TestObjectClient.Factory.create("https://app.testobject.com/api/rest");
	}

	@Test
	public void testUploadingRunnerIpaWhichIsBrokenZipResultsInInvalidUserInputException() throws InvalidUserInputServerException {
		expectedException.expect(InvalidUserInputServerException.class);
		expectedException.expectMessage("IPA file is not a valid zip archive.");
		client.uploadRunnerIpa(API_KEY, IPA_INVALID_BAD_ZIP);
	}

	@Test
	public void testWaitForInstrumentationReportWhenWaitTimeoutIsTooBig() throws TimeoutException {
		expectedException.expectMessage("The timeout should be a reasonable value: no more than 120 minutes. Got 600 minutes.");

		long bigTimeout = TimeUnit.HOURS.toMillis(10);

		client.waitForInstrumentationReport("", 0, bigTimeout, 0);
	}

	@Test
	public void testStartQualityReport() {
		long qualityReportId = client.startQualityReport(USER_ID, PROJECT_ID, API_KEY);
		System.out.println(qualityReportId);
	}

	@Test
	public void testGetSessionReport() {
		List<SessionReport> sessionReports = client.getSessionReports(USER_ID, 0, 10, 30, API_KEY).getEntities();

		assertTrue(!sessionReports.isEmpty());

		for (SessionReport sessionReport : sessionReports) {
			assertEquals(USER_ID, sessionReport.getUserId());
		}
	}

	@Test
	public void testGetAvailableDescriptors() {
		List<DeviceDescriptor> deviceDescriptors = client.getAvailableDeviceDescriptors();

		for (DeviceDescriptor deviceDescriptor : deviceDescriptors) {
			System.out.println(deviceDescriptor);
		}

		assertTrue("No available device descriptors?", deviceDescriptors.size() > 0);
	}

	@Test
	public void testGetTestReportAndVideo() {
		long testReportId = 1;

		AppiumTestReport appiumTestReport = client.getTestReport(USER_ID, PROJECT_ID, testReportId, API_KEY).getReport();

		File video = new File("/tmp/appium-website-video.mp4");
		video = client.saveScreenRecording(USER_ID, PROJECT_ID, appiumTestReport.getVideoId(), API_KEY, video);

		assertTrue(video.exists());
		assertTrue(video.length() / 1024 > 1); // video over 1kb
		System.out.println(video);
	}

	@Test
	public void testUpdateTestReportStatus() {
		long testReportId = 1;
		String sessionId = "sessionId";

		client.updateTestReportStatus(sessionId, false);
		AppiumTestReport appiumTestReport = client.getTestReport(USER_ID, PROJECT_ID, testReportId, API_KEY).getReport();
		assertEquals("FAILURE", appiumTestReport.getStatus());

		client.updateTestReportStatus(sessionId, true);
		appiumTestReport = client.getTestReport(USER_ID, PROJECT_ID, testReportId, API_KEY).getReport();
		assertEquals("SUCCESS", appiumTestReport.getStatus());
	}

	@Test @Ignore
	public void testStartSuite() {
		long suiteId = 1;

		client.updateInstrumentationTestSuite(suiteId, APP_APK, INSTRUMENTATION_APK, null, API_KEY);

		long testSuiteReportId = client.startInstrumentationTestSuite(suiteId, API_KEY);

		TestSuiteReport testSuiteReport = client.waitForSuiteReport(suiteId, API_KEY);

		assertEquals(testSuiteReport.getId(), testSuiteReportId);
	}

	@After
	public void tearDown() {
		client.close();
	}

}
