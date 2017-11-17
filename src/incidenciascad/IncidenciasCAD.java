package incidenciascad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase prinjcipal del componenete de acceso a datos especializado en acceder
 * a la base de datos de incidencias
 * @author Ignacio Fontecha Hernández
 * @version 1.0
 */
public class IncidenciasCAD {
    private Connection conexion;
    
    public static Integer ASCENDENTE = 1;
    public static Integer DESCENDENTE = 2;
    public static Integer NUMERO_ETIQUETA_CONSEJERIA = 3;
    public static Integer TIPO_EQUIPO = 4;
    
    
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
     * Inserta un usuario en la base de datos
     * @author Óscar Barahona Ortega
     * @param usuario Datos del usuario a insertar
     * @return Cantidad de registros insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Integer insertarUsuario(Usuario usuario) throws ExcepcionIncidenciasCAD {
        String dml = "insert into usuario(cuenta,nombre,apellido,departamento) values (?,?,?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, usuario.getCuenta());
            sentenciaPreparada.setString(2, usuario.getNombre());
            sentenciaPreparada.setString(3, usuario.getApellido());
            sentenciaPreparada.setString(4, usuario.getDepartamento());
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
                    e.setMensajeErrorUsuario("El nombre de cuenta es obligatorio");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El nombre de cuenta ya existe y no se puede repetir");
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
     * Elimina un usuario de la base de datos
     * @author Óscar Barahona Ortega
     * @param usuarioId Identificador del usuario a eliminar
     * @return Cantidad de registros eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarUsuario(Integer usuarioId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from usuario where usuario_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setInt(1, usuarioId);
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
                    e.setMensajeErrorUsuario("No se puede eliminar el usuario ya que tiene incidencias asociadas");
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
     * Modifica un usuario de la base de datos
     * @author Óscar Barahona Ortega
     * @param usuarioId Identificador del usuario a modificar
     * @param usuario Nuevos datos del usuario a modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarUsuario(Integer usuarioId, Usuario usuario) throws ExcepcionIncidenciasCAD {
        String dml = "update usuario set cuenta = ?, nombre = ?, apellido = ?, departamento = ? where usuario_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, usuario.getCuenta());
            sentenciaPreparada.setString(2, usuario.getNombre());
            sentenciaPreparada.setString(3, usuario.getApellido());
            sentenciaPreparada.setString(4, usuario.getDepartamento());
            sentenciaPreparada.setInt(5, usuarioId);
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
                    e.setMensajeErrorUsuario("El nombre de cuenta es obligatorio");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El nombre de cuenta ya existe y no se puede repetir");
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
     * Lee un usuario de la base de datos
     * @author Óscar Barahona Ortega
     * @param usuarioId Identificador del usuario a leer
     * @return Usuario a leer de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Usuario leerUsuario(Integer usuarioId) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from usuario "
                + "where usuario_id = ?";
        PreparedStatement sentenciaPreparada = null;
        Usuario usuario = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            sentenciaPreparada.setInt(1, usuarioId);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {                
                usuario = new Usuario();
                usuario.setUsuarioId(resultado.getInt("usuario_id"));
                usuario.setCuenta(resultado.getString("cuenta"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellido(resultado.getString("apellido"));
                usuario.setDepartamento(resultado.getString("departamento"));
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return usuario;
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    sentenciaPreparada.toString());
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Lee todos los usuarios de la base de datos
     * @author Óscar Barahona Ortega
     * @return Lista con todos los usuarios de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Usuario> leerUsuarios() throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from usuario ";
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        Usuario usuario = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                usuario = new Usuario();
                usuario.setUsuarioId(resultado.getInt("usuario_id"));
                usuario.setCuenta(resultado.getString("cuenta"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellido(resultado.getString("apellido"));
                usuario.setDepartamento(resultado.getString("departamento"));
                listaUsuarios.add(usuario);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return listaUsuarios;          
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

/**
     * Inserta un tipo de equipo en la base de datos
     * @author Ramon Tezanos San Emeterio
     * @param tipoEquipo Datos de la dependencia a insertar
     * @return Cantidad de registros insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Integer insertarTipoEquipo(TipoEquipo tipoEquipo) throws ExcepcionIncidenciasCAD {
        String dml = "insert into tipo_equipo(codigo,nombre) values (?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, tipoEquipo.getCodigo());
            sentenciaPreparada.setString(2, tipoEquipo.getNombre());
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
                    e.setMensajeErrorUsuario("El código y nombre del tipo de equipo son obligatorios");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El código y/o el nombre del tipo de equipo ya existen y no se pueden repetir");
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
     * Elimina un tipo de equipo de la base de datos
     * @author Ramon Tezanos San Emeterio
     * @param tipoEquipoID Identificador de la dependencia a eliminar
     * @return Cantidad de registros eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarTipoEquipo(Integer tipoEquipoID) throws ExcepcionIncidenciasCAD {
        String dml = "delete from tipo_equipo where tipo_equipo_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setInt(1, tipoEquipoID);
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
                    e.setMensajeErrorUsuario("No se puede eliminar este tipo de equipo ya que tiene equipos asociados");
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
     * Modifica un tipo de equipo de la base de datos
     * @author Ramon Tezanos San Emeterio
     * @param tipoEquipoId Identificador del tipo de equipo a modificar
     * @param tipoEquipo Nuevos datos del tipo de equipo a modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarTipoEquipo(Integer tipoEquipoId, TipoEquipo tipoEquipo) throws ExcepcionIncidenciasCAD {
        String dml = "update tipo_equipo set codigo = ?, nombre = ? where tipo_equipo_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, tipoEquipo.getCodigo());
            sentenciaPreparada.setString(2, tipoEquipo.getNombre());
            sentenciaPreparada.setInt(3, tipoEquipoId);
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
                    e.setMensajeErrorUsuario("El código y nombre del tipo de equipo son obligatorios");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El código y/o el nombre del tipo de equipo ya existen y no se pueden repetir");
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
     * 
     * @param nombre Recibe el nombre del tipo de equipo buscado
     * @return Devuelve un tipo de equipo
     * @throws ExcepcionIncidenciasCAD 
     */
    public TipoEquipo leerTipoEquipo(String nombre) throws ExcepcionIncidenciasCAD {
        String dql = "select * from tipo_equipo where nombre = ?";
        PreparedStatement sentenciaPreparada = null;
        
        TipoEquipo tipoEquipo = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            sentenciaPreparada.setString(1, nombre);
            while (resultado.next()) {
                
                tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("te.tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("te.codigo"));
                tipoEquipo.setNombre(resultado.getString("te.nombre"));
                
                
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return tipoEquipo;            
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
    /**
     * Lee todos los tipos de equipo de la base de datos
     * @author Ramon Tezanos San Emeterio
     * @return Lista con todos los equipos de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<TipoEquipo> leerTipoEquipo() throws ExcepcionIncidenciasCAD {
        String dql = "select * from tipo_equipo";
        PreparedStatement sentenciaPreparada = null;
        ArrayList<TipoEquipo> listaTipoEquipos = new ArrayList(); 
        TipoEquipo tipoEquipo = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("te.tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("te.codigo"));
                tipoEquipo.setNombre(resultado.getString("te.nombre"));                
                listaTipoEquipos.add(tipoEquipo);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return listaTipoEquipos;            
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
    
    /**
     * Inserta un equipo en la base de datos
     * @author Óscar Barahona Ortega
     * @param equipo Datos del equipo a insertar
     * @return Cantidad de registros insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Integer insertarEquipo(Equipo equipo) throws ExcepcionIncidenciasCAD {
        String dml = "insert into equipo(numero_etiqueta_consejeria,tipo_equipo_id) values (?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, equipo.getNumeroEtiquetaConsejeria());
            sentenciaPreparada.setInt(2, equipo.getTipoEquipo().getTipoEquipoId());
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
                    e.setMensajeErrorUsuario("El número de etiqueta de consejería y el tipo de equipo son obligatorios");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El número de etiqueta de consejería ya existe y no se puede repetir");
                    break;
                case 1452:
                    e.setMensajeErrorUsuario("El tipo de equipo seleccionado no existe");
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
     * Elimina un equipo de la base de datos
     * @author Óscar Barahona Ortega
     * @param equipoId Identificador del equipo a eliminar
     * @return Cantidad de registros eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarEquipo(Integer equipoId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from equipo where equipo_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setInt(1, equipoId);
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
                    e.setMensajeErrorUsuario("No se puede eliminar el equipo ya que tiene incidencias asociadas");
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
     * Modifica un equipo de la base de datos
     * @author Óscar Barahona Ortega
     * @param equipoId Identificador del equipo a modificar
     * @param equipo Nuevos datos del equipo a modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarEquipo(Integer equipoId, Equipo equipo) throws ExcepcionIncidenciasCAD {
        String dml = "update equipo set numero_etiqueta_consejeria = ?, tipo_equipo_id = ? where equipo_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, equipo.getNumeroEtiquetaConsejeria());
            sentenciaPreparada.setInt(2, equipo.getTipoEquipo().getTipoEquipoId());
            sentenciaPreparada.setInt(3, equipoId);
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
                    e.setMensajeErrorUsuario("El número de etiqueta de consejería y el tipo de equipo son obligatorios");
                    break;
                case 1452:
                    e.setMensajeErrorUsuario("El tipo de equipo seleccionado no existe");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El ID de equipo y/o el número de etiqueta de consejería ya existen y no se pueden repetir");
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
     * Lee un equipo de la base de datos
     * @author Ignacio Fontecha Hernández
     * @param equipoId Identificador del equipo a leer
     * @return Equipo a leer de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Equipo leerEquipo(Integer equipoId) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from tipo_equipo te, equipo e "
                + "where te.tipo_equipo_id = e.tipo_equipo_id "
                + "  and equipo_id = ?";
        PreparedStatement sentenciaPreparada = null;
        Equipo equipo = null;
        TipoEquipo tipoEquipo = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            sentenciaPreparada.setInt(1, equipoId);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                equipo = new Equipo();
                equipo.setEquipoId(resultado.getInt("equipo_id"));
                equipo.setNumeroEtiquetaConsejeria(resultado.getString("numero_etiqueta_consejeria"));
                tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("te.tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("te.codigo"));
                tipoEquipo.setNombre(resultado.getString("te.nombre"));
                equipo.setTipoEquipo(tipoEquipo);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return equipo;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    sentenciaPreparada.toString());
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
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
                tipoEquipo.setTipoEquipoId(resultado.getInt("te.tipo_equipo_id"));
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

    /**
     * Lee todos los equipos de la base de datos
     * @author Ignacio Fontecha Hernández
     * @param numeroEtiquetaConsejeria
     * @param tipoEquipoId
     * @param orden
     * @return Lista con todos los equipos de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Equipo> leerEquipos(String numeroEtiquetaConsejeria, Integer tipoEquipoId, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from tipo_equipo te, equipo e "
                + "where te.tipo_equipo_id = e.tipo_equipo_id";
        if (numeroEtiquetaConsejeria != null) dql = dql + " and numero_etiqueta_consejeria like '%" + numeroEtiquetaConsejeria + "%'";
        if (tipoEquipoId !=null) dql = dql + " and tipo_equipo_id = " + tipoEquipoId;
        if (criterioOrden == NUMERO_ETIQUETA_CONSEJERIA) {
            dql = dql + " order by numero_etiqueta_consejeria";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == TIPO_EQUIPO) {
            dql = dql + " order by te.codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
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
                tipoEquipo.setTipoEquipoId(resultado.getInt("te.tipo_equipo_id"));
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
    
 /**
     * Inserta una incidencia en la base de datos
     * @author Víctor Bolado Obregón
     * @param incidencia Datos de la incidencia a insertar
     * @return Cantidad de registros insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Integer insertarIncidencia(Incidencia incidencia) throws ExcepcionIncidenciasCAD {
        String dml = "insert into incidencia(posicion_equipo_dependencia, descripcion, "
                + "comentario_administrador, fecha_estado_actual, usuario_id, equipo_id, dependencia_id, estado_id) "
                + "values (?,?,?,?,?,?,?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, incidencia.getPosicionEquipoDependencia());
            sentenciaPreparada.setString(2, incidencia.getDescripcion());
            sentenciaPreparada.setString(3, incidencia.getComentarioAdministrador());
            //Se crea una java.sql.date para formatear la fecha de la incidencia.
            java.sql.Date fecha = new java.sql.Date(incidencia.getFechaEstadoActual().getTime());
            sentenciaPreparada.setDate(4, fecha);
            sentenciaPreparada.setInt(5, incidencia.getUsuario().getUsuarioId());
            sentenciaPreparada.setInt(6, incidencia.getEquipo().getEquipoId());
            sentenciaPreparada.setInt(7, incidencia.getDependencia().getDependenciaId());
            sentenciaPreparada.setInt(8, incidencia.getEstado().getEstadoId());
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
                    e.setMensajeErrorUsuario("La descripción, la fecha del estado actual, el usuario, el equipo, "
                            + "la dependencia y el estado de las incidencias son obligatorios");
                    break;
                case 1452:
                    e.setMensajeErrorUsuario("El equipo, usuario, dependencia y/o estado no existen.");
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
     * Borra una incidencia en la base de datos
     * @author Víctor Bolado Obregón
     * @param incidenciaId Identificador de la incidencia a borrar
     * @return Cantidad de registros borrados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarIncidencia(Integer incidenciaId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from incidencia where incidencia_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setInt(1, incidenciaId);
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
                    e.setMensajeErrorUsuario("No se puede eliminar la incidencia ya que tiene registros en el historial.");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador.");
                    break;
            }
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica una incidencia de la base de datos
     * @author IVíctor Bolado Obregón
     * @param incidenciaId Identificador de la incidencia a modificar
     * @param incidencia Nuevos datos de la incidencia a modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarIncidencia(Integer incidenciaId, Incidencia incidencia) throws ExcepcionIncidenciasCAD {
        String dml = "update incidencia set posicion_equipo_dependencia=?, "
                + "descripcion=?, "
                + "comentario_administrador=?, "
                + "fecha_estado_actual=?, "
                + "usuario_id=?, "
                + "equipo_id=?, "
                + "dependencia_id=?, "
                + "estado_id=? "
                + "where incidencia_id=" + incidenciaId;
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, incidencia.getPosicionEquipoDependencia());
            sentenciaPreparada.setString(2, incidencia.getDescripcion());
            sentenciaPreparada.setString(3, incidencia.getComentarioAdministrador());
            //Se crea una java.sql.date para formatear la fecha de la incidencia.
            java.sql.Date fecha = new java.sql.Date(incidencia.getFechaEstadoActual().getTime());
            sentenciaPreparada.setDate(4, fecha);
            sentenciaPreparada.setInt(5, incidencia.getUsuario().getUsuarioId());
            sentenciaPreparada.setInt(6, incidencia.getEquipo().getEquipoId());
            sentenciaPreparada.setInt(7, incidencia.getDependencia().getDependenciaId());
            sentenciaPreparada.setInt(8, incidencia.getEstado().getEstadoId());
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
                    e.setMensajeErrorUsuario("La descripción, la fecha del estado actual, el usuario, el equipo, "
                            + "la dependencia y el estado de las incidencias son obligatorios");
                    break;
                case 1452:
                    e.setMensajeErrorUsuario("El equipo, usuario, dependencia y/o estado no existen.");
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
     * Muestra el contenido completo de la tabla incidencias
     * @author Víctor Bolado Obregón
     * @return Una lista de incidencias
     * @throws ExcepcionIncidenciasCAD se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Incidencia> leerIncidencias() throws ExcepcionIncidenciasCAD {
        String dql = "select * from incidencia";
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Incidencia> listaIncidencias = new ArrayList();
        Incidencia incidencia = null;
        Equipo equipo = new Equipo();
        Usuario usuario = new Usuario();
        Dependencia dependencia = new Dependencia();
        Estado estado = new Estado();
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                incidencia = new Incidencia();
                incidencia.setIncidenciaId(resultado.getInt("incidencia_id"));
                incidencia.setPosicionEquipoDependencia(resultado.getString("posicion_equipo_dependencia"));
                incidencia.setDescripcion(resultado.getString("descripcion"));
                incidencia.setComentarioAdministrador(resultado.getString("comentario_administrador"));
                incidencia.setFechaEstadoActual(resultado.getDate("fecha_estado_actual"));
                usuario.setUsuarioId(resultado.getInt("usuario_id"));
                incidencia.setUsuario(usuario);
                equipo.setEquipoId(resultado.getInt("equipo_id"));
                incidencia.setEquipo(equipo);
                dependencia.setDependenciaId(resultado.getInt("dependencia_id"));
                incidencia.setDependencia(dependencia);
                estado.setEstadoId(resultado.getInt("estado_id"));
                incidencia.setEstado(estado);
                listaIncidencias.add(incidencia);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return listaIncidencias;         
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
    
    /**
     * Muestra una incidencia de la tabla incidencias
     * @author Víctor Bolado Obregón
     * @return Una lista de incidencias
     * @param incidenciaId identificador de la incidencia a mostrar
     * @throws ExcepcionIncidenciasCAD se lanza en el caso de que se produzca cualquier excepción
     */
    public Incidencia leerIncidencia(int incidenciaId) throws ExcepcionIncidenciasCAD {
        String dql = "select * from incidencia where incidencia_id=" + incidenciaId;
        PreparedStatement sentenciaPreparada = null;
        Incidencia incidencia = null;
        Equipo equipo = new Equipo();
        Usuario usuario = new Usuario();
        Dependencia dependencia = new Dependencia();
        Estado estado = new Estado();
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                incidencia = new Incidencia();
                incidencia.setIncidenciaId(resultado.getInt("incidencia_id"));
                incidencia.setPosicionEquipoDependencia(resultado.getString("posicion_equipo_dependencia"));
                incidencia.setDescripcion(resultado.getString("descripcion"));
                incidencia.setComentarioAdministrador(resultado.getString("comentario_administrador"));
                incidencia.setFechaEstadoActual(resultado.getDate("fecha_estado_actual"));
                usuario.setUsuarioId(resultado.getInt("usuario_id"));
                incidencia.setUsuario(usuario);
                equipo.setEquipoId(resultado.getInt("equipo_id"));
                incidencia.setEquipo(equipo);
                dependencia.setDependenciaId(resultado.getInt("dependencia_id"));
                incidencia.setDependencia(dependencia);
                estado.setEstadoId(resultado.getInt("estado_id"));
                incidencia.setEstado(estado);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return incidencia;       
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
    /**
     * Inserta un dato historico en la base de datos
     * @author Diego Fernández Díaz
     * @param historial Datos del dato historico a insertar
     * @return Cantidad de registros insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Integer insertarHistorial(Historial historial) throws ExcepcionIncidenciasCAD {
        String dml = "insert into historial"
                + "(fecha,incidencia_id,estado_id) values (?,?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            Date fechaUtil = historial.getFecha();
            java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
            sentenciaPreparada.setDate(1, fechaSql);
            sentenciaPreparada.setInt(2, historial.getIncidencia().getIncidenciaId());
            sentenciaPreparada.setInt(3, historial.getEstado().getEstadoId());
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
            switch (ex.getErrorCode()) {
                case 1364:  
                    e.setMensajeErrorUsuario("La fecha, la incidencia o el estado de historial son obligatorios");
                    break;
                case 1452:
                    e.setMensajeErrorUsuario("La incidendia o el estado indicado no existe");
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
     * Elimina un dato historico de la base de datos
     * @author Diego Fernández Díaz
     * @param historialId Identificador del dato historico a eliminar
     * @return Cantidad de registros eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarHistorial(Integer historialId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from historial where historial_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setInt(1, historialId);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    sentenciaPreparada.toString());
            cerrarConexion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica un dato historico de la base de datos
     * @author Diego Fernandez Diaz
     * @param historialId Identificador del dato historico a modificar
     * @param historial Nuevos datos del dato historico a modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarHistorial(Integer historialId, Historial historial) throws ExcepcionIncidenciasCAD {
        String dml = "update historial set fecha = ?, incidencia_id = ?, estado_id = ? where historial_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            Date fechaUtil = historial.getFecha();
            java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
            sentenciaPreparada.setDate(1, fechaSql);
            sentenciaPreparada.setInt(2, historial.getIncidencia().getIncidenciaId());
            sentenciaPreparada.setInt(3, historial.getEstado().getEstadoId());
            sentenciaPreparada.setInt(4, historialId);
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
            switch (ex.getErrorCode()) {
                case 1366:  
                    e.setMensajeErrorUsuario("El identificador de la incidencia y del estado son obligatorios");
                    break;
                case 1452:
                    e.setMensajeErrorUsuario("La incidendia o el estado indicado no existe");
                    break;
                case 1292:
                    e.setMensajeErrorUsuario("La fecha es un campo obligatorio");
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
     * Lee un dato historico de la base de datos
     * @author Diego Fernandez Diaz
     * @param historialId Identificador del dato historico a leer
     * @return dato historico a leer de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Historial leerHistorial(Integer historialId) {
        return null;
    }

    /**
     * Lee todos los datos historicos de la base de datos
     * @author Diego Fernandez Diaz
     * @return Lista con todos los datos historicos de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Historial> leerHistorial() throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from historial";
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Historial> listaHistorial = new ArrayList();
        Historial historial = null;
        Incidencia incidencia = null;
        Estado estado = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                historial = new Historial();
                historial.setHistorialId(resultado.getInt("historial_id"));
                historial.setFecha(resultado.getDate("fecha"));
                incidencia = new Incidencia();
                estado = new Estado();
                incidencia.setIncidenciaId(resultado.getInt("incidencia_id"));
                estado.setEstadoId(resultado.getInt("estado_id"));
                historial.setIncidencia(incidencia);
                historial.setEstado(estado);
                listaHistorial.add(historial);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return listaHistorial;            
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
