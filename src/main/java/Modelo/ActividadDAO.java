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
public class ActividadDAO {
    public List<Actividad> getByDiaCuota(String d, int c){
        List<Actividad> listaActividades = new ArrayList();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createQuery("SELECT m FROM Actividad m WHERE m.dia= :dia AND m.precioBaseMes > :cuota" , Actividad.class);
            consulta.setParameter("dia", d);
            consulta.setParameter("cuota", c);
            listaActividades = (ArrayList<Actividad>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaActividades;
    }
}
