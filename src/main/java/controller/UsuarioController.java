package controller;

import java.util.List;

import model.Usuario;
import repository.UsuarioRepository;

public class UsuarioController {
	private final UsuarioRepository usuarioRepository;

	public UsuarioController() {
		this.usuarioRepository = new UsuarioRepository();
	}

	/** Obtener todos los usuarios */
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.listar();
	}

	/** Obtener usuario por ID */
	public Usuario obtenerUsuarioPorId(int idUsuario) {
		return usuarioRepository.leerUsuario(idUsuario);
	}

	/** Crear un nuevo usuario */
	public void crearUsuario(Usuario usuario) {
		usuarioRepository.insertar(usuario);
	}

	/** Actualizar un usuario */
	public boolean actualizarUsuario(Usuario usuario) {
		return usuarioRepository.actualizar(usuario);
	}

	/** Eliminar un usuario */
	public void eliminarUsuario(int idUsuario) {
		usuarioRepository.eliminar(idUsuario);
	}
}
