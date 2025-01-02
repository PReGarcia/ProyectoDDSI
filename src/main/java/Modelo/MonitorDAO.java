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
public class MonitorDAO {

    Monitor monitor;
    ArrayList<Monitor> listaMonitores;
    Query consulta;
    
    public boolean esVacio(Monitor m){
        return m.getCorreo().isEmpty() || m.getDni().isEmpty() || m.getNick().isEmpty() || m.getFechaEntrada().isEmpty() || m.getNombre().isEmpty() || m.getTelefono().isEmpty();
    }

    public String getNextId() {
        int tam = listaMonitores.size() + 1;
        String s = "M";
        if (tam < 100) {
            s = s + "0" + tam;
        } else {
            s = s + tam;
        }
        return s;
    }

    public void borrarMonitor(Session s, Monitor m) throws Exception {
        s.delete(m);
    }

    public void insertaActualizaMonitor(Session s, Monitor m) throws Exception {
        s.saveOrUpdate(m);
    }
    
    public ArrayList<Monitor> getAll(Session s) throws Exception {
        consulta = s.createQuery("FROM Monitor m", Monitor.class);
        listaMonitores = (ArrayList<Monitor>) consulta.getResultList();

        return listaMonitores;
    }

    public Monitor getByCodigo(Session s, String n) throws Exception {
        consulta = s.createQuery("FROM Monitor m WHERE m.codMonitor= :n", Monitor.class).setParameter("n",n);
        monitor = (Monitor) consulta.getSingleResult();

        return monitor;
    }
    
    public Monitor getByName(Session s, String n) throws Exception {
        consulta = s.createQuery("FROM Monitor m WHERE m.nombre = :n", Monitor.class).setParameter("n", n);
        monitor = (Monitor) consulta.getSingleResult();

        return monitor;
    }
}
