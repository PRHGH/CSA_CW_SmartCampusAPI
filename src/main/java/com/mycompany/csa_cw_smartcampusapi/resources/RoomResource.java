/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa_cw_smartcampusapi.resources;

import com.mycompany.csa_cw_smartcampusapi.exceptions.DuplicateResourceException;
import com.mycompany.csa_cw_smartcampusapi.exceptions.ResourceNotFoundException;
import com.mycompany.csa_cw_smartcampusapi.exceptions.RoomNotEmptyException;
import com.mycompany.csa_cw_smartcampusapi.service.DataStore;
import com.mycompany.csa_cw_smartcampusapi.models.Room;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pamin
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {
    
    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }
    
    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {
        
        Room room = DataStore.rooms.get(id);
        if(room == null){
            throw new ResourceNotFoundException("Room not found");
        }
        
        return Response.ok(room).build();
    }
    
    @POST
    public Response createRoom(Room room) {
        
        DataStore.rooms.put(room.getId(), room);
        
        if (DataStore.rooms.containsKey(room.getId())) {
            throw new DuplicateResourceException("Room with this ID already exists");
        }
        
        return Response.status(Response.Status.CREATED).entity(room).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {
        Room room = DataStore.rooms.get(id);
        
        if(room == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        if(room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room still has assigned sensors");
        }
        
        DataStore.rooms.remove(id);
        
//        return Response.status(Response.Status.NO_CONTENT).build();
        return Response.noContent().build();
    }
}
