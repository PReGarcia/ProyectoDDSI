/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pareg
 */
public class ControladorMonitor {
    
    private MonitorDAO monitorDao;
    private Monitor monitor;
    private List<Monitor> listaMonitores;
    
    public ControladorMonitor(){
        monitorDao = new MonitorDAO();
        monitor = new Monitor();
        listaMonitores = new ArrayList();
    }
    
    
    public List<Monitor> getAll(){      
        listaMonitores = monitorDao.getAll();
        
        return listaMonitores;
    }
    
    public Monitor getByNick(String n){
        monitor = monitorDao.getByNick(n);
        
        return monitor;
    }
}
