package org.testobject.rest.api.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Produces("video/mp4")
public interface VideoResource {

	@GET
	@Path("{videoId}")
	@Produces("video/mp4")
	Response getScreenRecording(@PathParam("userId") String userId, @PathParam("projectId") String projectId,
			@PathParam("videoId") String videoId);

}
