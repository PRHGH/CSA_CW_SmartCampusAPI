/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa_cw_smartcampusapi.config;
    
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

/**
 *
 * @author pamin
 */
    
@ApplicationPath("/api/v1")
public class AppConfig extends ResourceConfig{
    
    public AppConfig() {
        packages("com.mycompany.csa_cw_smartcampusapi");
    }
}
