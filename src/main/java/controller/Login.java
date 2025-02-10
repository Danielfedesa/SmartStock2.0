package controller;

import org.mindrot.jbcrypt.BCrypt;

import DAO.DaoUsuario;
import model.Usuario;

/**
 * Clase para realizar operaciones de inicio de sesion 
 * con la tabla 'usuarios' en la base de datos.
 * Utiliza la calse DaoUsuario para validar los datos.
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class Login {

	/**
     * DAO de Usuario para interactuar con la base de datos.
     */
	private DaoUsuario usuarioDao;
	
	/**
     * Constructor de la clase Login.
     * Se requiere un DAO de Usuario para inicializar
     * la clase y gestionar la validacion.
     * @param usuarioDao Objeto de tipo DaoUsuario
     * que permite interactuar con la tabla de usuarios.
     */
	public Login(DaoUsuario usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
    /**
     * Constructor vacio.
     */
    public Login() {
            // Inicializa el DAO internamente
            this.usuarioDao = new DaoUsuario();
        }
	
    public Usuario iniciarSesion(String email, String contrasena) {
        try {
            // Buscar el usuario por su email
            Usuario usuario = usuarioDao.leerUsuarioPorEmail(email);

            if (usuario != null) {
                // Mostrar por consola la contraseña introducida y la almacenada
                System.out.println("Contraseña introducida: " + contrasena);
                System.out.println("Contraseña almacenada: " + usuario.getContrasena());

                // Verificar la contraseña utilizando BCrypt
                boolean contrasenaValida = BCrypt.checkpw(contrasena, usuario.getContrasena());
                if (contrasenaValida) {
                    return usuario;  // Si las contraseñas coinciden, devuelve el usuario
                } else {
                    System.out.println("Contraseñas no coinciden");
                }
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (Exception e) {
            System.err.println("Error al validar las credenciales: " + e.getMessage());
        }
        return null;  // Si las credenciales no son válidas, devolver null
    }
	
} // Class
