package org.testobject.rest.api.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testobject.rest.api.RestClient;
import org.testobject.rest.api.appium.common.data.DataCenterSuite;
import org.testobject.rest.api.appium.common.data.Suite;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

public class AppiumSuiteResource {

	private final RestClient client;

	public AppiumSuiteResource(RestClient client) {
		this.client = client;
	}

	public Set<DataCenterSuite> readDataCenterSuites(long suiteId) {
		return client
				.path("suites").path(Long.toString(suiteId))
				.path("deviceIds")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get(new GenericType<Set<DataCenterSuite>>(new TypeReference<Set<DataCenterSuite>>() {
				}.getType()));
	}

	public Suite updateSuite(Suite.Id suiteId, Suite suite) {
		return client
				.path("suites").path(suiteId.toString())
				.request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.json(suite), Suite.class);
	}

}
