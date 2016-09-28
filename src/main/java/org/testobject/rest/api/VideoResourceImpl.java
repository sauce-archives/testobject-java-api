package org.testobject.rest.api;

import org.testobject.rest.api.resource.VideoResource;

import javax.ws.rs.PathParam;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class VideoResourceImpl implements VideoResource {
	private final WebTarget target;

	public VideoResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public Response getScreenRecording(@PathParam("userId") String userId, @PathParam("projectId") String projectId,
			@PathParam("videoId") String videoId) {
		return target
				.path("users").path(userId)
				.path("projects").path(projectId)
				.path("video").path(videoId)
				.request("video/mp4")
				.get();
	}
}
