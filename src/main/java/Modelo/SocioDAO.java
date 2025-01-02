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
    
    public void insertaActualizaSocio(Session sesion, Socio s) throws Exception{
        sesion.saveOrUpdate(s);
    }
    
    public boolean esVacio(Socio s){
        return s.getCorreo().isEmpty() || s.getTelefono().isEmpty() || s.getFechaNacimiento().isEmpty() || s.getFechaEntrada().isEmpty() || s.getNombre().isEmpty() || s.getDni().isEmpty();
    }
    
    public void borrarSocio(Session sesion, Socio s) throws Exception{
        System.out.println(sesion);
        sesion.delete(s);
        
    }

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

    public ArrayList<Socio> getAll(Session s) throws Exception {
        Query consulta = s.createQuery("FROM Socio s", Socio.class);
        listaSocios = (ArrayList<Socio>) consulta.getResultList();

        return listaSocios;
    }
   
}
