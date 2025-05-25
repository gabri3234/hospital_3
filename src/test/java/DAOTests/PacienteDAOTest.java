package DAOTests;

import com.ejemplo.gestionhospital.dao.PacienteDAO;
import com.ejemplo.gestionhospital.model.Paciente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private PacienteDAO pacienteDAO;

    @BeforeEach
    void setUp() throws Exception {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);

        pacienteDAO = new PacienteDAO(dataSource);
    }

    @Test
    void testInsertarPaciente() throws Exception {
        Paciente paciente = new Paciente("Juan", "Perez", "12345678", 2);

        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(10);

        pacienteDAO.insertarPaciente(paciente);

        // Verificar que se ejecutó el prepared statement con los valores correctos
        verify(preparedStatement).setString(1, "Juan");
        verify(preparedStatement).setString(2, "Perez");
        verify(preparedStatement).setString(3, "12345678");
        verify(preparedStatement).setInt(4, 2);

        assertEquals(10, paciente.getId());
    }

    @Test
    void testObtenerPacientes() throws Exception {
        String query = "SELECT * FROM pacientes";

        Statement statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(query)).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false); // Dos pacientes
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("nombre")).thenReturn("Juan", "Ana");
        when(resultSet.getString("apellido")).thenReturn("Perez", "Lopez");
        when(resultSet.getString("dni")).thenReturn("12345678", "87654321");
        when(resultSet.getInt("gravedad")).thenReturn(2, 3);

        List<Paciente> pacientes = pacienteDAO.obtenerPacientes();

        assertEquals(2, pacientes.size());
        assertEquals("Juan", pacientes.get(0).getNombre());
        assertEquals("Ana", pacientes.get(1).getNombre());
    }

    @Test
    void testEliminarPaciente() throws Exception {
        Paciente paciente = new Paciente(5, "test", "test", "test", 2);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        pacienteDAO.eliminarPaciente(paciente);

        verify(preparedStatement).setInt(1, 5);
        verify(preparedStatement).executeUpdate();
    }


}
