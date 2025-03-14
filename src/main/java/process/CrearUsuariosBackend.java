package process;

import model.Usuario;
import repository.UsuarioRepository;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase para la creación de usuarios en la base de datos.
 * 
 * <p>
 * Este script permite insertar multiples usuarios en la base de datos
 * utilizando Hibernate para realizar las pruebas de la aplicación. Se pueden
 * insertar tantos usuarios como se desee. Las contrasenas se encriptan
 * utilizando BCrypt antes de ser almacenadas.
 * </p>
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 02/2025
 */
public class CrearUsuariosBackend {

	/**
	 * Clase auxiliar para crear un conjunto de usuarios predefinidos en la base de
	 * datos.
	 * 
	 * <p>
	 * Las contrasenas de los usuarios son encriptadas antes de ser almacenadas.
	 * </p>
	 */
	public static void main(String[] args) {
		// Datos de los usuarios que se desean agregar
		String[][] usuariosData = { { "Brandon", "Sanderson", "", "brandonsan@smartstock.com", "1234", "empleado" },
				{ "Victoria", "Schwab", "", "victoriaschwab@smartstock.com", "1234", "empleado" },
				{ "Terry", "Pratchett", "", "terrypratch@smartstock.com", "1234", "empleado" },
				{ "Philip", "Pullman", "", "philippul@smartstock.com", "1234", "empleado" },
				{ "Arturo", "Pérez", "Reberte", "arturoperez@smartstock.com", "1234", "empleado" },
				{ "Cristina", "Martín", "Jimenez", "cristinamartin@smartstock.com", "1234", "empleado" },
				{ "Administrador", "Administrador", "", "admin@smartstock.com", "1234", "admin" } };

		// Crear la instancia del DAO
		UsuarioRepository usuarioRepository = new UsuarioRepository();

		// Iterar sobre los datos de los usuarios y agregar cada uno a la base de datos
		for (String[] usuarioData : usuariosData) {
			// Extraemos los datos
			String nombreUsuario = usuarioData[0];
			String apellido1 = usuarioData[1];
			String apellido2 = usuarioData[2];
			String email = usuarioData[3];
			String contrasena = usuarioData[4]; // La contraseña original
			String rol = usuarioData[5];

			// Hashear la contraseña
			String contrasenaHasheada = BCrypt.hashpw(contrasena, BCrypt.gensalt());

			// Crear el objeto Usuario
			Usuario nuevoUsuario = new Usuario(nombreUsuario, apellido1, apellido2, 123456789, email,
					contrasenaHasheada, rol);

			// Insertar el usuario en la base de datos
			try {
				usuarioRepository.insertar(nuevoUsuario);
				System.out.println("Usuario creado correctamente: " + email);
			} catch (Exception e) {
				System.err.println("Error al crear el usuario: " + email);
				e.printStackTrace();
			}
		}
	}
}
