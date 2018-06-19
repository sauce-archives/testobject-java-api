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
import org.testobject.rest.api.model.*;
import org.testobject.rest.api.resource.*;
import org.testobject.rest.api.resource.v2.*;
import org.testobject.rest.api.resource.v2.ApiBatchResource.InstrumentationTestSuiteRequest;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.*;

public class TestObjectClientImpl implements TestObjectClient {

	private final ApiBatchReportResource apiBatchReportResource;
	private final ApiBatchResource apiBatchResource;
	private final ApiUploadResource apiUploadResource;
	private final AppStorageResource appStorageResource;
	private final InstrumentationResource instrumentationResource;

	private final ApiSessionReportResource apiSessionReportResource;
	private final DeviceDescriptorsResource deviceDescriptorsResource;
	private final ApiTestReportResource apiTestReportResource;
	private final ApiVideoResource apiVideoResource;

	private final Client client;

	public TestObjectClientImpl(String baseUrl, ProxySettings proxySettings) {
		client = buildClient(proxySettings);
		WebTarget target = client.target(baseUrl);

		apiBatchReportResource = new ApiBatchReportResource(target);
		apiBatchResource = new ApiBatchResource(target);
		apiUploadResource = new ApiUploadResource(target);
		appStorageResource = new AppStorageResource(target);
		instrumentationResource = new InstrumentationResource(target);

		apiSessionReportResource = new ApiSessionReportResource(target);
		deviceDescriptorsResource = new DeviceDescriptorsResource(target);
		apiTestReportResource = new ApiTestReportResource(target);
		apiVideoResource = new ApiVideoResource(target);
	}

	@Override
	public TestSuiteReport waitForSuiteReport(long suiteReportId, long waitTimeoutMs, long sleepTimeMs, String apiKey) {
		long start = now();
		while ((now() - start) < waitTimeoutMs) {
			TestSuiteReport testSuiteReport = apiBatchReportResource.getReport(suiteReportId, apiKey);

			if (!testSuiteReport.isRunning()) {
				return testSuiteReport;
			}
			sleep(sleepTimeMs);
		}

		throw new IllegalStateException("Failed to get test suite report result in 60 minutes.");
	}

	@Override
	public TestSuiteReport waitForSuiteReport(long suiteReportId, String apiKey) {
		return waitForSuiteReport(suiteReportId, MINUTES.toMillis(60), SECONDS.toMillis(30), apiKey);
	}

	@Override
	public String readTestSuiteXMLReport(long suiteReportId, String apiKey) {
		return apiBatchReportResource.getXMLReport(suiteReportId, apiKey);
	}

	@Override
	public long startInstrumentationTestSuite(long suiteId, String apiKey) {
		return apiBatchResource.startInstrumentationSuite(suiteId, apiKey);
	}

	@Override
	public void updateInstrumentationTestSuite(long suiteId, File appApk, File instrumentationApk, InstrumentationTestSuiteRequest request,
			String apiKey) {
		String appUploadId = apiUploadResource.uploadFile(apiKey, appApk).replace("\"", "");
		String testUploadId = apiUploadResource.uploadFile(apiKey, instrumentationApk).replace("\"", "");
		request.testUploadId = testUploadId;
		request.appUploadId = appUploadId;

		apiBatchResource.updateInstrumentationSuite(suiteId, request, apiKey);
	}

	@Override
	public String uploadFile(String apiKey, File apk) {
		return apiUploadResource.uploadFile(apiKey, apk);
	}

	@Override
	public long uploadRunnerIpa(String apiKey, File ipa) throws InvalidUserInputServerException {
		return Long.parseLong(appStorageResource.uploadAppXcuiTest(apiKey, ipa));
	}

	@Override
	public long uploadAppIpa(String apiKey, File ipa) throws InvalidUserInputServerException {
		return Long.parseLong(appStorageResource.uploadAppXcuiApp(apiKey, ipa));
	}

