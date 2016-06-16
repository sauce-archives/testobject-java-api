package org.testobject.rest.api.appium.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {

    private final String className;
    private final String methodName;
    private final String deviceId;

    @JsonCreator
    public Test(@JsonProperty("className") String className,
                @JsonProperty("methodName") String methodName,
                @JsonProperty("deviceId") String deviceId) {
        this.className = className;
        this.methodName = methodName;
        this.deviceId = deviceId;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Test test = (Test) o;

        if (!className.equals(test.className)) return false;
        if (!methodName.equals(test.methodName)) return false;
        return deviceId.equals(test.deviceId);
    }

    @Override
    public int hashCode() {
        int result = className.hashCode();
        result = 31 * result + methodName.hashCode();
        result = 31 * result + deviceId.hashCode();

        return result;
    }

}
