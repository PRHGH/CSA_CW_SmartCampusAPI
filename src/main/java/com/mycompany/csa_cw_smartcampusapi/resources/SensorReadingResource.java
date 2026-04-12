/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa_cw_smartcampusapi.resources;

import com.mycompany.csa_cw_smartcampusapi.exceptions.SensorUnavailableException;
import com.mycompany.csa_cw_smartcampusapi.models.Sensor;
import com.mycompany.csa_cw_smartcampusapi.service.DataStore;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pamin
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {
    
    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }
    
    @GET
    public List<SensorReading> getReadings() {
        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }
    
    @POST
    public Response addReading(SensorReading reading) {
        
        Sensor sensor = DataStore.sensors.get(sensorId);
        
        if(sensorId == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Sensor Not Found").build();
        }
        
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is under maintenance and cannot accept readings");
        }
        
        DataStore.readings.computeIfAbsent(sensorId, key -> new ArrayList<>()).add(reading);
        
        sensor.setCurrentValue(reading.getValue());
        
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
