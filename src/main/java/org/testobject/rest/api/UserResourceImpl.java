package org.testobject.rest.api;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class UserResourceImpl implements UserResource {
	
	private final WebResource resource;

	public UserResourceImpl(WebResource resource) {
		this.resource = resource;
	}

	@Override
	public void login(String userName, String password) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("user", userName);
		formData.add("password", password);
		
		ClientResponse response =  resource
				.path("users").path("login")
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, formData);
		
		if(Response.Status.OK.getStatusCode() != response.getStatus()){
			throw new IllegalStateException("expected status " + Response.Status.OK + " but was " + response.getStatus());
		}
	}

}
