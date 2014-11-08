package org.testobject.api;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestObjectClientTest {
	
	private static final String USER = "foobar";
	private static final String PASSWORD = "funkybob";
	
	private static final String PROJECT = "calculator";
	private static final long TEST_SUITE = 17;
	
	private static InputStream APP_APK = TestObjectClientTest.class.getResourceAsStream("calculator-debug-unaligned.apk");
	private static InputStream INSTRUMENTATION_APK = TestObjectClientTest.class.getResourceAsStream("calculator-debug-test-unaligned.apk");
	
	private TestObjectClient client;
	
	@Before
	public void setup(){
		client = TestObjectClient.Factory.create("http://localhost:7070/rest");
	}

	@Ignore @Test
	public void testLogin() {
		client.login(USER, PASSWORD);
		
		client.updateInstrumentationTestSuite(USER, PROJECT, TEST_SUITE, APP_APK, INSTRUMENTATION_APK);
		
		long testSuiteReport = client.startInstrumentationTestSuite(USER, PROJECT, TEST_SUITE);
		
		client.waitForSuiteReport(USER, PROJECT, testSuiteReport);
	}
	
	@After
	public void tearDown(){
		client.close();
	}

}
