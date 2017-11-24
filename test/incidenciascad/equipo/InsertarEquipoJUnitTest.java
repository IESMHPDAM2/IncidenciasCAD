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
 * Prueba del método insertarEquipo de la clase IncidenciasCAD
 * @author Ignacio Fontecha Hernández
 */
public class InsertarEquipoJUnitTest {
    
    public InsertarEquipoJUnitTest() {
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
    public void testInsertarEquipoOK() throws ExcepcionIncidenciasCAD {
        System.out.println("insertarEstado - Caso de éxito");
        TipoEquipo tipoEquipo = new TipoEquipo();
        tipoEquipo.setTipoEquipoId(1);
        Equipo equipo = new Equipo(null,"189981",tipoEquipo);
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.insertarEquipo(equipo);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de NN por el método
     */
    @Test
    public void testInsertarEquipoViolacionNN() {
        System.out.println("insertarEquipo - Caso de violación de NN");
        TipoEquipo tipoEquipo = new TipoEquipo();
        tipoEquipo.setTipoEquipoId(1);
        Equipo equipo = new Equipo(null,null,tipoEquipo);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarEquipo(equipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de UK por el método
     */
    @Test
    public void testInsertarEquipoViolacionUK() {
        System.out.println("insertarEquipo - Caso de violación de UK");
        TipoEquipo tipoEquipo = new TipoEquipo();
        tipoEquipo.setTipoEquipoId(1);
        Equipo equipo = new Equipo(null,"189981",tipoEquipo);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarEquipo(equipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }

    /**
     * Prueba la violación de la FK por el método
     */
    @Test
    public void testInsertarEquipoViolacionFK() {
        System.out.println("insertarEquipo - Caso de violación de FK");
        TipoEquipo tipoEquipo = new TipoEquipo();
        tipoEquipo.setTipoEquipoId(68);
        Equipo equipo = new Equipo(null,"989898",tipoEquipo);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarEquipo(equipo);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1452);
        }
    }
}
