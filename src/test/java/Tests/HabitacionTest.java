package Tests;

import com.ejemplo.gestionhospital.model.Habitacion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HabitacionTest {

    @Test
    void testConstructorSoloCapacidad() {
        Habitacion habitacion = new Habitacion(3);
        assertEquals(3, habitacion.getCapacidad());
        assertNull(habitacion.getNombre());
        assertEquals(0, habitacion.getId());
    }

    @Test
    void testConstructorNombreYCapacidad() {
        Habitacion habitacion = new Habitacion("Pediatría", 4);
        assertEquals("Pediatría", habitacion.getNombre());
        assertEquals(4, habitacion.getCapacidad());
        assertEquals(0, habitacion.getId());
    }

    @Test
    void testConstructorIdNombreCapacidad() {
        Habitacion habitacion = new Habitacion(101, "UCI", 2);
        assertEquals(101, habitacion.getId());
        assertEquals("UCI", habitacion.getNombre());
        assertEquals(2, habitacion.getCapacidad());
    }

    @Test
    void testSettersAndGetters() {
        Habitacion habitacion = new Habitacion(1);
        habitacion.setId(202);
        habitacion.setNombre("Quirófano");
        habitacion.setCapacidad(5);

        assertEquals(202, habitacion.getId());
        assertEquals("Quirófano", habitacion.getNombre());
        assertEquals(5, habitacion.getCapacidad());
    }

    @Test
    void testToString() {
        Habitacion habitacion = new Habitacion(301, "Emergencias", 6);
        String esperado = "● Habitacion 301 Capacidad: 6";
        assertEquals(esperado, habitacion.toString());
    }
}
