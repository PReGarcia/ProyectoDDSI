/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Config.HibernateUtil;
import Vista.VistaConexion;
import Vista.VistaMensaje;
import org.hibernate.SessionFactory;
/**
 *
 * @author pareg
 */
public class ControladorConexion {
    
    ControladorPrincipal controladorP;
    VistaConexion vConexion;
    VistaMensaje vMensaje;
    SessionFactory sessionFactory;
    public static String user;
    public static String pass;
    
    public ControladorConexion(String u, char[] p){
        user = u;
        pass = new String(p);
    }
    
    public void conectarBD(){
        sessionFactory = HibernateUtil.buildSessionFactory();
        if(sessionFactory == null){
            //vMensaje.main("error", "Error al introducir las credenciales");
        }else{
            //vMensaje.main("info", "Conexion correcta con hibernate"
                    //+ "\n Va a acceder a la aplicación");
            vConexion.dispose();
            controladorP = new ControladorPrincipal(sessionFactory);
        }
    }
}
