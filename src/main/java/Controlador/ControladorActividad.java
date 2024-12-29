/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Vista.VistaActividad;
import Vista.VistaMensaje;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author pareg
 */
public class ControladorActividad {
    private VistaActividad va;
    private VistaMensaje vMensaje;
    private ActividadDAO actividadDao;
    private Actividad actividad;
    private ArrayList<Actividad> listaActividades;
    
    public ControladorActividad(VistaActividad vActividad){
        va = vActividad;
        vMensaje = new VistaMensaje();
        
        actividadDao = new ActividadDAO();
        actividad = new Actividad();
        listaActividades = new ArrayList();
        
        GestionTablas.inicializarTablaActividad(va);
    }
    
    public ArrayList<Actividad> getAll(Session s){
        listaActividades = actividadDao.getAll(s);
        
        return listaActividades;
    }
    
    public ArrayList<Actividad> getByDiaCuota(Session s,String d, int c){
        listaActividades = actividadDao.getByDiaCuota(s,d,c);
        
        return listaActividades;
    }
    
    public void tablasActividad(Session s){
        GestionTablas.dibujarTablaActividad(va);
        Transaction tr = s.beginTransaction();
        try {
            ArrayList<Actividad> lActividades = getAll(s);
            GestionTablas.vaciarTablaActividad();
            GestionTablas.rellenarTablaActividad(lActividades);
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(false,true, "Error en la petición de monitores\n" + ex.getMessage());
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
    }
    
    
}
