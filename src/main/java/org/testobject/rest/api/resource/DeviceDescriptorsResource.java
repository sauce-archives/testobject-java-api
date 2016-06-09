package org.testobject.rest.api.resource;

import org.testobject.rest.api.model.DeviceDescriptor;

import java.util.List;

/**
 * Created by aluedeke on 11.06.15.
 */
public interface DeviceDescriptorsResource {
    List<DeviceDescriptor> listDevices();
}
