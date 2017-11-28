
package incidenciascad.estado;

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
 * 
 * @author Ramón Tezanos San Emeterio
 */
public class InsertarEstadoJUnitTest {
    
    public InsertarEstadoJUnitTest() {
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
    public void testInsertarEstadoOK() throws ExcepcionIncidenciasCAD {
        System.out.println("insertarEstado - Caso de éxito");
        Estado estado = new Estado(null, "11-RO", "Robado");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.insertarEstado(estado);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de NN por el método
     */
    @Test
    public void testInsertarEstadoViolacionNN() {
        System.out.println("insertarEstado - Caso de violación de NN");
        Estado estado = new Estado(null, null, null);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarEstado(estado);
            fail("Se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de UK por el método
     */
    @Test
    public void testInsertarEstadoViolacionUK() {
        System.out.println("insertarEstado - Caso de violación de UK");
        //Estado estado = new Estado(null, "jluis", "Jose Luis", "Gomez", "Administración");
        Estado estado = new Estado(null, "01-ET", "En trámite");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarEstado(estado);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1062);
        }
    }
}
