/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Config.HibernateUtil;
import org.hibernate.SessionFactory;

/**
 *
 * @author pareg
 */
public class ControladorLogin {
    private SessionFactory sessionFactory;
    
    public ControladorLogin(){};
    private boolean conectarBD(){
        try{
            sessionFactory = HibernateUtil.getSessionFactory();
            return true;
        }catch(ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            System.out.println("Error: " + cause);
            return false;
        }
    }
}
