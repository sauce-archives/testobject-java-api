package org.testobject.rest.api;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;


public class UserResourceImpl implements UserResource {
	
	private final WebTarget target;

	public UserResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public void login(String userName, String password) {
		MultivaluedMap<String, String> formData = new MultivaluedStringMap();
		formData.add("user", userName);
		formData.add("password", password);
		
		Response response =  target
				.path("users").path("login")
				.request()
				.post(Entity.entity(formData,MediaType.APPLICATION_FORM_URLENCODED));
		
		if(Response.Status.OK.getStatusCode() != response.getStatus()){
			throw new IllegalStateException("expected status " + Response.Status.OK + " but was " + response.getStatus());
		}
	}

}
