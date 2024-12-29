/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.VistaMensaje;
import Vista.VistaSocio;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author pareg
 */
public class ControladorSocio {
    
    private VistaSocio vs;
    private VistaMensaje vMensaje;
    private SocioDAO socioDao;
    private Socio socio;
    private ArrayList<Socio> listaSocios;
    
    
    public ControladorSocio(VistaSocio vSocio){
        vs = vSocio;
        vMensaje = new VistaMensaje();
        
        socioDao = new SocioDAO();
        socio = new Socio();
        listaSocios = new ArrayList();
        
        GestionTablas.inicializarTablaSocio(vs);
    }
    
     public Socio getByName(Session s,String n){
       socio = socioDao.getByName(s,n);
       
       return socio;
    }
    
    public ArrayList<Socio> getAll(Session s){      
        listaSocios = socioDao.getAll(s);
        return listaSocios;
    }
    
    
    public void tablasSocio(Session s){
        GestionTablas.dibujarTablaSocio(vs);
        Transaction tr = s.beginTransaction();
        try {
            ArrayList<Socio> lSocios = getAll(s);
            GestionTablas.vaciarTablaSocio();
            GestionTablas.rellenarTablaSocio(lSocios);
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(false,true, "Error en la petición de monitores\n" + ex.getMessage());
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
    }
    
   
}
