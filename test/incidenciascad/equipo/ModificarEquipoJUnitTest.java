package incidenciascad.equipo;

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
public class ModificarEquipoJUnitTest {
    
    public ModificarEquipoJUnitTest() {
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
    public void testModificarEquipoOK() throws ExcepcionIncidenciasCAD {
        System.out.println("modificarEquipo - Caso de éxito");
        TipoEquipo tipoEquipo=new TipoEquipo();
        tipoEquipo.setTipoEquipoId(1);
        Equipo equipo = new Equipo(0, "12123445", tipoEquipo);
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.modificarEquipo(1, equipo);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de la NN por el método
     */
    @Test
    public void testModificarEquipoViolacionNN() {
        System.out.println("modificarEquipo - Caso de violación de NN");
        TipoEquipo tipoEquipo=new TipoEquipo();
        tipoEquipo.setTipoEquipoId(1);
        Equipo equipo = new Equipo(0, "", tipoEquipo);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarEquipo(3, equipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de la UK por el método
     */
    @Test
    public void testModificarEquipoViolacionUK() {
        System.out.println("modificarEquipo - Caso de violación de UK");
        TipoEquipo tipoEquipo=new TipoEquipo();
        tipoEquipo.setTipoEquipoId(1);
        Equipo equipo = new Equipo(0, "12123445", tipoEquipo);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarEquipo(3, equipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1062);
        }
    }
    
    /**
     * Prueba la violación de la FK por el método
     */
    @Test
    public void testModificarEquipoFK() {
        System.out.println("modificarEquipo - Caso de violación de FK");
        TipoEquipo tipoEquipo=new TipoEquipo();
        tipoEquipo.setTipoEquipoId(99);
        Equipo equipo = new Equipo(0, "12123499", tipoEquipo);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarEquipo(3, equipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1452);
        }
    }
}
