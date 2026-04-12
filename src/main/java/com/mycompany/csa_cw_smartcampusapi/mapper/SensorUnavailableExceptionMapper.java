/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa_cw_smartcampusapi.mapper;

import com.mycompany.csa_cw_smartcampusapi.exceptions.SensorUnavailableException;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author pamin
 */
@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException exception) {

        Map<String, Object> error = new HashMap<>();
        error.put("error", "Sensor Unavailable");
        error.put("message", exception.getMessage());
        error.put("status", 403);

        return Response.status(Response.Status.FORBIDDEN)
                .entity(error)
                .build();
    }
}
