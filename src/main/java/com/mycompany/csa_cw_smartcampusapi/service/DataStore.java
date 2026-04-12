/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.csa_cw_smartcampusapi.service;

import com.mycompany.csa_cw_smartcampusapi.models.Room;
import com.mycompany.csa_cw_smartcampusapi.models.Sensor;
import com.mycompany.csa_cw_smartcampusapi.resources.SensorReading;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pamin
 */
public class DataStore {
    
    public static Map<String,Room> rooms = new HashMap<>();
    
    public static Map<String, Sensor> sensors = new HashMap<>();
    
    public static Map<String, List<SensorReading>> readings = new HashMap<>();
    
}
