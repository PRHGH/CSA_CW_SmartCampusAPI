/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa_cw_smartcampusapi.resources;

import com.mycompany.csa_cw_smartcampusapi.exceptions.DuplicateResourceException;
import com.mycompany.csa_cw_smartcampusapi.exceptions.LinkedResourceNotFoundException;
import com.mycompany.csa_cw_smartcampusapi.models.Room;
import com.mycompany.csa_cw_smartcampusapi.models.Sensor;
import com.mycompany.csa_cw_smartcampusapi.service.DataStore;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pamin
 */
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {
    
    @GET
    public Collection<Sensor> getAllSensors(@QueryParam("type") String type){
            
        if(type == null) {
            return DataStore.sensors.values();
        }
        
        return DataStore.sensors.values().stream().filter(sensor -> sensor.getType() != null && sensor.getType()
                .equalsIgnoreCase(type)).collect(Collectors.toList());
    }
    
    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") String id) {
        
        Sensor sensor = DataStore.sensors.get(id);
        
        if(sensor == null) {
            java.util.Map<String, Object> error = new java.util.HashMap<>();
                    error.put("error", "Not Found");
                    error.put("message", "Sensor not found");
                    error.put("status", 404);

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }
        
        return Response.ok(sensor).build();
    }
    
    @POST
    public Response createSensor(Sensor sensor) {
        
        if (DataStore.sensors.containsKey(sensor.getId())) {
            throw new DuplicateResourceException("Sensor with this ID already exists");
        }
        
        Room room = DataStore.rooms.get(sensor.getRoomId());
        
        if(room == null) {
            throw new LinkedResourceNotFoundException("Referenced room does not exist");
        }
        
        DataStore.sensors.put(sensor.getId(), sensor);
        room.getSensorIds().add(sensor.getId());
        
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }
    
    @Path("/{id}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("id") String id) {
        return new SensorReadingResource(id);
    }
}
