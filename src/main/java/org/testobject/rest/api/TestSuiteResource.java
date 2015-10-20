package org.testobject.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
        public Type type;

        @JsonProperty
        public Boolean typeRun;

        @JsonProperty
        public Set<String> typeToRun;

        @JsonProperty
        public Boolean annotationsRun;

        @JsonProperty
        public Set<String> annotations;

        @JsonProperty
        public Set<String> sizesToRun;

        @JsonProperty
        public Set<String> devices;

        @SuppressWarnings("unused")
        private InstrumentationTestSuiteRequest() {
        }

        public InstrumentationTestSuiteRequest(String appUploadId, String testUploadId) {
            this.appUploadId = appUploadId;
            this.testUploadId = testUploadId;
        }

        public InstrumentationTestSuiteRequest(String name, Type type, Boolean typeRun, Set<String> typeToRun, Boolean annotationsRun, Set<String> annotations, Set<String> sizesToRun, Set<String> devices) {
            this.name = name;
            this.type = type;
            this.typeRun = typeRun;
            this.typeToRun = typeToRun;
            this.annotations = annotations;
            this.annotationsRun = annotationsRun;
            this.sizesToRun = sizesToRun;
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
