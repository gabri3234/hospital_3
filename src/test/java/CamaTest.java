package com.ejemplo.gestionhospital.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CamaTest {

    @Test
    void testConstructorWithAllParameters() {
        // Arrange & Act
        Cama cama = new Cama(1, 101, "Disponible", 1001);

        // Assert
        assertEquals(1, cama.getId());
        assertEquals(101, cama.getHabitacion_id());
        assertEquals("Disponible", cama.getEstado());
        assertEquals(1001, cama.getPaciente_id());
    }

    @Test
    void testConstructorWithHabitacionId() {
        // Arrange & Act
        Cama cama = new Cama(102);

        // Assert
        assertEquals(102, cama.getHabitacion_id());
        assertEquals(0, cama.getId()); // Valor por defecto
        assertNull(cama.getEstado()); // Valor por defecto
        assertEquals(0, cama.getPaciente_id()); // Valor por defecto
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Cama cama = new Cama(1, 101, "Disponible", 1001);

        // Act
        cama.setId(2);
        cama.setHabitacion_id(102);
        cama.setEstado("Ocupada");
        cama.setPaciente_id(2002);

        // Assert
        assertEquals(2, cama.getId());
        assertEquals(102, cama.getHabitacion_id());
        assertEquals("Ocupada", cama.getEstado());
        assertEquals(2002, cama.getPaciente_id());
    }

    @Test
    void testToString() {
        // Arrange
        Cama cama = new Cama(1, 101, "Disponible", 1001);

        // Act
        String result = cama.toString();

        // Assert
        assertEquals("● Cama 1 Habitacion: 101 Estado: Disponible Paciente: 1001", result);
    }

    @Test
    void testDefaultValuesInConstructor() {
        // Arrange & Act
        Cama cama = new Cama(103);

        // Assert
        assertEquals(103, cama.getHabitacion_id());
        assertEquals(0, cama.getId());
        assertNull(cama.getEstado());
        assertEquals(0, cama.getPaciente_id());
    }
}
