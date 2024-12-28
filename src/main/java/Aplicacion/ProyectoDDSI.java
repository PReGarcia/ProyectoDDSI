/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Aplicacion;

import Controlador.ControladorConexion;
import Controlador.ControladorPrincipal;
import Vista.VistaConexion;
import java.util.Scanner;

/**
 *
 * @author pareg
 */
public class ProyectoDDSI {

    public static void main(String[] args) {
        
        VistaConexion vc = new VistaConexion();
        vc.main();
        
        //ControladorPrincipal cp = new ControladorPrincipal();
        //cp.iniciarVistaConexion();
        /*int n;
        Scanner input = new Scanner(System.in);
        System.out.println(
                "1-Información de los socios (HQL)" + '\n'
                + "2-Información de los socios (SQL Nativo" + '\n'
                + "3-Información de los socios (Consulta nombrada)" + '\n'
                + "4-Nombre y teléfono de los socios" + '\n'
                + "5-Nombre y categoría de los socios" + '\n'
                + "6-Nombre de monitor por nick" + '\n'
                + "7-Información de socio por nombre" + '\n'
                + "8-Información de actividades por día y cuota" + '\n'
                + "9-Información de socios por categoría (HQL)" + '\n'
                + "10-Información de socios por categoría (SQL nativo)" + '\n'
                + "11-Inserción de socio" + '\n'
                + "12-Borrado de socio por DNI" + '\n'
                + "13-Información de la actividad de la que es responsable un monitor por DNI" + '\n'
                + "14-Información de las actividades en las que está inscrito un socio por DNI" + '\n'
                + "15-Información de los socios inscritos en una actividad por nombre de la actividad" + '\n'
                + "Introduce un numero:"
        );

        do {
            n = input.nextInt();
            input.nextLine();
            switch (n) {
                case 1 -> {
                    System.out.println("Lista de socios con HQL: ");
                    cp.mostrarSociosHQL();
                }
                case 2 -> {
                    System.out.println("Lista de socios con SQL: ");
                    cp.mostrarSociosSQL();
                }
                case 3 -> {
                    System.out.println("Lista de socios con consulta nombrada: ");
                    cp.mostrarSociosConsultaNombrada();
                }
                case 4 -> {
                    System.out.println("Lista de socios con nombre y telefono: ");
                    cp.mostrarNomTelSocios();
                }
                case 5 -> {
                    System.out.println("Escribe el nombre de la categoria a buscar: ");
                    String entrada = input.nextLine();
                    char cat;
                    cat = entrada.charAt(0);
                    System.out.println("Lista de socios con nombre y categoria: ");
                    cp.mostrarNombreCategoria(cat);
                }
                case 6 -> {
                    System.out.println("Escribe el nick del monitor a buscar: ");
                    String nick = input.nextLine();
                    System.out.println("Lista de monitores por nick: ");
                    cp.mostrarMonitorNick(nick);
                }
                case 7 -> {
                    System.out.println("Escribe el nombre del socio  a buscar: ");
                    String nombre = input.nextLine();
                    System.out.println("Informacion de socio por nombre: ");
                    cp.mostrarSocioPorNombre(nombre);
                }
                case 8 -> {
                    System.out.println("Escribe el dia de la actividad: ");
                    String dia = input.nextLine();
                    System.out.println("Escribe la cuota de la actividad: ");
                    int cuota = input.nextInt();
                    System.out.println("Las actividades que cumplen las condiciones son: ");
                    cp.mostrarActividadDiaCuota(dia, cuota);
                }
                case 9 -> {
                    System.out.println("Escribe el nombre de la categoria de la que quieres ver los usuarios: ");
                    String entrada = input.nextLine();
                    char cat;
                    cat = entrada.charAt(0);
                    cp.mostrarCategoriaNamedQuery(cat);
                }
                case 10 -> {
                    System.out.println("Escribe el nombre de la categoria de la que quieres ver los usuarios: ");
                    String entrada = input.nextLine();
                    char cat;
                    cat = entrada.charAt(0);
                    cp.mostrarCategoriaNamedQuery(cat);
                }
                case 11 -> {
                    
                }
                case 12 -> {
                    
                }
                case 13 -> {
                    
                }
                case 14 -> {
                    
                }
                case 15 -> {
                    
                }
                default -> {
                    System.out.println("El numero introducido no esta entre las opciones posibles.Introduce uno valido.");
                }
            }
        } while (n < 1 || n > 10);*/
    }
}
