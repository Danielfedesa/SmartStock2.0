package controller;

import model.Usuario;

/**
 * Clase que gestiona la sesion del usuario actual en la aplicacion. Mantiene la
 * información del usuario que ha iniciado sesion y permite acceder a ella
 * durante la ejecucion del programa. Esta clase es estatica y no instancia un
 * objeto, sino que utiliza un unico usuario global para toda la sesion.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class UsuarioSesion {

	// Variable estatica que almacena el usuario actualmente en sesion.
	private static Usuario usuarioActual;

	/**
	 * Establece el usuario que esta actualmente en sesion.
	 * 
	 * @param usuario El objeto Usuario que representa al usuario que ha iniciado
	 *                sesion.
	 */
	public static void setUsuarioActual(Usuario usuario) {
		usuarioActual = usuario;
	}

	/**
	 * Obtiene el usuario actualmente en sesion.
	 * 
	 * @return El objeto Usuario que representa al usuario en sesion, o null si no
	 *         hay ningun usuario.
	 */
	public static Usuario getUsuarioActual() {
		return usuarioActual;
	}

	/**
	 * Obtiene el ID del usuario actualmente en sesion. Si no hay un usuario en
	 * sesión, devuelve -1.
	 * 
	 * @return El ID del usuario actual, o -1 si no hay usuario en sesion.
	 */
	public static int getIdUsuarioActual() {
		return usuarioActual != null ? usuarioActual.getIdUsuario() : -1;
	}

	/**
	 * Obtiene el rol del usuario actualmente en sesion. Si no hay un usuario en
	 * sesion, devuelve null.
	 * 
	 * @return El rol del usuario actual, o null si no hay usuario en sesión.
	 */
	public static String getRolUsuarioActual() {
		return usuarioActual != null ? usuarioActual.getRol() : null;
	}

	/**
	 * Limpia la sesion del usuario, eliminando el usuario actualmente en sesion.
	 * Este metodo se utiliza al cerrar la sesion.
	 */
	public static void cerrarSesion() {
		usuarioActual = null;
	}

} // Class