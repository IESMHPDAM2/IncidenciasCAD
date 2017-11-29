package incidenciascad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Clase principal del componenete de acceso a datos especializado en acceder
 * a la base de datos de incidencias
 * @author Ignacio Fontecha Hernández
 * @version 1.0
 */
public class IncidenciasCAD {
    private Connection conexion;
    private String cadenaConexion = "jdbc:mysql://10.0.1.96/incidencias?user=incidencias&password=incidencias";
    
    public static Integer ASCENDENTE = 1;
    public static Integer DESCENDENTE = 2;
    public static Integer DEPENDENCIA_CODIGO = 10;
    public static Integer DEPENDENCIA_NOMBRE = 11;
    public static Integer USUARIO_CUENTA = 20;
    public static Integer USUARIO_NOMBRE = 21;
    public static Integer USUARIO_APELLIDO = 22;
    public static Integer USUARIO_DEPARTAMENTO = 23;
    public static Integer TIPO_EQUIPO_CODIGO = 30;
    public static Integer TIPO_EQUIPO_NOMBRE = 31;
    public static Integer ESTADO_CODIGO = 40;
    public static Integer ESTADO_NOMBRE = 41;
    public static Integer EQUIPO_NUMERO_ETIQUETA_CONSEJERIA = 50;
    public static Integer INCIDENCIA_ID = 60;
    public static Integer INCIDENCIA_POSICION_EQUIPO_DEPENDENCIA = 61;
    public static Integer INCIDENCIA_DESCRIPCION = 62;
    public static Integer INCIDENCIA_COMENTARIO_ADMINISTRADOR = 63;
    public static Integer INCIDENCIA_FECHA_ESTADO_ACTUAL = 64;
    public static Integer INCIDENCIA_FECHA_REGISTRO = 65;
    public static Integer HISTORIAL_FECHA = 70;

    
    /**
     * Constructor vacío. Carga el jdbc en memoria
     * @author Ignacio Fontecha Hernández
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public IncidenciasCAD() throws ExcepcionIncidenciasCAD {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    -1,
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    null);
            throw e;
        }
    }
    
    /**
     * Abre la conexión con la base de datos 
     * @author Ignacio Fontecha Hernández
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
    */
    private void abrirConexion() throws ExcepcionIncidenciasCAD {
        try {
            conexion = DriverManager.getConnection(cadenaConexion);
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    null);
            throw e;
        }
    }
    
    /**
     * Cierra la conexión con la base de datos 
     * @author Ignacio Fontecha Hernández
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    private void cerrarConexion() throws ExcepcionIncidenciasCAD {
        try {
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    null);
            throw e;
        }
    }
    
    /**
     * Cierra tanto la conexión con la base de datos como la sentencia preparada
     * utilizada
     * @author Ignacio Fontecha Hernández
     * @param conexion Conexión con la base de datos
     * @param setenciaPreparada Setencia preparada utilizada para el acceso a la base de datos
     */
    private void cerrarConexionExcepcion(Connection conexion, PreparedStatement sentenciaPreparada) {
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
     * @return Cantidad de dependencias insertadas
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int insertarDependencia(Dependencia dependencia) throws ExcepcionIncidenciasCAD {
        if (dependencia == null) dependencia = new Dependencia();
        String dml = "insert into dependencia(codigo,nombre) values (?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, dependencia.getCodigo());
            sentenciaPreparada.setString(2, dependencia.getNombre());
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Elimina una dependencia de la base de datos
     * @author Ignacio Fontecha Hernández
     * @param dependenciaId Identificador de la dependencia a eliminar
     * @return Cantidad de dependencias eliminadas
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarDependencia(Integer dependenciaId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from dependencia where dependencia_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, dependenciaId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica una dependencia de la base de datos
     * @author Ignacio Fontecha Hernández
     * @param dependenciaId Identificador de la dependencia a modificar
     * @param dependencia Nuevos datos de la dependencia a modificar
     * @return Cantidad de dependencias modificadas
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarDependencia(Integer dependenciaId, Dependencia dependencia) throws ExcepcionIncidenciasCAD {
        if (dependencia == null) dependencia = new Dependencia();
        String dml = "update dependencia set codigo = ?, nombre = ? where dependencia_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, dependencia.getCodigo());
            sentenciaPreparada.setString(2, dependencia.getNombre());
            sentenciaPreparada.setObject(3, dependenciaId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Lee una dependencia de la base de datos
     * @author Marcos González Fernández
     * @param dependenciaId Identificador de la dependencia a leer
     * @return Dependencia a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Dependencia leerDependencia(Integer dependenciaId)throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from dependencia d "
                + "where dependencia_id = ?";                
        PreparedStatement sentenciaPreparada = null;       
        Dependencia dependencia = null;
        try {
            abrirConexion();
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
            cerrarConexion();
            return dependencia;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    sentenciaPreparada.toString());
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Lee una lista de dependencias de la base de datos a partir de una 
     * sentencia DQL
     * @author Óscar Barahona Ortega
     * @param dql Sentencia DQL que determina la lista de dependencias a leer
     * @return Lista de dependencias a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Dependencia> leerDependencias(String dql) throws ExcepcionIncidenciasCAD {
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Dependencia> listaDependencias = new ArrayList();
        Dependencia dependencia = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                dependencia = new Dependencia();
                dependencia.setDependenciaId(resultado.getInt("dependencia_id"));
                dependencia.setCodigo(resultado.getString("codigo"));
                dependencia.setNombre(resultado.getString("nombre"));
                
                listaDependencias.add(dependencia);
            }
            resultado.close();
            sentenciaPreparada.close();
            cerrarConexion();
            return listaDependencias;         
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Lee todas las dependencias de la base de datos
     * @author Óscar Barahona Ortega
     * @return Lista de todas las dependencias
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Dependencia> leerDependencias() throws ExcepcionIncidenciasCAD {
        String dql = "select * from dependencia";
        return leerDependencias(dql);
    }
    
    /**
     * Lee una lista de dependencias de la base de datos a partir de una serie 
     * de filtros y ordenando dicha lista por un criterio de ordenación - Se
     * incluirán en la lista únicamente las dependencias que cumplan las 
     * condiciones establecidas por todos y cada uno de los filtros
     * @author Óscar Barahona Ortega
     * @param codigo Texto contenido en el código de la dependencia. Se 
     * seleccionarán aquellas dependencias cuyo código contenga el texto 
     * contenido en este parámetro
     * @param nombre Texto contenido en el nombre de la dependencia. Se 
     * seleccionarán aquellas dependencias cuyo nombre contenga el texto 
     * contenido en este parámetro
     * @param criterioOrden Criterio de ordenación a aplicar en la lista. Los
     * valores posibles son: 
     * IncidenciasCAD.DEPENDENCIA_CODIGO para ordenar la lista por el código de 
     * dependencia
     * IncidenciasCAD.DEPENDENCIA_NOMBRE para ordenar la lista por el 
     * nombre de dependencia 
     * @param orden Indicador de si el orden a aplicar es ascendente o 
     * descendente. Si no se ha indicado un criterio de ordenación válido este 
     * parámetro es ignorado. Los valores posibles son:
     * IncidenciasCAD.ASCENDENTE para ordenar ascendentemente por el criterio de 
     * ordenación indicado
     * IncidenciasCAD.DESCENDENTE para ordenar descendentemente por el criterio 
     * de ordenación indicado
     * @return Lista ordenada de dependencias a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Dependencia> leerDependencias(String codigo, String nombre, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from dependencia "
                + "where 1=1";
        if (codigo != null) dql = dql + " and codigo like '%" + codigo + "%'";
        if (nombre != null) dql = dql + " and nombre like '%" + nombre + "%'";
        if (criterioOrden == DEPENDENCIA_CODIGO) {
            dql = dql + " order by codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == DEPENDENCIA_NOMBRE) {
            dql = dql + " order by codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        return leerDependencias(dql);
    }
    
    /**
     * Inserta un usuario en la base de datos
     * @author Óscar Barahona Ortega
     * @param usuario Datos del usuario a insertar
     * @return Cantidad de usuarios insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int insertarUsuario(Usuario usuario) throws ExcepcionIncidenciasCAD {
        if (usuario == null) usuario = new Usuario();
        String dml = "insert into usuario(cuenta,nombre,apellido,departamento) values (?,?,?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, usuario.getCuenta());
            sentenciaPreparada.setString(2, usuario.getNombre());
            sentenciaPreparada.setString(3, usuario.getApellido());
            sentenciaPreparada.setString(4, usuario.getDepartamento());
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex. getErrorCode()) {
                case 1048:  
                    e.setMensajeErrorUsuario("La cuenta es obligatoria");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("La cuenta ya existe y no se puede repetir");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Elimina un usuario de la base de datos
     * @author Óscar Barahona Ortega
     * @param usuarioId Identificador del usuario a eliminar
     * @return Cantidad de usuarios eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarUsuario(Integer usuarioId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from usuario where usuario_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, usuarioId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica un usuario de la base de datos
     * @author Óscar Barahona Ortega
     * @param usuarioId Identificador del usuario a modificar
     * @param usuario Nuevos datos del usuario a modificar
     * @return Cantidad de usuarios modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarUsuario(Integer usuarioId, Usuario usuario) throws ExcepcionIncidenciasCAD {
        if (usuario == null) usuario = new Usuario();
        String dml = "update usuario set cuenta = ?, nombre = ?, apellido = ?, departamento = ? where usuario_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, usuario.getCuenta());
            sentenciaPreparada.setString(2, usuario.getNombre());
            sentenciaPreparada.setString(3, usuario.getApellido());
            sentenciaPreparada.setString(4, usuario.getDepartamento());
            sentenciaPreparada.setObject(5, usuarioId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
            return registrosAfectados;
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex. getErrorCode()) {
                case 1048:  
                    e.setMensajeErrorUsuario("La cuenta es obligatoria");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("La cuenta ya existe y no se puede repetir");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
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
            abrirConexion();
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
            cerrarConexion();
            return usuario;
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    sentenciaPreparada.toString());
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Lee una lista de usuarios de la base de datos a partir de una 
     * sentencia DQL
     * @author Óscar Barahona Ortega
     * @param dql Sentencia DQL que determina la lista de usuarios a leer
     * @return Lista de usuarios a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Usuario> leerUsuarios(String dql) throws ExcepcionIncidenciasCAD {
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        Usuario usuario = null;
        try {
            abrirConexion();
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
            cerrarConexion();
            return listaUsuarios;          
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }

    /**
     * Lee todos los usuarios de la base de datos
     * @author Óscar Barahona Ortega
     * @return Lista de todos los usuarios
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Usuario> leerUsuarios() throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from usuario ";
        return leerUsuarios(dql);
    }    
    /**
     * Lee una lista de dependencias de la base de datos a partir de una serie 
     * de filtros y ordenando dicha lista por un criterio de ordenación - Se
     * incluirán en la lista únicamente las dependencias que cumplan las 
     * condiciones establecidas por todos y cada uno de los filtros
     * @author Óscar Barahona Ortega
     * @param cuenta Texto contenido en la cuenta del usuario. Se seleccionarán
     * aquellos usuarios cuya cuenta contenga el texto contenido en este
     * parámetro
     * @param nombre Texto contenido en el nombre del usuario. Se seleccionarán
     * aquellos usuarios cuyo nombre contenga el texto contenido en este
     * parámetro
     * @param apellido Texto contenido en el apellido del usuario. Se
     * seleccionarán aquellos usuarios cuyo apellido contenga el texto contenido
     * en este parámetro
     * @param departamento Texto contenido en el departamento del usuario. Se
     * seleccionarán aquellos usuarios cuyo departamento contenga el texto
     * contenido en este parámetro
     * @param criterioOrden Criterio de ordenación a aplicar en la lista. Los
     * valores posibles son: 
     * IncidenciasCAD.DEPENDENCIA_CODIGO para ordenar la lista por el código de 
     * dependencia
     * IncidenciasCAD.DEPENDENCIA_NOMBRE para ordenar la lista por el 
     * nombre de dependencia 
     * @param orden Indicador de si el orden a aplicar es ascendente o 
     * descendente. Si no se ha indicado un criterio de ordenación válido este 
     * parámetro es ignorado. Los valores posibles son:
     * IncidenciasCAD.ASCENDENTE para ordenar ascendentemente por el criterio de 
     * ordenación indicado
     * IncidenciasCAD.DESCENDENTE para ordenar descendentemente por el criterio 
     * de ordenación indicado
     * @return Lista ordenada de usuarios a leer
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
        if (criterioOrden == USUARIO_CUENTA) {
            dql = dql + " order by CUENTA";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == USUARIO_NOMBRE) {
            dql = dql + " order by nombre";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == USUARIO_APELLIDO) {
            dql = dql + " order by apellido";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == USUARIO_DEPARTAMENTO) {
            dql = dql + " order by departamento";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        return leerUsuarios(dql);
    }
    
    /**
     * Inserta un tipo de equipo en la base de datos
     * @author Ramon Tezanos San Emeterio
     * @param tipoEquipo Datos del tipo de equipo a insertar
     * @return Cantidad de tipos de equipo insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int insertarTipoEquipo(TipoEquipo tipoEquipo) throws ExcepcionIncidenciasCAD {
        if (tipoEquipo == null) tipoEquipo = new TipoEquipo();
        String dml = "insert into tipo_equipo(codigo,nombre) values (?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, tipoEquipo.getCodigo());
            sentenciaPreparada.setString(2, tipoEquipo.getNombre());
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Elimina un tipo de equipo de la base de datos
     * @author Ramon Tezanos San Emeterio
     * @param tipoEquipoID Identificador del tipo de equipo a eliminar
     * @return Cantidad de tipos de equipo eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarTipoEquipo(Integer tipoEquipoID) throws ExcepcionIncidenciasCAD {
        String dml = "delete from tipo_equipo where tipo_equipo_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, tipoEquipoID, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica un tipo de equipo de la base de datos
     * @author Ramon Tezanos San Emeterio
     * @param tipoEquipoId Identificador del tipo de equipo a modificar
     * @param tipoEquipo Nuevos datos del tipo de equipo a modificar
     * @return Cantidad de tipos de equipo modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarTipoEquipo(Integer tipoEquipoId, TipoEquipo tipoEquipo) throws ExcepcionIncidenciasCAD {
        if (tipoEquipo == null) tipoEquipo = new TipoEquipo();
        String dml = "update tipo_equipo set codigo = ?, nombre = ? where tipo_equipo_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, tipoEquipo.getCodigo());
            sentenciaPreparada.setString(2, tipoEquipo.getNombre());
            sentenciaPreparada.setObject(3, tipoEquipoId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
/**
     * Lee un tipo de equipo de la base de datos
     * @param tipoEquipoID Identificador del tipo de equipo a leer
     * @return Tipo de equipo a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public TipoEquipo leerTipoEquipo(Integer tipoEquipoID) throws ExcepcionIncidenciasCAD {
        String dql = "select * from tipo_equipo where tipo_equipo_id = ?";
        PreparedStatement sentenciaPreparada = null;
        
        TipoEquipo tipoEquipo = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dql);
            sentenciaPreparada.setInt(1, tipoEquipoID);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                
                tipoEquipo = new TipoEquipo();
                tipoEquipo.setTipoEquipoId(resultado.getInt("tipo_equipo_id"));
                tipoEquipo.setCodigo(resultado.getString("codigo"));
                tipoEquipo.setNombre(resultado.getString("nombre"));
                
                
            }
            resultado.close();
            sentenciaPreparada.close();
            cerrarConexion();
            return tipoEquipo;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    
    /**
     * Lee una lista de tipos de equipo de la base de datos a partir de una 
     * sentencia DQL
     * @author Ramon Tezanos San Emeterio
     * @param dql Sentencia DQL que determina la lista de tipos de equipo a leer
     * @return Lista de tipos de equipo a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<TipoEquipo> leerTiposEquipo(String dql) throws ExcepcionIncidenciasCAD {
        PreparedStatement sentenciaPreparada = null;
        ArrayList<TipoEquipo> listaTipoEquipos = new ArrayList(); 
        TipoEquipo tipoEquipo = null;
        try {
            abrirConexion();
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
            cerrarConexion();
            return listaTipoEquipos;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }

    /**
     * Lee todos los tipos de equipo de la base de datos
     * @author Ramon Tezanos San Emeterio
     * @return Lista de todos los tipos de equipo
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<TipoEquipo> leerTiposEquipo() throws ExcepcionIncidenciasCAD {
        String dql = "select * from tipo_equipo";
        return leerTiposEquipo(dql);
    }    
     /**
     * Lee los equipos de la base de datos que coincidan con los filtros pasados
     * @author Ramon Tezanos San Emeterio
     * @param codigo Recibe el codigo del tipo de equipo o null en caso de no querer filtrar por ese criterio
     * @param nombre Recibe el nombre del tipo de equipo o null en caso de no querer filtrar por ese criterio
     * @param criterioOrden Recibe el criterio por el cual se quiere ordenar la selección obtenida
     * @param orden Recibe la forma en la que se quiere ordenar, ascendentemenete o descendentemente
     * @return Devuelve la lista con todos los equipos de la base de datos una vez filtrado
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
     public ArrayList<TipoEquipo> leerTiposEquipo(String codigo, String nombre, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from tipo_equipo where 1 = 1";
        if(codigo != null || nombre != null)
        if (codigo != null) dql = dql + " and codigo like '%" + codigo + "%'";
        if (nombre != null) dql = dql + " and nombre like '%" + nombre + "%'";
        if (criterioOrden == TIPO_EQUIPO_NOMBRE) {
            dql = dql + " order by nombre";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == TIPO_EQUIPO_CODIGO) {
            dql = dql + " order by codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        return leerTiposEquipo(dql);
    }

    /**
     * Inserta un equipo en la base de datos
     * @author Óscar Barahona Ortega
     * @param equipo Datos del equipo a insertar
     * @return Cantidad de equipos insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int insertarEquipo(Equipo equipo) throws ExcepcionIncidenciasCAD {
        if (equipo == null) equipo = new Equipo();
        if (equipo.getTipoEquipo() == null) equipo.setTipoEquipo(new TipoEquipo());
        String dml = "insert into equipo(numero_etiqueta_consejeria,tipo_equipo_id) values (?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, equipo.getNumeroEtiquetaConsejeria());
            sentenciaPreparada.setObject(2, equipo.getTipoEquipo().getTipoEquipoId(), Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex. getErrorCode()) {
                case 1048:  
                    e.setMensajeErrorUsuario("El número de etiqueta de Consejería y el tipo de equipo son obligatorios");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El número de etiqueta de Consejería ya existe y no se puede repetir");
                    break;
                case 1452:
                    e.setMensajeErrorUsuario("El tipo de equipo seleccionado no existe");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Elimina un equipo de la base de datos
     * @author Óscar Barahona Ortega
     * @param equipoId Identificador del equipo a eliminar
     * @return Cantidad de equipos eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarEquipo(Integer equipoId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from equipo where equipo_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, equipoId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica un equipo de la base de datos
     * @author Óscar Barahona Ortega
     * @param equipoId Identificador del equipo a modificar
     * @param equipo Nuevos datos del equipo a modificar
     * @return Cantidad de equipos modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarEquipo(Integer equipoId, Equipo equipo) throws ExcepcionIncidenciasCAD {
        if (equipo == null) equipo = new Equipo();
        if (equipo.getTipoEquipo() == null) equipo.setTipoEquipo(new TipoEquipo());
        
        String dml = "update equipo set numero_etiqueta_consejeria = ?, tipo_equipo_id = ? where equipo_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, equipo.getNumeroEtiquetaConsejeria());
            sentenciaPreparada.setObject(2, equipo.getTipoEquipo().getTipoEquipoId(), Types.INTEGER);
            sentenciaPreparada.setObject(3, equipoId, Types.INTEGER);
            System.out.println(sentenciaPreparada.toString());
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex. getErrorCode()) {
                case 1048:  
                    e.setMensajeErrorUsuario("El número de etiqueta de Consejería y el tipo de equipo son obligatorios");
                    break;
                case 1452:
                    e.setMensajeErrorUsuario("El tipo de equipo seleccionado no existe");
                    break;
                case 1062:  
                    e.setMensajeErrorUsuario("El número de etiqueta de Consejería ya existe y no se puede repetir");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }


    /**
     * Lee un equipo de la base de datos
     * @author Ignacio Fontecha Hernández
     * @param equipoId Identificador del equipo a leer
     * @return Equipo a leer
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
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dql);
            sentenciaPreparada.setObject(1, equipoId, Types.INTEGER);
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
            cerrarConexion();
            return equipo;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    sentenciaPreparada.toString());
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }

    /**
     * Lee una lista de equipos de la base de datos a partir de una 
     * sentencia DQL
     * @author Ignacio Fontecha Hernández
     * @param dql Sentencia DQL que determina la lista de equipos a leer
     * @return Lista de equipos a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Equipo> leerEquipos(String dql) throws ExcepcionIncidenciasCAD {
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Equipo> listaEquipos = new ArrayList();
        Equipo equipo = null;
        TipoEquipo tipoEquipo = null;
        try {
            abrirConexion();
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
            cerrarConexion();
            return listaEquipos;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }

    /**
     * Lee todos los equipos de la base de datos
     * @author Ignacio Fontecha Hernández
     * @return Lista de todos los equipos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Equipo> leerEquipos() throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from tipo_equipo te, equipo e "
                + "where te.tipo_equipo_id = e.tipo_equipo_id";
        return leerEquipos(dql);
    }
    
    /**
     * Lee una lista de equipos de la base de datos a partir de una serie 
     * de filtros y ordenando dicha lista por un criterio de ordenación - Se
     * incluirán en la lista únicamente los equipos que cumplan las 
     * condiciones establecidas por todos y cada uno de los filtros
     * @author Ignacio Fontecha Hernández
     * @param numeroEtiquetaConsejeria Texto contenido en el número de etiqueta
     * de la Consejería del equipo. Se seleccionarán aquellos equipos cuyo 
     * número de etiqueta contenga el texto contenido en este parámetro
     * @param tipoEquipoId Identificador de tipo de equipo del equipo. Se 
     * seleccionarán aquellos equipos cuyo identificador de tipo de equipo sea 
     * el indicado en este parámetro
     * @param criterioOrden Criterio de ordenación a aplicar en la lista. Los
     * valores posibles son: 
     * IncidenciasCAD.EQUIPO_NUMERO_ETIQUETA_CONSEJERIA para ordenar la lista 
     * por el número de etiqueta de la Consejería del equipo
     * IncidenciasCAD.TIPO_EQUIPO_CODIGO para ordenar la lista por el 
     * código del tipo de equipo del equipo 
     * @param orden Indicador de si el orden a aplicar es ascendente o 
     * descendente. Si no se ha indicado un criterio de ordenación válido este 
     * parámetro es ignorado. Los valores posibles son:
     * IncidenciasCAD.ASCENDENTE para ordenar ascendentemente por el criterio de 
     * ordenación indicado
     * IncidenciasCAD.DESCENDENTE para ordenar descendentemente por el criterio 
     * de ordenación indicado
     * @return Lista ordenada de equipos a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Equipo> leerEquipos(String numeroEtiquetaConsejeria, Integer tipoEquipoId, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from tipo_equipo te, equipo e "
                + "where te.tipo_equipo_id = e.tipo_equipo_id";
        if (numeroEtiquetaConsejeria != null) dql = dql + " and numero_etiqueta_consejeria like '%" + numeroEtiquetaConsejeria + "%'";
        if (tipoEquipoId !=null) dql = dql + " and te.tipo_equipo_id = " + tipoEquipoId;
        if (criterioOrden == EQUIPO_NUMERO_ETIQUETA_CONSEJERIA) {
            dql = dql + " order by numero_etiqueta_consejeria";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == TIPO_EQUIPO_CODIGO) {
            dql = dql + " order by te.codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        return leerEquipos(dql);
    }
    
    /**
     * Inserta un estado en la base de datos
     * @author Marcos González Fernández
     * @param estado Datos del estado a insertar
     * @return Cantidad de estados insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int insertarEstado(Estado estado) throws ExcepcionIncidenciasCAD {
        if (estado == null) estado = new Estado();
        String dml = "insert into estado(codigo,nombre) values (?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, estado.getCodigo());
            sentenciaPreparada.setString(2, estado.getNombre());            
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Elimina un estado de la base de datos
     * @author Marcos González Fernández
     * @param estadoId Identificador del estado a eliminar
     * @return Cantidad de estados eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarEstado(Integer estadoId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from estado where estado_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, estadoId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex. getErrorCode()) {
                case 1451:  
                    e.setMensajeErrorUsuario("No se puede eliminar el estado ya que tiene incidencias y/o datos históricos asociados");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica un estado de la base de datos
     * @author Marcos González Fernández
     * @param estadoId Identificador del estado a modificar
     * @param estado Nuevos datos del estado a modificar
     * @return Cantidad de estados modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarEstado(Integer estadoId, Estado estado) throws ExcepcionIncidenciasCAD {
        if (estado == null) estado = new Estado();
        String dml = "update estado set codigo = ?, nombre = ? where estado_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, estado.getCodigo());
            sentenciaPreparada.setString(2, estado.getNombre());
            sentenciaPreparada.setObject(3, estadoId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
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
            abrirConexion();
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
            cerrarConexion();
            return estado;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    sentenciaPreparada.toString());
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }   
    
    /**
     * Lee una lista de estados de la base de datos a partir de una 
     * sentencia DQL
     * @author Marcos González Fernández
     * @param dql Sentencia DQL que determina la lista de estados a leer
     * @return Lista de estados a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Estado> leerEstados(String dql) throws ExcepcionIncidenciasCAD {
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Estado> listaEstados = new ArrayList();
        Estado estado = null;
        try {
            abrirConexion();
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
            cerrarConexion();
            return listaEstados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
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
        return leerEstados(dql);
    }
    
    /**
     * @author Marcos Gonzalez Fernandez
     * @param codigo Recibe el codigo de estado o null en caso de no querer filtrar por ese criterio
     * @param nombre Recibe el nombre de estado o null en caso de no querer filtrar por ese criterio
     * @param criterioOrden Recibe el criterio por el cual se quiere ordenar la selección obtenida
     * @param orden Recibe la forma en la que se quiere ordenar, ascendentemenete o descendentemente
     * @return Devuelve la lista con todos los estados de la base de datos una vez filtrado
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Estado> leerEstados(String codigo, String nombre, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select * "
                + "from Estado e where 1 = 1 ";   
        if (codigo !=null || nombre !=null)
        if (codigo !=null) dql = dql + " and codigo like '%" + codigo + "%'";
        if (nombre !=null) dql = dql + " and nombre like '%" + nombre + "%'";
        if (criterioOrden == ESTADO_NOMBRE){
            dql = dql + " order by e.nombre";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == ESTADO_CODIGO){
            dql = dql + " order by e.codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        return leerEstados(dql);
    }
    
    /**
     * Inserta una incidencia en la base de datos
     * @author Víctor Bolado Obregón
     * @param incidencia Datos de la incidencia a insertar
     * @return Cantidad de incidencias insertadas
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int insertarIncidencia(Incidencia incidencia) throws ExcepcionIncidenciasCAD {
        if (incidencia == null) incidencia = new Incidencia();
        if (incidencia.getDependencia() == null) incidencia.setDependencia(new Dependencia());
        if (incidencia.getEquipo() == null) incidencia.setEquipo(new Equipo());
        if (incidencia.getEstado() == null) incidencia.setEstado(new Estado());
        if (incidencia.getUsuario() == null) incidencia.setUsuario(new Usuario());
        String dml = "insert into incidencia(posicion_equipo_dependencia, descripcion, "
                + "comentario_administrador, fecha_registro, fecha_estado_actual, usuario_id, equipo_id, dependencia_id, estado_id) "
                + "values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, incidencia.getPosicionEquipoDependencia());
            sentenciaPreparada.setString(2, incidencia.getDescripcion());
            sentenciaPreparada.setString(3, incidencia.getComentarioAdministrador());
            //Se crea una java.sql.date para formatear la fecha de la incidencia.
            if (incidencia.getFechaRegistro() == null) {
                sentenciaPreparada.setNull(4, Types.DATE);
            } else {
                java.sql.Date fechaRegistro = new java.sql.Date(incidencia.getFechaRegistro().getTime());
                sentenciaPreparada.setObject(4, fechaRegistro, Types.DATE);
            }
            //Se crea una java.sql.date para formatear la fecha de la incidencia.
            if (incidencia.getFechaEstadoActual() == null) {
                sentenciaPreparada.setNull(5, Types.DATE);
            } else {
                java.sql.Date fechaEstadoActual = new java.sql.Date(incidencia.getFechaEstadoActual().getTime());
                sentenciaPreparada.setObject(5, fechaEstadoActual, Types.DATE);
            }
            sentenciaPreparada.setObject(6, incidencia.getUsuario().getUsuarioId(), Types.INTEGER);
            sentenciaPreparada.setObject(7, incidencia.getEquipo().getEquipoId(), Types.INTEGER);
            sentenciaPreparada.setObject(8, incidencia.getDependencia().getDependenciaId(), Types.INTEGER);
            sentenciaPreparada.setObject(9, incidencia.getEstado().getEstadoId(), Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Elimina una incidencia en la base de datos
     * @author Víctor Bolado Obregón
     * @param incidenciaId Identificador de la incidencia a eliminar
     * @return Cantidad de incidencias eliminadas
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarIncidencia(Integer incidenciaId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from incidencia where incidencia_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, incidenciaId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica una incidencia de la base de datos
     * @author IVíctor Bolado Obregón
     * @param incidenciaId Identificador de la incidencia a modificar
     * @param incidencia Nuevos datos de la incidencia a modificar
     * @return Cantidad de incidencias modificadas
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarIncidencia(Integer incidenciaId, Incidencia incidencia) throws ExcepcionIncidenciasCAD {
        if (incidencia == null) incidencia = new Incidencia();
        if (incidencia.getDependencia() == null) incidencia.setDependencia(new Dependencia());
        if (incidencia.getEquipo() == null) incidencia.setEquipo(new Equipo());
        if (incidencia.getEstado() == null) incidencia.setEstado(new Estado());
        if (incidencia.getUsuario() == null) incidencia.setUsuario(new Usuario());
        String dml = "update incidencia set posicion_equipo_dependencia=?, "
                + "descripcion=?, "
                + "comentario_administrador=?, "
                + "fecha_registro=?, "
                + "fecha_estado_actual=?, "
                + "usuario_id=?, "
                + "equipo_id=?, "
                + "dependencia_id=?, "
                + "estado_id=? "
                + "where incidencia_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, incidencia.getPosicionEquipoDependencia());
            sentenciaPreparada.setString(2, incidencia.getDescripcion());
            sentenciaPreparada.setString(3, incidencia.getComentarioAdministrador());
            //Se crea una java.sql.date para formatear la fecha de la incidencia.
            if (incidencia.getFechaRegistro() == null) {
                sentenciaPreparada.setNull(4, Types.DATE);
            } else {
                java.sql.Date fechaRegistro = new java.sql.Date(incidencia.getFechaRegistro().getTime());
                sentenciaPreparada.setObject(4, fechaRegistro, Types.DATE);
            }
            //Se crea una java.sql.date para formatear la fecha de la incidencia.
            if (incidencia.getFechaEstadoActual() == null) {
                sentenciaPreparada.setNull(5, Types.DATE);
            } else {
                java.sql.Date fecha = new java.sql.Date(incidencia.getFechaEstadoActual().getTime());
                sentenciaPreparada.setObject(5, fecha, Types.DATE);
            }
            sentenciaPreparada.setObject(6, incidencia.getUsuario().getUsuarioId(), Types.INTEGER);
            sentenciaPreparada.setObject(7, incidencia.getEquipo().getEquipoId(), Types.INTEGER);
            sentenciaPreparada.setObject(8, incidencia.getDependencia().getDependenciaId(), Types.INTEGER);
            sentenciaPreparada.setObject(9, incidencia.getEstado().getEstadoId(), Types.INTEGER);
            sentenciaPreparada.setObject(10, incidenciaId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Lee una incidencia de la base de datos. NOTA: en los datos de cada 
     * incidencia no se incluirán los cambios de estado de la misma. Para 
     * obtener dicha información consultar el método public Incidencia 
     * leerIncidencia(Integer incidenciaId)
     * @author Víctor Bolado Obregón
     * @param incidenciaId Identificardor de la incidencia a leer
     * @return Incidencia a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public Incidencia leerIncidencia(Integer incidenciaId) throws ExcepcionIncidenciasCAD {
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
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                //INCIDENCIA
                incidencia = new Incidencia();
                incidencia.setIncidenciaId(resultado.getInt("i.incidencia_id"));
                incidencia.setPosicionEquipoDependencia(resultado.getString("i.posicion_equipo_dependencia"));
                incidencia.setDescripcion(resultado.getString("i.descripcion"));
                incidencia.setComentarioAdministrador(resultado.getString("i.comentario_administrador"));
                incidencia.setFechaRegistro(resultado.getDate("i.fecha_registro"));
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
                //HISTORIAL
                incidencia.setHistoriales(leerHistoriales(incidenciaId,null,null,IncidenciasCAD.HISTORIAL_FECHA,IncidenciasCAD.ASCENDENTE));
            }
            resultado.close();
            sentenciaPreparada.close();
            cerrarConexion();
            return incidencia;         
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Lee una lista de incidencias de la base de datos a partir de una 
     * sentencia DQL. NOTA: en los datos de cada incidencia no se incluirán los
     * cambios de estado de la misma. Para obtener dicha información consultar 
     * el método public Incidencia leerIncidencia(Integer incidenciaId)
     * @author Víctor Bolado Obregón
     * @param dql Sentencia DQL que determina la lista de incidencias a leer
     * @return Lista de incidencias a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
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
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {
                //INCIDENCIA
                incidencia = new Incidencia();
                incidencia.setIncidenciaId(resultado.getInt("i.incidencia_id"));
                incidencia.setPosicionEquipoDependencia(resultado.getString("i.posicion_equipo_dependencia"));
                incidencia.setDescripcion(resultado.getString("i.descripcion"));
                incidencia.setComentarioAdministrador(resultado.getString("i.comentario_administrador"));
                incidencia.setFechaRegistro(resultado.getDate("i.fecha_registro"));
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
            cerrarConexion();
            return listaIncidencias;         
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }

    /**
     * Lee todas las incidencias de la base de datos. NOTA: en
     * los datos de cada incidencia no se incluirán los cambios de estado de
     * la misma. Para obtener dicha información consultar el método public 
     * Incidencia leerIncidencia(Integer incidenciaId)
     * @author Víctor Bolado Obregón
     * @return Lista de todas las incidencias
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
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
     * Lee una lista de incidencias de la base de datos a partir de una serie 
     * de filtros y ordenando dicha lista por un criterio de ordenación - Se
     * incluirán en la lista únicamente las incidencias que cumplan las 
     * condiciones establecidas por todos y cada uno de los filtros. NOTA: en
     * los datos de cada incidencia no se incluirán los cambios de estado de
     * la misma. Para obtener dicha información consultar el método public 
     * Incidencia leerIncidencia(Integer incidenciaId)
     * @author Víctor Bolado Obregón
     * @param incidenciaId Identificador de incidencia. Se seleccionará aquella
     * incidencia cuyo identificador sea el indicado en este parámetro
     * @param posicionEquipoDependencia Texto contenido en la posición del 
     * equipo afectado por la incidencia en la dependencia. Se seleccionarán 
     * aquellas incidencias cuya posición del equipo en la dependencia contenga
     * el texto contenido en este parámetro
     * @param descripcion Texto contenido en la descripción de la incidencia. Se
     * seleccionarán aquellas incidencias cuya descripción contenga el texto 
     * contenido en este parámetro
     * @param comentarioAdministrador Texto contenido en el comentario del
     * administrador de la incidencia. Se seleccionarán aquellas incidencias
     * cuyo comentario del administrador contenga el texto contenido en este 
     * parámetro
     * @param fechaRegistro Fecha en la que se generó la incidencia en el
     * sistema. Se seleccionarán aquellas incidencias cuya fecha de registro en
     * el sistema sea la indicada en este parámetro
     * @param fechaEstadoActual Fecha en la que la incidencia cambió de estado
     * por última vez. Se seleccionarán aquellas incidencias cuya última fecha
     * en la que la incidencia cambió de estado sea la indicada en este
     * parámetro
     * @param usuarioId Identificador de usuario qure registró la incidencia. Se
     * seleccionarán aquellas incidencias que hayan sido registradas en el 
     * sistema por el usuario cuyo identificador sea el indicado en este
     * parámetro
     * @param tipoEquipoId  Identificador de tipo de equipo del equipo afectado
     * por la incidencia. Se seleccionarán aquellas incidencias registradas 
     * sobre equipos cuyo identificador de tipo de equipo sea el indicado en
     * este parámetro
     * @param equipoNumeroEtiquetaConsejeria Texto contenido en el número de 
     * etiqueta de la Consejería del equipo afectado por la incidencia. Se
     * seleccionarán aquellas incidencias cuya descripción contenga el texto 
     * contenido en este parámetro
     * @param dependenciaId Identificador de dependencia en la que se encuentra
     * el equipo afectado por la incidencia. Se seleccionarán aquellas
     * incidencias registradas sobre equipos ubicados en la dependencia cuyo
     * identificador sea el indicado en este parámetro
     * @param estadoId Identificador de estado de la incidencia. Se 
     * seleccionarán aquellas incidencias cuyo estado actual tenga como
     * identificador  el indicado en este parámetro
     * @param criterioOrden Criterio de ordenación a aplicar en la lista. Los
     * valores posibles son: 
     * IncidenciasCAD.INCIDENCIA_POSICION_EQUIPO_DEPENDENCIA para ordenar
     * la lista por la posición del equipo afectado por la incidencia en la
     * dependencia
     * IncidenciasCAD.INCIDENCIA_DESCRIPCION para ordenar la lista por la 
     * descripción de la incidencia 
     * IncidenciasCAD.INCIDENCIA_COMENTARIO_ADMINISTRADOR para ordenar la
     * lista por el comentario del administrador a la incidencia
     * IncidenciasCAD.INCIDENCIA_FECHA_REGISTRO para ordenar la lista por
     * la fecha de registro de la incidencia en el sistema
     * IncidenciasCAD.INCIDENCIA_FECHA_ESTADO_ACTUAL para ordenar la lista por
     * la fecha del último cambio de estado de la incidencia
     * IncidenciasCAD.USUARIO_CUENTA para ordenar la lista por la cuenta del
     * usuario que registró la incidencia en el sistema
     * IncidenciasCAD.TIPO_EQUIPO_CODIGO para ordenar la lista por el 
     * código del tipo de equipo del equipo afectado por la incidencia
     * IncidenciasCAD.EQUIPO_NUMERO_ETIQUETA_CONSEJERIA para ordenar la lista 
     * por el número de etiqueta de la Consejería del equipo afectado por la
     * incidencia
     * IncidenciasCAD.DEPENDENCIA_CODIGO para ordenar la lista por el 
     * código de la dependencia en la que se encuentra el equipo afectado por la
     * incidencia 
     * IncidenciasCAD.ESTADO_CODIGO para ordenar la lista por el 
     * código del estado en el que se encuentra la incidencia 
     * @param orden Indicador de si el orden a aplicar es ascendente o 
     * descendente. Si no se ha indicado un criterio de ordenación válido este 
     * parámetro es ignorado. Los valores posibles son:
     * IncidenciasCAD.ASCENDENTE para ordenar ascendentemente por el criterio de 
     * ordenación indicado
     * IncidenciasCAD.DESCENDENTE para ordenar descendentemente por el criterio 
     * de ordenación indicado
     * @return Lista ordenada de incidencias a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Incidencia> leerIncidencias(Integer incidenciaId, String posicionEquipoDependencia, String descripcion, String comentarioAdministrador, Date fechaRegistro, Date fechaEstadoActual, Integer usuarioId, Integer tipoEquipoId, String equipoNumeroEtiquetaConsejeria, Integer dependenciaId, Integer estadoId, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
        String dql = "select i.*,u.*,te.*,e.*,d.*,es.* from incidencia i, usuario u, dependencia d, equipo e, tipo_equipo te, estado es " +
            "where i.USUARIO_ID=u.USUARIO_ID " +
            "and i.DEPENDENCIA_ID=d.DEPENDENCIA_ID " +
            "and e.TIPO_EQUIPO_ID=te.TIPO_EQUIPO_ID " +
            "and i.EQUIPO_ID=e.EQUIPO_ID " +
            "and i.ESTADO_ID=es.ESTADO_ID";
        
        if (incidenciaId != null) dql = dql + " and incidencia_id =" + incidenciaId;
        if (posicionEquipoDependencia != null) dql = dql + " and posicion_equipo_dependencia like '%" + posicionEquipoDependencia + "%'";
        if (descripcion != null) dql = dql + " and descripcion like '%" + descripcion + "%'";
        if (comentarioAdministrador != null) dql = dql + " and comentario_administrador like '%" + comentarioAdministrador + "%'";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (fechaRegistro != null) {
            dql = dql + " and date_format(i.FECHA_REGISTRO, \"%Y-%m-%d\") like '%" + sdf.format(fechaRegistro) + "%'";
        }
        if (fechaEstadoActual != null) {
            dql = dql + " and date_format(i.FECHA_ESTADO_ACTUAL, \"%Y-%m-%d\") like '%" + sdf.format(fechaEstadoActual) + "%'";
        }
        if (usuarioId != null) dql = dql + " and u.usuario_id =" + usuarioId;
        if (tipoEquipoId != null) dql = dql + " and te.tipo_equipo_id =" + tipoEquipoId;
        if (equipoNumeroEtiquetaConsejeria != null) dql = dql + " and e.numero_etiqueta_consejeria like '%" + equipoNumeroEtiquetaConsejeria + "%'";
        if (dependenciaId !=null) dql = dql + " and d.dependencia_id =" + dependenciaId;
        if (estadoId != null) dql = dql + " and es.estado_id =" + estadoId;
        
        if (criterioOrden == INCIDENCIA_POSICION_EQUIPO_DEPENDENCIA) 
        {
            dql = dql + " order by posicion_equipo_dependencia";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        
        if (criterioOrden == INCIDENCIA_DESCRIPCION) 
        {
            dql = dql + " order by descripcion";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        
        if (criterioOrden == INCIDENCIA_COMENTARIO_ADMINISTRADOR) 
        {
            dql = dql + " order by comentario_administrador";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        
        if (criterioOrden == INCIDENCIA_FECHA_REGISTRO) 
        {
            dql = dql + " order by fecha_registro";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        
        if (criterioOrden == INCIDENCIA_FECHA_ESTADO_ACTUAL) 
        {
            dql = dql + " order by fecha_estado_actual";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        
        if (criterioOrden == USUARIO_CUENTA) 
        {
            dql = dql + " order by u.cuenta";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        
        if (criterioOrden == TIPO_EQUIPO_CODIGO) 
        {
            dql = dql + " order by te.codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        
        if (criterioOrden == EQUIPO_NUMERO_ETIQUETA_CONSEJERIA) 
        {
            dql = dql + " order by e.numero_etiqueta_consejeria";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        
        if (criterioOrden == DEPENDENCIA_CODIGO) 
        {
            dql = dql + " order by d.codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        
        if (criterioOrden == ESTADO_CODIGO) 
        {
            dql = dql + " order by es.codigo";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        return leerIncidencias(dql);
    }


    /**
     * Inserta un dato histórico en la base de datos
     * @author Diego Fernández Díaz
     * @param historial Datos del dato historico a insertar
     * @return Cantidad de datos históricos insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int insertarHistorial(Historial historial) throws ExcepcionIncidenciasCAD {
        if (historial == null) historial = new Historial();
        if (historial.getEstado() == null) historial.setEstado(new Estado());
        if (historial.getIncidencia() == null) historial.setIncidencia(new Incidencia());
        String dml = "insert into historial"
                + "(fecha,incidencia_id,estado_id) values (?,?,?)";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            if (historial.getFecha() == null) {
                sentenciaPreparada.setNull(1, Types.DATE);
            } else {
                java.sql.Date fecha = new java.sql.Date(historial.getFecha().getTime());
                sentenciaPreparada.setObject(1, fecha, Types.DATE);
            }
            sentenciaPreparada.setObject(2, historial.getIncidencia().getIncidenciaId(), Types.INTEGER);
            sentenciaPreparada.setObject(3, historial.getEstado().getEstadoId(), Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex.getErrorCode()) {
                case 1048:  
                    e.setMensajeErrorUsuario("La fecha, la incidencia y el estado de historial son obligatorios");
                    break;
                case 1452:
                    e.setMensajeErrorUsuario("La incidendia o el estado indicado no existe");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Elimina un dato histórico de la base de datos
     * @author Diego Fernández Díaz
     * @param historialId Identificador del dato histórico a eliminar
     * @return Cantidad de datos históricos eliminados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int eliminarHistorial(Integer historialId) throws ExcepcionIncidenciasCAD {
        String dml = "delete from historial where historial_id=?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1, historialId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    sentenciaPreparada.toString());
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Modifica un dato histórico de la base de datos
     * @author Diego Fernandez Diaz
     * @param historialId Identificador del dato histórico a modificar
     * @param historial Nuevos datos del dato histórico a modificar
     * @return Cantidad de datos históricos modificados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int modificarHistorial(Integer historialId, Historial historial) throws ExcepcionIncidenciasCAD {
        if (historial == null) historial = new Historial();
        if (historial.getEstado() == null) historial.setEstado(new Estado());
        if (historial.getIncidencia() == null) historial.setIncidencia(new Incidencia());
        String dml = "update historial set fecha = ?, incidencia_id = ?, estado_id = ? where historial_id = ?";
        PreparedStatement sentenciaPreparada = null;
        try {
            abrirConexion();
            sentenciaPreparada = conexion.prepareStatement(dml);
            if (historial.getFecha() == null) {
                sentenciaPreparada.setNull(4, Types.DATE);
            } else {
                java.sql.Date fecha = new java.sql.Date(historial.getFecha().getTime());
                sentenciaPreparada.setObject(1, fecha, Types.DATE);
            }
            sentenciaPreparada.setObject(2, historial.getIncidencia().getIncidenciaId(), Types.INTEGER);
            sentenciaPreparada.setObject(3, historial.getEstado().getEstadoId(), Types.INTEGER);
            sentenciaPreparada.setObject(4, historialId, Types.INTEGER);
            int registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            cerrarConexion();
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }
    
    /**
     * Lee un dato histórico de la base de datos
     * @author Diego Fernandez Diaz
     * @param historialId Identificador del dato histórico a leer
     * @return Dato histórico a leer
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
            abrirConexion();
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
                incidencia.setFechaRegistro(resultado.getDate("inc.fecha_registro"));
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
            cerrarConexion();
            return historial;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }

    /**
     * Lee una lista de datos históricos de la base de datos a partir de una 
     * sentencia DQL
     * @author Diego Fernandez Diaz
     * @param dql Sentencia DQL que determina la lista de datos históricos a leer
     * @return Lista de datos históricos a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Historial> leerHistoriales(String dql) throws ExcepcionIncidenciasCAD {
        PreparedStatement sentenciaPreparada = null;
        ArrayList<Historial> listaHistorial = new ArrayList();
        try {
            abrirConexion();
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
                incidencia.setFechaRegistro(resultado.getDate("inc.fecha_registro"));
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
            cerrarConexion();
            return listaHistorial;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    "Error general del sistema. Consulte con el administrador",
                    dql);
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }

    /**
     * Lee todos los datos históricos de la base de datos
     * @author Diego Fernandez Diaz
     * @return Lista de todos los datos históricos
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Historial> leerHistoriales() throws ExcepcionIncidenciasCAD {
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
        return leerHistoriales(dql);
    }
    
    /**
     * Lee una lista de datos históricos de la base de datos a partir de una
     * serie de filtros y ordenando dicha lista por un criterio de ordenación -
     * Se incluirán en la lista únicamente las incidencias que cumplan las 
     * condiciones establecidas por todos y cada uno de los filtros.
     * @author Diego Fernández Díaz
     * @param incidenciaId Identificador de incidencia. Se seleccionarán 
     * aquellos datos históricos que correspondan con la incidencia cuyo
     * identificador sea el indicado en este parámetro
     * @param estadoId Identificador de estado de la incidencia. Se 
     * seleccionarán aquellos datos históricos cuyo identificador de estado 
     * sea el indicado en este parámetro
     * @param fecha Fecha en la que se produzco el cambio de estado de la 
     * incidencia. Se seleccionarán aquellos datos históricos cuya fecha de
     * cambuio de estado sea la indicada en este parámetro
     * @param criterioOrden Criterio de ordenación a aplicar en la lista. Los
     * valores posibles son: 
     * IncidenciasCAD.INCIDENCIA_ID para ordenar la lista por el número de
     * incidencia a la que se refiere el dato histórico
     * IncidenciasCAD.ESTADO_CODIGO para ordenar la lista por el código de
     * estado al que se refiere el dato histórico 
     * IncidenciasCAD.HISTORIAL_FECHA para ordenar la lista por fecha del 
     * cambio de estado al que se refiere el dato histórico 
     * @param orden Indicador de si el orden a aplicar es ascendente o 
     * descendente. Si no se ha indicado un criterio de ordenación válido este 
     * parámetro es ignorado. Los valores posibles son:
     * IncidenciasCAD.ASCENDENTE para ordenar ascendentemente por el criterio de 
     * ordenación indicado
     * IncidenciasCAD.DESCENDENTE para ordenar descendentemente por el criterio 
     * de ordenación indicado
     * @return Lista ordenada de datos históricos a leer
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Historial> leerHistoriales(Integer incidenciaId, Integer estadoId, Date fecha, Integer criterioOrden, Integer orden) throws ExcepcionIncidenciasCAD {
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
        if (incidenciaId !=null) dql = dql + " and hi.incidencia_id = " + incidenciaId;
        if (estadoId !=null) dql = dql + " and hi.estado_id = " + estadoId;
        if (fecha != null) dql = dql + " and date_format(hi.fecha,'%Y-%m-%d') = '"+ formatearFecha(fecha)+"'";
        if (criterioOrden == INCIDENCIA_ID) {
            dql = dql + " order by hi.incidencia_id";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == ESTADO_CODIGO) {
            dql = dql + " order by hi.estado_id";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        if (criterioOrden == HISTORIAL_FECHA) {
            dql = dql + " order by hi.fecha";
            if (orden == ASCENDENTE) dql = dql + " asc";
            if (orden == DESCENDENTE) dql = dql + " desc";
        }
        return leerHistoriales(dql);
    }
    
    /**
     * Establece una configuración para el sistema y la almacena de la base de
     * datos
     * @author Óscar Barahona Ortega
     * @param configuracion Datos de la configuración a establecer
     * @return Cantidad de registros de configuración insertados
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public int establecerConfiguracion(Configuracion configuracion) throws ExcepcionIncidenciasCAD {
        String dql = "select count(*) total from configuracion";
        PreparedStatement sentenciaPreparada = null;
        int numeroConfiguraciones = 0;
        int registrosAfectados = 0;

        try {
            sentenciaPreparada = conexion.prepareStatement(dql);
            ResultSet resultado = sentenciaPreparada.executeQuery();
            while (resultado.next()) {                
                numeroConfiguraciones = resultado.getInt("total");
            }
            resultado.close();         
            if (numeroConfiguraciones < 1) {
                String dml = "insert into configuracion(empresa_consejeria_nombre,empresa_consejeria_telefono,empresa_consejeria_email,ies_nombre,ies_cif,ies_codigo_centro,ies_persona_contacto_nombre,"
                        + "ies_persona_contacto_apellido1,ies_persona_contacto_apellido2,ies_email,estado_inicial_incidencia,estado_final_incidencia,ldap_url,ldap_dominio,ldap_dn,ldap_atributo_cuenta,"
                        + "ldap_atributo_nombre,ldap_atributo_apellido,ldap_atributo_departamento,ldap_atributo_perfil) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                sentenciaPreparada = conexion.prepareStatement(dml);
                sentenciaPreparada.setString(1, configuracion.getEmpresaConsejeriaNombre());
                sentenciaPreparada.setString(2, configuracion.getEmpresaConsejeriaTelefono());
                sentenciaPreparada.setString(3, configuracion.getEmpresaConsejeriaEmail());
                sentenciaPreparada.setString(4, configuracion.getIesNombre());
                sentenciaPreparada.setString(5, configuracion.getIesCif());
                sentenciaPreparada.setString(6, configuracion.getIesCodigoCentro());
                sentenciaPreparada.setString(7, configuracion.getIesPersonaContactoNombre());
                sentenciaPreparada.setString(8, configuracion.getIesPersonaContactoApellido1());
                sentenciaPreparada.setString(9, configuracion.getIesPersonaContactoApellido2());
                sentenciaPreparada.setString(10, configuracion.getIesEmail());
                sentenciaPreparada.setInt(11, configuracion.getEstadoInicial().getEstadoId());
                sentenciaPreparada.setInt(12, configuracion.getEstadoFinal().getEstadoId());
                sentenciaPreparada.setString(13, configuracion.getLdapUrl());
                sentenciaPreparada.setString(14, configuracion.getLdapDominio());
                sentenciaPreparada.setString(15, configuracion.getLdapDn());
                sentenciaPreparada.setString(16, configuracion.getLdapAtributoCuenta());
                sentenciaPreparada.setString(17, configuracion.getLdapAtributoNombre());
                sentenciaPreparada.setString(18, configuracion.getLdapAtributoApellido());
                sentenciaPreparada.setString(19, configuracion.getLdapAtributoDepartamento());
                sentenciaPreparada.setString(20, configuracion.getLdapAtributoPerfil());
                registrosAfectados = sentenciaPreparada.executeUpdate();
                sentenciaPreparada.close();
                conexion.close();
            } else if (numeroConfiguraciones == 1) {
                String dml = "update configuracion set empresa_consejeria_nombre = ?, empresa_consejeria_telefono = ?, empresa_consejeria_email = ?, ies_nombre = ?, ies_cif = ?, ies_codigo_centro = ?, "
                        + "ies_persona_contacto_nombre = ?, ies_persona_contacto_apellido1 = ?, ies_persona_contacto_apellido2 = ?, ies_email = ?, estado_inicial_incidencia = ?, estado_final_incidencia = ?,"
                        + "ldap_url = ?, ldap_dominio = ?, ldap_dn = ?, ldap_atributo_cuenta = ?, ldap_atributo_nombre = ?, ldap_atributo_apellido = ?, ldap_atributo_departamento = ?, ldap_atributo_perfil = ?";
                sentenciaPreparada = conexion.prepareStatement(dml);
                sentenciaPreparada.setString(1, configuracion.getEmpresaConsejeriaNombre());
                sentenciaPreparada.setString(2, configuracion.getEmpresaConsejeriaTelefono());
                sentenciaPreparada.setString(3, configuracion.getEmpresaConsejeriaEmail());
                sentenciaPreparada.setString(4, configuracion.getIesNombre());
                sentenciaPreparada.setString(5, configuracion.getIesCif());
                sentenciaPreparada.setString(6, configuracion.getIesCodigoCentro());
                sentenciaPreparada.setString(7, configuracion.getIesPersonaContactoNombre());
                sentenciaPreparada.setString(8, configuracion.getIesPersonaContactoApellido1());
                sentenciaPreparada.setString(9, configuracion.getIesPersonaContactoApellido2());
                sentenciaPreparada.setString(10, configuracion.getIesEmail());
                sentenciaPreparada.setInt(11, configuracion.getEstadoInicial().getEstadoId());
                sentenciaPreparada.setInt(12, configuracion.getEstadoFinal().getEstadoId());
                sentenciaPreparada.setString(13, configuracion.getLdapUrl());
                sentenciaPreparada.setString(14, configuracion.getLdapDominio());
                sentenciaPreparada.setString(15, configuracion.getLdapDn());
                sentenciaPreparada.setString(16, configuracion.getLdapAtributoCuenta());
                sentenciaPreparada.setString(17, configuracion.getLdapAtributoNombre());
                sentenciaPreparada.setString(18, configuracion.getLdapAtributoApellido());
                sentenciaPreparada.setString(19, configuracion.getLdapAtributoDepartamento());
                sentenciaPreparada.setString(20, configuracion.getLdapAtributoPerfil());
                registrosAfectados = sentenciaPreparada.executeUpdate();
                sentenciaPreparada.close();
                conexion.close();
            } else {
                String dml = "delete from configuracion where 1=1";
                sentenciaPreparada = conexion.prepareStatement(dml);
                registrosAfectados = sentenciaPreparada.executeUpdate();
                dml = "insert into configuracion(empresa_consejeria_nombre,empresa_consejeria_telefono,empresa_consejeria_email,ies_nombre,ies_cif,ies_codigo_centro,ies_persona_contacto_nombre,"
                        + "ies_persona_contacto_apellido1,ies_persona_contacto_apellido2,ies_email,estado_inicial_incidencia,estado_final_incidencia,ldap_url,ldap_dominio,ldap_dn,ldap_atributo_cuenta,"
                        + "ldap_atributo_nombre,ldap_atributo_apellido,ldap_atributo_departamento,ldap_atributo_perfil) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                sentenciaPreparada = conexion.prepareStatement(dml);
                sentenciaPreparada.setString(1, configuracion.getEmpresaConsejeriaNombre());
                sentenciaPreparada.setString(2, configuracion.getEmpresaConsejeriaTelefono());
                sentenciaPreparada.setString(3, configuracion.getEmpresaConsejeriaEmail());
                sentenciaPreparada.setString(4, configuracion.getIesNombre());
                sentenciaPreparada.setString(5, configuracion.getIesCif());
                sentenciaPreparada.setString(6, configuracion.getIesCodigoCentro());
                sentenciaPreparada.setString(7, configuracion.getIesPersonaContactoNombre());
                sentenciaPreparada.setString(8, configuracion.getIesPersonaContactoApellido1());
                sentenciaPreparada.setString(9, configuracion.getIesPersonaContactoApellido2());
                sentenciaPreparada.setString(10, configuracion.getIesEmail());
                sentenciaPreparada.setInt(11, configuracion.getEstadoInicial().getEstadoId());
                sentenciaPreparada.setInt(12, configuracion.getEstadoFinal().getEstadoId());
                sentenciaPreparada.setString(13, configuracion.getLdapUrl());
                sentenciaPreparada.setString(14, configuracion.getLdapDominio());
                sentenciaPreparada.setString(15, configuracion.getLdapDn());
                sentenciaPreparada.setString(16, configuracion.getLdapAtributoCuenta());
                sentenciaPreparada.setString(17, configuracion.getLdapAtributoNombre());
                sentenciaPreparada.setString(18, configuracion.getLdapAtributoApellido());
                sentenciaPreparada.setString(19, configuracion.getLdapAtributoDepartamento());
                sentenciaPreparada.setString(20, configuracion.getLdapAtributoPerfil());
                registrosAfectados = sentenciaPreparada.executeUpdate();
            }    
            return registrosAfectados;            
        } catch (SQLException ex) {
            ExcepcionIncidenciasCAD e = new ExcepcionIncidenciasCAD(
                    ex.getErrorCode(),
                    ex.getMessage(),
                    null,
                    sentenciaPreparada.toString());
            switch (ex.getErrorCode()) {
                case 1048:  
                    e.setMensajeErrorUsuario("Todos los campos son obligatorios");
                    break;
                default:    
                    e.setMensajeErrorUsuario("Error general del sistema. Consulte con el administrador");
                    break;
            }
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    }

    /**
     * Lee todos los registros de configuración de la base de datos 
     * @author Marcos Gonzalez Fernandez
     * @return Lista de todos los registros de configuración
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    public ArrayList<Configuracion> leerConfiguracion() throws ExcepcionIncidenciasCAD {
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
            cerrarConexionExcepcion(conexion, sentenciaPreparada);
            throw e;
        }
    } 
    
    /**
     * Convierte un objeto Date en un objeto String con formato "año-mes-día"
     * @param fecha Fecha a formatear
     * @return Fecha formateada
     * @throws ExcepcionIncidenciasCAD Se lanza en el caso de que se produzca cualquier excepción
     */
    private String formatearFecha(Date fecha)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fecha);
        return gc.get(Calendar.YEAR)+ "-"+(gc.get(Calendar.MONTH)+1)+"-"+gc.get(Calendar.DAY_OF_MONTH);
    }
    
}
