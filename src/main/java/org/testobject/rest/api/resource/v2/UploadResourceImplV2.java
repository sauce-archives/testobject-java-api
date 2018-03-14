package org.testobject.rest.api.resource.v2;

import org.glassfish.jersey.media.multipart.Boundary;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.testobject.rest.api.resource.v2.UploadResourceV2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.File;

public class UploadResourceImplV2 implements UploadResourceV2 {

	private final WebTarget target;

	public UploadResourceImplV2(WebTarget target) {
		this.target = target;
	}

	@Override
	public String uploadFile(String apiKey, File apk) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());

		FormDataMultiPart form = new FormDataMultiPart();
		form.bodyPart(new FileDataBodyPart("file", apk, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		MediaType contentType = MediaType.MULTIPART_FORM_DATA_TYPE;
		contentType = Boundary.addBoundary(contentType);

		return target
				.path("v2").path("upload").path("file")
				.request()
				.header("Authorization", authorizationHeaderValue)
				.post(Entity.entity(form, contentType), String.class);
	}

}
