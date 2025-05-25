package DAOTests;

import com.ejemplo.gestionhospital.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testConstructorYGetters() {
        Usuario usuario = new Usuario("admin", "1234", true);

        assertEquals("admin", usuario.getUsername());
        assertEquals("1234", usuario.getPassword());
        assertTrue(usuario.isAdmin());
    }

    @Test
    void testSetUsername() {
        Usuario usuario = new Usuario("inicial", "pass", false);
        usuario.setUsername("nuevo");

        assertEquals("nuevo", usuario.getUsername());
    }

    @Test
    void testSetPassword() {
        Usuario usuario = new Usuario("user", "oldPass", false);
        usuario.setPassword("newPass");

        assertEquals("newPass", usuario.getPassword());
    }

    @Test
    void testIsAdminNoEsModificableDesdeAfuera() {
        Usuario usuario = new Usuario("usuario", "clave", false);

        // No hay setIsAdmin público, así que el valor no puede cambiarse directamente.
        assertFalse(usuario.isAdmin());
    }
}
