/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import Vista.VistaFormularioMonitor;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private VistaFormularioMonitor vFormulario;

    private SessionFactory sessionFactory;
    private Session sesion;
    private Transaction tr;

    public ControladorMonitor(VistaMonitor vMonitor, SessionFactory s) {
        monitorDao = new MonitorDAO();

        sessionFactory = s;
        sesion = sessionFactory.openSession();
        listaMonitores = new ArrayList();

        vFormulario = new VistaFormularioMonitor();
        vm = vMonitor;
        vMensaje = new VistaMensaje();

        addListeners();

        GestionTablas.inicializarTablaMonitor(vm);
    }

    private void addListeners() {
        vm.Eliminar.addActionListener(this);
        vm.Actualizar.addActionListener(this);
        vm.Insertar.addActionListener(this);
        vFormulario.insertarForm.addActionListener(this);
        vFormulario.Cancelar.addActionListener(this);
    }

    private ArrayList<Monitor> getAll() {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            listaMonitores = monitorDao.getAll(sesion);
        } catch (Exception ex) {
            vMensaje.Mensaje(true, "Error en la consulta");
        }finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaMonitores;
    }
    
    private Monitor getByCod(String id){
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            monitor = (Monitor) sesion.get(Monitor.class, id);
        }catch(Exception ex){
            tr.rollback();
            vMensaje.Mensaje(true, ex.getMessage());
        }finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return monitor;
    }

    private void altaMonitor(Monitor m) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            monitorDao.insertaActualizaMonitor(sesion, m);
            tr.commit();
            vMensaje.Mensaje(false, "Monitor insertado correctamente");
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private void borrarMonitor(String id) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            monitor = (Monitor) sesion.get(Monitor.class, id);
            System.out.println(monitor);

            monitorDao.borrarMonitor(sesion, monitor);

            tr.commit();
            vMensaje.Mensaje(false, "Monitor borrado correctamente");
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }
    
    public void tablasMonitor(){
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            GestionTablas.dibujarTablaMonitor(vm);
            ArrayList<Monitor> lMonitores = getAll();
            GestionTablas.vaciarTablaMonitor();
            GestionTablas.rellenarTablaMonitor(lMonitores);
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(false, "Error en la consulta para las tablas");
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Eliminar" -> {
                if (vm.jTableMonitor.getSelectedRow() == -1) {
                    vMensaje.Mensaje(true, "Selecciona primero una columna");
                } else {
                    if (vMensaje.Confirm("¿Estás seguro de eliminar a " + vm.jTableMonitor.getValueAt(vm.jTableMonitor.getSelectedRow(), 0) + vm.jTableMonitor.getValueAt(vm.jTableMonitor.getSelectedRow(), 1) + "?")) {
                        String num = (String) vm.jTableMonitor.getValueAt(vm.jTableMonitor.getSelectedRow(), 0);
                        borrarMonitor(num);
                        tablasMonitor();
                    }
                }
            }

            case "Actualizar" -> {
                if (vm.jTableMonitor.getSelectedRow() == -1) {
                    vMensaje.Mensaje(true, "Selecciona primero una columna");
                } else {
                    monitor = getByCod((String)vm.jTableMonitor.getValueAt(vm.jTableMonitor.getSelectedRow(), 0)) ;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
                    try{
                        Date d = sdf.parse(monitor.getFechaEntrada());
                        vFormulario.Form(monitor.getCodMonitor(),monitor.getNombre(),monitor.getDni(),monitor.getTelefono(),monitor.getCorreo(),d,monitor.getNick());
                    }catch(ParseException ex){
                        vMensaje.Mensaje(true, ex.getMessage());
                    }
                    
                    vFormulario.setVisible(true);
                }
            }

            case "Insertar" -> {
                vFormulario.Form(monitorDao.getNextId());
                vFormulario.setVisible(true);
            }
            case "insertarForm" -> {
                monitor = new Monitor(vFormulario.Codigo.getText(), vFormulario.Nombre.getText(), vFormulario.DNI.getText(), vFormulario.Telefono.getText(), vFormulario.Correo.getText(), vFormulario.Fecha.getDateFormatString(), vFormulario.Nick.getText());
                altaMonitor(monitor);
                vFormulario.dispose();
                tablasMonitor();
            }

            case "Cancelar" -> {
                vFormulario.dispose();
            }
        }

    }
}

