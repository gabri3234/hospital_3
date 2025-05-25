package DAOTests;

import com.ejemplo.gestionhospital.dao.CamaDAO;
import com.ejemplo.gestionhospital.dao.ConexionDB;
import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Cama;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CamaDAOTest {

    private CamaDAO camaDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private Statement mockStatement;

    @BeforeEach
    void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockStatement = mock(Statement.class);

        // Mock de la conexión estática
        Mockito.mockStatic(ConexionDB.class).when(ConexionDB::getConnection).thenReturn(mockConnection);

        camaDAO = new CamaDAO();
    }

    @Test
    void testInsertarCama() throws Exception {
        Cama cama = new Cama(1, 1, "Disponible", 0);

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(101);

        camaDAO.insertarCama(cama);

        verify(mockPreparedStatement, times(1)).setInt(1, cama.getHabitacion_id());
        verify(mockPreparedStatement, times(1)).setNull(2, Types.INTEGER);
        verify(mockPreparedStatement, times(1)).setString(3, cama.getEstado());
        verify(mockPreparedStatement, times(1)).executeUpdate();

        assertEquals(101, cama.getId());
    }

    @Test
    void testInsertarCamaThrowsException() throws Exception {
        Cama cama = new Cama(1, 1, "Disponible", 0);

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenThrow(new SQLException("Database error"));

        assertThrows(AccessDataException.class, () -> camaDAO.insertarCama(cama));
    }

    @Test
    void testActualizarEstadoCama() throws Exception {
        Cama cama = new Cama(101, 1, "Ocupada", 1001);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        camaDAO.actualizarEstadoCama(cama);

        verify(mockPreparedStatement, times(1)).setString(1, cama.getEstado());
        verify(mockPreparedStatement, times(1)).setInt(2, cama.getId());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testObtenerCamas() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getInt("habitacion_id")).thenReturn(101, 102);
        when(mockResultSet.getString("estado")).thenReturn("Disponible", "Ocupada");
        when(mockResultSet.getInt("paciente_id")).thenReturn(0, 1001);

        List<Cama> camas = camaDAO.obtenerCamas();

        assertEquals(2, camas.size());
        assertEquals(1, camas.get(0).getId());
        assertEquals("Disponible", camas.get(0).getEstado());
        assertEquals(1001, camas.get(1).getPaciente_id());
    }

    @Test
    void testObtenerCamasLibres() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("habitacion_id")).thenReturn(101);
        when(mockResultSet.getString("estado")).thenReturn("Disponible");
        when(mockResultSet.getInt("paciente_id")).thenReturn(0);

        List<Cama> camas = camaDAO.obtenerCamasLibres();

        assertEquals(1, camas.size());
        assertEquals(1, camas.get(0).getId());
        assertEquals(0, camas.get(0).getPaciente_id());
    }

    @Test
    void testAsignarCamaPaciente() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        camaDAO.asignarCamaPaciente(1001, 101);

        verify(mockPreparedStatement, times(1)).setInt(1, 1001);
        verify(mockPreparedStatement, times(1)).setInt(2, 101);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testAsignarCamaPacienteThrowsException() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        assertThrows(AccessDataException.class, () -> camaDAO.asignarCamaPaciente(1001, 101));
    }
}
