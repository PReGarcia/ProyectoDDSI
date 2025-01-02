/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.ActividadDAO;
import Modelo.Socio;
import Modelo.SocioDAO;

import Vista.VistaFormularioSocio;
import Vista.VistaFormularioSocioActividad;
import Vista.VistaMensaje;
import Vista.VistaSocio;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author pareg
 */
public class ControladorSocio implements ActionListener {

    private VistaFormularioSocioActividad vSAForm;
    private VistaFormularioSocio vFormulario;
    private VistaSocio vSocio;
    private VistaMensaje vMensaje;

    private ActividadDAO actividadDao;
    private SocioDAO socioDao;
    private Socio socio;
    private ArrayList<Socio> listaSocios;

    private ControladorActividad ca;

    private SessionFactory sessionFactory;
    private Session sesion;
    private Transaction tr;

    boolean alta;

    public ControladorSocio(VistaSocio vSocio, SessionFactory s) {
        this.vSocio = vSocio;
        vMensaje = new VistaMensaje();

        sessionFactory = s;

        actividadDao = new ActividadDAO();
        socioDao = new SocioDAO();
        socio = new Socio();
        listaSocios = new ArrayList();

        ca = new ControladorActividad(s);

        vFormulario = new VistaFormularioSocio();
        vFormulario.setLocationRelativeTo(null);
        vFormulario.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        vFormulario.setResizable(false);

        vSAForm = new VistaFormularioSocioActividad();
        vSAForm.setLocationRelativeTo(null);
        vSAForm.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        vSAForm.setResizable(false);

        addListeners();

        GestionTablas.inicializarTablaSocio(vSocio);
    }

    private void addListeners() {
        vSocio.altaSA.addActionListener(this);
        vSocio.bajaSA.addActionListener(this);

        vSocio.Eliminar.addActionListener(this);
        vSocio.Actualizar.addActionListener(this);
        vSocio.Insertar.addActionListener(this);

        vFormulario.insertarForm.addActionListener(this);
        vFormulario.Cancelar.addActionListener(this);

        vSAForm.aceptarSA.addActionListener(this);
        vSAForm.cancelarSA.addActionListener(this);
    }

    private int filaSeleccionada() {
        return vSocio.jTableSocio.getSelectedRow();
    }

