package incidenciascad.equipo;

import incidenciascad.ExcepcionIncidenciasCAD;
import incidenciascad.IncidenciasCAD;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Prueba del método eliminarEquipo de la clase IncidenciasCAD
 * @author Ignacio Fontecha Hernández
 */
public class EliminarEquipoJUnitTest {
    
    public EliminarEquipoJUnitTest() {
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
    public void testEliminarEquipoOK() throws ExcepcionIncidenciasCAD {
        System.out.println("eliminarEquipo - Caso de éxito");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.eliminarEquipo(35); 
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de la FK por el método
     */
    @Test
    public void testEliminarEquipoViolacionFK() {
        System.out.println("eliminarEquipo - Caso de violación de FK");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.eliminarEquipo(2);
            fail("No se ha lanzado una ExccepcionHR");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1451);
        }
    }
}
