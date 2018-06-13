package org.testobject.rest.api.resource.v2;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static java.util.Base64.getEncoder;

public class ApiVideoResource {

	private final WebTarget target;

	public ApiVideoResource(WebTarget target) {
		this.target = target;
	}

	public Response getScreenRecording(String videoId, String apiKey) {
		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());

		return target
				.path("v2")
				.path("video").path(videoId)
				.request("video/mp4")
				.header("Authorization", apiKeyHeader)
				.get();
	}

}
