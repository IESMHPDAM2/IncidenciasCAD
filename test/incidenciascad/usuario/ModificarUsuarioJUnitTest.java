package incidenciascad.usuario;

import incidenciascad.usuario.*;
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
 * Prueba del método modificarUsuario de la clase IncidenciasCAD
 * @author Ignacio Fontecha Hernández
 */
public class ModificarUsuarioJUnitTest {
    
    public ModificarUsuarioJUnitTest() {
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

    /**
     * Prueba el caso de éxito del método
     * @throws ExcepcionIncidenciasCAD si se produce cualquier excepcion
     */
    @Test
    public void testModificarUsuarioOK() throws ExcepcionIncidenciasCAD {
        System.out.println("ModificarCountry - Caso de éxito");
        Usuario usuario = new Usuario(null, "sfdsasdasds", null,null,null);
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.modificarUsuario(10, usuario);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de la NN por el método
     */
    @Test
    public void testModificarUsuarioViolacionNN() {
        System.out.println("modificarUsuario - Caso de violación de NN");
        Usuario usuario = new Usuario(null, null,null,null,null);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarUsuario(3, usuario);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de la UK por el método
     */
    @Test
    public void testModificarUsuarioViolacionUK() {
        System.out.println("modificarUsuario - Caso de violación de UK");
        Usuario usuario = new Usuario(null, "rtezanos", null,null,null);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarUsuario(10, usuario);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1062);
        }
    }
}
