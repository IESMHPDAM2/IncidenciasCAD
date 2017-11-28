package incidenciascad.tipoEquipo;

import incidenciascad.dependencia.*;
import incidenciascad.Dependencia;
import incidenciascad.ExcepcionIncidenciasCAD;
import incidenciascad.IncidenciasCAD;
import incidenciascad.TipoEquipo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Prueba del método insertarEquipo de la clase IncidenciasCAD
 * @author Ignacio Fontecha Hernández
 */
public class InsertarTipoEquipoJUnitTest {
    
    public InsertarTipoEquipoJUnitTest() {
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
    public void testInsertarTipoEquipoOK() throws ExcepcionIncidenciasCAD {
        System.out.println("insertarTipoEquipo - Caso de éxito");
        TipoEquipo tipoEquipo = new TipoEquipo(null,"Algo","Algo");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.insertarTipoEquipo(tipoEquipo);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de NN por el método
     */
    @Test
    public void testInsertarTipoEquipoViolacionNN() {
        System.out.println("insertarTipoEquipo - Caso de violación de NN");
        TipoEquipo tipoEquipo = new TipoEquipo(null,"ruter",null);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarTipoEquipo(tipoEquipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de UK por el método
     */
    @Test
    public void testInsertarTipoEquipoViolacionUK() {
        System.out.println("insertarTipoEquipo - Caso de violación de UK");
        TipoEquipo tipoEquipo = new TipoEquipo(null,"Torre","ruter");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarTipoEquipo(tipoEquipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1062);
        }
    }
}
