package incidenciascad.estado;

import incidenciascad.estado.*;
import incidenciascad.estado.*;
import incidenciascad.Estado;
import incidenciascad.ExcepcionIncidenciasCAD;
import incidenciascad.IncidenciasCAD;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Prueba del método modificarEstado de la clase IncidenciasCAD
 * @author Ignacio Fontecha Hernández
 */
public class ModificarEstadoJUnitTest {
    
    public ModificarEstadoJUnitTest() {
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
    public void testModificarEstadoOK() throws ExcepcionIncidenciasCAD {
        System.out.println("ModificarCountry - Caso de éxito");
        Estado estado = new Estado(null, "1asd", "adsaddsa");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.modificarEstado(6, estado);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de la NN por el método
     */
    @Test
    public void testModificarEstadoViolacionNN() {
        System.out.println("modificarEstado - Caso de violación de NN");
        Estado estado = new Estado(null, null, null);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarEstado(2, estado);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de la UK por el método
     */
    @Test
    public void testModificarEstadoViolacionUK() {
        System.out.println("modificarEstado - Caso de violación de UK");
        Estado estado = new Estado(null, "00-RE", "00 - Recibida");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarEstado(2, estado);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1062);
        }
    }
}
