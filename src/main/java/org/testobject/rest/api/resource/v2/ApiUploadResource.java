package org.testobject.rest.api.resource.v2;

import org.glassfish.jersey.media.multipart.Boundary;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.File;

import static java.util.Base64.getEncoder;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA_TYPE;

public class ApiUploadResource {

	private final WebTarget target;

	public ApiUploadResource(WebTarget target) {
		this.target = target;
	}

	public String uploadFile(String apiKey, File apk) {
		String apiKeyHeader = "Basic " + getEncoder().encodeToString((":" + apiKey).getBytes());

		FormDataMultiPart form = new FormDataMultiPart();
		form.bodyPart(new FileDataBodyPart("file", apk, APPLICATION_OCTET_STREAM_TYPE));

		MediaType contentType = MULTIPART_FORM_DATA_TYPE;
		contentType = Boundary.addBoundary(contentType);

		return target
				.path("v2")
				.path("upload")
				.path("file")
				.request()
				.header("Authorization", apiKeyHeader)
				.post(Entity.entity(form, contentType), String.class);
	}

}
