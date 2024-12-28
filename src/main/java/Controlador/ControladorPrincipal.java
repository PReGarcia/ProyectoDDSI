/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import java.util.List;
/**
 *
 * @author pareg
 */
public class ControladorPrincipal {
    
    private Socio socio;
    private Monitor monitor;
    private Actividad actividad;
    private ControladorSocio controladorSocio;
    private ControladorActividad controladorActividad;
    private ControladorMonitor controladorMonitor;
    
    public ControladorPrincipal(){
        controladorSocio = new ControladorSocio();
        controladorMonitor = new ControladorMonitor();
        controladorActividad = new ControladorActividad();
        socio = new Socio();
        monitor = new Monitor();
        actividad = new Actividad();
    }
    
    public <T> void mostrarTodos(List<T> lista){
        for (T elemento : lista){
            System.out.println(elemento);
        }
    }
    
    public void mostrarSociosHQL(){
        mostrarTodos(controladorSocio.getAllHQL());
    }
    
    public void mostrarSociosSQL(){
        mostrarTodos(controladorSocio.getAllSQL());
    }
    
    public void mostrarSociosConsultaNombrada(){
        mostrarTodos(controladorSocio.getAllConsultaNombrada());
    }
    
    public void mostrarNomTelSocios(){
        List<Object[]> listaSocios = controladorSocio.getNomTel();
        
        for (Object[] socioFetch : listaSocios){
            System.out.println("Nombre: " + socioFetch[0] + '\n' + "Telefono: " + socioFetch[1] + '\n');
        }
    }
    
    public void mostrarMonitorNick(String n){
        System.out.println("Nombre: " + controladorMonitor.getByNick(n).getNombre() + '\n');
    }
    
    public void mostrarNombreCategoria(char cat){
        List<Socio> listaSocios = controladorSocio.getByCategoria(cat);
        
        for (Socio socioFetch : listaSocios){
            System.out.println("Nombre: " + socioFetch.getNombre() + '\n' + "Telefono: " + socioFetch.getCategoria() + '\n');
        }
    }
    
    public void mostrarSocioPorNombre(String n){
        socio = controladorSocio.getByName(n);
        
        System.out.println(socio);
    }
            
    public void mostrarActividadDiaCuota(String d, int c){

        List<Actividad> listaActividades = controladorActividad.getByDiaCuota(d,c);
        
        for (Actividad actividadFetch : listaActividades){
            System.out.println(actividadFetch);
        }
    }
    public void mostrarCategoriaNamedQuery(char c){
 
        List<Socio> listaSocios = controladorSocio.getByCategoriaNamedQuery(c);
        
        for (Socio socioFetch : listaSocios){
            System.out.println(socioFetch);
        }
    }
}
