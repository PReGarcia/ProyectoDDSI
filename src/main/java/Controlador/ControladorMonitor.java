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
import java.awt.Dialog;
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

    private VistaMonitor vMonitor;
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
        vFormulario.setLocationRelativeTo(null);
        vFormulario.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        vFormulario.setResizable(false);
        
        this.vMonitor = vMonitor;
        vMensaje = new VistaMensaje();

        addListeners();

        GestionTablas.inicializarTablaMonitor(vMonitor);
    }

    public ControladorMonitor(SessionFactory s) {
        monitorDao = new MonitorDAO();

        sessionFactory = s;
        listaMonitores = new ArrayList();
    }

    private void addListeners() {
        vMonitor.Eliminar.addActionListener(this);
        vMonitor.Actualizar.addActionListener(this);
        vMonitor.Insertar.addActionListener(this);
        vFormulario.insertarForm.addActionListener(this);
        vFormulario.Cancelar.addActionListener(this);
    }

    public ArrayList<Monitor> getAll() {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            listaMonitores = monitorDao.getAll(sesion);
        } catch (Exception ex) {
            vMensaje.Mensaje(vMonitor, true, "Error en la consulta");
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaMonitores;
    }

    public Monitor getByCod(String id) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            monitor = (Monitor) sesion.get(Monitor.class, id);
        } catch (Exception ex) {
            vMensaje.Mensaje(vFormulario, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return monitor;
    }

    public Monitor getByName(String s) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            monitor = monitorDao.getByName(sesion, s);
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(vFormulario, true, ex.getMessage());
        } finally {
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
            vMensaje.Mensaje(vFormulario, false, "Monitor insertado correctamente");
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(vFormulario, true, ex.getMessage());
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

            monitorDao.borrarMonitor(sesion, monitor);

            tr.commit();
            vMensaje.Mensaje(vMonitor, false, "Monitor borrado correctamente");
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(vMonitor, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    public void tablasMonitor() {
        GestionTablas.dibujarTablaMonitor(vMonitor);
        ArrayList<Monitor> lMonitores = getAll();
        GestionTablas.vaciarTablaMonitor();
        GestionTablas.rellenarTablaMonitor(lMonitores);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Eliminar" -> {
                if (vMonitor.jTableMonitor.getSelectedRow() == -1) {
                    vMensaje.Mensaje(vMonitor, true, "Selecciona primero una columna");
                } else {
                    if (vMensaje.Confirm(vMonitor, "¿Estás seguro de eliminar a " + vMonitor.jTableMonitor.getValueAt(vMonitor.jTableMonitor.getSelectedRow(), 0) + vMonitor.jTableMonitor.getValueAt(vMonitor.jTableMonitor.getSelectedRow(), 1) + "?")) {
                        String num = (String) vMonitor.jTableMonitor.getValueAt(vMonitor.jTableMonitor.getSelectedRow(), 0);
                        borrarMonitor(num);
                        tablasMonitor();
                    }
                }
            }

            case "Actualizar" -> {
                if (vMonitor.jTableMonitor.getSelectedRow() == -1) {
                    vMensaje.Mensaje(vMonitor, true, "Selecciona primero una columna");
                } else {
                    monitor = getByCod((String) vMonitor.jTableMonitor.getValueAt(vMonitor.jTableMonitor.getSelectedRow(), 0));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date d = sdf.parse(monitor.getFechaEntrada());
                        vFormulario.Form(monitor.getCodMonitor(), monitor.getNombre(), monitor.getDni(), monitor.getTelefono(), monitor.getCorreo(), d, monitor.getNick());
                        vFormulario.setVisible(true);
                    } catch (ParseException ex) {
                        vMensaje.Mensaje(vMonitor, true, ex.getMessage());
                    }
                }
            }

            case "Insertar" -> {
                vFormulario.Form(monitorDao.getNextId());
                vFormulario.setVisible(true);
            }
            case "insertarForm" -> {
                int d = vFormulario.Fecha.getDate().getDate();
                int m = vFormulario.Fecha.getDate().getMonth() + 1;
                int y = vFormulario.Fecha.getDate().getYear() + 1900;
                String s = d + "/" + m + "/" + y;
                monitor = new Monitor(vFormulario.Codigo.getText(), vFormulario.Nombre.getText(), vFormulario.DNI.getText(), vFormulario.Telefono.getText(), vFormulario.Correo.getText(), s, vFormulario.Nick.getText());
                if (monitorDao.esVacio(monitor)) {
                    vMensaje.Mensaje(vFormulario, true, "Llena todos los campos");
                } else {
                    altaMonitor(monitor);
                    vFormulario.dispose();
                    tablasMonitor();
                }

            }

            case "Cancelar" -> {
                vFormulario.dispose();
            }
        }
    }
}
