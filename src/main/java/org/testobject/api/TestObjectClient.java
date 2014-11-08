package org.testobject.api;

import java.io.Closeable;
import java.io.InputStream;

import org.testobject.rest.api.TestSuiteReportResource.TestSuiteReport;

public interface TestObjectClient extends Closeable {
	
	public final class ProxySettings {
		
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

	public final class Factory {
		public static TestObjectClient create(String baseUrl) {
			return create(baseUrl, null);
		}
		
		public static TestObjectClient create(String baseUrl, ProxySettings proxySettings) {
			return new TestObjectRemoteClient(baseUrl, proxySettings);
		}
	}
	
	public void login(String username, String password);
	
	public void updateInstrumentationTestSuite(String user, String project, long testSuite, InputStream appApk, InputStream instrumentationAPK);
	
	public long startInstrumentationTestSuite(String user, String project, long testSuite);
	
	public TestSuiteReport waitForSuiteReport(String user, String project, long testSuiteReport);
	
	public void close();

}
