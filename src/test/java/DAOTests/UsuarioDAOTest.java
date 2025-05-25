package DAOTests;

import com.ejemplo.gestionhospital.dao.UsuarioDAO;
import com.ejemplo.gestionhospital.exception.AccessDataException;
import com.ejemplo.gestionhospital.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioDAOTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    void testObtenerUsuario_Existe() throws Exception {
        String username = "juan";
        String password = "clave123";
        boolean isAdmin = true;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("password")).thenReturn(password);
        when(resultSet.getBoolean("isAdmin")).thenReturn(isAdmin);

        Usuario usuario = usuarioDAO.obtenerUsuario(username);

        assertNotNull(usuario);
        assertEquals(username, usuario.getUsername());
        assertEquals(password, usuario.getPassword());
        assertTrue(usuario.isAdmin());
    }

    @Test
    void testObtenerUsuario_NoExiste() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // No se encontró usuario

        Usuario usuario = usuarioDAO.obtenerUsuario("inexistente");

        assertNull(usuario);
    }

    @Test
    void testObtenerUsuario_SQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Error simulado"));

        assertThrows(AccessDataException.class, () -> {
            usuarioDAO.obtenerUsuario("error");
        });
    }
}
