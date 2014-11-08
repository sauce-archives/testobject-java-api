package org.testobject.api;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestObjectClientTest {
	
	private static final String USER = "foobar";
	private static final String PASSWORD = "funkybob";
	
	private static final String PROJECT = "calculator";
	private static final long BATCH = 17;
	
	private static InputStream APP_APK = TestObjectClientTest.class.getResourceAsStream("calculator-debug-unaligned.apk");
	private static InputStream INSTRUMENTATION_APK = TestObjectClientTest.class.getResourceAsStream("calculator-debug-test-unaligned.apk");
	
	private TestObjectClient client;
	
	@Before
	public void setup(){
		client = TestObjectClient.Factory.create("http://localhost:7070/rest");
	}

	@Test
	public void testLogin() {
		client.login(USER, PASSWORD);
		
		client.updateInstrumentationBatch(USER, PROJECT, BATCH, APP_APK, INSTRUMENTATION_APK);
		
		long batchReport = client.runInstrumentationBatch(USER, PROJECT, BATCH);
		
		client.waitForBatchReport(USER, PROJECT, batchReport);
	}
	
	@After
	public void tearDown(){
		client.close();
	}

}
