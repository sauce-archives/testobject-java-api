package org.testobject.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.Set;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("users/{user}/projects/{project}/batches")
public interface TestSuiteResource {
    public enum Type {
        TEST_CLASS, TEST_CASE, TEST_PACKAGE
    }

    public class InstrumentationTestSuiteRequest {

        @JsonProperty
        public String appUploadId;

        @JsonProperty
        public String testUploadId;

        @JsonProperty
        public String name;

        @JsonProperty
        public Map<String, String> configuration;

        @JsonProperty
        public Set<String> devices;

        @SuppressWarnings("unused")
        private InstrumentationTestSuiteRequest() {
        }

        public InstrumentationTestSuiteRequest(String appUploadId, String testUploadId) {
            this.appUploadId = appUploadId;
            this.testUploadId = testUploadId;
        }

        public InstrumentationTestSuiteRequest(String name, Map<String, String> configuration, Set<String> devices) {
            this.name = name;
            this.configuration = configuration;
            this.devices = devices;
        }
    }

    @PUT
    @Path("instrumentation/{testSuite}")
    public void updateInstrumentationTestSuite(@PathParam("user") String user, @PathParam("project") String project,
                                               @PathParam("testSuite") long testSuite,
                                               InstrumentationTestSuiteRequest request);

    @POST
    @Path("instrumentation/{testSuite}/replay")
    public long runInstrumentationTestSuite(@PathParam("user") String user, @PathParam("project") String project, @PathParam("testSuite") long testSuite);

    @PUT
    @Path("instrumentation/newSuite/{testSuite}")
    public Long createInstrumentationTestSuite(@PathParam("user") String user, @PathParam("project") String project,
                                               @PathParam("testSuite") long testSuite,
                                               InstrumentationTestSuiteRequest request);
}
