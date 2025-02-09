package controller;

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
     * la clase y gestionar la validación.
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
	
	/**
	 * Metodo para iniciar sesión en el sistema.
     * Valida las credenciales proporcionadas (correo electronico y contrasena)
     * y devuelve el objeto Usuario con el Rol si son correctas.
     * En caso contrario, devuelve {@code null}.
     * 
	 * @param email El correo electronico del usuario que intenta iniciar sesion.
	 * @param contrasena La contrasena del usuario que intenta iniciar sesion.
	 * @return Un objeto {@link Usuario} si las credenciales son validas, o {@code null} si no lo son.
	 */
	public Usuario iniciarSesion(String email, String contrasena) {
        try {
        	// Validar las credenciales con el DAO
            return usuarioDao.validarCredenciales(email, contrasena);
        } catch (Exception e) {
            System.err.println("Error al validar las credenciales: " + e.getMessage());
            return null;
        }
    }
	
} // Class