    private Socio getByCod(String n) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            socio = (Socio) sesion.get(Socio.class, n);
        } catch (Exception ex) {
            vMensaje.Mensaje(vSocio, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return socio;
    }

    private void altaActividad(Socio s, String n) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            ArrayList<Actividad> l = new ArrayList(s.getActividades());
            l.add(actividadDao.getByName(sesion, n));
            s.setActividades(new HashSet(l));
            socioDao.insertaActualizaSocio(sesion, s);
            tr.commit();
            ArrayList<Actividad> i = new ArrayList(s.getActividades());
            for (Actividad p: i){
                System.out.println(p);
            }
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(vSocio, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private ArrayList<String> getActividadesNuevas(String id) {
        ArrayList<String> acts = new ArrayList();
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            ca.getAll().forEach(y -> {
                acts.add(y.getNombre());
            });
            for (String n : getActividadesBySocio(id)) {
                acts.remove(n);
            }
        } catch (Exception ex) {
            vMensaje.Mensaje(vSocio, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return acts;
    }

    private ArrayList<String> getActividadesBySocio(String id) {

        ArrayList<String> lActividades = new ArrayList();
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            socio = sesion.get(Socio.class, id);
            socio.getActividades().forEach(x -> {
                lActividades.add(x.getNombre());
            });
        } catch (Exception ex) {
            vMensaje.Mensaje(vSocio, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }

        return lActividades;
    }

    private ArrayList<Socio> getAll() {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            listaSocios = socioDao.getAll(sesion);
        } catch (Exception ex) {
            
            vMensaje.Mensaje(vSocio, true, "Error en la consulta");
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaSocios;
    }

    private void crearSocio(Socio s) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            socioDao.insertaActualizaSocio(sesion, s);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(vFormulario, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    private void borrarSocio(String s) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            socio = (Socio) sesion.get(Socio.class, s);
            System.out.println(socio);
            socioDao.borrarSocio(sesion, socio);
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(vSocio, true, ex.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
    }

    public void tablasSocio() {
        GestionTablas.dibujarTablaSocio(vSocio);
        ArrayList<Socio> lSocios = getAll();
        GestionTablas.vaciarTablaSocio();
        GestionTablas.rellenarTablaSocio(lSocios);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Eliminar" -> {
                if (filaSeleccionada() == -1) {
                    vMensaje.Mensaje(vSocio, true, "Selecciona primero una columna");
                } else {
                    if (vMensaje.Confirm(vSocio, "¿Estás seguro de eliminar a " + vSocio.jTableSocio.getValueAt(filaSeleccionada(), 1) + "?")) {
                        String num = (String) vSocio.jTableSocio.getValueAt(filaSeleccionada(), 0);

                        borrarSocio(num);

                        tablasSocio();

                        vMensaje.Mensaje(vSocio, false, "Se ha eliminado a " + vSocio.jTableSocio.getValueAt(filaSeleccionada(), 1));
                    }
                }

            }

            case "Actualizar" -> {
                if (filaSeleccionada() == -1) {
                    vMensaje.Mensaje(vSocio, true, "Selecciona primero una columna");
                } else {
                    socio = getByCod((String) vSocio.jTableSocio.getValueAt(filaSeleccionada(), 0));
                    vFormulario.Form(socio.getNumeroSocio());
                    vFormulario.setVisible(true);
                }
            }

            case "Insertar" -> {
                vFormulario.Form(socioDao.getNextId());
                vFormulario.setVisible(true);
            }
            case "insertarForm" -> {
                int d = vFormulario.fechaEntrada.getDate().getDay(), m = vFormulario.fechaEntrada.getDate().getMonth() + 1, y = vFormulario.fechaEntrada.getDate().getYear() + 1900;
                String f1 = d + "/" + m + "/" + y;
                d = vFormulario.fechaNacimiento.getDate().getDay();
                m = vFormulario.fechaNacimiento.getDate().getMonth() + 1;
                y = vFormulario.fechaNacimiento.getDate().getYear() + 1900;
                String f2 = d + "/" + m + "/" + y;
                String s = (String) vFormulario.Cat.getSelectedItem();
                Character c = (Character) s.charAt(0);
                socio = new Socio(vFormulario.Codigo.getText(), vFormulario.Nombre.getText(), vFormulario.DNI.getText(), f1, vFormulario.Telefono.getText(), c, vFormulario.Correo.getText());
                socio.setFechaNacimiento(f2);
                if (socioDao.esVacio(socio)) {
                    vMensaje.Mensaje(vFormulario, true, "Llena todos los campos");
                } else {
                    crearSocio(socio);
                    tablasSocio();
                    vFormulario.dispose();
                }
            }

            case "Cancelar" -> {
                vFormulario.dispose();
            }

            case "altaSA" -> {
                if (filaSeleccionada() == -1) {
                    vMensaje.Mensaje(vSocio, true, "Selecciona primero una columna");
                } else {
                    socio = getByCod((String) vSocio.jTableSocio.getValueAt(filaSeleccionada(), 0));
                    vSAForm.Form(true, getActividadesNuevas(socio.getNumeroSocio()));
                    alta = true;
                    vSAForm.setVisible(true);
                }
            }
            case "bajaSA" -> {
                if (filaSeleccionada() == -1) {
                    vMensaje.Mensaje(vSocio, true, "Selecciona primero una columna");
                } else {
                    socio = getByCod((String) vSocio.jTableSocio.getValueAt(filaSeleccionada(), 0));
                    alta = false;
                    ArrayList<String> listaActividades = getActividadesBySocio(socio.getNumeroSocio());
                    vSAForm.Form(false, listaActividades);
                    vSAForm.setVisible(true);
                }
            }

            case "aceptarSA" -> {
                if (alta) {
                    ca.altaSocio(socio, ca.getByName((String) vSAForm.actividades.getSelectedItem()));
                    //altaActividad(socio, (String) vSAForm.actividades.getSelectedItem());

                    
                    vSAForm.dispose();

                } else {
                    vSAForm.dispose();
                }

            }

            case "cancelarSA" -> {
                vSAForm.dispose();
            }
        }

    }
}
