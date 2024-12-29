/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Config.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pareg
 */
public class MonitorDAO {
    public ArrayList<Monitor> getAll(Session s){
        ArrayList<Monitor> listaMonitores = new ArrayList();
        try {
            Query consulta = s.createQuery("FROM Monitor m" , Monitor.class);
            listaMonitores = (ArrayList<Monitor>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
        return listaMonitores;
    }
    
    public Monitor getByNick(Session s, String n){
        Monitor monitor = new Monitor();
         try {
            Query consulta = s.createQuery("FROM Monitor m WHERE nick='n'" , Monitor.class);
            monitor = (Monitor) consulta.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
        return monitor;
    }
}
