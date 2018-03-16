package org.testobject.rest.api.resource;

import org.glassfish.jersey.media.multipart.Boundary;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.File;

public class AppStorageResource {

	private final WebTarget target;

	public AppStorageResource(WebTarget target) {
		this.target = target;
	}

	public String uploadAppXcuiTest(String apiKey, File ipa) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());
		FormDataMultiPart form = new FormDataMultiPart();
		form.bodyPart(new FileDataBodyPart("file", ipa, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		MediaType contentType = MediaType.MULTIPART_FORM_DATA_TYPE;
		contentType = Boundary.addBoundary(contentType);

		return target
				.path("storage").path("upload")
				.request()
				.header("Authorization", authorizationHeaderValue)
				.header("App-Type", "XCUITEST")
				.post(Entity.entity(form, contentType), String.class);
	}

}
