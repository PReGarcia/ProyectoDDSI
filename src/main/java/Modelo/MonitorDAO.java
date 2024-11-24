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
    public List<Monitor> getAll(){
        List<Monitor> listaMonitores = new ArrayList();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createQuery("FROM Monitor m" , Monitor.class);
            listaMonitores = (ArrayList<Monitor>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaMonitores;
    }
    
    public Monitor getByNick(String n){
        Monitor monitor = new Monitor();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createQuery("FROM Monitor m WHERE nick='n'" , Monitor.class);
            monitor = (Monitor) consulta.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return monitor;
    }
}
