/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

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
//        Dependencia d = new Dependencia(null,"pp","ppito");
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
//            int r = i.modificarDependencia(8,d);
//            System.out.println(r + "registros");
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }

        try {
            IncidenciasCAD i = new IncidenciasCAD();
//            System.out.println(i.leerEquipos("4",null,IncidenciasCAD.CODIGO_TIPO_EQUIPO,IncidenciasCAD.DESCENDENTE));
//            System.out.println(i.leerEquipos("kk",null,null));
            System.out.println(i.leerDependencias());
        } catch (ExcepcionIncidenciasCAD ex) {
            System.out.println(ex);
        }
        
//        System.out.println("Hola");
//        Dependencia d = new Dependencia(0,"kkj","Aula 4034 de Informática");
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
//            int r = i.insertarDependencia(d);
//            System.out.println(r + "registros");
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
        
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
//            int r = i.eliminarDependencia(5);
//            System.out.println(r + "registros");
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
        
    }
    
}
