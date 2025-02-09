package model;

import java.util.List;
import DAO.DaoUsuario;
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
	 * @return idUsuario Identificador del usuario.
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario.
	 * 
	 * @param idUsuario Identificador del usuario.
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el nombre del usuario.
	 * 
	 * @return nombreUsuario Nombre del usuario.
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Establece el nombre del usuario.
	 * 
	 * @param nombreUsuario Nombre del usuario.
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Obtiene el primer apellido del usuario.
	 * 
	 * @return apellido1 Primer apellido del usuario.
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Establece el primer apellido del usuario.
	 * 
	 * @param apellido1 Primer apellido del usuario.
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * Obtiene el segundo apellido del usuario.
	 * 
	 * @return apellido2 Segundo apellido del usuario.
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * Establece el segundo apellido del usuario.
	 * 
	 * @param apellido2 Segundo apellido del usuario.
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * Obtiene el numero de telefono del usuario.
	 * 
	 * @return telefono Numero de telefono del usuario.
	 */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * Establece el numero de telefono del usuario.
	 * 
	 * @param telefono Numero de telefono del usuario.
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtiene el correo electronico del usuario.
	 * 
	 * @return email Correo electronico del usuario.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el correo electronico del usuario.
	 * 
	 * @param email Correo electronico del usuario.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene la contrasena del usuario.
	 * 
	 * @return contrasena Contrasena del usuario.
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contrasena del usuario.
	 * 
	 * @param contrasena Contrasena del usuario.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Obtiene el rol del usuario.
	 * 
	 * @return Rol del usuario.
	 */
	public String getRol() {
		return Rol;
	}

	/**
	 * Establece el rol del usuario.
	 * 
	 * @param rol Rol del usuario.
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

	/*
	 * Metodo para crear un nuevo usuario en la base de datos.
	 */
	public void crearUsuario() {
		DaoUsuario daoUsuario = new DaoUsuario();
		daoUsuario.insertar(this);
	}

	/**
	 * Metodo para listar todos los usuarios de la base de datos.
	 * 
	 * @return Lista de usuarios.
	 */
	public List<Usuario> listarUsuarios() {
		DaoUsuario daoUsuario = new DaoUsuario();
		return daoUsuario.listar();
	}

	/**
	 * Metodo para recuperar un usuario por su ID y cargar sus datos.
	 * 
	 * @param idUsuario El ID del usuario.
	 * @return El usuario recuperado.
	 */
	public Usuario recuperarUsu(int idUsuario) {
		DaoUsuario daoUsuario = new DaoUsuario();
		return daoUsuario.leerUsuario(idUsuario);
	}

	/**
	 * Metodo para actualizar un usuario.
	 * 
	 * @return true si la actualizacion fue exitosa, false en caso contrario.
	 */
	public boolean actualizarUsuario() {
		DaoUsuario daoUsuario = new DaoUsuario();
		return daoUsuario.actualizar(this);
	}

	/**
	 * Metodo para eliminar un usuario de la base de datos.
	 * 
	 * @param idUsuario El ID del usuario a eliminar.
	 */
	public void eliminarUsuario(int idUsuario) {
		DaoUsuario daoUsuario = new DaoUsuario();
		daoUsuario.eliminar(idUsuario);
	}

}
