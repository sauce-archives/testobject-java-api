package org.testobject.rest.api;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.testobject.rest.api.date.LocalDateTime;

import java.util.List;



public class SessionReport {


    private final String id, projectId, userId, deviceDescriptorId, usage;
    private final long appId;
    private final long frameworkAppId;
    private final String testFrameworkType;
    private final String testFrameworkVersion;
    private final List<Integer> testReportIds;
    private final List<Integer> testIds;
    private final long batchId;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final long durationInSeconds;

    @JsonCreator
    public SessionReport(@JsonProperty("id") String id, @JsonProperty("projectId") String projectId,
                         @JsonProperty("userId") String userId, @JsonProperty("deviceDescriptorId") String deviceDescriptorId,
                         @JsonProperty("usage") String usage, @JsonProperty("appId") long appId,
                         @JsonProperty("frameworkAppId") long frameworkAppId, @JsonProperty("testFrameworkType") String testFrameworkType,
                         @JsonProperty("testFrameworkVersion") String testFrameworkVersion, @JsonProperty("testReportIds") List<Integer> testReportIds,
                         @JsonProperty("testIds") List<Integer> testIds, @JsonProperty("batchId") long batchId,
                         @JsonProperty("startDateTime") LocalDateTime startDateTime, @JsonProperty("endDateTime") LocalDateTime endDateTime,
                         @JsonProperty("durationInSeconds") long durationInSeconds) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.deviceDescriptorId = deviceDescriptorId;
        this.usage = usage;
        this.appId = appId;
        this.frameworkAppId = frameworkAppId;
        this.testFrameworkType = testFrameworkType;
        this.testFrameworkVersion = testFrameworkVersion;
        this.testReportIds = testReportIds;
        this.testIds = testIds;
        this.batchId = batchId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.durationInSeconds = durationInSeconds;
    }


    public String getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDeviceDescriptorId() {
        return deviceDescriptorId;
    }

    public String getUsage() {
        return usage;
    }

    public long getAppId() {
        return appId;
    }

    public long getFrameworkAppId() {
        return frameworkAppId;
    }

    public String getTestFrameworkType() {
        return testFrameworkType;
    }

    public String getTestFrameworkVersion() {
        return testFrameworkVersion;
    }

    public List<Integer> getTestReportIds() {
        return testReportIds;
    }

    public List<Integer> getTestIds() {
        return testIds;
    }

    public long getBatchId() {
        return batchId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public long getDurationInSeconds() {
        return durationInSeconds;
    }


}
