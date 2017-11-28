package incidenciascad.dependencia;

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
public class ModificarDependenciaJUnitTest {
    
    public ModificarDependenciaJUnitTest() {
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
    public void testModificarDependenciaOK() throws ExcepcionIncidenciasCAD {
        System.out.println("ModificarDependencia - Caso de éxito");
        Dependencia dependencia= new Dependencia(2, "IF05", "Aula info 05");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.modificarDependencia(2, dependencia);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de la NN por el método
     */
    @Test
    public void testModificarDependenciaViolacionNN() {
        System.out.println("modificarDependencia - Caso de violación de NN");
        Dependencia dependencia= new Dependencia(2, null, "Aula info 07");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarDependencia(2, dependencia);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de la UK por el método
     */
    @Test
    public void testModificarDependenciaViolacionUK() {
        System.out.println("modificarDependencia - Caso de violación de UK");
        Dependencia dependencia= new Dependencia(2, "IF01", "Aula info 09");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.modificarDependencia(2, dependencia);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1062);
        }
    }
}
