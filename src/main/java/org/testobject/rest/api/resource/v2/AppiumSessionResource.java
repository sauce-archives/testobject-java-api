package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.RestClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class AppiumSessionResource {

	private final RestClient client;

	public AppiumSessionResource(RestClient client) {
		this.client = client;
	}

	public Response updateTestReportStatus(String sessionId, boolean passed) {
		return client
				.path("v2")
				.path("appium")
				.path("session").path(sessionId)
				.path("test")
				.request(APPLICATION_JSON_TYPE)
				.put(Entity.json(Collections.singletonMap("passed", passed)));
	}

	public Response updateTestReportName(String sessionId, String suiteName, String testName) {
		Map<String, String> values = new HashMap<>();
		values.put("suiteName", suiteName);
		values.put("testName", testName);

		return client
				.path("v2")
				.path("appium")
				.path("session").path(sessionId)
				.path("test")
				.request(APPLICATION_JSON_TYPE)
				.put(Entity.json(Collections.singletonMap("passed", values)));
	}

}
