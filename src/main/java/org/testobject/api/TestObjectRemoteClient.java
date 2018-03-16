package org.testobject.api;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.testobject.rest.api.*;
import org.testobject.rest.api.model.*;
import org.testobject.rest.api.resource.*;
import org.testobject.rest.api.resource.v2.InstrumentationResource;
import org.testobject.rest.api.resource.v2.InstrumentationResourceImpl;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestObjectRemoteClient implements TestObjectClient {

	private final UploadResource upload;
	private final AppVersionResource appVersion;
	private final TestSuiteResource testSuite;
	private final TestSuiteReportResource testSuiteReport;
	private final QualityReportResource qualityReport;
	private final SessionReportResource sessionReport;
	private final DeviceDescriptorsResource deviceDescriptors;
	private final TestReportResource testReportResource;
	private final VideoResourceImpl videoResource;
	private final AppStorageResource appStorageResource;

	private final Client client;
	private final InstrumentationResource instrumentationResource;

	public TestObjectRemoteClient(String baseUrl, ProxySettings proxySettings) {

		X509HostnameVerifier defaultHostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
		SslConfigurator sslConfig = SslConfigurator.newInstance();
		LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslConfig.createSSLContext(),
				new String[] { "TLSv1", "TLSv1.1", "TLSv1.2" },
				null,
				defaultHostnameVerifier);

		final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sslSocketFactory)
				.build();

		ClientConfig config = new ClientConfig();
		config.property(ApacheClientProperties.CONNECTION_MANAGER, new PoolingHttpClientConnectionManager(registry));

		ApacheConnectorProvider connector = new ApacheConnectorProvider();

		config.connectorProvider(connector);
		config.register(MultiPartFeature.class);
		config.register(JacksonFeature.class);

		if (proxySettings != null) {
			config.getProperties().put(ClientProperties.PROXY_URI,
					"http://" + proxySettings.getHost() + ":" + proxySettings.getPort());
			if (proxySettings.getUsername() != null) {
				config.getProperties().put(
						ClientProperties.PROXY_USERNAME,
						proxySettings.getUsername()
				);

				config.getProperties().put(
						ClientProperties.PROXY_PASSWORD,
						proxySettings.getPassword()
				);
			}
		}

		SSLContext sslContext = sslConfig.createSSLContext();
		client = ClientBuilder.newBuilder().sslContext(sslContext).newClient(config);

		WebTarget resource = client.target(baseUrl);
		upload = new UploadResourceImpl(resource);
		appVersion = new AppVersionResourceImpl(resource);
		testSuite = new TestSuiteResourceImpl(resource);
		testSuiteReport = new TestSuiteReportResourceImpl(resource);
		qualityReport = new QualityReportResourceImpl(resource);
		deviceDescriptors = new DeviceDescriptorsResourceImpl(resource);
		sessionReport = new SessionReportResourceImpl(resource);
		testReportResource = new TestReportResourceImpl(resource);
		videoResource = new VideoResourceImpl(resource);
		instrumentationResource = new InstrumentationResourceImpl(resource);
		appStorageResource = new AppStorageResource(resource);
	}

	@Override
	public void updateInstrumentationTestSuite(String user, String project, long testSuite, File appApk, File testApk,
			TestSuiteResource.InstrumentationTestSuiteRequest request) {
		String appUploadId = upload.uploadFile(user, project, appApk).replace("\"", "");
		String testUploadId = upload.uploadFile(user, project, testApk).replace("\"", "");
		request.testUploadId = testUploadId;
		request.appUploadId = appUploadId;

		this.testSuite.updateInstrumentationTestSuite(user, project, testSuite,
				request);
	}

	@Override
	public long uploadIpa(String apikey, File ipa) {
		return Long.parseLong(appStorageResource.uploadAppXcuiTest(apikey, ipa));
	}

	@Override
	public Long createInstrumentationTestSuite(String user, String project, long testSuite, File appApk, File testApk,
			TestSuiteResource.InstrumentationTestSuiteRequest instrumentationTestSuiteRequest) {
		String appUploadId = upload.uploadFile(user, project, appApk).replace("\"", "");
		String testUploadId = upload.uploadFile(user, project, testApk).replace("\"", "");
		instrumentationTestSuiteRequest.appUploadId = appUploadId;
		instrumentationTestSuiteRequest.testUploadId = testUploadId;

		return this.testSuite.createInstrumentationTestSuite(user, project, testSuite,
				instrumentationTestSuiteRequest);
	}

	@Override
	public long startXcuiTestSuite(String apiKey, InstrumentationRequestData requestData) {
		return this.instrumentationResource.createAndStartXCUITestInstrumentation(apiKey, requestData);
	}

	@Override
	public long startInstrumentationTestSuite(String user, String project, long testSuite) {
		return this.testSuite.runInstrumentationTestSuite(user, project, testSuite);
	}

	@Override
	public TestSuiteReport waitForSuiteReport(final String user, final String project, final long testSuiteReportId) {
		return waitForSuiteReport(user, project, testSuiteReportId, TimeUnit.MINUTES.toMillis(60), TimeUnit.SECONDS.toMillis(30));
	}

	@Override
	public String readXCUITestJunitReport(final String apiKey, final long testReportId) {
		return this.instrumentationResource.getJUnitReport(apiKey, testReportId);
	}

	@Override
	public XcuiTestReport waitForXcuiTestReport(final String apiKey, final long testSuiteReportId
	) {
		long start = now();

		while ((now() - start) < TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)) {
			XcuiTestReport testSuiteReport = TestObjectRemoteClient.this.instrumentationResource.getTestReport(apiKey, testSuiteReportId);
			if (testSuiteReport.isRunning() == false) {
				return testSuiteReport;
			}

			sleep(1000);
		}

		throw new IllegalStateException("unable to get test suite report result after 60min");
	}

	@Override
	public TestSuiteReport waitForSuiteReport(final String user, final String project, final long testSuiteReportId, long waitTimeoutMs,
			long sleepTimeMs) {
		long start = now();
		while ((now() - start) < waitTimeoutMs) {
			TestSuiteReport testSuiteReport = TestObjectRemoteClient.this.testSuiteReport.getReport(user, project, testSuiteReportId,
					MediaType.APPLICATION_JSON);
			if (testSuiteReport.isRunning() == false) {
				return testSuiteReport;
			}

			sleep(sleepTimeMs);
		}

		throw new IllegalStateException("unable to get test suite report result after 60min");
	}

	@Override
	public String readTestSuiteXMLReport(final String user, final String project, final long testSuiteReportId) {
		return TestObjectRemoteClient.this.testSuiteReport.getXMLReport(user, project, testSuiteReportId);
	}

	@Override
	public void createAppVersion(String userId, String projectId, File appApk) {
		String appUploadId = upload.uploadFile(userId, projectId, appApk).replace("\"", "");

		this.appVersion.createAppVersion(userId, projectId,
				new AppVersionResource.CreateAppVersionRequest(appUploadId));
	}

	@Override
	public long startQualityReport(String userId, String projectId) {
		return qualityReport.startQualityReport(userId, projectId);
	}

	@Override
	public PaginationObject<SessionReport> getSessionReport(String user) {
		return sessionReport.getSessionReport(user);
	}

	@Override
	public PaginationObject<SessionReport> getSessionReport(String user, String userId, long offset, int limit, int lastDays) {
		return sessionReport.getSessionReport(user, userId, offset, limit, lastDays);
	}

	@Override
	public List<DeviceDescriptor> listDevices() {
		return deviceDescriptors.listDevices();
	}

	@Override
	public AppiumTestReport getTestReport(String user, String project, long reportId) {
		return testReportResource.getTestReport(user, project, reportId).getReport();
	}

	@Override
	public File saveVideo(String user, String project, String videoId, File file) {
		try (InputStream inputStream = videoResource.getScreenRecording(user, project, videoId).readEntity(InputStream.class);
				OutputStream outputStream = new FileOutputStream(file)) {

			int read;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to save video", e);
		}

		return file;
	}

	private void sleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private long now() {
		return System.currentTimeMillis();
	}

	public void close() {
		client.close();
	}

}
