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
    
    private final UsuarioRepository usuarioRepository;

	/**
	 * Constructor que inicializa el repositorio de usuarios.
	 */
    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    /**
     * Crea un nuevo usuario con validaciones antes de guardarlo en la base de datos.
     * 
     * @param nombreUsuario Nombre del usuario.
     * @param apellido1 Primer apellido del usuario.
     * @param apellido2 Segundo apellido del usuario (opcional).
     * @param telefono Numero de telefono del usuario.
     * @param email Correo electronico unico del usuario.
     * @param contrasena Contrasena en texto plano (se cifrara).
     * @param rol Rol del usuario (admin o empleado).
     * @throws IllegalArgumentException Si algun campo obligatorio esta vacio.
     */
    public void crearUsuario(String nombreUsuario, String apellido1, String apellido2, int telefono, String email, String contrasena, String rol) {
        if (nombreUsuario.isEmpty() || apellido1.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
            throw new IllegalArgumentException("Los campos obligatorios no pueden estar vacíos.");
        }
        Usuario nuevoUsuario = new Usuario(nombreUsuario, apellido1, apellido2, telefono, email, contrasena, rol);
        usuarioRepository.insertar(nuevoUsuario);
    }

    /**
     * Obtiene una lista de todos los usuarios almacenados en la base de datos.
     * 
     * @return Lista de objetos {@link Usuario}.
     */
    public List<Usuario> listarUsuarios() {
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
            throw new IllegalArgumentException("No se encontró el usuario con ID: " + idUsuario);
        }
        return usuario;
    }

    /**
     * Actualiza los datos de un usuario en la base de datos con validaciones.
     * 
     * @param idUsuario ID del usuario a actualizar.
     * @param nombreUsuario Nuevo nombre del usuario.
     * @param apellido1 Nuevo primer apellido del usuario.
     * @param apellido2 Nuevo segundo apellido del usuario (opcional).
     * @param telefono Nuevo numero de telefono del usuario.
     * @param email Nuevo correo electronico del usuario.
     * @param rol Nuevo rol del usuario (admin o empleado).
     * @return {@code true} si la actualizacion fue exitosa, {@code false} en caso contrario.
     * @throws IllegalArgumentException Si el usuario con el ID especificado no existe.
     */
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

    /**
     * Elimina un usuario de la base de datos por su ID.
     * 
     * @param idUsuario ID del usuario a eliminar.
     * @throws IllegalArgumentException Si el usuario con el ID especificado no existe.
     */
    public void eliminarUsuario(int idUsuario) {
        Usuario usuario = usuarioRepository.leerUsuario(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario con ID " + idUsuario + " no existe.");
        }
        usuarioRepository.eliminar(idUsuario);
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