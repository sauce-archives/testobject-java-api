package org.testobject.rest.api;


import org.testobject.rest.api.model.DeviceDescriptor;
import org.testobject.rest.api.resource.DeviceDescriptorsResource;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aluedeke on 11.06.15.
 */
public class DeviceDescriptorsResourceImpl implements DeviceDescriptorsResource {

    private final WebTarget target;

    public DeviceDescriptorsResourceImpl(WebTarget target) {
        this.target = target;
    }

    @Override
    public List<DeviceDescriptor> listDevices() {

        List<DeviceDescriptor.DeviceContainer> deviceList = target.path("descriptors").request(MediaType.APPLICATION_JSON).get(new GenericType<List<DeviceDescriptor.DeviceContainer>>() {});
        List<String> available = target.path("descriptors/availableDescriptors").request(MediaType.APPLICATION_JSON).get(new GenericType<List<String>>(){});

        List<DeviceDescriptor> devices = new LinkedList<DeviceDescriptor>();
        for (DeviceDescriptor.DeviceContainer deviceContainer : deviceList) {
            devices.add(new DeviceDescriptor(deviceContainer, available.contains(deviceContainer.id)));
        }

        return devices;
    }

    @Override
    public DeviceDescriptor getDeviceDescriptorForSession(String sessiontId) {
        return target.path("v2").path("appium").path("session").path(sessiontId).path("device")
                .request(MediaType.APPLICATION_JSON)
                .get(DeviceDescriptor.class);
    }
}
