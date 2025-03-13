package controller;

import java.util.List;

import DAO.DaoUsuario;
import model.Usuario;

public class UsuarioController {
	private final DaoUsuario daoUsuario;

	public UsuarioController() {
		this.daoUsuario = new DaoUsuario();
	}

	/** Obtener todos los usuarios */
	public List<Usuario> listarUsuarios() {
		return daoUsuario.listar();
	}

	/** Obtener usuario por ID */
	public Usuario obtenerUsuarioPorId(int idUsuario) {
		return daoUsuario.leerUsuario(idUsuario);
	}

	/** Crear un nuevo usuario */
	public void crearUsuario(Usuario usuario) {
		daoUsuario.insertar(usuario);
	}

	/** Actualizar un usuario */
	public boolean actualizarUsuario(Usuario usuario) {
		return daoUsuario.actualizar(usuario);
	}

	/** Eliminar un usuario */
	public void eliminarUsuario(int idUsuario) {
		daoUsuario.eliminar(idUsuario);
	}
}
