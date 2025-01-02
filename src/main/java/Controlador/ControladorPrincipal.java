/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author pareg
 */
public final class ControladorPrincipal implements ActionListener {

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
    private Transaction tr;

    public ControladorPrincipal(SessionFactory s) {
        vPrincipal = new VistaPrincipal();
        vInicio = new VistaInicio();
        vActividad = new VistaActividad();
        vSocio = new VistaSocio();
        vMonitor = new VistaMonitor();
        vMensaje = new VistaMensaje();
        cl = new CardLayout();

        sessionFactory = s;

        cm = new ControladorMonitor(vMonitor, sessionFactory);
        ca = new ControladorActividad(vActividad, sessionFactory);
        cs = new ControladorSocio(vSocio, sessionFactory);

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

    public void muestraPanel(String s) {
        cl.show(vPrincipal.getContentPane(), s);
    }

    private void addListeners() {
        vPrincipal.GestionMonitor.addActionListener(this);
        vPrincipal.GestionActividad.addActionListener(this);
        vPrincipal.GestionSocio.addActionListener(this);
        vPrincipal.Salir.addActionListener(this);
        vPrincipal.Inicio.addActionListener(this);
        vPrincipal.SocioActividad.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Inicio" -> {
                muestraPanel("Inicio");
            }
            case "Salir" -> {
                vMensaje.Mensaje(vPrincipal,false, "Salida correcta de la aplicaion");
                System.exit(0);
            }
            case "GestionActividad" -> {
                muestraPanel("Actividad");
                ca.tablasActividad();
            }
            case "GestionMonitor" -> {
                muestraPanel("Monitor");
                cm.tablasMonitor();
            }
            case "GestionSocio" -> {
                muestraPanel("Socio");
                vSocio.Actividades(false);
                cs.tablasSocio();
            }
            case "SocioActividad" -> {
                muestraPanel("Socio");
                vSocio.Actividades(true);
                cs.tablasSocio();
            }
        } 
    }
}
