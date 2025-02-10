package process;

import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import DAO.DaoUsuario;

public class CrearUsuariosBackend {

    public static void main(String[] args) {
        // Datos de los usuarios que se desean agregar
        String[][] usuariosData = {
            {"Empleado1", "Apellido1", "Apellido2", "empleado1@smartstock.com", "1234", "empleado"},
            {"Empleado2", "Apellido1", "Apellido2", "empleado2@smartstock.com", "1234", "empleado"},
            {"Empleado3", "Apellido1", "Apellido2", "empleado3@smartstock.com", "1234", "empleado"},
            {"Empleado4", "Apellido1", "Apellido2", "empleado4@smartstock.com", "1234", "empleado"},
            {"Empleado5", "Apellido1", "Apellido2", "empleado5@smartstock.com", "1234", "empleado"},
            {"Empleado6", "Apellido1", "Apellido2", "empleado6@smartstock.com", "1234", "empleado"},
            {"Admin", "Administrador", "", "admin@smartstock.com", "1234", "admin"}
        };

        // Crear la instancia del DAO
        DaoUsuario daoUsuario = new DaoUsuario();

        // Iterar sobre los datos de los usuarios y agregar cada uno a la base de datos
        for (String[] usuarioData : usuariosData) {
            // Extraemos los datos
            String nombreUsuario = usuarioData[0];
            String apellido1 = usuarioData[1];
            String apellido2 = usuarioData[2];
            String email = usuarioData[3];
            String contrasena = usuarioData[4];  // La contraseña original
            String rol = usuarioData[5];

            // Hashear la contraseña
            String contrasenaHasheada = BCrypt.hashpw(contrasena, BCrypt.gensalt());

            // Crear el objeto Usuario
            Usuario nuevoUsuario = new Usuario(nombreUsuario, apellido1, apellido2, 123456789, email, contrasenaHasheada, rol);

            // Insertar el usuario en la base de datos
            try {
                daoUsuario.insertar(nuevoUsuario);
                System.out.println("Usuario creado correctamente: " + email);
            } catch (Exception e) {
                System.err.println("Error al crear el usuario: " + email);
                e.printStackTrace();
            }
        }
    }
}
