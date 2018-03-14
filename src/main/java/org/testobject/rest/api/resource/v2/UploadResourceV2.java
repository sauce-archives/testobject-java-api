package org.testobject.rest.api.resource.v2;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;

@Path("v2/upload")
public interface UploadResourceV2 {

	@POST
	@Path("file")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("multipart/form-data;boundary=file")
	public String uploadFile(String apiKey, File apk);

}
