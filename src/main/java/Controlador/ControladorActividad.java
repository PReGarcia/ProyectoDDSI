/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Vista.VistaActividad;
import Vista.VistaFormularioActividad;
import Vista.VistaMensaje;
import java.awt.Dialog;
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
public class ControladorActividad implements ActionListener {

    private VistaFormularioActividad vFormulario;
    private VistaActividad vActividad;
    private VistaMensaje vMensaje;

    private ControladorMonitor cMonitor;
    private ActividadDAO actividadDao;
    private Actividad actividad;
    private ArrayList<Actividad> listaActividades;

    private SessionFactory sessionFactory;
    private Session sesion;
    private Transaction tr;

    public ControladorActividad(VistaActividad vActividad, SessionFactory s) {
        this.vActividad = vActividad;
        vMensaje = new VistaMensaje();
        vFormulario = new VistaFormularioActividad();
        vFormulario.setLocationRelativeTo(null);
        vFormulario.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        vFormulario.setResizable(false);

        sessionFactory = s;

        cMonitor = new ControladorMonitor(sessionFactory);
        actividadDao = new ActividadDAO();
        actividad = new Actividad();
        listaActividades = new ArrayList();

        GestionTablas.inicializarTablaActividad(this.vActividad);
        addListeners();
    }

    private void addListeners() {
        vActividad.Eliminar.addActionListener(this);
        vActividad.Actualizar.addActionListener(this);
        vActividad.Insertar.addActionListener(this);
        vFormulario.insertarForm.addActionListener(this);
        vFormulario.Cancelar.addActionListener(this);
    }

    private boolean esValido(Actividad a) {
        return !actividadDao.esVacio(getAll().stream().filter(x -> (x.getDia().equals(a.getDia()) && x.getHora() == a.getHora() && x.getMonitorResponsable().equals(a.getMonitorResponsable()))).findFirst().orElse(null));
    }

    private void crearActividad(Actividad a) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            actividadDao.insertaActualizaActividad(sesion, a);
            tr.commit();
            vMensaje.Mensaje(vFormulario, false, "Actividad insertado correctamente");
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(vFormulario, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private void borrarActividad(String id) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            actividad = (Actividad) sesion.get(Actividad.class, id);

            actividadDao.borrarActividad(sesion, actividad);

            tr.commit();
            vMensaje.Mensaje(vActividad, false, "Monitor borrado correctamente");
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(vActividad, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    public ArrayList<Actividad> getAll() {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            listaActividades = actividadDao.getAll(sesion);
        } catch (Exception ex) {
            vMensaje.Mensaje(vActividad, true, "Error en la consulta");
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaActividades;
    }

    private ArrayList<String> getMonitores() {
        ArrayList<String> lMonitores = new ArrayList();
        cMonitor.getAll().stream().forEach(x -> {
            lMonitores.add(x.getNombre());
        });
        return lMonitores;
    }

    public void tablasActividad() {
        GestionTablas.dibujarTablaActividad(vActividad);
        ArrayList<Actividad> lActividades = getAll();
        GestionTablas.vaciarTablaActividad();
        GestionTablas.rellenarTablaActividad(lActividades);
    }

    private Actividad getByCod(String id) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            actividad = (Actividad) sesion.get(Actividad.class, id);
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(vActividad, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return actividad;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Eliminar" -> {
                if (vActividad.jTableActividad.getSelectedRow() == -1) {
                    vMensaje.Mensaje(vActividad, true, "Selecciona primero una columna");
                } else {
                    if (vMensaje.Confirm(vActividad, "¿Estás seguro de eliminar a " + vActividad.jTableActividad.getValueAt(vActividad.jTableActividad.getSelectedRow(), 0) + vActividad.jTableActividad.getValueAt(vActividad.jTableActividad.getSelectedRow(), 1) + "?")) {
                        String num = (String) vActividad.jTableActividad.getValueAt(vActividad.jTableActividad.getSelectedRow(), 0);
                        borrarActividad(num);
                        tablasActividad();
                    }
                }
            }

            case "Actualizar" -> {
                if (vActividad.jTableActividad.getSelectedRow() == -1) {
                    vMensaje.Mensaje(vActividad, true, "Selecciona primero una columna");
                } else {
                    actividad = getByCod((String) vActividad.jTableActividad.getValueAt(vActividad.jTableActividad.getSelectedRow(), 0));
                    if (actividad.getMonitorResponsable() != null) {
                        vFormulario.Form(actividad.getIdActividad(), actividad.getNombre(), actividad.getDia(), actividad.getHora(), Integer.toString(actividad.getPrecioBaseMes()), actividad.getDescripcion(), actividad.getMonitorResponsable().getNombre(), getMonitores());
                    } else {
                        vFormulario.Form(actividad.getIdActividad(), actividad.getNombre(), actividad.getDia(), actividad.getHora(), Integer.toString(actividad.getPrecioBaseMes()), actividad.getDescripcion(), cMonitor.getByCod("M001").getNombre(), getMonitores());
                    }

                    vFormulario.setVisible(true);
                }
            }

            case "Insertar" -> {
                vFormulario.Form(actividadDao.getNextId(), getMonitores());
                vFormulario.setVisible(true);
            }
            case "insertarForm" -> {

                actividad = new Actividad(vFormulario.Codigo.getText(), vFormulario.Nombre.getText(), (String) vFormulario.Dia.getSelectedItem(), (int) vFormulario.Hora.getSelectedItem(), (int) Integer.parseInt(vFormulario.Precio.getText()));
                actividad.setDescripcion(vFormulario.Descripcion.getText());
                actividad.setMonitorResponsable(cMonitor.getByName((String) vFormulario.Monitor.getSelectedItem()));
                if (actividadDao.esVacio(actividad)) {
                    vMensaje.Mensaje(vFormulario, true, "Llena todos los campos");
                }else if(esValido(actividad)){
                    vMensaje.Mensaje(vFormulario, true, "El monitor no está disponible");
                }else{
                    crearActividad(actividad);
                    vFormulario.dispose();
                    tablasActividad();
                }             
            }

            case "Cancelar" -> {
                vFormulario.dispose();
            }
        }

    }

}
