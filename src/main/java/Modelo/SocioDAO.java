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

    ArrayList<Socio> listaSocios;
    Socio socio;

    public String getNextId() {
        int tam = listaSocios.size() + 1;
        String s = "S";
        if (tam < 100) {
            s = s + "0" + tam;
        } else {
            s = s + tam;
        }
        return s;
    }

    public Socio getByName(Session s, String n) throws Exception {
        Query consulta = s.createQuery("FROM Socio s WHERE s.nombre LIKE: parametro", Socio.class);
        consulta.setParameter("parametro", n);
        socio = (Socio) consulta.getSingleResult();

        return socio;
    }

    public ArrayList<Socio> getAll(Session s) throws Exception {
        Query consulta = s.createQuery("FROM Socio s", Socio.class);
        listaSocios = (ArrayList<Socio>) consulta.getResultList();

        return listaSocios;
    }
}
