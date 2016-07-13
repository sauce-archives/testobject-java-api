package org.testobject.rest.api.resource;

import org.testobject.rest.api.RestClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AppiumResource {

	private final RestClient client;

	public AppiumResource(RestClient client) {
		this.client = client;
	}

	public void updateTestReportStatus(String sessionId, boolean passed) {
		client
				.path("session")
				.path(sessionId)
				.path("test").request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.json(Collections.singletonMap("passed", passed)));
	}

	public void updateTestReportName(String sessionId, String suiteName, String testName) {
		Map<String, String> values = new HashMap<String, String>();
		values.put("suiteName", suiteName);
		values.put("testName", testName);

		client
				.path("session")
				.path(sessionId)
				.path("test")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.json(Collections.singletonMap("passed", values)));
	}

	class Data {

		public boolean passed;

		Data(boolean passed) {
			this.passed = passed;
		}

	}

}
