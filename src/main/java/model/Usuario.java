package model;


import org.mindrot.jbcrypt.BCrypt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import model.Usuario;

/**
 * Clase Usuario que representa la informacion, constructores y metodos
 * referentes a los usuarios del sistema.
 * 
 * @author Daniel Fernandez Sanchez.
 * @version 2.0 02/2025
 */
@Entity
@Table(name = "Usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Id autoincremental en la base de datos
	@Column(name = "id_Usuario") // Mapea el campo 'idUsuario' a la columna 'id_Usuario' en la base de datos
	private int idUsuario;

	@Column(name = "nombre_Usuario", nullable = false)
	private String nombreUsuario;

	@Column(name = "apellido1", nullable = false)
	private String apellido1;

	@Column(name = "apellido2")
	private String apellido2;

	@Column(name = "telefono", nullable = false)
	private int telefono;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "contrasena", nullable = false)
	private String contrasena;

	@Column(name = "rol", nullable = false)
	private String Rol;

	/**
	 * Constructor por defecto (vacio).
	 */
	public Usuario() {
	}

	/**
	 * Constructor completo Usuario.
	 * 
	 * @param idUsuario     Identificador unico del usuario.
	 * @param nombreUsuario Nombre del usuario.
	 * @param apellido1     Primer apellido del usuario.
	 * @param apellido2     Segundo apellido del usuario.
	 * @param telefono      Telefono del usuario.
	 * @param email         Correo electronico unico del usuario.
	 * @param contrasena    Password del usuario.
	 * @param rol           Rol del usuario (admin o empleado).
	 */
	public Usuario(int idUsuario, String nombreUsuario, String apellido1, String apellido2, int telefono, String email,
			String contrasena, String rol) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.telefono = telefono;
		this.email = email;
		this.contrasena = contrasena;
		Rol = rol;
	}

	/**
	 * Constructor para crear un Usuario sin asignar el id.
	 * 
	 * @param nombreUsuario Nombre del usuario.
	 * @param apellido1     Primer apellido del usuario.
	 * @param apellido2     Segundo apellido del usuario.
	 * @param telefono      Telefono del usuario.
	 * @param email         Correo electronico unico del usuario.
	 * @param contrasena    Password del usuario.
	 * @param rol           Rol del usuario (admin o empleado).
	 */
	public Usuario(String nombreUsuario, String apellido1, String apellido2, int telefono, String email,
			String contrasena, String rol) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.telefono = telefono;
		this.email = email;
		this.contrasena = contrasena;
		Rol = rol;
	}

	/**
	 * Constructor para listar todos los usuarios sin la contrasena.
	 * 
	 * @param nombreUsuario Nombre del usuario.
	 * @param apellido1     Primer apellido del usuario.
	 * @param apellido2     Segundo apellido del usuario.
	 * @param telefono      Telefono del usuario.
	 * @param email         Correo electronico unico del usuario.
	 * @param rol           Rol del usuario (admin o empleado).
	 */
	public Usuario(int idUsuario, String nombreUsuario, String apellido1, String apellido2, int telefono, String email,
			String rol) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.telefono = telefono;
		this.email = email;
		Rol = rol;
	}

	/**
	 * Obtiene el identificador del usuario.
	 * 
	 * @return Identificador del usuario.
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario.
	 * 
	 * @param Identificador del usuario.
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el nombre del usuario.
	 * 
	 * @return Nombre del usuario.
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Establece el nombre del usuario.
	 * 
	 * @param Nombre del usuario.
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Obtiene el primer apellido del usuario.
	 * 
	 * @return Primer apellido del usuario.
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Establece el primer apellido del usuario.
	 * 
	 * @param Primer apellido del usuario.
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * Obtiene el segundo apellido del usuario.
	 * 
	 * @return Segundo apellido del usuario.
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * Establece el segundo apellido del usuario.
	 * 
	 * @param Segundo apellido del usuario.
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * Obtiene el numero de telefono del usuario.
	 * 
	 * @return Numero de telefono del usuario.
	 */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * Establece el numero de telefono del usuario.
	 * 
	 * @param Numero de telefono del usuario.
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtiene el correo electronico del usuario.
	 * 
	 * @return Correo electronico del usuario.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el correo electronico del usuario.
	 * 
	 * @param Correo electronico del usuario.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene la contrasena del usuario.
	 * 
	 * @return Contrasena del usuario.
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contrasena del usuario encriptandola antes de guardarla en
	 * memoria.
	 * 
	 * @param Contrasena del usuario.
	 */
	public void setContrasena(String contrasena) {
		// Encripta la contraseña
		this.contrasena = encriptarContrasena(contrasena);
	}

	/**
	 * Obtiene el rol del usuario.
	 * 
	 * @return del usuario.
	 */
	public String getRol() {
		return Rol;
	}

	/**
	 * Establece el rol del usuario.
	 * 
	 * @param Rol del usuario.
	 */
	public void setRol(String rol) {
		Rol = rol;
	}

	/**
	 * Devuelve una representacion en forma de cadena del objeto Usuario.
	 * 
	 * @return String Representacion en cadena del objeto Usuario.
	 */
	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", apellido1=" + apellido1
				+ ", apellido2=" + apellido2 + ", telefono=" + telefono + ", email=" + email + ", contrasena="
				+ contrasena + ", Rol=" + Rol + "]";
	}

	/**
     * Encripta una contrasena usando BCrypt.
     * 
     * @param contrasena Contraseña en texto plano.
     * @return Contrasena encriptada.
     */
	public static String encriptarContrasena(String contrasena) {
		return BCrypt.hashpw(contrasena, BCrypt.gensalt());
	}

	/**
     * Verifica si una contraseña coincide con su version encriptada.
     * 
     * @param contrasena      Contrasena en texto plano.
     * @param contrasenaHash  Contrasena encriptada almacenada.
     * @return true si la contrasena coincide, false en caso contrario.
     */
	public static boolean verificarContrasena(String contrasena, String contrasenaHash) {
	    return BCrypt.checkpw(contrasena, contrasenaHash); // Compara las contraseñas
	}

}
