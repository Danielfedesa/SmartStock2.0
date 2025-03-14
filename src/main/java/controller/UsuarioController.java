package controller;

import java.util.List;

import model.Usuario;
import repository.UsuarioRepository;
import service.UsuarioService;

/**
 * Controlador para gestionar las operaciones relacionadas con los usuarios.
 * 
 * Actua como intermediario entre la vista y la capa de repositorio,
 * delegando la gestion de usuarios a {@link UsuarioRepository}.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 03/2025
 */
public class UsuarioController {
	
	private UsuarioService usuarioService = new UsuarioService();


	 /**
     * Obtiene una lista con todos los usuarios almacenados en la base de datos.
     *
     * @return Lista de usuarios disponibles en el sistema.
     */
	public List<Usuario> listarUsuarios() {
		return usuarioService.obtenerTodosUsuarios();
	}

	/**
     * Obtiene un usuario especifico segun su ID.
     *
     * @param idUsuario ID del usuario a buscar.
     * @return Objeto {@link Usuario} si se encuentra en la base de datos, null en caso contrario.
     */
	public Usuario obtenerUsuarioPorId(int idUsuario) {
		return usuarioService.obtenerUsuarioPorId(idUsuario);
	}
	
	/**
     * Crea un nuevo usuario y lo almacena en la base de datos.
     *
     * @param usuario Objeto {@link Usuario} con la informacion del usuario a registrar.
     */
	public void crearUsuario(Usuario usuario) {
        usuarioService.crearUsuario(usuario);
    }

	/**
     * Actualiza la informacion de un usuario existente.
     *
     * @param usuario Objeto {@link Usuario} con los datos actualizados.
     * @return true si la actualizacion fue exitosa, false en caso contrario.
     */
	public boolean actualizarUsuario(Usuario usuario) {
        return usuarioService.actualizarUsuario(usuario);
    }

	/**
     * Elimina un usuario de la base de datos segun su ID.
     *
     * @param idUsuario ID del usuario a eliminar.
     *  @return true si la eliminacion fue exitosa, false en caso de error.
     */
	public boolean eliminarUsuario(int idUsuario) {
        return usuarioService.eliminarUsuario(idUsuario);
    }
}
