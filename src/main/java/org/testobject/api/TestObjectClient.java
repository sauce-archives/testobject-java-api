package org.testobject.api;

import java.io.Closeable;
import java.io.InputStream;

import org.testobject.rest.api.BatchReportResource.BatchReport;

public interface TestObjectClient extends Closeable {

	public final class Factory {
		public static TestObjectClient create(String baseUrl) {
			return new TestObjectRemoteClient(baseUrl);
		}
	}
	
	public void login(String username, String password);
	
	public void updateInstrumentationBatch(String user, String project, long batch, InputStream appApk, InputStream instrumentationAPK);
	
	public long runInstrumentationBatch(String user, String project, long batch);
	
	public BatchReport waitForBatchReport(String user, String project, long batchReport);
	
	public void close();

}
