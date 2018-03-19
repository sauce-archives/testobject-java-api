package org.testobject.rest.api.resource;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AppStorageResource {

	private final WebTarget target;

	public AppStorageResource(WebTarget target) {
		this.target = target;
	}

	public String uploadAppXcuiTest(String apiKey, File ipa) {
		return uploadXCUITestIPA(apiKey, ipa, "XCUITEST");
	}

	public String uploadAppXcuiApp(String apiKey, File ipa) {
		return uploadXCUITestIPA(apiKey, ipa, "NATIVE");
	}

	private String uploadXCUITestIPA(String apiKey, File ipa, String type) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());
		try {
			return target
					.path("storage").path("upload")
					.request()
					.header("Authorization", authorizationHeaderValue)
					.header("App-Type", type)
					.post(Entity.entity(Files.newInputStream(ipa.toPath()), MediaType.APPLICATION_OCTET_STREAM), String.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

}
