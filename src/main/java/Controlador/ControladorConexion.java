/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Config.HibernateUtil;
import Vista.VistaConexion;
import Vista.VistaMensaje;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.hibernate.SessionFactory;
/**
 *
 * @author pareg
 */
public class ControladorConexion implements ActionListener{
    
    ControladorPrincipal controladorP;
    VistaConexion vConexion;
    VistaMensaje vMensaje;
    SessionFactory sessionFactory;
    public static String user;
    public static String pass;
    
    public ControladorConexion(){
        vMensaje = new VistaMensaje();
        vConexion = new VistaConexion();
        
        addListeners();
        
        vConexion.setLocationRelativeTo(null);
        vConexion.setVisible(true);
    }
    
    private void addListeners(){
        vConexion.entrar.addActionListener(this);
        vConexion.cancelar.addActionListener(this);
    }
    
    public SessionFactory conectarBD(){
        sessionFactory = HibernateUtil.buildSessionFactory();
        if(sessionFactory == null){
            vMensaje.Mensaje(true, "Error al introducir las credenciales");
            vConexion.dispose();
            System.exit(0);
        }else{
            vMensaje.Mensaje(false, """
                                      Conexion correcta con hibernate
                                      Va a acceder a la aplicaci\u00f3n""");
            vConexion.dispose();
        }
        return sessionFactory;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch ( e.getActionCommand()){
            case "entrarAplicacion" -> {
                user = vConexion.jTextField1.getText();
                pass = new String(vConexion.jPasswordField1.getPassword());
                sessionFactory = conectarBD();
                controladorP = new ControladorPrincipal(sessionFactory);
            }
            case "Cancelar" -> {
                vMensaje.Mensaje(false, "Salida correcta de la aplicaion");
                System.exit(0);
            }
        }
    }
}
