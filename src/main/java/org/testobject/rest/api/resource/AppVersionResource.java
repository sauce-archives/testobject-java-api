package org.testobject.rest.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("users/{user}/projects/{project}/appVersions")
public interface AppVersionResource {

	@XmlRootElement
	class CreateAppVersionRequest {

		@XmlElement
		public final String type = "NATIVE";

		@XmlElement
		public final String usage = "TESTING";

		@XmlElement
		public String uploadId;

		@SuppressWarnings("unused")
		private CreateAppVersionRequest() {
		}

		public CreateAppVersionRequest(String uploadId) {
			this.uploadId = uploadId;
		}

	}

	@POST
	void createAppVersion(@PathParam("userId") String userId, @PathParam("projectId") String projectId,
			CreateAppVersionRequest request);

}
