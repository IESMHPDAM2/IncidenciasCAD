/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import incidenciascad.Departamento;
import incidenciascad.Dependencia;
import incidenciascad.ExcepcionIncidenciasCAD;
import incidenciascad.IncidenciasCAD;

/**
 *
 * @author ifontecha
 */
public class Prueba {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hola");
        Dependencia d = new Dependencia(0,"kkj","Aula 4034 de Informática");
        try {
            IncidenciasCAD i = new IncidenciasCAD();
            int r = i.insertarDependencia(d);
            System.out.println(r + "registros");
        } catch (ExcepcionIncidenciasCAD ex) {
            System.out.println(ex);
        }
        
        
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
//            int r = i.eliminarDependencia(5);
//            System.out.println(r + "registros");
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
        
    }
    
}
