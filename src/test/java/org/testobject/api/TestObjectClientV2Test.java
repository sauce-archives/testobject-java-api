package org.testobject.api;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.testobject.api.v2.InvalidUserInputServerException;
import org.testobject.api.v2.TestObjectClientV2;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestObjectClientV2Test {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static final String API_KEY = "E0B5898DA4914B1996CC7C05CE46BDAB";
	private static File IPA_INVALID_BAD_ZIP = new File(TestObjectClientV2Test.class.getResource("ipa-invalid-bad-zip-archive.ipa").getPath());

	private TestObjectClientV2 client;

	@Before
	public void setup(){
		client = TestObjectClientV2.Factory.create("https://app.staging.testobject.org/api/rest");
	}

	@Test @Ignore
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

	@After
	public void tearDown(){
		client.close();
	}
}
