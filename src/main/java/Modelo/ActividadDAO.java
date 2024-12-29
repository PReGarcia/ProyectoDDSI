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
    public ArrayList<Actividad> getAll(Session s){
        ArrayList<Actividad> listaActividades = new ArrayList();
        try{
            Query consulta = s.createQuery("FROM Actividad m" , Actividad.class);
            listaActividades = (ArrayList<Actividad>) consulta.getResultList();
        }catch(Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
        return listaActividades;
    }
    
    public ArrayList<Actividad> getByDiaCuota(Session s,String d, int c){
        ArrayList<Actividad> listaActividades = new ArrayList();
        try {
            Query consulta = s.createQuery("SELECT m FROM Actividad m WHERE m.dia= :dia AND m.precioBaseMes > :cuota" , Actividad.class);
            consulta.setParameter("dia", d);
            consulta.setParameter("cuota", c);
            listaActividades = (ArrayList<Actividad>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
        return listaActividades;
    }
}
