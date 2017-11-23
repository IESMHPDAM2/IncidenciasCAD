package incidenciascad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    public static Integer CUENTA = 5;
    public static Integer NOMBRE = 6;
    public static Integer APELLIDO = 7;
    public static Integer DEPARTAMENTO = 8;
    public static Integer CODIGO_TIPO_EQUIPO = 9;
    public static Integer NOMBRE_TIPO_EQUIPO = 10;
    public static Integer NOMBRE_ESTADO = 12;
    public static Integer CODIGO_ESTADO = 13;
    public static Integer FECHA = 15;
    public static Integer INCIDENCIA_ID = 16;
    public static Integer ESTADO_HISTORIAL_ID = 17;
    /**
     * Constructor vacío
     * @author Ignacio Fontecha Hernández
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public IncidenciasCAD() throws ExcepcionIncidenciasCAD {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://10.0.1.96/incidencias?user=incidencias&password=incidencias");
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
    
    /**
     * Lee una dependencia de la base de datos
     * @author Marcos González Fernández
     * @param dependenciaId Identificador de la dependencia a leer
     * @return La dependencia a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Dependencia leerDependencia(Integer dependenciaId)throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from dependencias d "
                + "where dependencia_id = ?";                
        PreparedStatement sentenciaPreparada = null;       
        Dependencia dependencia = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            sentenciaPreparada.setInt(1,dependenciaId);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                dependencia = new Dependencia();
                dependencia.setDependenciaId(resultado.getInt("dependencia_id"));
                dependencia.setCodigo(resultado.getString("codigo"));
                dependencia.setNombre(resultado.getString("nombre"));
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return dependencia;            
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
     * Lee todas las dependencias de la base de datos
     * @author Marcos González Fernández
     * @return Lista de todas las dependencias
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Dependencia> leerDependencias() throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from dependencia d ";                
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Dependencia> listaDependencia = new ArrayList();
        Dependencia dependencia = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                dependencia = new Dependencia();
                dependencia.setDependenciaId(resultado.getInt("dependencia_id"));
                dependencia.setCodigo(resultado.getString("codigo"));
                dependencia.setNombre(resultado.getString("nombre"));
                listaDependencia.add(dependencia);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return listaDependencia;            
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
     * Lee todos los usuarios de la base de datos
     * @author Óscar Barahona Ortega
     * @param cuenta
     * @param nombre
     * @param apellido
     * @param departamento
     * @param criterioOrden
     * @param orden
     * @return Lista con todos los usuarios de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Usuario> leerUsuarios(String cuenta, String nombre, String apellido, String departamento, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from usuario "
                + "where 1=1";
        if (cuenta != null) dql = dql + " and cuenta like '%" + cuenta + "%'";
        if (nombre != null) dql = dql + " and nombre like '%" + nombre + "%'";
        if (apellido != null) dql = dql + " and apellido like '%" + apellido + "%'";
        if (departamento != null) dql = dql + " and departamento like '%" + departamento + "%'";
        if (criterioOrden == CUENTA) {
            dql = dql + " order by CUENTA";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == NOMBRE) {
            dql = dql + " order by nombre";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == APELLIDO) {
            dql = dql + " order by apellido";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == DEPARTAMENTO) {
            dql = dql + " order by departamento";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
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
     * Lee un tipo de equipo de la base de datos
     * @param tipoEquipoID Recibe el id del tipo de equipo buscado
     * @return Devuelve un tipo de equipo
     * @throws ExcepcionIncidenciasCAD 
     */
    public TipoEquipo leerTipoEquipo(Integer tipoEquipoID) throws ExcepcionIncidenciasCAD {
        String dql = "select * from tipo_equipo where tipo_equipo_id = ?";
        PreparedStatement sentenciaPreparada = null;
        
        TipoEquipo tipoEquipo = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            sentenciaPreparada.setInt(1, tipoEquipoID);
            while (resultado.next()) {
                
                tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("codigo"));
                tipoEquipo.setNombre(resultado.getString("nombre"));
                
                
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
     * Lee todos los equipos de la base de datos
     * @author Ramon Tezanos San Emeterio
     * @return Lista con todos los equipos de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<TipoEquipo> leerTiposEquipo() throws ExcepcionIncidenciasCAD {
        String dql = "select * from tipo_equipo";
        PreparedStatement sentenciaPreparada = null;
        ArrayList<TipoEquipo> listaTipoEquipos = new ArrayList(); 
        TipoEquipo tipoEquipo = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("codigo"));
                tipoEquipo.setNombre(resultado.getString("nombre"));                
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
     * Lee los equipos de la base de datos que coincidan con los filtros pasados
     * @author Ramon Tezanos San Emeterio
     * @param codigo Recibe el codigo del tipo de equipo o null en caso de no querer filtrar por ese criterio
     * @param nombre Recibe el nombre del tipo de equipo o null en caso de no querer filtrar por ese criterio
     * @param criterioOrden Recibe el criterio por el cual se quiere ordenar la selección obtenida
     * @param orden Recibe la forma en la que se quiere ordenar, ascendentemenete o descendentemente
     * @return Devuelve la lista con todos los equipos de la base de datos una vez filtrado
     * @throws ExcepcionIncidenciasCAD 
     */
     public ArrayList<TipoEquipo> leerTiposEquipo(String codigo, String nombre, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from tipo_equipo where 1 = 1";
        if(codigo != null || nombre != null)
        if (codigo != null) dql = dql + " and codigo like '%" + codigo + "%'";
        if (nombre != null) dql = dql + " and nombre like '%" + nombre + "%'";
        if (criterioOrden == NOMBRE_TIPO_EQUIPO) {
            dql = dql + " order by nombre";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == CODIGO_TIPO_EQUIPO) {
            dql = dql + " order by codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }PreparedStatement sentenciaPreparada = null;
        ArrayList<TipoEquipo> listaTipoEquipos = new ArrayList(); 
        TipoEquipo tipoEquipo = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("codigo"));
                tipoEquipo.setNombre(resultado.getString("nombre"));                
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
        if (criterioOrden == CODIGO_TIPO_EQUIPO) {
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
     * Inserta un estado en la base de datos
     * @author Marcos González Fernández
     * @param estado Datos de estado a insertar
     * @return Cantidad de registros insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Integer insertarEstado(Estado estado) throws ExcepcionIncidenciasCAD {
        String dml = "insert into estado(codigo,nombre) values (?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, estado.getCodigo());
            sentenciaPreparada.setString(2, estado.getNombre());            
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
                    e.setMensajeErrorUsuario("El código y nombre del estado son obligatorios");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El código y/o nombre del estado ya existen y no se pueden repetir");
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
     * Elimina un estado de la base de datos
     * @author Marcos González Fernández
     * @param estadoId Identificador del estado a eliminar
     * @return Cantidad de registros eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarEstado(Integer estadoId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from estado where estado_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setInt(1, estadoId);
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
                    e.setMensajeErrorUsuario("No se puede eliminar el estado ya que tiene historiales asociados");
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
     * Modifica un estado de la base de datos
     * @author Marcos González Fernández
     * @param estadoId Identificador del estado a modificar
     * @param estado Nuevos datos del estado a modificar
     * @return Cantidad de registros modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarEstado(Integer estadoId, Estado estado) throws ExcepcionIncidenciasCAD {
        String dml = "update estado set codigo = ?, nombre = ? where estado_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, estado.getCodigo());
            sentenciaPreparada.setString(2, estado.getNombre());
            sentenciaPreparada.setInt(3, estadoId);
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
                    e.setMensajeErrorUsuario("El código y nombre  del estado son obligatorios");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El código y/o nombre del estado ya existen y no se pueden repetir");
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
     * Lee un estado de la base de datos
     * @author Marcos González Fernández
     * @param estadoId Identificador del estado a leer
     * @return Estado a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Estado leerEstado(Integer estadoId) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from estado e "
                +"where estado_id = ?";                
        PreparedStatement sentenciaPreparada = null;       
        Estado estado = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            sentenciaPreparada.setInt(1,estadoId);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                estado = new Estado();
                estado.setEstadoId(resultado.getInt("estado_id"));
                estado.setCodigo(resultado.getString("codigo"));
                estado.setNombre(resultado.getString("nombre"));
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return estado;            
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
     * Lee todos los estados de la base de datos
     * @author Marcos González Fernández
     * @return Lista de todos los estados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Estado> leerEstados() throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from estado e ";                
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Estado> listaEstados = new ArrayList();
        Estado estado = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                estado = new Estado();
                estado.setEstadoId(resultado.getInt("estado_id"));
                estado.setCodigo(resultado.getString("codigo"));
                estado.setNombre(resultado.getString("nombre"));
                listaEstados.add(estado);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return listaEstados;            
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
     * @author Marcos Gonzalez Fernandez
     * @param codigo Recibe el codigo de estado o null en caso de no querer filtrar por ese criterio
     * @param nombre Recibe el nombre de estado o null en caso de no querer filtrar por ese criterio
     * @param criterioOrden Recibe el criterio por el cual se quiere ordenar la selección obtenida
     * @param orden Recibe la forma en la que se quiere ordenar, ascendentemenete o descendentemente
     * @return Devuelve la lista con todos los estados de la base de datos una vez filtrado
     * @throws ExcepcionIncidenciasCAD 
     */
    public ArrayList<Estado> leerEstados(String codigo, String nombre, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from estado e ";   
        if (codigo !=null || nombre !=null)
        if (codigo !=null) dql = dql + " and codigo like '%" + codigo + "%'";
        if (nombre !=null) dql = dql + " and nombre like '%" + nombre + "%'";
        if (criterioOrden == NOMBRE_ESTADO){
            dql = dql + " order by e.nombre";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == CODIGO_ESTADO){
            dql = dql + " order by e.codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        } PreparedStatement sentenciaPreparada = null;
        ArrayList<Estado> listaEstados = new ArrayList();
        Estado estado = null;   
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                estado = new Estado();
                estado.setEstadoId(resultado.getInt("e.estado_id"));
                estado.setCodigo(resultado.getString("e.codigo"));
                estado.setNombre(resultado.getString("e.nombre"));
                listaEstados.add(estado);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return listaEstados;            
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
     * Lee las incidencias filtradas de la base de datos
     * @author Víctor Bolado Obregón
     * @return Una lista de incidencias
     * @throws ExcepcionIncidenciasCAD se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Incidencia> leerIncidencias(String dql) throws ExcepcionIncidenciasCAD {
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Incidencia> listaIncidencias = new ArrayList();
        Incidencia incidencia = null;
        Equipo equipo = new Equipo();
        TipoEquipo tipoEquipo = new TipoEquipo();
        Usuario usuario = new Usuario();
        Dependencia dependencia = new Dependencia();
        Estado estado = new Estado();
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                //INCIDENCIA
                incidencia = new Incidencia();
                incidencia.setIncidenciaId(resultado.getInt("i.incidencia_id"));
                incidencia.setPosicionEquipoDependencia(resultado.getString("i.posicion_equipo_dependencia"));
                incidencia.setDescripcion(resultado.getString("i.descripcion"));
                incidencia.setComentarioAdministrador(resultado.getString("i.comentario_administrador"));
                incidencia.setFechaEstadoActual(resultado.getDate("i.fecha_estado_actual"));
                //USUARIO
                usuario.setUsuarioId(resultado.getInt("u.usuario_id"));
                usuario.setCuenta(resultado.getString("u.cuenta"));
                usuario.setNombre(resultado.getString("u.nombre"));
                usuario.setApellido(resultado.getString("u.apellido"));
                usuario.setDepartamento(resultado.getString("u.departamento"));
                incidencia.setUsuario(usuario);
                //TIPO EQUIPO
                tipoEquipo.setTipoEquipoId(resultado.getInt("te.tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("te.codigo"));
                tipoEquipo.setNombre(resultado.getString("te.nombre"));
                //EQUIPO
                equipo.setEquipoId(resultado.getInt("e.equipo_id"));
                equipo.setNumeroEtiquetaConsejeria(resultado.getString("e.numero_etiqueta_consejeria"));
                equipo.setTipoEquipo(tipoEquipo);
                incidencia.setEquipo(equipo);
                //DEPENDENCIA
                dependencia.setDependenciaId(resultado.getInt("d.dependencia_id"));
                dependencia.setCodigo(resultado.getString("d.codigo"));
                dependencia.setNombre(resultado.getString("d.nombre"));
                incidencia.setDependencia(dependencia);
                //ESTADO
                estado.setEstadoId(resultado.getInt("es.estado_id"));
                estado.setCodigo(resultado.getString("es.codigo"));
                estado.setNombre(resultado.getString("es.nombre"));
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
     * Lee las incidencias de la base de datos
     * @author Víctor Bolado Obregón
     * @return Una lista de incidencias
     * @throws ExcepcionIncidenciasCAD se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Incidencia> leerIncidencias() throws ExcepcionIncidenciasCAD {
        String dql = "select * from incidencia i, usuario u, dependencia d, equipo e, tipo_equipo te, estado es " +
            "where i.USUARIO_ID=u.USUARIO_ID " +
            "and i.DEPENDENCIA_ID=d.DEPENDENCIA_ID " +
            "and e.TIPO_EQUIPO_ID=te.TIPO_EQUIPO_ID " +
            "and i.EQUIPO_ID=e.EQUIPO_ID " +
            "and i.ESTADO_ID=es.ESTADO_ID";
        return leerIncidencias(dql);
    }
    
    /**
     * Lee una incidencia de la base de datos
     * @author Víctor Bolado Obregón
     * @param incidenciaId Identificardor de la incidencia
     * @return Una lista de incidencias
     * @throws ExcepcionIncidenciasCAD se lanza en el caso de que se produzca cualquier excepción
     */
    public Incidencia leerIncidencia(int incidenciaId) throws ExcepcionIncidenciasCAD {
        String dql = "select * from incidencia i, usuario u, dependencia d, equipo e, tipo_equipo te, estado es " +
            "where i.USUARIO_ID=u.USUARIO_ID " +
            "and i.DEPENDENCIA_ID=d.DEPENDENCIA_ID " +
            "and e.TIPO_EQUIPO_ID=te.TIPO_EQUIPO_ID " +
            "and i.EQUIPO_ID=e.EQUIPO_ID " +
            "and i.ESTADO_ID=es.ESTADO_ID "
                + "and i.incidencia_id = " + incidenciaId;
        PreparedStatement sentenciaPreparada = null;
        Incidencia incidencia = null;
        Equipo equipo = new Equipo();
        TipoEquipo tipoEquipo = new TipoEquipo();
        Usuario usuario = new Usuario();
        Dependencia dependencia = new Dependencia();
        Estado estado = new Estado();
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                //INCIDENCIA
                incidencia = new Incidencia();
                incidencia.setIncidenciaId(resultado.getInt("i.incidencia_id"));
                incidencia.setPosicionEquipoDependencia(resultado.getString("i.posicion_equipo_dependencia"));
                incidencia.setDescripcion(resultado.getString("i.descripcion"));
                incidencia.setComentarioAdministrador(resultado.getString("i.comentario_administrador"));
                incidencia.setFechaEstadoActual(resultado.getDate("i.fecha_estado_actual"));
                //USUARIO
                usuario.setUsuarioId(resultado.getInt("u.usuario_id"));
                usuario.setCuenta(resultado.getString("u.cuenta"));
                usuario.setNombre(resultado.getString("u.nombre"));
                usuario.setApellido(resultado.getString("u.apellido"));
                usuario.setDepartamento(resultado.getString("u.departamento"));
                incidencia.setUsuario(usuario);
                //TIPO EQUIPO
                tipoEquipo.setTipoEquipoId(resultado.getInt("te.tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("te.codigo"));
                tipoEquipo.setNombre(resultado.getString("te.nombre"));
                //EQUIPO
                equipo.setEquipoId(resultado.getInt("e.equipo_id"));
                equipo.setNumeroEtiquetaConsejeria(resultado.getString("e.numero_etiqueta_consejeria"));
                equipo.setTipoEquipo(tipoEquipo);
                incidencia.setEquipo(equipo);
                //DEPENDENCIA
                dependencia.setDependenciaId(resultado.getInt("d.dependencia_id"));
                dependencia.setCodigo(resultado.getString("d.codigo"));
                dependencia.setNombre(resultado.getString("d.nombre"));
                incidencia.setDependencia(dependencia);
                //ESTADO
                estado.setEstadoId(resultado.getInt("es.estado_id"));
                estado.setCodigo(resultado.getString("es.codigo"));
                estado.setNombre(resultado.getString("es.nombre"));
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
    
//    /**
//     * Lee las incidencias filtradas de la base de datos
//     * @author Víctor Bolado Obregón
//     * @return Una lista de incidencias
//     * @param posicionEquipoDependencia posicion del equipo en la dependencia
//     * @param descripcion descripción de la incidencia
//     * @param comentarioAdministrador comentario del administrador para la incidencia
//     * @param fechaEstadoActual fecha del estado de la incidencia
//     * @param usuarioID identificador del usuario
//     * @param equipoID identificador del equipo
//     * @param dependenciaID identificador de la dependencia
//     * @param estadoID identificador del estado
//     * @param criterioOrden numero para indicar el orden en que se va a mostrar
//     * @param orden numero que indica si el orden es ascendente o descendente
//     * @throws ExcepcionIncidenciasCAD se lanza en el caso de que se produzca cualquier excepción
//     */
//    public ArrayList<Incidencia> leerIncidencias(String posicionEquipoDependencia, String descripcion, String comentarioAdministrador, Date fechaEstadoActual, Integer usuarioID, Integer equipoID, Integer dependenciaID, Integer estadoID, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
//        String dql = "select i.*,u.*,te.*,e.*,d.*,es.* from incidencia i, usuario u, dependencia d, equipo e, tipo_equipo te, estado es " +
//            "where i.USUARIO_ID=u.USUARIO_ID " +
//            "and i.DEPENDENCIA_ID=d.DEPENDENCIA_ID " +
//            "and e.TIPO_EQUIPO_ID=te.TIPO_EQUIPO_ID " +
//            "and i.EQUIPO_ID=e.EQUIPO_ID " +
//            "and i.ESTADO_ID=es.ESTADO_ID";
//        
//        if (posicionEquipoDependencia != null) dql = dql + " and posicion_equipo_dependencia like '%" + posicionEquipoDependencia + "%'";
//        if (descripcion != null) dql = dql + " and descripcion like '%" + descripcion + "%'";
//        if (comentarioAdministrador != null) dql = dql + " and comentario_administrador like '%" + comentarioAdministrador + "%'";
//        if (fechaEstadoActual != null)
//        {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            dql = dql + " and date_format(i.FECHA_ESTADO_ACTUAL, \"%Y-%m-%d\") like '%" + sdf.format(fechaEstadoActual) + "%'";
//        }
//        if (usuarioID != null) dql = dql + " and usuario_id like '%" + usuarioID + "%'";
//        if (equipoID != null) dql = dql + " and equipo_id like '%" + equipoID + "%'";
//        if (dependenciaID !=null) dql = dql + " and dependencia_id like '%" + dependenciaID + "%'";
//        if (estadoID != null) dql = dql + " and estado_id like '%" + estadoID + "%'";
//        
//        if (criterioOrden == POSICION_EQUIPO_DEPENDENCIA) 
//        {
//            dql = dql + " order by posicion_equipo_dependencia";
//            if (orden == ASCENDENTE) dql = dql + " asc";
//            if (orden == DESCENDENTE) dql = dql + " desc";
//        }
//        
//        if (criterioOrden == DESCRIPCION) 
//        {
//            dql = dql + " order by descripcion";
//            if (orden == ASCENDENTE) dql = dql + " asc";
//            if (orden == DESCENDENTE) dql = dql + " desc";
//        }
//        
//        if (criterioOrden == COMENTARIO_ADMINISTRADOR) 
//        {
//            dql = dql + " order by comentario_administrador";
//            if (orden == ASCENDENTE) dql = dql + " asc";
//            if (orden == DESCENDENTE) dql = dql + " desc";
//        }
//        
//        if (criterioOrden == FECHA_ESTADO_ACTUAL) 
//        {
//            dql = dql + " order by fecha_estado_actual";
//            if (orden == ASCENDENTE) dql = dql + " asc";
//            if (orden == DESCENDENTE) dql = dql + " desc";
//        }
//        
//        if (criterioOrden == USUARIO_ID) 
//        {
//            dql = dql + " order by usuario_id";
//            if (orden == ASCENDENTE) dql = dql + " asc";
//            if (orden == DESCENDENTE) dql = dql + " desc";
//        }
//        
//        if (criterioOrden == EQUIPO_ID) 
//        {
//            dql = dql + " order by equipo_id";
//            if (orden == ASCENDENTE) dql = dql + " asc";
//            if (orden == DESCENDENTE) dql = dql + " desc";
//        }
//        
//        if (criterioOrden == DEPENDENCIA_ID) 
//        {
//            dql = dql + " order by dependencia_id";
//            if (orden == ASCENDENTE) dql = dql + " asc";
//            if (orden == DESCENDENTE) dql = dql + " desc";
//        }
//        
//        if (criterioOrden == ESTADO_ID) 
//        {
//            dql = dql + " order by estado_id";
//            if (orden == ASCENDENTE) dql = dql + " asc";
//            if (orden == DESCENDENTE) dql = dql + " desc";
//        }
//        return leerIncidencias(dql);
//    }
    
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
     * @return Lista un dato historico a leer de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Historial leerHistorial(Integer historialId) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from historial hi,incidencia inc,estado es,estado ies, "
                + "usuario us,equipo eq,dependencia de,tipo_equipo teq "
                + "where hi.incidencia_id=inc.incidencia_id "
                + "and hi.estado_id=es.estado_id "
                + "and inc.usuario_id=us.usuario_id "
                + "and inc.equipo_id=eq.equipo_id "
                + "and inc.dependencia_id=de.dependencia_id "
                + "and inc.estado_id=ies.estado_id "
                + "and eq.tipo_equipo_id=teq.tipo_equipo_id "
                + "and hi.historial_id="+historialId;
        PreparedStatement sentenciaPreparada = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            Historial historial =null;
            while (resultado.next()) {
                TipoEquipo tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("teq.tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("teq.codigo"));
                tipoEquipo.setNombre(resultado.getString("teq.nombre"));
                Dependencia dependencia = new Dependencia();
                dependencia.setDependenciaId(resultado.getInt("de.dependencia_id"));
                dependencia.setCodigo(resultado.getString("de.codigo"));
                dependencia.setNombre(resultado.getString("de.nombre"));
                Usuario usuario = new Usuario();
                usuario.setUsuarioId(resultado.getInt("us.usuario_id"));
                usuario.setCuenta(resultado.getString("us.cuenta"));
                usuario.setNombre(resultado.getString("us.nombre"));
                usuario.setApellido(resultado.getString("us.apellido"));
                usuario.setDepartamento(resultado.getString("us.departamento"));
                Equipo equipo = new Equipo();
                equipo.setEquipoId(resultado.getInt("eq.equipo_id"));
                equipo.setNumeroEtiquetaConsejeria(resultado.getString("eq.numero_etiqueta_consejeria"));
                equipo.setTipoEquipo(tipoEquipo);
                Estado estadoIncidencia = new Estado();
                estadoIncidencia.setEstadoId(resultado.getInt("ies.estado_id"));
                estadoIncidencia.setCodigo(resultado.getString("ies.codigo"));
                estadoIncidencia.setNombre(resultado.getString("ies.nombre"));
                Incidencia incidencia = new Incidencia();
                incidencia.setIncidenciaId(resultado.getInt("inc.incidencia_id"));
                incidencia.setPosicionEquipoDependencia(resultado.getString("inc.posicion_equipo_dependencia"));
                incidencia.setDescripcion(resultado.getString("inc.descripcion"));
                incidencia.setComentarioAdministrador(resultado.getString("inc.comentario_administrador"));
                incidencia.setFechaEstadoActual(resultado.getDate("inc.fecha_estado_actual"));
                incidencia.setUsuario(usuario);
                incidencia.setEquipo(equipo);
                incidencia.setDependencia(dependencia);
                incidencia.setEstado(estadoIncidencia);
                Estado estadoHistorial = new Estado();
                estadoHistorial.setEstadoId(resultado.getInt("es.estado_id"));
                estadoHistorial.setCodigo(resultado.getString("es.codigo"));
                estadoHistorial.setNombre(resultado.getString("es.nombre"));
                historial = new Historial();
                historial.setHistorialId(resultado.getInt("hi.historial_id"));
                historial.setFecha(resultado.getDate("hi.fecha"));
                historial.setIncidencia(incidencia);
                historial.setEstado(estadoHistorial);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return historial;            
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
     * Lee todos los datos historicos de la base de datos
     * @author Diego Fernandez Diaz
     * @return Lista con todos los datos historicos de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Historial> leerHistorial() throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from historial hi,incidencia inc,estado es,estado ies, "
                + "usuario us,equipo eq,dependencia de,tipo_equipo teq "
                + "where hi.incidencia_id=inc.incidencia_id "
                + "and hi.estado_id=es.estado_id "
                + "and inc.usuario_id=us.usuario_id "
                + "and inc.equipo_id=eq.equipo_id "
                + "and inc.dependencia_id=de.dependencia_id "
                + "and inc.estado_id=ies.estado_id "
                + "and eq.tipo_equipo_id=teq.tipo_equipo_id";
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Historial> listaHistorial = new ArrayList();
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                TipoEquipo tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("teq.tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("teq.codigo"));
                tipoEquipo.setNombre(resultado.getString("teq.nombre"));
                Dependencia dependencia = new Dependencia();
                dependencia.setDependenciaId(resultado.getInt("de.dependencia_id"));
                dependencia.setCodigo(resultado.getString("de.codigo"));
                dependencia.setNombre(resultado.getString("de.nombre"));
                Usuario usuario = new Usuario();
                usuario.setUsuarioId(resultado.getInt("us.usuario_id"));
                usuario.setCuenta(resultado.getString("us.cuenta"));
                usuario.setNombre(resultado.getString("us.nombre"));
                usuario.setApellido(resultado.getString("us.apellido"));
                usuario.setDepartamento(resultado.getString("us.departamento"));
                Equipo equipo = new Equipo();
                equipo.setEquipoId(resultado.getInt("eq.equipo_id"));
                equipo.setNumeroEtiquetaConsejeria(resultado.getString("eq.numero_etiqueta_consejeria"));
                equipo.setTipoEquipo(tipoEquipo);
                Estado estadoIncidencia = new Estado();
                estadoIncidencia.setEstadoId(resultado.getInt("ies.estado_id"));
                estadoIncidencia.setCodigo(resultado.getString("ies.codigo"));
                estadoIncidencia.setNombre(resultado.getString("ies.nombre"));
                Incidencia incidencia = new Incidencia();
                incidencia.setIncidenciaId(resultado.getInt("inc.incidencia_id"));
                incidencia.setPosicionEquipoDependencia(resultado.getString("inc.posicion_equipo_dependencia"));
                incidencia.setDescripcion(resultado.getString("inc.descripcion"));
                incidencia.setComentarioAdministrador(resultado.getString("inc.comentario_administrador"));
                incidencia.setFechaEstadoActual(resultado.getDate("inc.fecha_estado_actual"));
                incidencia.setUsuario(usuario);
                incidencia.setEquipo(equipo);
                incidencia.setDependencia(dependencia);
                incidencia.setEstado(estadoIncidencia);
                Estado estadoHistorial = new Estado();
                estadoHistorial.setEstadoId(resultado.getInt("es.estado_id"));
                estadoHistorial.setCodigo(resultado.getString("es.codigo"));
                estadoHistorial.setNombre(resultado.getString("es.nombre"));
                Historial historial = new Historial();
                historial.setHistorialId(resultado.getInt("hi.historial_id"));
                historial.setFecha(resultado.getDate("hi.fecha"));
                historial.setIncidencia(incidencia);
                historial.setEstado(estadoHistorial);
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
    /**
     * Lee todos los datos historicos de la base de datos
     * @author Diego Fernández Díaz
     * @param fecha
     * @param incidenciaId
     * @param orden
     * @param estadoId 
     * @return Lista con todos los equipos de la base de datos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Historial> leerHistorial(Date fecha, Integer incidenciaId,Integer estadoId, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from historial hi,incidencia inc,estado es,estado ies, "
                + "usuario us,equipo eq,dependencia de,tipo_equipo teq "
                + "where hi.incidencia_id=inc.incidencia_id "
                + "and hi.estado_id=es.estado_id "
                + "and inc.usuario_id=us.usuario_id "
                + "and inc.equipo_id=eq.equipo_id "
                + "and inc.dependencia_id=de.dependencia_id "
                + "and inc.estado_id=ies.estado_id "
                + "and eq.tipo_equipo_id=teq.tipo_equipo_id";
        if (fecha != null) dql = dql + " and date_format(hi.fecha,'%Y-%m-%d') = '"+ formatearFecha(fecha)+"'";
        if (incidenciaId !=null) dql = dql + " and hi.incidencia_id = " + incidenciaId;
        if (estadoId !=null) dql = dql + " and hi.estado_id = " + estadoId;
        if (criterioOrden == FECHA) {
            dql = dql + " order by hi.fecha";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == INCIDENCIA_ID) {
            dql = dql + " order by hi.incidencia_id";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == ESTADO_HISTORIAL_ID) {
            dql = dql + " order by hi.estado_id";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Historial> listaHistorial = new ArrayList();
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                TipoEquipo tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("teq.tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("teq.codigo"));
                tipoEquipo.setNombre(resultado.getString("teq.nombre"));
                Dependencia dependencia = new Dependencia();
                dependencia.setDependenciaId(resultado.getInt("de.dependencia_id"));
                dependencia.setCodigo(resultado.getString("de.codigo"));
                dependencia.setNombre(resultado.getString("de.nombre"));
                Usuario usuario = new Usuario();
                usuario.setUsuarioId(resultado.getInt("us.usuario_id"));
                usuario.setCuenta(resultado.getString("us.cuenta"));
                usuario.setNombre(resultado.getString("us.nombre"));
                usuario.setApellido(resultado.getString("us.apellido"));
                usuario.setDepartamento(resultado.getString("us.departamento"));
                Equipo equipo = new Equipo();
                equipo.setEquipoId(resultado.getInt("eq.equipo_id"));
                equipo.setNumeroEtiquetaConsejeria(resultado.getString("eq.numero_etiqueta_consejeria"));
                equipo.setTipoEquipo(tipoEquipo);
                Estado estadoIncidencia = new Estado();
                estadoIncidencia.setEstadoId(resultado.getInt("ies.estado_id"));
                estadoIncidencia.setCodigo(resultado.getString("ies.codigo"));
                estadoIncidencia.setNombre(resultado.getString("ies.nombre"));
                Incidencia incidencia = new Incidencia();
                incidencia.setIncidenciaId(resultado.getInt("incidencia_id"));
                incidencia.setPosicionEquipoDependencia(resultado.getString("posicion_equipo_dependencia"));
                incidencia.setDescripcion(resultado.getString("descripcion"));
                incidencia.setComentarioAdministrador(resultado.getString("comentario_administrador"));
                incidencia.setFechaEstadoActual(resultado.getDate("fecha_estado_actual"));
                incidencia.setUsuario(usuario);
                incidencia.setEquipo(equipo);
                incidencia.setDependencia(dependencia);
                incidencia.setEstado(estadoIncidencia);
                Estado estadoHistorial = new Estado();
                estadoHistorial.setEstadoId(resultado.getInt("es.estado_id"));
                estadoHistorial.setCodigo(resultado.getString("es.codigo"));
                estadoHistorial.setNombre(resultado.getString("es.nombre"));
                Historial historial = new Historial();
                historial.setHistorialId(resultado.getInt("hi.historial_id"));
                historial.setFecha(resultado.getDate("hi.fecha"));
                historial.setIncidencia(incidencia);
                historial.setEstado(estadoHistorial);
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
    
    /**
     * Lee todas las configuraciones 
     * @author Marcos Gonzalez Fernandez
     * @return Lista con todas las configuraciones
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Configuracion> leerConfiguraciones() throws ExcepcionIncidenciasCAD {
        String dql = "select * from estado e, configuracion co where e.estado_id = co.estado_inicial_incidencia";                
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Configuracion> listaConfiguracion = new ArrayList();
        Configuracion configuracion = null;
        Estado estado = null;
        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery(dql);
            while (resultado.next()) {
                configuracion = new Configuracion();
                configuracion.setEmpresaConsejeriaNombre(resultado.getString("empresa_consejeria_nombre"));
                configuracion.setEmpresaConsejeriaTelefono(resultado.getString("empresa_consejeria_telefono"));
                configuracion.setEmpresaConsejeriaEmail(resultado.getString("empresa_consejeria_email"));
                configuracion.setIesNombre(resultado.getString("ies_nombre"));
                configuracion.setIesCif(resultado.getString("ies_cif"));
                configuracion.setIesCodigoCentro(resultado.getString("ies_codigo_centro"));
                configuracion.setIesPersonaContactoNombre(resultado.getString("ies_persona_contacto_nombre"));
                configuracion.setIesPersonaContactoApellido1(resultado.getString("ies_persona_contacto_apellido1"));
                configuracion.setIesPersonaContactoApellido2(resultado.getString("ies_persona_contacto_apellido2"));
                configuracion.setIesEmail(resultado.getString("ies_email"));
                configuracion.setLdapUrl(resultado.getString("ldap_url"));
                configuracion.setLdapDominio(resultado.getString("ldap_dominio"));
                configuracion.setLdapDn(resultado.getString("ldap_dn"));
                configuracion.setLdapAtributoCuenta(resultado.getString("ldap_atributo_cuenta"));
                configuracion.setLdapAtributoNombre(resultado.getString("ldap_atributo_nombre"));
                configuracion.setLdapAtributoApellido(resultado.getString("ldap_atributo_apellido"));
                configuracion.setLdapAtributoDepartamento(resultado.getString("ldap_atributo_departamento"));
                configuracion.setLdapAtributoPerfil(resultado.getString("ldap_atributo_perfil"));
                estado = new Estado();
                estado.setEstadoId(resultado.getInt("e.estado_id"));
                estado.setCodigo(resultado.getString("e.codigo"));
                estado.setNombre(resultado.getString("e.nombre"));
                listaConfiguracion.add(configuracion);
            }
            resultado.close();
            sentenciaPreparada.close();
            conexion.close();
            return listaConfiguracion;            
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
    
    public String formatearFecha(Date fecha)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fecha);
        return gc.get(Calendar.YEAR)+ "-"+(gc.get(Calendar.MONTH)+1)+"-"+gc.get(Calendar.DAY_OF_MONTH);
    }
}
