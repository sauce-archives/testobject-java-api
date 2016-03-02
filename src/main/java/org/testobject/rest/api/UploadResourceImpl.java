package org.testobject.rest.api;


import org.glassfish.jersey.media.multipart.Boundary;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.File;

public class UploadResourceImpl implements UploadResource {

	private final WebTarget target;

	public UploadResourceImpl(WebTarget target) {
		this.target = target;
	}

	@Override
	public String uploadFile(String user, String project, File apk) {
		FormDataMultiPart form = new FormDataMultiPart();
		form.field("userId", user);
		form.field("projectId", project);
		form.bodyPart(new FileDataBodyPart("file", apk, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		MediaType contentType = MediaType.MULTIPART_FORM_DATA_TYPE;
		contentType = Boundary.addBoundary(contentType); // import org.glassfish.jersey.media.multipart.Boundary;

		return target.path("upload").path("file").request().post(Entity.entity(form, contentType), String.class);
	}

}
