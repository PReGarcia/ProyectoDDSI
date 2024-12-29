/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import Vista.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
/**
 *
 * @author pareg
 */
public class ControladorPrincipal implements ActionListener{
    
    private ControladorMonitor cm;
    private ControladorSocio cs;
    private ControladorActividad ca;
    
    private VistaPrincipal vPrincipal;
    private VistaInicio vInicio;
    private VistaActividad vActividad;
    private VistaSocio vSocio;
    private VistaMonitor vMonitor;
    private VistaMensaje vMensaje;
    private CardLayout cl;
    
    private SessionFactory sessionFactory;
    private Session sesion;
    private Transaction tr;
            
    
    public ControladorPrincipal(SessionFactory s){
        vPrincipal = new VistaPrincipal();
        vInicio = new VistaInicio();
        vActividad  = new VistaActividad();
        vSocio = new VistaSocio();
        vMonitor = new VistaMonitor();
        vMensaje = new VistaMensaje();
        cl = new CardLayout();
        
        cm = new ControladorMonitor(vMonitor);
        ca = new ControladorActividad(vActividad);
        cs = new ControladorSocio(vSocio);
        
        sessionFactory = s;
        
        vPrincipal.getContentPane().setLayout(cl);
        vPrincipal.add(vInicio, "Inicio");
        vPrincipal.add(vMonitor, "Monitor");
        vPrincipal.add(vSocio, "Socio");
        vPrincipal.add(vActividad, "Actividad");        

        vPrincipal.setLocationRelativeTo(null);
        vPrincipal.setVisible(true);

        addListeners();
        muestraPanel("Inicio");
    }
    
    public void muestraPanel(String s){
        cl.show(vPrincipal.getContentPane(), s);
    }
    
    private void addListeners(){
        vPrincipal.GestionMonitor.addActionListener(this);
        vPrincipal.GestionActividad.addActionListener(this);
        vPrincipal.GestionSocio.addActionListener(this);
        vPrincipal.Salir.addActionListener(this);
        vPrincipal.Inicio.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Inicio" ->{
                muestraPanel("Inicio");
            }
            case "Salir" ->{
                vMensaje.Mensaje(false,false,  "Salida correcta de la aplicaion");
                System.exit(0);
            }
            case "GestionActividad" ->{
                muestraPanel("Actividad");
                sesion = sessionFactory.openSession();
                ca.tablasActividad(sesion);
            }
            case "GestionMonitor"->{
                muestraPanel("Monitor");
                sesion = sessionFactory.openSession();
                cm.tablasMonitor(sesion);
            }
            case "GestionSocio"->{
                muestraPanel("Socio");
                sesion = sessionFactory.openSession();
                cs.tablasSocio(sesion);
            }
        }
    }
}
