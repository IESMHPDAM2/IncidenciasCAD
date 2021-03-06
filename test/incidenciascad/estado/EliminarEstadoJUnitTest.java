package incidenciascad.estado;

import incidenciascad.ExcepcionIncidenciasCAD;
import incidenciascad.IncidenciasCAD;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Prueba del método eliminarEstado de la clase IncidenciasCAD
 * @author Ignacio Fontecha Hernández
 */
public class EliminarEstadoJUnitTest {
    
    public EliminarEstadoJUnitTest() {
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
    public void testEliminarEstadoOK() throws ExcepcionIncidenciasCAD {
        System.out.println("eliminarEstado - Caso de éxito");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.eliminarEstado(6); 
        assertEquals(expResult, result);
    }
    
    
}
