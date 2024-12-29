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
public class SocioDAO {

    public Socio getByName(Session s,String n) {
        Socio socio = null;
        try {
            Query consulta = s.createQuery("FROM Socio s WHERE s.nombre LIKE: parametro" , Socio.class);
            consulta.setParameter("parametro", n);
            socio = (Socio) consulta.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
        return socio;
    }
    
    public ArrayList<Socio> getAll(Session s){
        ArrayList<Socio> listaSocios = new ArrayList();
        try {
            Query consulta = s.createQuery("FROM Socio s" , Socio.class);
            listaSocios = (ArrayList<Socio>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
        return listaSocios;
    }
}
