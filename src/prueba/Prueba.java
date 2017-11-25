/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import incidenciascad.Dependencia;
import incidenciascad.Equipo;
import incidenciascad.Estado;
import incidenciascad.ExcepcionIncidenciasCAD;
import incidenciascad.IncidenciasCAD;
import incidenciascad.TipoEquipo;
import incidenciascad.Usuario;
import java.util.ArrayList;

/**
 *
 * @author ifontecha
 */
public class Prueba {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println("Dependencia");
//        Dependencia d = new Dependencia(0,"kk","Aula 14034 de Informática");
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
//            int r = i.insertarDependencia(d);
//            System.out.println(r + "registros");
////            d = i.leerDependencia(7);
////            System.out.println(d);
////            ArrayList<Dependencia> l = i.leerDependencias();
////            System.out.println(l);
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
//        System.out.println("Estado");
//        Estado e = new Estado(0,"R","Pend d");
//        ArrayList<Estado> l = null;
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
//            int r = i.eliminarEstado(1);
//            System.out.println(r + "registros");
////            d = i.leerDependencia(7);
////            System.out.println(d);
////            l = i.leerEstados();
////            System.out.println(l);
////            l = i.leerEstados("kk",null,null,null);
////            System.out.println(l);
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
//        System.out.println("Usuario");
//        Usuario u = new Usuario(0,null,"","apes","compras");
//        ArrayList<Usuario> l = null;
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
////            int r = i.insertarUsuario(u);
////            System.out.println(r + "registros");
////            u = i.leerUsuario(1);
////            System.out.println(u);
////            l = i.leerUsuarios();
////            System.out.println(l);
//            l = i.leerUsuarios("ro","","h","",IncidenciasCAD.APELLIDO,IncidenciasCAD.DESCENDENTE);
//            System.out.println(l);
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
//        System.out.println("Tipo de Equipo");
//        TipoEquipo te = new TipoEquipo(0,"R","xxxPendf d");
//        ArrayList<TipoEquipo> l = null;
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
////            int r = i.modificarTipoEquipo(7,te);
////            System.out.println(r + "registros");
////            te = i.leerTipoEquipo(7);
////            System.out.println(te);
////            l = i.leerTiposEquipo();
////            System.out.println(l);
//            l = i.leerTiposEquipo(null,null,IncidenciasCAD.CODIGO_TIPO_EQUIPO,IncidenciasCAD.DESCENDENTE);
//            System.out.println(l);
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
        System.out.println("Equipo");
        Equipo e = new Equipo(null,"23451",new TipoEquipo(null,null,null));
//        Equipo e = new Equipo(null,"23451",new TipoEquipo(2,null,null));
        ArrayList<Equipo> l = null;
        try {
            IncidenciasCAD i = new IncidenciasCAD();
            int r = i.modificarEquipo(null,e);
            System.out.println(r + "registros");
//            te = i.leerTipoEquipo(7);
//            System.out.println(te);
//            l = i.leerTiposEquipo();
//            System.out.println(l);
//            l = i.leerTiposEquipo(null,null,IncidenciasCAD.CODIGO_TIPO_EQUIPO,IncidenciasCAD.DESCENDENTE);
//            System.out.println(l);
        } catch (ExcepcionIncidenciasCAD ex) {
            System.out.println(ex);
        }
        
        
    }
    
}
