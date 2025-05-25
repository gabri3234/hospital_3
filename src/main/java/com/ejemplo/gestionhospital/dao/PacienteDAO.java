package com.ejemplo.gestionhospital.dao;

import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PacienteDAOTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private PacienteDAO pacienteDAO;

    private Paciente paciente;

    @BeforeEach
    void setUp() throws Exception {
        paciente = new Paciente(0, "Juan", "Pérez", "12345678A", 3);
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    void testInsertarPaciente() throws Exception {
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        pacienteDAO.insertarPaciente(paciente);

        verify(preparedStatement).setString(1, "Juan");
        verify(preparedStatement).setString(2, "Pérez");
        verify(preparedStatement).setString(3, "12345678A");
        verify(preparedStatement).setInt(4, 3);
        assertEquals(1, paciente.getId());
    }

    @Test
    void testObtenerPacientes() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nombre")).thenReturn("Juan");
        when(resultSet.getString("apellido")).thenReturn("Pérez");
        when(resultSet.getString("dni")).thenReturn("12345678A");
        when(resultSet.getInt("gravedad")).thenReturn(3);

        List<Paciente> pacientes = pacienteDAO.obtenerPacientes();
        assertEquals(1, pacientes.size());
        assertEquals("Juan", pacientes.get(0).getNombre());
    }

    @Test
    void testEliminarPaciente() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        paciente.setId(1);

        pacienteDAO.eliminarPaciente(paciente);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testObtenerPacientesIngresados() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nombre")).thenReturn("Juan");
        when(resultSet.getString("apellido")).thenReturn("Pérez");
        when(resultSet.getString("dni")).thenReturn("12345678A");
        when(resultSet.getInt("gravedad")).thenReturn(3);

        List<Paciente> pacientes = pacienteDAO.obtenerPacientesIngresados();
        assertEquals(1, pacientes.size());
    }

    @Test
    void testObtenerPacientesNoIngresados() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nombre")).thenReturn("Juan");
        when(resultSet.getString("apellido")).thenReturn("Pérez");
        when(resultSet.getString("dni")).thenReturn("12345678A");
        when(resultSet.getInt("gravedad")).thenReturn(3);

        List<Paciente> pacientes = pacienteDAO.obtenerPacientesNoIngresados();
        assertEquals(1, pacientes.size());
    }

    @Test
    void testInsertarPacienteSQLException() throws Exception {
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenThrow(new SQLException("Error"));

        AccessDataException exception = assertThrows(
                AccessDataException.class,
                () -> pacienteDAO.insertarPaciente(paciente)
        );

        assertTrue(exception.getMessage().contains("Error al insertar paciente"));
    }
}
