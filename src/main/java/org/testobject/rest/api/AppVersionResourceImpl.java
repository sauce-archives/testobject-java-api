package org.testobject.rest.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class AppVersionResourceImpl implements AppVersionResource {

	private final WebTarget target;

	public AppVersionResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public void createAppVersion(String userId, String projectId, CreateAppVersionRequest request) {
		target.path("users").path(userId).path("projects").path(projectId).path("apps")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON));
	}

}
