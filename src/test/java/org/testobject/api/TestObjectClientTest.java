package org.testobject.api;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestObjectClientTest {
	
	private static final String USER = "testobject";
	private static final String PASSWORD = "--------";
	
	private static final String PROJECT = "zeppelin-ad-disposition";
	private static final long TEST_SUITE = 17;
	
	private static File APP_APK = new File(TestObjectClientTest.class.getResource("calculator-debug-unaligned.apk").getPath());
	private static File INSTRUMENTATION_APK = new File(TestObjectClientTest.class.getResource("calculator-debug-test-unaligned.apk").getPath());
	
	private TestObjectClient client;
	
	@Before
	public void setup(){
		client = TestObjectClient.Factory.create("https://app.testobject.com/api/rest");
	}

	@Test @Ignore
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
