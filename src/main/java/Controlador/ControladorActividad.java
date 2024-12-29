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

    public ControladorActividad(VistaActividad vActividad) {
        va  = vActividad;
        vMensaje = new VistaMensaje();

        actividadDao = new ActividadDAO();
        actividad = new Actividad();
        listaActividades = new ArrayList();

        GestionTablas.inicializarTablaActividad(va);
    }

    public ArrayList<Actividad> getAll(Session s) {
        try{
            listaActividades = actividadDao.getAll(s);
        }catch(Exception ex){
            vMensaje.Mensaje(true, "Error en la consulta");
        }
        
        return listaActividades;
    }

    public ArrayList<Actividad> getByDiaCuota(Session s, String d, int c) {
        try{
            listaActividades = actividadDao.getByDiaCuota(s, d, c);
        }catch(Exception ex){
            vMensaje.Mensaje(true, "Error en la consulta");
        }

        return listaActividades;
    }

    public void tablasActividad(Session s) {
        GestionTablas.dibujarTablaActividad(va);
        ArrayList<Actividad> lActividades = getAll(s);
        GestionTablas.vaciarTablaActividad();
        GestionTablas.rellenarTablaActividad(lActividades);
    }

}
