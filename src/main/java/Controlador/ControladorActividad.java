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
public class ControladorActividad implements ActionListener{

    private VistaFormularioActividad vFormulario;
    private VistaActividad vActividad;
    private VistaMensaje vMensaje;
    
    private ActividadDAO actividadDao;
    private Actividad actividad;
    private ArrayList<Actividad> listaActividades;

    private SessionFactory sessionFactory;
    private Session sesion;
    private Transaction tr;

    public ControladorActividad(VistaActividad vActividad, SessionFactory s){
        this.vActividad  = vActividad;
        vMensaje = new VistaMensaje();
        vFormulario = new VistaFormularioActividad();

        sessionFactory = s;
        
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
    
    private void crearActividad(Actividad a) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            actividadDao.insertaActualizaActividad(sesion, a);
            tr.commit();
            vMensaje.Mensaje(false, "Actividad insertado correctamente");
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(true, ex.getMessage());
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
    
    public ArrayList<Actividad> getAll() {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            listaActividades = actividadDao.getAll(sesion);
        } catch (Exception ex) {
            vMensaje.Mensaje(true, "Error en la consulta");
        }finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaActividades;
    }

    public ArrayList<Actividad> getByDiaCuota(String d, int c) {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            listaActividades = actividadDao.getByDiaCuota(sesion, d, c);
        } catch (Exception ex) {
            vMensaje.Mensaje(true, "Error en la consulta");
        }finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }

        return listaActividades;
    }

    public void tablasActividad() {
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            GestionTablas.dibujarTablaActividad(vActividad);
            ArrayList<Actividad> lActividades = getAll();
            GestionTablas.vaciarTablaActividad();
            GestionTablas.rellenarTablaActividad(lActividades);
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(false, "Error en la consulta para las tablas");
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }

    }
    
    private Actividad getByCod(String id){
        sesion = sessionFactory.openSession();
        tr = sesion.beginTransaction();
        try {
            actividad = (Actividad) sesion.get(Actividad.class, id);
        }catch(Exception ex){
            tr.rollback();
            vMensaje.Mensaje(true, ex.getMessage());
        }finally {
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
                    vMensaje.Mensaje(true, "Selecciona primero una columna");
                } else {
                    if (vMensaje.Confirm("¿Estás seguro de eliminar a " + vActividad.jTableActividad.getValueAt(vActividad.jTableActividad.getSelectedRow(), 0) + vActividad.jTableActividad.getValueAt(vActividad.jTableActividad.getSelectedRow(), 1) + "?")) {
                        String num = (String) vActividad.jTableActividad.getValueAt(vActividad.jTableActividad.getSelectedRow(), 0);
                        borrarActividad(num);
                        tablasActividad();
                    }
                }
            }

            case "Actualizar" -> {
                if (vActividad.jTableActividad.getSelectedRow() == -1) {
                    vMensaje.Mensaje(true, "Selecciona primero una columna");
                } else {
                    actividad = getByCod((String) vActividad.jTableActividad.getValueAt(vActividad.jTableActividad.getSelectedRow(), 0));
                    vFormulario.Form(actividad.getIdActividad(),actividad.getNombre(), actividad.getDia(), Integer.toString(actividad.getHora()), Integer.toString(actividad.getPrecioBaseMes()), actividad.getDescripcion());
                    vFormulario.setVisible(true);
                }
            }

            case "Insertar" -> {
                vFormulario.Form(actividadDao.getNextId());
                vFormulario.setVisible(true);
            }
            case "insertarForm" -> {
                
                actividad = new Actividad(vFormulario.Codigo.getText(), vFormulario.Nombre.getText(),vFormulario.Dia.getText(),Integer.parseInt(vFormulario.Hora.getText()),Integer.parseInt(vFormulario.Precio.getText()));
                actividad.setDescripcion(vFormulario.Descripcion.getText());
                if(actividadDao.esVacio(actividad)){
                    vMensaje.Mensaje(true, "Llena todos los campos");
                }else{
                    crearActividad(actividad);
                }
                vFormulario.dispose();
                tablasActividad();
            }

            case "Cancelar" -> {
                vFormulario.dispose();
            }
        }

    }

}
