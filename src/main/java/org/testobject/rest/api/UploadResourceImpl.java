package org.testobject.rest.api;

import java.io.File;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class UploadResourceImpl implements UploadResource {
	
	private final WebResource resource;

	public UploadResourceImpl(WebResource resource) {
		this.resource = resource;
	}

	@Override
	public String uploadFile(String user, String project, File apk) {
		FormDataMultiPart form = new FormDataMultiPart();
        form.field("userId", user);
        form.field("projectId", project);
        form.bodyPart(new FileDataBodyPart("file", apk, MediaType.APPLICATION_OCTET_STREAM_TYPE));

        return resource.path("upload").path("file").type(MediaType.MULTIPART_FORM_DATA).post(String.class, form);
	}

}
