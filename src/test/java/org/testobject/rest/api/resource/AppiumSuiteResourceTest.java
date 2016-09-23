package org.testobject.rest.api.resource;

import org.junit.Before;
import org.junit.Test;
import org.testobject.rest.api.RestClient;

public class AppiumSuiteResourceTest {
	AppiumSuiteResource appiumSuiteResource;

	@Before
	public void setUp() {
		RestClient restClient = RestClient.Builder.createClient()
				.path("https://app.testobject.com:443/api/rest/appium/v1")
				.build();
		appiumSuiteResource = new AppiumSuiteResource(restClient);
	}

	@Test
	public void testDeviceList() {
		appiumSuiteResource.readSuiteDeviceIds(9447);
	}
}