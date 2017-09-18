package org.testobject.rest.api.resource;

import org.testobject.rest.api.model.DeviceDescriptor;

import java.util.List;

public interface DeviceDescriptorsResource {

	List<DeviceDescriptor> listDevices();

	DeviceDescriptor.DeviceContainer getDeviceDescriptorForSession(String sessiontId);

}
