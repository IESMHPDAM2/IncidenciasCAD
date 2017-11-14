/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase prinjcipal del componenete de acceso a datos especializado en acceder
 * a la base de datos de incidencias
 * @author Ignacio Fontecha Hernández
 * @version 1.0
 */
public class IncidenciasCAD {
    private Connection conexion;
    
    /**
     * Constructor vacío
     * @author Ignacio Fontecha Hernández
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public IncidenciasCAD() throws ExcepcionIncidenciasCAD {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/incidencias?user=root&password=root");
        } catch (ClassNotFoundException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    -1,
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    null);
            throw e;
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    null);
            cerrarConexion(conexion, null);
            throw e;
        }
    }

    /**
     * Cierra tanto la conexión con la base de datos como la sentencia preparada
     * utilizada
     * @author Ignacio Fontecha Hernández
     * @param conexion Coexión con la base de datos
     * @param setenciaPreparada Setencia preparada utilizada para el acceso a la base de datos
     */
    private void cerrarConexion(Connection conexion, PreparedStatement sentenciaPreparada) {
        try {
           sentenciaPreparada.close();
        } catch (SQLException | NullPointerException ex) {}
        try {
           conexion.close();
        } catch (SQLException | NullPointerException ex) {}
       
    }
    
    /**
     * Inserta una dependencia en la base de datos
     * @author Ignacio Fontecha Hernández
     * @param dependencia Datos de la dependencia a insertar
     * @return Cantidad de registros insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Integer insertarDependencia(Dependencia dependencia) throws ExcepcionIncidenciasCAD {
        String dml = "insert into dependencia(codigo,nombre) values (?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, dependencia.getCodigo());
            sentenciaPreparada.setString(2, dependencia.getNombre());
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex. getErrorCode()) {
                case 1048:  
                    e.setMensajeErrorUsuario("El código y nombre de dependencia son obligatorios");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El código y/o el nombre de dependencia ya existen y no se pueden repetir");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Elimina una dependencia de la base de datos
     * @author Ignacio Fontecha Hernández
     * @param dependenciaId Identificador de la dependencia a eliminar
     * @return Cantidad de registros eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarDependencia(Integer dependenciaId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from dependencia where dependencia_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setInt(1, dependenciaId);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex. getErrorCode()) {
                case 1451:  
                    e.setMensajeErrorUsuario("No se puede eliminar la dependencia ya que tiene incidencias asociadas");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica una dependencia de la base de datos
     * @author Ignacio Fontecha Hernández
     * @param dependenciaId Identificador de la dependencia a modificar
     * @param dependencia Nuevos datos de la dependencia a modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarDependencia(Integer dependenciaId, Dependencia dependencia) throws ExcepcionIncidenciasCAD {
        String dml = "update dependencia set codigo = ?, nombre = ? where dependencia_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, dependencia.getCodigo());
            sentenciaPreparada.setString(2, dependencia.getNombre());
            sentenciaPreparada.setInt(3, dependenciaId);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex. getErrorCode()) {
                case 1048:  
                    e.setMensajeErrorUsuario("El código y nombre de dependencia son obligatorios");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El código y/o el nombre de dependencia ya existen y no se pueden repetir");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    public Departamento leerDependencia(Integer dependenciaId) {
        return null;
    }
    
    public ArrayList<Dependencia> leerDependencias() {
        return null;
    }
    
    public ArrayList<Departamento> leerDependencias(String filtroCodigo, String filtroNombre, String orden) {
        return null;
    }
    
    /**
     * Lee un equipo de la base de datos
     * @author Ignacio Fontecha Hernández
     * @param equipoId Identificador del equipo a leer
     * @return Equipo a leer de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Departamento leerEquipo(Integer equipoId) {
        return null;
    }

    /**
     * Lee todos los equipos de la base de datos
     * @author Ignacio Fontecha Hernández
     * @return Lista con todos los equipos de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Equipo> leerEquipos() throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from tipo_equipo te, equipo e "
                + "where te.tipo_equipo_id = e.tipo_equipo_id";
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Equipo> listaEquipos = new ArrayList();
        Equipo equipo = null;
        TipoEquipo tipoEquipo = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                equipo = new Equipo();
                equipo.setEquipoId(resultado.getInt("equipo_id"));
                equipo.setNumeroEtiquetaConsejeria(resultado.getString("numero_etiqueta_consejeria"));
                tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipòEquipoId(resultado.getInt("te.tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("te.codigo"));
                tipoEquipo.setNombre(resultado.getString("te.nombre"));
                equipo.setTipoEquipo(tipoEquipo);
                listaEquipos.add(equipo);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return listaEquipos;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    
}
