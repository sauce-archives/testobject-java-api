package org.testobject.rest.api.resource.v2;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testobject.rest.api.RestClient;
import org.testobject.rest.api.appium.common.data.DataCenterSuite;
import org.testobject.rest.api.appium.common.data.Suite;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class AppiumSuiteResource {

	private final RestClient client;

	public AppiumSuiteResource(RestClient client) {
		this.client = client;
	}

	/**
	 * Returns the IDs of the devices which you had selected for the specified suite
	 */
	public Set<DataCenterSuite> readDeviceDescriptorIds(long suiteId) {
		return client
				.path("v2")
				.path("appium")
				.path("suites").path(Long.toString(suiteId))
				.path("deviceIds")
				.request(APPLICATION_JSON_TYPE)
				.get(new GenericType<>(new TypeReference<Set<DataCenterSuite>>() {
				}.getType()));
	}

	/**
	 * Updates the properties of a suite
	 */
	public Suite updateSuite(long suiteId, Suite suite) {
		return client
				.path("v2")
				.path("appium")
				.path("suites").path(Long.toString(suiteId))
				.request(APPLICATION_JSON_TYPE)
				.put(Entity.json(suite), Suite.class);
	}

}
