/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa_cw_smartcampusapi.resources;

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
    
    @POST
    public Response createSensor(Sensor sensor) {
        Room room = DataStore.rooms.get(sensor.getRoomId());
        
        if(room == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Referenced room does not exist").build();
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
