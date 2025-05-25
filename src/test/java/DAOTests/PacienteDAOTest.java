package DAOTests;

import com.ejemplo.gestionhospital.dao.PacienteDAO;
import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteDAOTest {

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

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    void testInsertarPaciente() throws Exception {
        Paciente paciente = new Paciente(0, "Juan", "Pérez", "12345678A", 3);
        ResultSet generatedKeys = mock(ResultSet.class);

        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getInt(1)).thenReturn(10);

        pacienteDAO.insertarPaciente(paciente);

        verify(preparedStatement).setString(1, "Juan");
        verify(preparedStatement).setString(2, "Pérez");
        verify(preparedStatement).setString(3, "12345678A");
        verify(preparedStatement).setInt(4, 3);
        verify(preparedStatement).executeUpdate();

        assertEquals(10, paciente.getId());
    }

    @Test
    void testObtenerPacientes() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nombre")).thenReturn("Ana");
        when(resultSet.getString("apellido")).thenReturn("López");
        when(resultSet.getString("dni")).thenReturn("11111111B");
        when(resultSet.getInt("gravedad")).thenReturn(2);

        List<Paciente> pacientes = pacienteDAO.obtenerPacientes();

        assertEquals(1, pacientes.size());
        assertEquals("Ana", pacientes.get(0).getNombre());
    }

    @Test
    void testEliminarPaciente() throws Exception {
        Paciente paciente = new Paciente(5, "Carlos", "Gómez", "22222222C", 1);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        pacienteDAO.eliminarPaciente(paciente);

        verify(preparedStatement).setInt(1, 5);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testObtenerPacientesIngresados() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(2);
        when(resultSet.getString("nombre")).thenReturn("Laura");
        when(resultSet.getString("apellido")).thenReturn("Martínez");
        when(resultSet.getString("dni")).thenReturn("33333333D");
        when(resultSet.getInt("gravedad")).thenReturn(4);

        List<Paciente> pacientes = pacienteDAO.obtenerPacientesIngresados();

        assertEquals(1, pacientes.size());
        assertEquals("Laura", pacientes.get(0).getNombre());
    }

    @Test
    void testObtenerPacientesNoIngresados() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(3);
        when(resultSet.getString("nombre")).thenReturn("Pedro");
        when(resultSet.getString("apellido")).thenReturn("Ramírez");
        when(resultSet.getString("dni")).thenReturn("44444444E");
        when(resultSet.getInt("gravedad")).thenReturn(1);

        List<Paciente> pacientes = pacienteDAO.obtenerPacientesNoIngresados();

        assertEquals(1, pacientes.size());
        assertEquals("Pedro", pacientes.get(0).getNombre());
    }

    @Test
    void testInsertarPacienteSQLException() throws Exception {
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenThrow(new SQLException("DB error"));

        Paciente paciente = new Paciente(0, "Mario", "Sánchez", "55555555F", 2);

        assertThrows(AccessDataException.class, () -> pacienteDAO.insertarPaciente(paciente));
    }
}
