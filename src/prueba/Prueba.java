/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import incidenciascad.Departamento;
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
        Departamento d = new Departamento(0,"");
        IncidenciasCAD i = new IncidenciasCAD();
        try {
            i.insertarDepartamento(d);
        } catch (ExcepcionIncidenciasCAD ex) {
            System.out.println(ex);
        }
        
    }
    
}
