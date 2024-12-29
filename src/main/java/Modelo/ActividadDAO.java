/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pareg
 */
public class ActividadDAO {
    ArrayList<Actividad> listaActividades;
    Actividad actividad;
    
    public String getNextId() {
        int tam = listaActividades.size() + 1;
        String s = "A";
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
    
    public ArrayList<Actividad> getByDiaCuota(Session s,String d, int c) throws Exception{
        Query consulta = s.createQuery("SELECT m FROM Actividad m WHERE m.dia= :dia AND m.precioBaseMes > :cuota" , Actividad.class);
        consulta.setParameter("dia", d);
        consulta.setParameter("cuota", c);
        listaActividades = (ArrayList<Actividad>) consulta.getResultList();
        return listaActividades;
    }
}
