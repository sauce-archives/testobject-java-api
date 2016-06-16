package org.testobject.rest.api.resource;

import org.testobject.rest.api.RestClient;
import org.testobject.rest.api.appium.common.data.Suite;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Set;

public class AppiumSuiteResource {

	private final RestClient client;

	public AppiumSuiteResource(RestClient client) {
		this.client = client;
	}

	public Set<String> readSuiteDeviceIds(long suiteId) {
		return client
				.path("suites").path(Long.toString(suiteId))
				.path("deviceIds")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get(new GenericType<Set<String>>(Set.class));
	}

	public Suite updateSuite(Suite.Id suiteId, Suite suite) {
		return client
				.path("suites").path(suiteId.toString())
				.request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.json(suite), Suite.class);
	}

}
