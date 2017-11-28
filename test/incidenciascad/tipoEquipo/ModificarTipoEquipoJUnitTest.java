package incidenciascad.tipoEquipo;

import incidenciascad.dependencia.*;
import incidenciascad.Dependencia;
import incidenciascad.equipo.*;
import incidenciascad.Equipo;
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
 * Prueba del método modificarEquipo de la clase IncidenciasCAD
 * @author Ignacio Fontecha Hernández
 */
public class ModificarTipoEquipoJUnitTest {
    
    public ModificarTipoEquipoJUnitTest() {
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
    public void testModificarTipoEquipoOK() throws ExcepcionIncidenciasCAD {
        System.out.println("ModificarTipoEquipo - Caso de éxito");
        TipoEquipo tipoEquipo = new TipoEquipo(2, "Algo", "Algo");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.modificarTipoEquipo(8, tipoEquipo);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de la NN por el método
     */
    @Test
    public void testModificarTipoEquipoViolacionNN() {
        System.out.println("modificarTipoEquipo - Caso de violación de NN");
        TipoEquipo tipoEquipo = new TipoEquipo(2, null, "Algo");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarTipoEquipo(8, tipoEquipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de la UK por el método
     */
    @Test
    public void testModificarTipoEquipoViolacionUK() {
        System.out.println("modificarTipoEquipo - Caso de violación de UK");
        TipoEquipo tipoEquipo = new TipoEquipo(2, "Torre", "Algo");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarTipoEquipo(8, tipoEquipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1062);
        }
    }
}
