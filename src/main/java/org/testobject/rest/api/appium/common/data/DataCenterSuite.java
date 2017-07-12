package org.testobject.rest.api.appium.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCenterSuite {
	public Set<String> deviceDescriptorIds;
	public URL dataCenterURL;
	public String dataCenterId;

	@JsonCreator
	public DataCenterSuite(
			@JsonProperty("dataCenterId") String dataCenterId,
			@JsonProperty("dataCenterURL") URL dataCenterURL,
			@JsonProperty("deviceIds") Set<String> deviceDescriptorIds) {
		this.dataCenterId = dataCenterId;
		this.dataCenterURL = dataCenterURL;
		this.deviceDescriptorIds = deviceDescriptorIds;
	}

	public Set<String> getDeviceDescriptorIds() {
		return deviceDescriptorIds;
	}

	public URL getDataCenterURL() {
		return dataCenterURL;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	@Override public String toString() {
		return "DataCenterSuite{" +
				"deviceDescriptorIds=" + deviceDescriptorIds +
				", dataCenterURL=" + dataCenterURL +
				", dataCenterId='" + dataCenterId + '\'' +
				'}';
	}
}
