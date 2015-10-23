package org.testobject.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.testobject.rest.api.*;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestObjectRemoteClient implements TestObjectClient {

    private final UserResource user;
    private final UploadResource upload;
    private final AppVersionResource appVersion;
    private final TestSuiteResource testSuite;
    private final TestSuiteReportResource testSuiteReport;
    private final QualityReportResource qualityReport;
    private final DeviceDescriptorsResource deviceDescriptors;

    private final Client client;

    public TestObjectRemoteClient(String baseUrl, ProxySettings proxySettings) {
        ApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
        config.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);

        if (proxySettings != null) {
            config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_PROXY_URI, "http://" + proxySettings.getHost() + ":" + proxySettings.getPort());
            if (proxySettings.getUsername() != null) {
                config.getState().getHttpState().setProxyCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxySettings.getUsername(), proxySettings.getPassword()));
            }
        }

        client = ApacheHttpClient.create(config);

        WebResource resource = client.resource(baseUrl);
        user = new UserResourceImpl(resource);
        upload = new UploadResourceImpl(resource);
        appVersion = new AppVersionResourceImpl(resource);
        testSuite = new TestSuiteResourceImpl(resource);
        testSuiteReport = new TestSuiteReportResourceImpl(resource);
        qualityReport = new QualityReportResourceImpl(resource);
        deviceDescriptors = new DeviceDescriptorsResourceImpl(resource);
    }

    public void login(String username, String password) {
        user.login(username, password);
    }

    public void updateInstrumentationTestSuite(String user, String project, long testSuite, File appApk, File testApk, TestSuiteResource.InstrumentationTestSuiteRequest request) {
        String appUploadId = upload.uploadFile(user, project, appApk).replace("\"", "");
        String testUploadId = upload.uploadFile(user, project, testApk).replace("\"", "");
        request.testUploadId = testUploadId;
        request.appUploadId = appUploadId;

        this.testSuite.updateInstrumentationTestSuite(user, project, testSuite,
                request);
    }

    public Long createInstrumentationTestSuite(String user, String project, long testSuite, File appApk, File testApk, TestSuiteResource.InstrumentationTestSuiteRequest instrumentationTestSuiteRequest) {
        String appUploadId = upload.uploadFile(user, project, appApk).replace("\"", "");
        String testUploadId = upload.uploadFile(user, project, testApk).replace("\"", "");
        instrumentationTestSuiteRequest.appUploadId = appUploadId;
        instrumentationTestSuiteRequest.testUploadId = testUploadId;

        return this.testSuite.createInstrumentationTestSuite(user, project, testSuite,
                instrumentationTestSuiteRequest);
    }

    public long startInstrumentationTestSuite(String user, String project, long testSuite) {
        return this.testSuite.runInstrumentationTestSuite(user, project, testSuite);
    }

    public TestSuiteReport waitForSuiteReport(final String user, final String project, final long testSuiteReportId) {
        return waitForSuiteReport(user, project, testSuiteReportId, TimeUnit.MINUTES.toMillis(60), TimeUnit.SECONDS.toMillis(30));
    }

    public TestSuiteReport waitForSuiteReport(final String user, final String project, final long testSuiteReportId, long waitTimeoutMs, long sleepTimeMs) {
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
    public List<DeviceDescriptor> listDevices() {
        return deviceDescriptors.listDevices();
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
        client.destroy();
    }

}
