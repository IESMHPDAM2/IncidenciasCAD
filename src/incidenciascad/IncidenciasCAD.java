/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidenciascad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ifontecha
 */
public class IncidenciasCAD {
    private Connection conexion;
    
    public IncidenciasCAD() throws ExcepcionIncidenciasCAD {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/incidencias?user=root&password=root");
        } catch (ClassNotFoundException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(-1,ex.getMessage(),"Error general del sistema. Consulte con el administrador",null);
            throw e;
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(ex.getErrorCode(),ex.getMessage(),"Error general del sistema. Consulte con el administrador",null);
            cerrarConexion(conexion, null);
            throw e;
        }
    }

    private void cerrarConexion(Connection conexion, PreparedStatement sentenciaPreparada) {
        try {
           sentenciaPreparada.close();
           conexion.close();
        } catch (SQLException | NullPointerException ex) {}
       
    }
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
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(ex.getErrorCode(),ex.getMessage(),null,dml);
            switch (ex. getErrorCode()) {
                case 1048:  e.setMensajeErrorUsuario("El código y nombre de dependencia son obligatorios");
                            break;
                case 1062:  e.setMensajeErrorUsuario("El código y/o el nombre de dependencia ya existen y no se pueden repetir");
                            break;
                default:    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                            break;
            }
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
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
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(ex.getErrorCode(),ex.getMessage(),null,dml);
            switch (ex. getErrorCode()) {
                case 1451:  e.setMensajeErrorUsuario("No se puede eliminar la dependencia ya que tiene incidencias asociadas");
                            break;
                default:    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                            break;
            }
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    public int modificarDepartamento(Integer departamentoId, Departamento departamento) {
        return 0;
    }
    
    public Departamento leerDepartamento(Integer departamentoId) {
        return null;
    }
    
    public ArrayList<Departamento> leerDepartamentos() {
        return null;
    }
    
    public ArrayList<Departamento> leerDepartamentos(String filtroNombre) {
        return null;
    }
    
    
}
