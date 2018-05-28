package org.testobject.rest.api.resource;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static java.util.Base64.getEncoder;

public class VideoResource {

	private final WebTarget target;

	public VideoResource(WebTarget target) {
		this.target = target;
	}

	public Response getScreenRecording(String userId, String projectId, String videoId, String apiKey) {
		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());

		return target
				.path("users").path(userId)
				.path("projects").path(projectId)
				.path("video").path(videoId)
				.request("video/mp4")
				.header("Authorization", apiKeyHeader)
				.get();
	}

}
