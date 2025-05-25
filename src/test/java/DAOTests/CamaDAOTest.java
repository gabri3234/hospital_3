package DAOTests;

import com.ejemplo.gestionhospital.dao.*;
import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Cama;
import com.ejemplo.gestionhospital.model.Habitacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CamaDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private CamaDAO camaDAO;

    @BeforeEach
    public void setUp() throws Exception {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        statement = mock(Statement.class);
        resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);

        camaDAO = new CamaDAO(dataSource);
    }

    @Test
    public void testInsertarCama() throws Exception {
        Cama cama = new Cama();
        cama.setHabitacion_id(1);
        cama.setEstado("LIBRE");

        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(10);

        camaDAO.insertarCama(cama);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setNull(2, Types.INTEGER);
        verify(preparedStatement).setString(3, "LIBRE");
        verify(preparedStatement).executeUpdate();

        assertEquals(10, cama.getId());
    }

    @Test
    public void testActualizarEstadoCama() throws Exception {
        Cama cama = new Cama();
        cama.setId(5);
        cama.setEstado("OCUPADA");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        camaDAO.actualizarEstadoCama(cama);

        verify(preparedStatement).setString(1, "OCUPADA");
        verify(preparedStatement).setInt(2, 5);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testObtenerCamas() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("habitacion_id")).thenReturn(10);
        when(resultSet.getString("estado")).thenReturn("OCUPADA");
        when(resultSet.getInt("paciente_id")).thenReturn(99);

        List<Cama> camas = camaDAO.obtenerCamas();

        assertEquals(1, camas.size());
        Cama cama = camas.get(0);
        assertEquals(1, cama.getId());
        assertEquals(10, cama.getHabitacion_id());
        assertEquals("OCUPADA", cama.getEstado());
        assertEquals(99, cama.getPaciente_id());
    }

    @Test
    public void testObtenerCamasOcupadasHabitacionN() throws Exception {
        Habitacion habitacion = new Habitacion();
        habitacion.setId(2);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(3);
        when(resultSet.getInt("habitacion_id")).thenReturn(2);
        when(resultSet.getString("estado")).thenReturn("OCUPADA");
        when(resultSet.getInt("paciente_id")).thenReturn(77);

        List<Cama> camas = camaDAO.obtenerCamasHabitacionN(habitacion);

        assertEquals(1, camas.size());
        assertEquals(77, camas.get(0).getPaciente_id());
    }

    @Test
    public void testObtenerCamasLibres() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(4);
        when(resultSet.getInt("habitacion_id")).thenReturn(5);
        when(resultSet.getString("estado")).thenReturn("LIBRE");
        when(resultSet.getInt("paciente_id")).thenReturn(0);

        List<Cama> camas = camaDAO.obtenerCamasLibres();

        assertEquals(1, camas.size());
        assertEquals("LIBRE", camas.get(0).getEstado());
    }

    @Test
    public void testAsignarCamaPaciente() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        camaDAO.asignarCamaPaciente(99, 88);

        verify(preparedStatement).setInt(1, 99);
        verify(preparedStatement).setInt(2, 88);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testInsertarCamaThrowsAccessDataException() throws Exception {
        when(dataSource.getConnection()).thenThrow(new SQLException("Error"));

        Cama cama = new Cama();
        cama.setHabitacion_id(1);
        cama.setEstado("LIBRE");

        assertThrows(AccessDataException.class, () -> camaDAO.insertarCama(cama));
    }
}
