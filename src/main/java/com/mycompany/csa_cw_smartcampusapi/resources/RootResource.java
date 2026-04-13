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
  
    @GET
    public Map<String, Object> getApiInfo() {

        Map<String, Object> response = new HashMap<>();
        response.put("version", "v1");
        response.put("adminContact", "admin@smartcampus.local");

        Map<String, String> resources = new HashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");

        response.put("resources", resources);

        return response;
    }
}
