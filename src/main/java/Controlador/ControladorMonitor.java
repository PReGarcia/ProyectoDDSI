/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Config.HibernateUtil;
import Modelo.Monitor;
import Modelo.MonitorDAO;
import Vista.VistaFormulario;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author pareg
 */
public class ControladorMonitor implements ActionListener {

    private MonitorDAO monitorDao;
    private Monitor monitor;

    private ArrayList<Monitor> listaMonitores;

    private VistaMonitor vm;
    private VistaMensaje vMensaje;
    private VistaFormulario vFormulario;

    private SessionFactory sessionFactory;
    private Session sesion;
    private Transaction tr;
    

    public ControladorMonitor(VistaMonitor vMonitor, SessionFactory s) {
        monitorDao = new MonitorDAO();
        
        sessionFactory = s;
        sesion = sessionFactory.openSession();
        listaMonitores = new ArrayList();

        vFormulario = new VistaFormulario();
        vm = vMonitor;
        vMensaje = new VistaMensaje();

        addListeners();

        GestionTablas.inicializarTablaMonitor(vm);
    }

    private void addListeners() {
        vm.Eliminar.addActionListener(this);
        vm.Actualizar.addActionListener(this);
        vm.Insertar.addActionListener(this);
    }

    private ArrayList<Monitor> getAll(Session s) {
        try {
            listaMonitores = monitorDao.getAll(s);
        } catch (Exception ex) {
            vMensaje.Mensaje(true, "Error en la consulta");
        }
        return listaMonitores;
    }

    private void altaMonitor(Monitor m) {
        sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            tr = sesion.beginTransaction();
            monitor = new Monitor();

            monitorDao.insertaMonitor(sesion, monitor);
            tr.commit();
            vMensaje.Mensaje(false, "Monitor insertado correctamente");
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(true, "Error en la inserción del monitor");
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    private void borrarMonitor(String id){
        sesion = HibernateUtil.getSessionFactory().openSession();
        tr = sesion.beginTransaction();
        try {
            monitor = (Monitor) monitorDao.getByCodigo(sesion, id);
 
            monitorDao.borrarMonitor(sesion, monitor);
            
            
            tr.commit();
            vMensaje.Mensaje(false, "Monitor borrado correctamente");
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(true, "Error en el borrado del monitor");
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void tablasMonitor(Session s) {
        GestionTablas.dibujarTablaMonitor(vm);
        ArrayList<Monitor> lMonitores = getAll(s);
        GestionTablas.vaciarTablaMonitor();
        GestionTablas.rellenarTablaMonitor(lMonitores);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Eliminar" -> {
                if (vm.jTableMonitor.getSelectedRow() == -1) {
                    vMensaje.Mensaje(true, "Selecciona primero una columna");
                } else {
                    if (vMensaje.Confirm("¿Estás seguro de eliminar a "+ vm.jTableMonitor.getValueAt(vm.jTableMonitor.getSelectedRow(),0) + vm.jTableMonitor.getValueAt(vm.jTableMonitor.getSelectedRow(), 1) + "?")) {
                        String num = (String) vm.jTableMonitor.getValueAt(vm.jTableMonitor.getSelectedRow(), 0);
                        borrarMonitor(num);
                    } else {
                        System.out.println("Se cancela el borrado");
                    }
                }
            }
            
            case "Actualizar" -> {
                vm.jTableMonitor.getSelectedRow();
            }
            
            case "Insertar" -> {
                vFormulario.Form(monitorDao.getNextId());
                vFormulario.setVisible(true);
                switch (e.getActionCommand()) {
                    case "Insertar" -> {
                        monitor = new Monitor(monitorDao.getNextId(), vFormulario.Nombre.getText(), vFormulario.DNI.getText(), vFormulario.Telefono.getText(), vFormulario.Correo.getText(), vFormulario.Fecha.getDateFormatString(), vFormulario.Nick.getText());
                        altaMonitor(monitor);
                    }
                    
                    case "Cancelar" -> {
                        vFormulario.dispose();
                    }
                }
            }

        }
    }
}
