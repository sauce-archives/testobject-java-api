package org.testobject.rest.api.resource.v2;

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
		return uploadFile(apiKey, ipa, "XCUITEST");
	}

	public String uploadAppXcuiApp(String apiKey, File ipa) {
		return uploadFile(apiKey, ipa, "NATIVE");
	}

	public String uploadAppAndroidTest(String apiKey, File apk) {
		return uploadFile(apiKey, apk, "ANDROID_INSTRUMENTATION_TEST");
	}

	public String uploadAppAndroidApp(String apiKey, File apk) {
		return uploadFile(apiKey, apk, "NATIVE");
	}

	private String uploadFile(String apiKey, File file, String type) {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());
		try {
			return target
					.path("storage").path("upload")
					.request()
					.header("Authorization", authorizationHeaderValue)
					.header("App-Type", type)
					.post(Entity.entity(Files.newInputStream(file.toPath()), MediaType.APPLICATION_OCTET_STREAM), String.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

}
