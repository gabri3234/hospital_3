package DAOTests;

import com.ejemplo.gestionhospital.dao.*;
import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Habitacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HabitacionDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private HabitacionDAO habitacionDAO;
    private CamaDAO camaDAO;

    @BeforeEach
    public void setUp() throws Exception {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        statement = mock(Statement.class);
        resultSet = mock(ResultSet.class);
        camaDAO = mock(CamaDAO.class);

        habitacionDAO = new HabitacionDAO(dataSource) {
            @Override
            public int obtenerEspacioDisponible(Habitacion habitacion) {
                return super.obtenerEspacioDisponible(habitacion);
            }

            @Override
            public List<Habitacion> obtenerHabitaciones() {
                return super.obtenerHabitaciones();
            }
        };
    }

    @Test
    public void testInsertarHabitacion() throws Exception {
        Habitacion habitacion = new Habitacion();
        habitacion.setNombre("A101");
        habitacion.setCapacidad(3);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        habitacionDAO.insertarHabitacion(habitacion);

        verify(preparedStatement).setString(1, "A101");
        verify(preparedStatement).setInt(2, 3);
        verify(preparedStatement).executeUpdate();

        assertEquals(1, habitacion.getId());
    }

    @Test
    public void testEliminarHabitacion() throws Exception {
        Habitacion habitacion = new Habitacion();
        habitacion.setId(1);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        habitacionDAO.eliminarHabitacion(habitacion);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testObtenerHabitaciones() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nombre")).thenReturn("A101");
        when(resultSet.getInt("capacidad")).thenReturn(3);

        List<Habitacion> habitaciones = habitacionDAO.obtenerHabitaciones();

        assertEquals(1, habitaciones.size());
        assertEquals("A101", habitaciones.get(0).getNombre());
        assertEquals(3, habitaciones.get(0).getCapacidad());
    }

    @Test
    public void testObtenerEspacioDisponible() {
        Habitacion habitacion = new Habitacion(1, "A101", 5);
        CamaDAO camaDAOMock = mock(CamaDAO.class);
        HabitacionDAO habitacionDAOConMock = new HabitacionDAO(dataSource) {
            @Override
            public int obtenerEspacioDisponible(Habitacion h) {
                return 5 - 2;
            }
        };

        int espacio = habitacionDAOConMock.obtenerEspacioDisponible(habitacion);

        assertEquals(3, espacio);
    }

    @Test
    public void testInsertarHabitacionSQLException() throws Exception {
        when(dataSource.getConnection()).thenThrow(new SQLException("DB error"));

        Habitacion habitacion = new Habitacion();
        habitacion.setNombre("A101");
        habitacion.setCapacidad(3);

        assertThrows(AccessDataException.class, () -> habitacionDAO.insertarHabitacion(habitacion));
    }
}