	@Override
	public long uploadRunnerApk(String apiKey, File apk) throws InvalidUserInputServerException {
		return Long.parseLong(appStorageResource.uploadAppAndroidTest(apiKey, apk));
	}

	@Override
	public long uploadAppApk(String apiKey, File apk) throws InvalidUserInputServerException {
		return Long.parseLong(appStorageResource.uploadAppAndroidApp(apiKey, apk));
	}

	@Override
	public StartInstrumentationResponse startXcuiTestSuite(String apiKey, StaticInstrumentationRequestData requestData) {
		return instrumentationResource.createAndStartXCUITestInstrumentation(apiKey, requestData);
	}

	@Override
	public StartInstrumentationResponse startXcuiTestSuite(String apiKey, DynamicInstrumentationRequestData requestData) {
		return instrumentationResource.createAndStartXCUITestInstrumentation(apiKey, requestData);
	}

	@Override
	public StartInstrumentationResponse startAndroidSuite(String apiKey, StaticInstrumentationRequestData requestData) {
		return instrumentationResource.createAndStartAndroidInstrumentation(apiKey, requestData);
	}

	@Override
	public StartInstrumentationResponse startAndroidSuite(String apiKey, DynamicInstrumentationRequestData requestData) {
		return instrumentationResource.createAndStartAndroidInstrumentation(apiKey, requestData);
	}

	@Override
	public String readJunitReport(String apiKey, long testReportId) {
		return instrumentationResource.getJUnitReport(apiKey, testReportId);
	}

	@Override
	public String readJunitReport(String apiKey, List<Long> testReportIds) {
		return instrumentationResource.getJUnitReport(apiKey, testReportIds);
	}

	@Override
	public InstrumentationReport waitForInstrumentationReport(String apiKey, long testSuiteReportId, long waitTimeoutMs, long sleepTimeMs)
			throws TimeoutException {

		long waitTimeoutMinutes = TimeUnit.MILLISECONDS.toMinutes(waitTimeoutMs);
		boolean timeoutTooLong = waitTimeoutMinutes > HOURS.toMinutes(2);
		if (timeoutTooLong) {
			String errorMessage = "Timeout should be a reasonable value: no more than 120 minutes. Got " + waitTimeoutMinutes + " minutes.";
			throw new IllegalArgumentException(errorMessage);
		}

		long start = now();
		while ((now() - start) < waitTimeoutMs) {
			InstrumentationReport testSuiteReport = instrumentationResource.getTestReport(apiKey, testSuiteReportId);

			if (!testSuiteReport.isRunning()) {
				return testSuiteReport;
			}
			sleep(sleepTimeMs);
		}

		String exceptionMessage = String.format("Failed to get a test suite report result in %d minutes.", waitTimeoutMinutes);
		throw new TimeoutException(exceptionMessage);
	}

	@Override
	public PaginationObject<SessionReport> getSessionReports(String userId, long offset, int limit, int lastDays, String apiKey) {
		return apiSessionReportResource.getSessionReports(userId, offset, limit, lastDays, apiKey);
	}

	@Override
	public List<DeviceDescriptor> getAvailableDeviceDescriptors() {
		return deviceDescriptorsResource.getAvailableDeviceDescriptors();
	}

	@Override
	public List<String> getAvailableDeviceDescriptorIds() {
		return deviceDescriptorsResource.getAvailableDeviceDescriptorIds();
	}

	@Override
	public AppiumTestReport getTestReport(long reportId, String apiKey) {
		return apiTestReportResource.getTestReport(reportId, apiKey);
	}

	@Override
	public File saveScreenRecording(String videoId, String apiKey, File file) {

		try (InputStream inputStream = apiVideoResource.getScreenRecording(videoId, apiKey).readEntity(InputStream.class);
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

	private Client buildClient(ProxySettings proxySettings) {
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
		return ClientBuilder.newBuilder().sslContext(sslContext).newClient(config);
	}

	public void close() {
		client.close();
	}
}
