package process;

import DAO.DaoUsuario;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class CrearUsuario {

    public static void main(String[] args) {
        // Datos de ejemplo del nuevo usuario
        String nombreUsuario = "Juan";
        String apellido1 = "Pérez";
        String apellido2 = "González";
        int telefono = 123456789;
        String email = "juan.perez@example.com";
        String contrasena = "1234";  // La contraseña que se introducirá
        String rol = "empleado";  // El rol del usuario (puede ser "admin" o "empleado")

        // Hashear la contraseña con BCrypt
        String contrasenaHasheada = BCrypt.hashpw(contrasena, BCrypt.gensalt());

        // Crear el objeto Usuario con los datos proporcionados
        Usuario nuevoUsuario = new Usuario(nombreUsuario, apellido1, apellido2, telefono, email, contrasenaHasheada, rol);

        // Crear una instancia de DaoUsuario
        DaoUsuario daoUsuario = new DaoUsuario();

        // Llamar al método para insertar el nuevo usuario en la base de datos
        daoUsuario.insertar(nuevoUsuario);

        System.out.println("Usuario creado exitosamente: " + nuevoUsuario.getNombreUsuario());
    }
}