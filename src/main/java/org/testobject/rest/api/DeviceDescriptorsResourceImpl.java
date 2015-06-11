package org.testobject.rest.api;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aluedeke on 11.06.15.
 */
public class DeviceDescriptorsResourceImpl implements DeviceDescriptorsResource {

    private final WebResource resource;

    public DeviceDescriptorsResourceImpl(WebResource resource) {
        this.resource = resource;
    }

    @Override
    public List<DeviceDescriptor> listDevices() {

        List<DeviceDescriptor.DeviceContainer> deviceList = resource.path("descriptors").get(new GenericType<List<DeviceDescriptor.DeviceContainer>>() {});
        List<String> available = resource.path("descriptors/availableDescriptors").get(new GenericType<List<String>>() {});

        List<DeviceDescriptor> devices = new LinkedList<DeviceDescriptor>();
        for (DeviceDescriptor.DeviceContainer deviceContainer : deviceList) {
            devices.add(new DeviceDescriptor(deviceContainer, available.contains(deviceContainer.id)));
        }

        return devices;
    }
}
