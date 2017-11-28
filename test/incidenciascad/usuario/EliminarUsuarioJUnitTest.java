package incidenciascad.usuario;

import incidenciascad.equipo.*;
import incidenciascad.ExcepcionIncidenciasCAD;
import incidenciascad.IncidenciasCAD;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Prueba del método eliminarUsuario de la clase IncidenciasCAD
 * @author Ignacio Fontecha Hernández
 */
public class EliminarUsuarioJUnitTest {
    
    public EliminarUsuarioJUnitTest() {
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
    public void testEliminarUsuarioOK() throws ExcepcionIncidenciasCAD {
        System.out.println("eliminarUsuario - Caso de éxito");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.eliminarUsuario(8); 
        assertEquals(expResult, result);
    }
    
    
}
