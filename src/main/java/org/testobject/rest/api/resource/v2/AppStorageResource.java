package org.testobject.rest.api.resource.v2;

import org.testobject.api.v2.InvalidUserInputServerException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Optional;

public class AppStorageResource {

	private final WebTarget target;

	public AppStorageResource(WebTarget target) {
		this.target = target;
	}

	public String uploadAppXcuiTest(String apiKey, File ipa) throws InvalidUserInputServerException {
		return uploadFile(apiKey, ipa, "XCUITEST");
	}

	public String uploadAppXcuiApp(String apiKey, File ipa) throws InvalidUserInputServerException {
		return uploadFile(apiKey, ipa, "NATIVE");
	}

	public String uploadAppAndroidTest(String apiKey, File apk) throws InvalidUserInputServerException {
		return uploadFile(apiKey, apk, "ANDROID_INSTRUMENTATION_TEST");
	}

	public String uploadAppAndroidApp(String apiKey, File apk) throws InvalidUserInputServerException {
		return uploadFile(apiKey, apk, "NATIVE");
	}

	private String uploadFile(String apiKey, File file, String type) throws InvalidUserInputServerException {
		String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(("user" + ":" + apiKey).getBytes());
		try {
			return target
					.path("storage").path("upload")
					.request()
					.header("Authorization", authorizationHeaderValue)
					.header("App-Type", type)
					.post(Entity.entity(Files.newInputStream(file.toPath()), MediaType.APPLICATION_OCTET_STREAM), String.class);
		} catch (BadRequestException e) {
			Response response = e.getResponse();

			String errorMessage = parseConventionalErrorMessage(response).orElseThrow(
				() -> new RuntimeException(e)
			);

			throw new InvalidUserInputServerException(errorMessage);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

	private Optional<String> parseConventionalErrorMessage(Response response) {
		try {
			HashMap responseBody = response.readEntity(HashMap.class);

			if (responseBody.containsKey("message")) {
				Object responseBodyObj = responseBody.get("message");

				if (responseBodyObj instanceof String) {
					String message = (String)responseBodyObj;

					return Optional.of(message);
				}
			}
		} catch (Exception errorBodyParsingException) {
			// Do nothing
		}

		return Optional.empty();
	}
}
