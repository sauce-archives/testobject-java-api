package org.testobject.rest.api;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class AppVersionResourceImpl implements AppVersionResource {

	private final WebResource resource;

	public AppVersionResourceImpl(WebResource resource) {
		this.resource = resource;
	}

	@Override
	public void createAppVersion(String userId, String projectId, CreateAppVersionRequest request) {
		resource
				.path("users").path(userId).path("projects").path(projectId).path("apps")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(request);
	}

}
