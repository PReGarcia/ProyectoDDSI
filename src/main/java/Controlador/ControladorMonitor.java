/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Monitor;
import Modelo.MonitorDAO;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author pareg
 */
public class ControladorMonitor implements ActionListener{

    private MonitorDAO monitorDao;
    private Monitor monitor;
    private ArrayList<Monitor> listaMonitores;
    private VistaMonitor vm;
    private VistaMensaje vMensaje;

    public ControladorMonitor(VistaMonitor vMonitor) {
        monitorDao = new MonitorDAO();
        monitor = new Monitor();
        listaMonitores = new ArrayList();
        
        vm = vMonitor;
        vMensaje = new VistaMensaje();
        
        addListeners();
        
        GestionTablas.inicializarTablaMonitor(vm);
    }
    
    private void addListeners(){
        vm.Eliminar.addActionListener(this);
        vm.Actualizar.addActionListener(this);
        vm.Insertar.addActionListener(this);
    } 

    public ArrayList<Monitor> getAll(Session s) {
        listaMonitores = monitorDao.getAll(s);
        return listaMonitores;
    }

    public void tablasMonitor(Session s) {

        GestionTablas.dibujarTablaMonitor(vm);
        Transaction tr = s.beginTransaction();
        try {
            ArrayList<Monitor> lMonitores = getAll(s);
            GestionTablas.vaciarTablaMonitor();
            GestionTablas.rellenarTablaMonitor(lMonitores);
        } catch (Exception ex) {
            tr.rollback();
            vMensaje.Mensaje(false,true, "Error en la petición de monitores\n" + ex.getMessage());
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Eliminar" -> {
                if(vm.jTableMonitor.getSelectedRow() == -1){
                    vMensaje.Mensaje(false, true, "Selecciona primero una columna");
                }
                vMensaje.Mensaje(true, false, "¿Estás seguro de eliminar a " + vm.jTableMonitor.getValueAt(vm.jTableMonitor.getSelectedRow(), 1) + "?");
            }
            case "Actualizar" ->{
                vm.jTableMonitor.getSelectedRow();
            }
            case "Insertar" ->{
                
            }
                
        }
    }
}
