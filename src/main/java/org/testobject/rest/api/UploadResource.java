package org.testobject.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.MultiPart;

@Path("upload")
public interface UploadResource {
	

	@POST
	@Path("file")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes("multipart/form-data;boundary=file" )
	public String uploadFile(MultiPart multiPart);

}
