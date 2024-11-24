/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Config.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pareg
 */
public class SocioDAO {

    public Socio getByName(String n) {
        Socio socio = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createQuery("FROM Socio s WHERE s.nombre LIKE: parametro" , Socio.class);
            consulta.setParameter("parametro", n);
            socio = (Socio) consulta.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return socio;
    }
    
    public List<Socio> getAllHQL(){
        List<Socio> listaSocios = new ArrayList();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createQuery("FROM Socio m" , Socio.class);
            listaSocios = (ArrayList<Socio>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaSocios;
    }
    
    public List<Socio> getAllSQL(){
        List<Socio> listaSocios = new ArrayList();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createQuery("SELECT * FROM Socio" , Socio.class);
            listaSocios = (ArrayList<Socio>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaSocios;
    }
    
    public List<Socio> getAllConsultaNombrada(){
        List<Socio> listaSocios = new ArrayList();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createNamedQuery("Socio.findAll" , Socio.class);
            listaSocios = (ArrayList<Socio>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaSocios;
    }
    
    public List<Object[]> getNomTel(){
        List<Object[]> listaSocios = new ArrayList();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createQuery("SELECT nombre,telefono FROM Socio");
            listaSocios = (ArrayList<Object[]>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaSocios;
    }
    
    public List<Socio> getByCategoria(char cat){
        List<Socio> listaSocios = new ArrayList();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createQuery("SELECT s FROM Socio s WHERE categoria= :cat", Socio.class);
            consulta.setParameter("cat", cat);
            listaSocios = (ArrayList<Socio>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaSocios;
    }
    
    public List<Socio> getByCategoriaNamedQuery(char c){
        List<Socio> listaSocios = new ArrayList();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createNamedQuery("Socio.findByCategoria", Socio.class);
            consulta.setParameter("categoria", c);
            listaSocios = (ArrayList<Socio>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaSocios;
    }
    
    public List<Socio> getByCategoriaSQL(char c){
        List<Socio> listaSocios = new ArrayList();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sesion.createQuery("SELECT s FROM Socio WHERE s.categoria= :c ", Socio.class);
            consulta.setParameter("categoria", c);
            listaSocios = (ArrayList<Socio>) consulta.getResultList();
        } catch (Exception e) {
            System.out.println("Error en la recuperación "
                    + e.getMessage());
        } finally {
            if (sesion != null && sesion.isOpen()) {
                sesion.close();
            }
        }
        return listaSocios;
    }
}
