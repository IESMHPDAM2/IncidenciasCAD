package incidenciascad.tipoEquipo;


import incidenciascad.dependencia.*;
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
public class EliminarTipoEquipoJUnitTest {
    
    public EliminarTipoEquipoJUnitTest() {
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
    public void testEliminarTipoEquipoOK() throws ExcepcionIncidenciasCAD {
        System.out.println("eliminarTipoEquipo - Caso de éxito");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.eliminarTipoEquipo(13); 
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de la FK por el método
     */
    @Test
    public void testEliminarTipoEquipoViolacionFK() {
        System.out.println("eliminarTipoEquipo - Caso de violación de FK");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.eliminarTipoEquipo(1);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1451);
        }
    }
}
