package controller;

import java.util.List;

import model.Usuario;
import repository.UsuarioRepository;

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
	private final UsuarioRepository usuarioRepository;

	/**
     * Constructor que inicializa el repositorio de usuarios.
     */
	public UsuarioController() {
		this.usuarioRepository = new UsuarioRepository();
	}

	 /**
     * Obtiene una lista con todos los usuarios almacenados en la base de datos.
     *
     * @return Lista de objetos {@link Usuario} disponibles en el sistema.
     */
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.listar();
	}

	/**
     * Obtiene un usuario especifico segun su ID.
     *
     * @param idUsuario ID del usuario a buscar.
     * @return Objeto {@link Usuario} si se encuentra en la base de datos, null en caso contrario.
     */
	public Usuario obtenerUsuarioPorId(int idUsuario) {
		return usuarioRepository.leerUsuario(idUsuario);
	}
	
	/**
     * Crea un nuevo usuario y lo almacena en la base de datos.
     *
     * @param usuario Objeto {@link Usuario} con la informacion del usuario a registrar.
     */
	public void crearUsuario(Usuario usuario) {
		usuarioRepository.insertar(usuario);
	}

	/**
     * Actualiza la informacion de un usuario existente.
     *
     * @param usuario Objeto {@link Usuario} con los datos actualizados.
     * @return true si la actualizacion fue exitosa, false en caso contrario.
     */
	public boolean actualizarUsuario(Usuario usuario) {
		return usuarioRepository.actualizar(usuario);
	}

	/**
     * Elimina un usuario de la base de datos segun su ID.
     *
     * @param idUsuario ID del usuario a eliminar.
     */
	public void eliminarUsuario(int idUsuario) {
		usuarioRepository.eliminar(idUsuario);
	}
}
