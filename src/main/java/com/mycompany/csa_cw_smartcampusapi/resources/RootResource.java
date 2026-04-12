/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa_cw_smartcampusapi.resources;

import com.mycompany.csa_cw_smartcampusapi.service.DataStore;
import com.mycompany.csa_cw_smartcampusapi.models.Room;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author pamin
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RootResource {
    
//    @GET
//    public Map<String, Object> getInfo() {
//        
//        Map<String, Object> response = new HashMap<>();
//        response.put("version", "v1");
//        response.put("message", "My Api is Running");
//        response.put("status", "OK");
//        return response;
//    } 
//    
//    @GET
//    public Room testRoom() {
//    
//        Room room = new Room("A1","Arena one", 150);
//        return room;
//    }
    
    @GET
    public Object testStore() {
        Room room = new Room("B1", "Basketball court one", 200);
        
        DataStore.rooms.put(room.getId(), room);
        
        return DataStore.rooms.values();
    }
}
