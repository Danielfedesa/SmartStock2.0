package process;

import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import DAO.DaoUsuario;

public class CrearUsuariosBackend {

    public static void main(String[] args) {
        // Datos de los usuarios que se desean agregar
        String[][] usuariosData = {
            {"Brandon", "Sanderson", "", "brandonsan@smartstock.com", "1234", "empleado"},
            {"Victoria", "Schwab", "", "victoriaschwab@smartstock.com", "1234", "empleado"},
            {"Terry", "Pratchett", "", "terrypratch@smartstock.com", "1234", "empleado"},
            {"Philip", "Pullman", "", "philippul@smartstock.com", "1234", "empleado"},
            {"Arturo", "Pérez", "Reberte", "arturoperez@smartstock.com", "1234", "empleado"},
            {"Cristina", "Martín", "Jimenez", "cristinamartin@smartstock.com", "1234", "empleado"},
            {"Administrador", "Administrador", "", "admin@smartstock.com", "1234", "admin"}
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
