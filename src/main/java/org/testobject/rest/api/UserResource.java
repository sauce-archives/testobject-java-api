package org.testobject.rest.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("users")
public interface UserResource {
	
	@POST
	@Path("login")
	Response login(@FormParam("user") String userName, @FormParam("password") String password);

}
