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

    private void cerrarConexion(Connection conexion, PreparedStatement sentenciaPreparada) {
        try {
           sentenciaPreparada.close();
           conexion.close();
        } catch (SQLException | NullPointerException ex) {}
       
    }
    public int insertarDepartamento(Departamento departamento) throws ExcepcionIncidenciasCAD {
        String dml = "";
        Connection conexion = null;
        PreparedStatement sentenciaPreparada = null;
        if (departamento.getNombre().equals("")) departamento.setNombre(null);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/incidencias?user=root&password=root");
            dml = "insert into departamento set nombre=?";
            sentenciaPreparada = conexion.prepareStatement(dml);

            sentenciaPreparada.setString(1, departamento.getNombre());
            int registrosAfectados = sentenciaPreparada.executeUpdate();

            sentenciaPreparada.close();
            conexion.close();
 
            return registrosAfectados;            
            
        } catch (ClassNotFoundException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(-1,ex.getMessage(),"Error general del sistema. Consulte con el administrador",null);
            throw e;
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(ex.getErrorCode(),ex.getMessage(),null,dml);
            switch (ex. getErrorCode()) {
                case 1048:  e.setMensajeErrorUsuario("El nombre de departamento es obligatorio");
                            break;
                case 1062:  e.setMensajeErrorUsuario("El nombre de departamento ya existe");
                            break;
                default:    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                            break;
            }
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    public int eliminarDepartamento(int departamentoId) {
        return 0;
    }
    
    public int modificarDepartamento(int departamentoId, Departamento departamento) {
        return 0;
    }
    
    public Departamento leerDepartamento(int departamentoId) {
        return null;
    }
    
    public ArrayList<Departamento> leerDepartamentos() {
        return null;
    }
    
    public ArrayList<Departamento> leerDepartamentos(String filtroNombre) {
        return null;
    }
    
    
}
