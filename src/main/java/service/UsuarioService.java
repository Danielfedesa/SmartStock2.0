package service;

import java.util.List;

import model.Usuario;
import repository.UsuarioRepository;

/**
 * Servicio para gestionar las operaciones de la entidad Usuario.
 * 
 * Esta clase actua como intermediario entre el controlador y el repositorio,
 * aplicando reglas de negocio antes de interactuar con la base de datos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 1.0 03/2025
 */
public class UsuarioService {

	/**
	 * Constructor que inicializa el repositorio de usuarios.
	 */
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    /**
     * Crea un nuevo usuario con validaciones antes de guardarlo en la base de datos.
     * 
     * La contrasena sera cifrada antes de almacenarse en la base de datos.
     * 
     * @param usuario Objeto {@link Usuario} con los datos del usuario.
     * @throws IllegalArgumentException Si algun campo obligatorio esta vacio.
     */
    public void crearUsuario(Usuario usuario) {
        if (usuario.getNombreUsuario().isEmpty() || usuario.getApellido1().isEmpty() ||
            usuario.getEmail().isEmpty() || usuario.getContrasena().isEmpty()) {
            throw new IllegalArgumentException("Los campos obligatorios no pueden estar vacíos.");
        }

        // Verifica si la contraseña ya está cifrada para evitar doble cifrado
        if (!usuario.getContrasena().startsWith("$2a$")) {
            usuario.setContrasena(Usuario.encriptarContrasena(usuario.getContrasena()));
        }

        usuarioRepository.insertar(usuario);
    }


    /**
     * Obtiene una lista de todos los usuarios almacenados en la base de datos.
     * 
     * @return Lista de objetos {@link Usuario}.
     */
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.listar();
    }

    /**
     * Busca un usuario en la base de datos por su ID.
     * 
     * @param idUsuario ID del usuario a buscar.
     * @return Objeto {@link Usuario} si se encuentra, de lo contrario lanza una excepcion.
     * @throws IllegalArgumentException Si el usuario con el ID especificado no existe.
     */
    public Usuario obtenerUsuarioPorId(int idUsuario) {
        Usuario usuario = usuarioRepository.leerUsuario(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontro el usuario con ID: " + idUsuario);
        }
        return usuario;
    }

    /**
     * Actualiza los datos de un usuario en la base de datos con validaciones.
     * 
     * Si se proporciona una nueva contrasena, esta sera cifrada antes de almacenarse.
     * 
     * @param usuario Objeto {@link Usuario} con los datos actualizados.
     * @return {@code true} si la actualizacion fue exitosa, {@code false} en caso contrario.
     * @throws IllegalArgumentException Si el usuario con el ID especificado no existe.
     */
    public boolean actualizarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.leerUsuario(usuario.getIdUsuario());
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("El usuario con ID " + usuario.getIdUsuario() + " no existe.");
        }

        usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
        usuarioExistente.setApellido1(usuario.getApellido1());
        usuarioExistente.setApellido2(usuario.getApellido2());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setRol(usuario.getRol());

        // Si la contrasena fue cambiada, cifrarla antes de actualizarla
        if (!usuario.getContrasena().equals(usuarioExistente.getContrasena())) {
            usuarioExistente.setContrasena(Usuario.encriptarContrasena(usuario.getContrasena()));
        }

        return usuarioRepository.actualizar(usuarioExistente);
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     * 
     * @param idUsuario ID del usuario a eliminar.
     * @throws IllegalArgumentException Si el usuario con el ID especificado no existe.
     */
    public boolean eliminarUsuario(int idUsuario) {
        Usuario usuario = usuarioRepository.leerUsuario(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe.");
        }
        return usuarioRepository.eliminar(idUsuario);
    }

    /**
     * Verifica si las credenciales de un usuario son validas.
     * <p>
     * Compara la contrasena ingresada con la almacenada en la base de datos utilizando BCrypt.
     * </p>
     * 
     * @param email Correo electronico del usuario.
     * @param contrasena Contrasena en texto plano ingresada por el usuario.
     * @return {@code true} si las credenciales son correctas, {@code false} en caso contrario.
     */
    public boolean verificarCredenciales(String email, String contrasena) {
        Usuario usuario = usuarioRepository.leerUsuarioPorEmail(email);
        if (usuario != null) {
            return Usuario.verificarContrasena(contrasena, usuario.getContrasena());
        }
        return false;
    }
}