package incidenciascad.dependencia;

import incidenciascad.Dependencia;
import incidenciascad.ExcepcionIncidenciasCAD;
import incidenciascad.IncidenciasCAD;
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
public class InsertarDependenciaJUnitTest {
    
    public InsertarDependenciaJUnitTest() {
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
    public void testInsertarDependenciaOK() throws ExcepcionIncidenciasCAD {
        System.out.println("insertarDependencia - Caso de éxito");
        Dependencia dependencia = new Dependencia(null,"IF050","1264");
        IncidenciasCAD instance = new IncidenciasCAD();
        int expResult = 1;
        int result = instance.insertarDependencia(dependencia);
        assertEquals(expResult, result);
    }
    
    /**
     * Prueba la violación de NN por el método
     */
    @Test
    public void testInsertarDependenciaViolacionNN() {
        System.out.println("insertarDependencia - Caso de violación de NN");
        Dependencia dependencia = new Dependencia(null,"15",null);
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarDependencia(dependencia);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }
    
    /**
     * Prueba la violación de UK por el método
     */
    @Test
    public void testInsertarDependenciaViolacionUK() {
        System.out.println("insertarDependencia - Caso de violación de UK");
        Dependencia dependencia = new Dependencia(null,"IF02","");
        try {
            IncidenciasCAD instance = new IncidenciasCAD();
            instance.insertarDependencia(dependencia);
            fail("No se ha lanzado una ExcepcionIncidenciasCAD");
        } catch (ExcepcionIncidenciasCAD ex) {
            assertEquals((int) ex.getCodigoErrorSistema(),1048);
        }
    }

}
