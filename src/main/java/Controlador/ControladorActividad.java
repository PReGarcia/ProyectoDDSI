/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pareg
 */
public class ControladorActividad {
    
    private ActividadDAO actividadDao;
    private Actividad actividad;
    private List<Actividad> listaActividades;
    
    public ControladorActividad(){
        actividadDao = new ActividadDAO();
        actividad = new Actividad();
        listaActividades = new ArrayList();
    }
    
    public List<Actividad> getByDiaCuota(String d, int c){
        listaActividades = actividadDao.getByDiaCuota(d,c);
        
        return listaActividades;
    }
}
