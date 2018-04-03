package org.testobject.api.v2;

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
import org.testobject.rest.api.model.InstrumentationRequestData;
import org.testobject.rest.api.model.StartInstrumentationResponse;
import org.testobject.rest.api.model.TestSuiteReport;
import org.testobject.rest.api.model.XcuiTestReport;
import org.testobject.rest.api.resource.v2.*;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.File;

public class TestObjectRemoteClientV2 implements TestObjectClientV2 {

	private final UploadResourceV2 uploadV2;
	private final TestSuiteResourceV2 testSuiteV2;
	private final TestSuiteReportResourceV2 testSuiteReportV2;
	private final InstrumentationResource instrumentationResource;
	private final AppStorageResource appStorageResource;

	private final Client client;

	public TestObjectRemoteClientV2(String baseUrl, ProxySettings proxySettings) {

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
			config.property(ClientProperties.PROXY_URI,
					"http://" + proxySettings.getHost() + ":" + proxySettings.getPort());
			if (proxySettings.getUsername() != null) {
				config.property(
						ClientProperties.PROXY_USERNAME,
						proxySettings.getUsername()
				);

				config.property(
						ClientProperties.PROXY_PASSWORD,
						proxySettings.getPassword()
				);
			}
		}

		SSLContext sslContext = sslConfig.createSSLContext();
		client = ClientBuilder.newBuilder().sslContext(sslContext).newClient(config);
		WebTarget resource = client.target(baseUrl);

		uploadV2 = new UploadResourceImplV2(resource);
		testSuiteV2 = new TestSuiteResourceImplV2(resource);
		testSuiteReportV2 = new TestSuiteReportResourceImplV2(resource);
		instrumentationResource = new InstrumentationResourceImpl(resource);
		appStorageResource = new AppStorageResource(resource);
	}

	@Override
	public long uploadRunnerIpa(String apikey, File ipa) {
		return Long.parseLong(appStorageResource.uploadAppXcuiTest(apikey, ipa));
	}

	@Override
	public long uploadAppIpa(String apikey, File ipa) {
		return Long.parseLong(appStorageResource.uploadAppXcuiApp(apikey, ipa));
	}

	@Override
	public long uploadRunnerApk(String apikey, File apk) {
		return Long.parseLong(appStorageResource.uploadAppAndroidTest(apikey, apk));
	}

	@Override
	public long uploadAppApk(String apikey, File apk) {
		return Long.parseLong(appStorageResource.uploadAppAndroidApp(apikey, apk));
	}


	@Override
	public StartInstrumentationResponse startXcuiTestSuite(String apiKey, InstrumentationRequestData requestData) {
		return this.instrumentationResource.createAndStartXCUITestInstrumentation(apiKey, requestData);
	}

	@Override
	public StartInstrumentationResponse startAndroidSuite(String apiKey, InstrumentationRequestData requestData) {
		return this.instrumentationResource.createAndStartAndroidInstrumentation(apiKey, requestData);
	}

	@Override
	public String readJunitReport(final String apiKey, final long testReportId) {
		return this.instrumentationResource.getJUnitReport(apiKey, testReportId);
	}

	@Override
	public XcuiTestReport waitForXcuiTestReport(final String apiKey, final long testSuiteReportId, long waitTimeoutMs,
			long sleepTimeMs
	) {
		long start = now();

		while ((now() - start) < waitTimeoutMs) {
			XcuiTestReport testSuiteReport = TestObjectRemoteClientV2.this.instrumentationResource.getTestReport(apiKey, testSuiteReportId);
			if (testSuiteReport.isRunning() == false) {
				return testSuiteReport;
			}

			sleep(sleepTimeMs);
		}

		throw new IllegalStateException("unable to get test suite report result after 60min");
	}

	@Override
	public void updateInstrumentationTestSuite(long testSuite, File appApk, File testApk,
			TestSuiteResourceV2.InstrumentationTestSuiteRequest request, String apiKey) {
		String appUploadId = uploadV2.uploadFile(apiKey, appApk).replace("\"", "");
		String testUploadId = uploadV2.uploadFile(apiKey, testApk).replace("\"", "");
		request.testUploadId = testUploadId;
		request.appUploadId = appUploadId;

		this.testSuiteV2.updateInstrumentationTestSuite(testSuite, request, apiKey);
	}

	@Override
	public long startInstrumentationTestSuite(long testSuite, String apiKey) {
		return this.testSuiteV2.runInstrumentationTestSuite(testSuite, apiKey);
	}

	@Override
	public TestSuiteReport waitForSuiteReport(final long testSuiteReportId, long waitTimeoutMs,
			long sleepTimeMs, String apiKey) {
		long start = now();
		while ((now() - start) < waitTimeoutMs) {
			TestSuiteReport testSuiteReport = TestObjectRemoteClientV2.this.testSuiteReportV2.getReport(testSuiteReportId,
					MediaType.APPLICATION_JSON, apiKey);
			if (testSuiteReport.isRunning() == false) {
				return testSuiteReport;
			}

			sleep(sleepTimeMs);
		}

		throw new IllegalStateException("unable to get test suite report result after 60min");
	}

	@Override
	public String readTestSuiteXMLReport(final long testSuiteReportId, String apiKey) {
		return TestObjectRemoteClientV2.this.testSuiteReportV2.getXMLReport(testSuiteReportId, apiKey);
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
