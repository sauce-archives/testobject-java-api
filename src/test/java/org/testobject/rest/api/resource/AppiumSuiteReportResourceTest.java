package org.testobject.rest.api.resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.testobject.rest.api.RestClient;
import org.testobject.rest.api.appium.common.data.SuiteReport;

@Ignore
public class AppiumSuiteReportResourceTest {

	private AppiumSuiteReportResource appiumSuiteReportResource;

	@Before
	public void setUp() {
		RestClient restClient = RestClient.Builder
				.createClient()
				.withToken("username=7CDE94EFFE3E4EF4A773DB2728688C53")
				.withUrl("https://app.testobject.com:443/api/rest/appium/v1")
				.build();
		appiumSuiteReportResource = new AppiumSuiteReportResource(restClient);
	}

	@Test
	public void testFinished() {
		appiumSuiteReportResource.finishSuiteReport(9447, new SuiteReport.Id(6408L));
	}
}