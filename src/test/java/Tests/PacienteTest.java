package Tests;

import com.ejemplo.gestionhospital.model.Paciente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {

    @Test
    void testConstructorConId() {
        Paciente paciente = new Paciente(1, "Juan", "Pérez", "12345678A", 4);

        assertEquals(1, paciente.getId());
        assertEquals("Juan", paciente.getNombre());
        assertEquals("Pérez", paciente.getApellido());
        assertEquals("12345678A", paciente.getDni());
        assertEquals(4, paciente.getGravedad());
    }

    @Test
    void testConstructorSinId() {
        Paciente paciente = new Paciente("Ana", "López", "87654321B", 2);

        assertEquals("Ana", paciente.getNombre());
        assertEquals("López", paciente.getApellido());
        assertEquals("87654321B", paciente.getDni());
        assertEquals(2, paciente.getGravedad());
        assertEquals(0, paciente.getId()); // No se asignó ID
    }

    @Test
    void testSettersAndGetters() {
        Paciente paciente = new Paciente("Maria", "Gomez", "00000000Z", 1);
        paciente.setId(10);
        paciente.setNombre("Laura");
        paciente.setApellido("Fernandez");
        paciente.setDni("11111111X");
        paciente.setGravedad(5);

        assertEquals(10, paciente.getId());
        assertEquals("Laura", paciente.getNombre());
        assertEquals("Fernandez", paciente.getApellido());
        assertEquals("11111111X", paciente.getDni());
        assertEquals(5, paciente.getGravedad());
    }

    @Test
    void testToString() {
        Paciente paciente = new Paciente(7, "Carlos", "Ramírez", "99999999Y", 3);
        String expected = "● ID: 7 Carlos Ramírez 99999999Y Gravedad: 3";

        assertEquals(expected, paciente.toString());
    }
}
