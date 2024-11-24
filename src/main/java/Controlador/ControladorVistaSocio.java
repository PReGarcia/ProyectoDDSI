/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import Vista.VistaSocio;
import java.util.List;

/**
 *
 * @author pareg
 */
public class ControladorVistaSocio {
    private VistaSocio vista;
    private ControladorSocio controlador;
    
    public ControladorVistaSocio(List<Socio> listaSocios){
        vista = new VistaSocio();
        controlador = new ControladorSocio();
        rellenarTabla(listaSocios);
        vista.setVisible(true);
    }
    
    public void rellenarTabla(List<Socio> listaSocios){
        for(Socio socio: listaSocios){
            Object[] fila = new Object[8];
            fila[0] = socio.getNumeroSocio();
            fila[1] = socio.getNombre();
            fila[2] = socio.getDni();
            fila[3] = socio.getFechaNacimiento();
            fila[4] = socio.getTelefono();
            fila[5] = socio.getCorreo();
            fila[6] = socio.getFechaEntrada();
            fila[7] = socio.getCategoria();
            vista.agregarSocio(fila);
        }
        
        vista.rellenarTabla();
    }
}
