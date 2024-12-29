/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.Monitor;
import Modelo.Socio;
import Vista.VistaActividad;
import Vista.VistaMonitor;
import Vista.VistaSocio;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pareg
 */
public class GestionTablas {
    public static DefaultTableModel modeloTablaMonitor;
    public static DefaultTableModel modeloTablaSocio;
    public static DefaultTableModel modeloTablaActividad;
    
    public static void inicializarTablaMonitor(VistaMonitor vm){
        modeloTablaMonitor = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        vm.jTableMonitor.setModel(modeloTablaMonitor);
    }
    
    public static void dibujarTablaMonitor(VistaMonitor vm){
        String[] columnasTabla = {"Código", "Nombre", "DNI", "Teléfono", "Correo", "Fecha Incorporación", "Nick"};
        modeloTablaMonitor.setColumnIdentifiers(columnasTabla);
        
        vm.jTableMonitor.getTableHeader().setResizingAllowed(false);
        vm.jTableMonitor.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        vm.jTableMonitor.getColumnModel().getColumn(0).setPreferredWidth(40);
        vm.jTableMonitor.getColumnModel().getColumn(1).setPreferredWidth(240);
        vm.jTableMonitor.getColumnModel().getColumn(2).setPreferredWidth(70);
        vm.jTableMonitor.getColumnModel().getColumn(3).setPreferredWidth(70);
        vm.jTableMonitor.getColumnModel().getColumn(4).setPreferredWidth(200);
        vm.jTableMonitor.getColumnModel().getColumn(5).setPreferredWidth(150);
        vm.jTableMonitor.getColumnModel().getColumn(6).setPreferredWidth(60);
    }
    
    public static void rellenarTablaMonitor(ArrayList<Monitor> monitores){
        Object[] fila = new Object[7];
        for(Monitor monitor : monitores){
            fila[0] = monitor.getCodMonitor();
            fila[1] = monitor.getNombre();
            fila[2] = monitor.getDni();
            fila[3] = monitor.getTelefono();
            fila[4] = monitor.getCorreo();
            fila[5] = monitor.getFechaEntrada();
            fila[6] = monitor.getNick();
            modeloTablaMonitor.addRow(fila);
        }
    }
    
    public static void vaciarTablaMonitor(){
        while(modeloTablaMonitor.getRowCount() > 0){
            modeloTablaMonitor.removeRow(0);
        }
    }
    
    
    
    
    public static void inicializarTablaSocio(VistaSocio vs){
        modeloTablaSocio = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        vs.jTableSocio.setModel(modeloTablaSocio);
    }
    
    public static void dibujarTablaSocio(VistaSocio vs){
        String[] columnasTabla = {"Número", "Nombre", "DNI", "Fecha Nacimiento", "Teléfono", "Correo", "Fecha Entrada", "Categoría"};
        modeloTablaSocio.setColumnIdentifiers(columnasTabla);
        
        vs.jTableSocio.getTableHeader().setResizingAllowed(false);
        vs.jTableSocio.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        vs.jTableSocio.getColumnModel().getColumn(0).setPreferredWidth(40);
        vs.jTableSocio.getColumnModel().getColumn(1).setPreferredWidth(240);
        vs.jTableSocio.getColumnModel().getColumn(2).setPreferredWidth(70);
        vs.jTableSocio.getColumnModel().getColumn(3).setPreferredWidth(70);
        vs.jTableSocio.getColumnModel().getColumn(4).setPreferredWidth(200);
        vs.jTableSocio.getColumnModel().getColumn(5).setPreferredWidth(150);
        vs.jTableSocio.getColumnModel().getColumn(6).setPreferredWidth(60);
        vs.jTableSocio.getColumnModel().getColumn(7).setPreferredWidth(60);
    }
    
    public static void rellenarTablaSocio(ArrayList<Socio> socios){
        Object[] fila = new Object[8];
        for(Socio socio : socios){
            fila[0] = socio.getNumeroSocio();
            fila[1] = socio.getNombre();
            fila[2] = socio.getDni();
            fila[3] = socio.getFechaNacimiento();
            fila[4] = socio.getTelefono();
            fila[5] = socio.getCorreo();
            fila[6] = socio.getFechaEntrada();
            fila[7] = socio.getCategoria();
            modeloTablaSocio.addRow(fila);
        }
    }
    
    public static void vaciarTablaSocio(){
        while(modeloTablaSocio.getRowCount() > 0){
            modeloTablaSocio.removeRow(0);
        }
    }
    
    
    
    public static void inicializarTablaActividad(VistaActividad va){
        modeloTablaActividad = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        va.jTableActividad.setModel(modeloTablaActividad);
    }
    
    public static void dibujarTablaActividad(VistaActividad va){
        String[] columnasTabla = {"Id", "Nombre", "Día", "Hora", "Descripción", "Precio"};
        modeloTablaActividad.setColumnIdentifiers(columnasTabla);
        
        va.jTableActividad.getTableHeader().setResizingAllowed(false);
        va.jTableActividad.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        va.jTableActividad.getColumnModel().getColumn(0).setPreferredWidth(40);
        va.jTableActividad.getColumnModel().getColumn(1).setPreferredWidth(240);
        va.jTableActividad.getColumnModel().getColumn(2).setPreferredWidth(70);
        va.jTableActividad.getColumnModel().getColumn(3).setPreferredWidth(70);
        va.jTableActividad.getColumnModel().getColumn(4).setPreferredWidth(200);
        va.jTableActividad.getColumnModel().getColumn(5).setPreferredWidth(150);
    }
    
    public static void rellenarTablaActividad(ArrayList<Actividad> actividades){
        Object[] fila = new Object[6];
        for(Actividad actividad : actividades){
            fila[0] = actividad.getIdActividad();
            fila[1] = actividad.getNombre();
            fila[2] = actividad.getDia();
            fila[3] = actividad.getHora();
            fila[4] = actividad.getDescripcion();
            fila[5] = actividad.getPrecioBaseMes();
            modeloTablaActividad.addRow(fila);
        }
    }
    
    public static void vaciarTablaActividad(){
        while(modeloTablaActividad.getRowCount() > 0){
            modeloTablaActividad.removeRow(0);
        }
    }
}
