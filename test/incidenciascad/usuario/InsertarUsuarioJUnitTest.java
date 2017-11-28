
package incidenciascad.usuario;

import incidenciascad.Usuario;
import incidenciascad.ExcepcionIncidenciasCAD;
import incidenciascad.IncidenciasCAD;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Ramón Tezanos San Emeterio
 */
public class InsertarUsuarioJUnitTest {
    
    public InsertarUsuarioJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testInsertarUsuarioOK() throws ExcepcionIncidenciasCAD {
        System.out.println("insertarEstado - Caso de éxito");
        Usuario usuario = new Usuario(null, "rtezanos4", "Ramón2", "Tezanos2", "Informatica");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.insertarUsuario(usuario);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de NN por el método
     */
    @Test
    public void testInsertarUsuarioViolacionNN() {
        System.out.println("insertarUsuario - Caso de violación de NN");
        Usuario usuario = new Usuario(null, null, null, null, null);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarUsuario(usuario);
            fail("Se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de UK por el método
     */
    @Test
    public void testInsertarUsuarioViolacionUK() {
        System.out.println("insertarUsuario - Caso de violación de UK");
        //Usuario usuario = new Usuario(null, "jluis", "Jose Luis", "Gomez", "Administración");
        Usuario usuario = new Usuario(null, "rtezanos", null, null, null);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarUsuario(usuario);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1062);
        }
    }
}
