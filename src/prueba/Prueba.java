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
import incidenciascad.Incidencia;
import incidenciascad.IncidenciasCAD;
import static incidenciascad.IncidenciasCAD.DEPENDENCIA_CODIGO;
import static incidenciascad.IncidenciasCAD.EQUIPO_NUMERO_ETIQUETA_CONSEJERIA;
import static incidenciascad.IncidenciasCAD.ESTADO_CODIGO;
import static incidenciascad.IncidenciasCAD.USUARIO_CUENTA;
import incidenciascad.TipoEquipo;
import incidenciascad.Usuario;
import java.util.ArrayList;
import java.util.Date;

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
//        Dependencia d = new Dependencia(0,"S0p2"," 1l4 formática");
//        int r;
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
////            r = i.insertarDependencia(d);
////            System.out.println(r + "registros");
//            r = i.eliminarDependencia(22);
//            System.out.println(r + "registros");
//            r = i.modificarDependencia(20,d);
//            System.out.println(r + "registros");
//            d = i.leerDependencia(7);
//            System.out.println(d);
//            ArrayList<Dependencia> l = i.leerDependencias();
//            System.out.println(l);
//            l = i.leerDependencias("","n",IncidenciasCAD.DEPENDENCIA_CODIGO,IncidenciasCAD.ASCENDENTE);
//            System.out.println(l);
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
//        System.out.println("Estado");
//        Estado e = new Estado(0,"12345678","Penj cfdf d");
//        ArrayList<Estado> l = null;
//        int r;
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
////            r = i.insertarEstado(e);
////            System.out.println(r + "registros");
//            r = i.eliminarEstado(7);
//            System.out.println(r + "registros");
//            r = i.modificarEstado(143, e);
//            System.out.println(r + "registros");
//            e = i.leerEstado(1);
//            System.out.println(e);
//            l = i.leerEstados();
//            System.out.println(l);
//            l = i.leerEstados("","i",IncidenciasCAD.ESTADO_NOMBRE,IncidenciasCAD.DESCENDENTE);
//            System.out.println(l);
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
//        System.out.println("Usuario");
//        Usuario u = new Usuario(0,"lAruiz","","aApes","coAmas");
//        ArrayList<Usuario> l = null;
//        int r;
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
////            r = i.insertarUsuario(u);
////            System.out.println(r + "registros");
//            r = i.eliminarUsuario(100);
//            System.out.println(r + "registros");
//            r = i.modificarUsuario(100, null);
//            System.out.println(r + "registros");
//            u = i.leerUsuario(1);
//            System.out.println(u);
//            l = i.leerUsuarios();
//            System.out.println(l);
//            l = i.leerUsuarios("","","h","",IncidenciasCAD.USUARIO_DEPARTAMENTO,IncidenciasCAD.DESCENDENTE);
//            System.out.println(l);
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
//        System.out.println("Tipo de Equipo");
//        TipoEquipo te = new TipoEquipo(0,"wlcw","cclxxendf d");
//        ArrayList<TipoEquipo> l = null;
//        int r;
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
////            r = i.insertarTipoEquipo(te);
////            System.out.println(r + "registros");
//            r = i.eliminarTipoEquipo(null);
//            System.out.println(r + "registros");
//            r = i.modificarTipoEquipo(150,te);
//            System.out.println(r + "registros");
//            te = i.leerTipoEquipo(7);
//            System.out.println(te);
//            l = i.leerTiposEquipo();
//            System.out.println(l);
//            l = i.leerTiposEquipo(null,null,IncidenciasCAD.TIPO_EQUIPO_CODIGO,IncidenciasCAD.DESCENDENTE);
//            System.out.println(l);
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
//        System.out.println("Equipo");
//        Equipo e = new Equipo(null,"444x4vvv",new TipoEquipo(1,null,null));
////        Equipo e = new Equipo(null,"23451",new TipoEquipo(2,null,null));
//        ArrayList<Equipo> l = null;
//        int r;
//        try {
//            IncidenciasCAD i = new IncidenciasCAD();
//            r = i.insertarEquipo(e);
//            System.out.println(r + "registros");
//            r = i.eliminarEquipo(34);
//            System.out.println(r + "registros");
//            r = i.modificarEquipo(10,e);
//            System.out.println(r + "registros");
//            e = i.leerEquipo(null);
//            System.out.println(e);
//            l = i.leerEquipos();
//            System.out.println(l);
//            l = i.leerEquipos("18",2,IncidenciasCAD.TIPO_EQUIPO_CODIGO,IncidenciasCAD.ASCENDENTE);
//            System.out.println(l);
//        } catch (ExcepcionIncidenciasCAD ex) {
//            System.out.println(ex);
//        }
        
        System.out.println("Incidencia");
        Incidencia in = new Incidencia(null,"1","2","3",
                new Date(),
                new Usuario(1,null,null,null,null),
                new Equipo(1,null,null), 
                new Dependencia(1,null,null), 
                new Estado(189,null,null));
        ArrayList<Incidencia> l = null;
        int r;
        try {
            IncidenciasCAD i = new IncidenciasCAD();
//            r = i.insertarIncidencia(null);
//            System.out.println(r + "registros");
//            r = i.eliminarIncidencia(589);
//            System.out.println(r + "registros");
//            r = i.modificarIncidencia(52,in);
//            System.out.println(r + "registros");
            in = i.leerIncidencia(1);
            System.out.println(in);
            l = i.leerIncidencias();
            System.out.println(l);
//            l = i.leerIncidencias(null,"","o","",java.sql.Date.valueOf("2017-11-26"),null,null,null,null,null,null);
            l = i.leerIncidencias(null,"","o","",null,"lruiz","p","444","SG","R",IncidenciasCAD.ESTADO_CODIGO,IncidenciasCAD.ASCENDENTE);
            System.out.println(l);
        } catch (ExcepcionIncidenciasCAD ex) {
            System.out.println(ex);
        }
        
        
    }
    
}
