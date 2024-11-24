/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import Modelo.SocioDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pareg
 */
public class ControladorSocio {
    
    private SocioDAO socioDao;
    private Socio socio;
    private List<Socio> listaSocios;
    
    
    public ControladorSocio(){
        socioDao = new SocioDAO();
        socio = new Socio();
        listaSocios = new ArrayList();
    }
    
    public Socio getByName(String n){
       socio = socioDao.getByName(n);
       
       return socio;
    }
    
    public List<Socio> getAllHQL(){      
        listaSocios = socioDao.getAllHQL();
        
        return listaSocios;
    }
    
    public List<Socio> getAllSQL(){
        listaSocios = socioDao.getAllSQL();
        
        return listaSocios;
    }
    
    public List<Socio> getAllConsultaNombrada(){
        listaSocios = socioDao.getAllConsultaNombrada();
        
        return listaSocios;
    }
    
    public List<Object[]> getNomTel(){     
        List<Object[]> listaObjetos = socioDao.getNomTel();
        
        return listaObjetos;
    }
    
    public List<Socio> getByCategoria(char cat){     
        listaSocios = socioDao.getByCategoria(cat);
        
        return listaSocios; 
    }
    
    public List<Socio> getByCategoriaNamedQuery(char c){
        listaSocios = socioDao.getByCategoriaNamedQuery(c);
        
        return listaSocios; 
    }
}
