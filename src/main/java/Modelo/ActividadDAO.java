/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pareg
 */
public class ActividadDAO {
    ArrayList<Actividad> listaActividades;
    Actividad actividad;
    
    public boolean esVacio(Actividad actividad){
        if(actividad == null)
            return true;
        return actividad.getDia().isEmpty() || actividad.getNombre().isEmpty() || actividad.getDescripcion().isEmpty();
    }
    
    public void insertaActualizaActividad(Session sesion, Actividad a) throws LazyInitializationException{
        sesion.saveOrUpdate(a);
    }
    
    public void borrarActividad(Session sesion, Actividad a) throws Exception{
        sesion.delete(a);
    }
    
    public String getNextId() {
        int tam = listaActividades.size() + 1;
        String s = "AC";
        if (tam < 100) {
            s = s + "0" + tam;
        } else {
            s = s + tam;
        }
        return s;
    }
    
    public ArrayList<Actividad> getAll(Session s) throws Exception{
        Query consulta = s.createQuery("FROM Actividad m" , Actividad.class);
        listaActividades = (ArrayList<Actividad>) consulta.getResultList();
        
        return listaActividades;
    }
    
    public Actividad getByName(Session s, String n){
        Query consulta = s.createQuery("From Actividad a where a.nombre = :n", Actividad.class).setParameter("n", n);
        actividad = (Actividad)consulta.getSingleResult();
        
        return actividad;
    }
}
