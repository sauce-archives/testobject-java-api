package org.testobject.rest.api.resource;

import org.testobject.rest.api.model.DeviceDescriptor;
import org.testobject.rest.api.model.DeviceDescriptor.DeviceContainer;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceDescriptorsResource {

	private final WebTarget target;

	public DeviceDescriptorsResource(WebTarget target) {
		this.target = target;
	}

	public List<String> getAvailableDeviceDescriptorIds() {
		return target
				.path("descriptors")
				.path("availableDescriptors")
				.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<String>>() {
				});
	}

	public List<DeviceDescriptor> getAvailableDeviceDescriptors() {
		List<DeviceContainer> deviceDescriptors = target
				.path("descriptors")
				.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<DeviceContainer>>() {
				});

		return filterAvailable(deviceDescriptors);
	}

	private List<DeviceDescriptor> filterAvailable(List<DeviceContainer> descriptors) {
		List<String> availableDescriptorIds = getAvailableDeviceDescriptorIds();

		return descriptors.stream()
				.filter(deviceContainer -> availableDescriptorIds.contains(deviceContainer.id))
				.map(deviceContainer -> new DeviceDescriptor(deviceContainer, true))
				.collect(Collectors.toList());
	}

}
