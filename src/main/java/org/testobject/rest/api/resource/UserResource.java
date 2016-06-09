package org.testobject.rest.api.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("users")
public interface UserResource {
	
	@POST
	@Path("login")
	void login(@FormParam("user") String userName, @FormParam("password") String password);

}
