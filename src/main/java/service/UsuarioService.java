package service;

import java.util.List;

import model.Usuario;
import repository.UsuarioRepository;

public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    /** Constructor */
    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    /** Crear un nuevo usuario con validaciones */
    public void crearUsuario(String nombreUsuario, String apellido1, String apellido2, int telefono, String email, String contrasena, String rol) {
        if (nombreUsuario.isEmpty() || apellido1.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
            throw new IllegalArgumentException("Los campos obligatorios no pueden estar vacíos.");
        }
        Usuario nuevoUsuario = new Usuario(nombreUsuario, apellido1, apellido2, telefono, email, contrasena, rol);
        usuarioRepository.insertar(nuevoUsuario);
    }

    /** Listar todos los usuarios */
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listar();
    }

    /** Obtener un usuario por ID */
    public Usuario obtenerUsuarioPorId(int idUsuario) {
        Usuario usuario = usuarioRepository.leerUsuario(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró el usuario con ID: " + idUsuario);
        }
        return usuario;
    }

    /** Actualizar usuario con validaciones */
    public boolean actualizarUsuario(int idUsuario, String nombreUsuario, String apellido1, String apellido2, int telefono, String email, String rol) {
        Usuario usuarioExistente = usuarioRepository.leerUsuario(idUsuario);
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe.");
        }

        usuarioExistente.setNombreUsuario(nombreUsuario);
        usuarioExistente.setApellido1(apellido1);
        usuarioExistente.setApellido2(apellido2);
        usuarioExistente.setTelefono(telefono);
        usuarioExistente.setEmail(email);
        usuarioExistente.setRol(rol);

        return usuarioRepository.actualizar(usuarioExistente);
    }

    /** Eliminar un usuario */
    public void eliminarUsuario(int idUsuario) {
        Usuario usuario = usuarioRepository.leerUsuario(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe.");
        }
        usuarioRepository.eliminar(idUsuario);
    }

    /** Verificar login */
    public boolean verificarCredenciales(String email, String contrasena) {
        Usuario usuario = usuarioRepository.leerUsuarioPorEmail(email);
        if (usuario != null) {
            return Usuario.verificarContrasena(contrasena, usuario.getContrasena());
        }
        return false;
    }
}