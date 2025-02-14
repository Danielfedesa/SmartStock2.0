package model;

import java.time.LocalDateTime;
import java.util.List;

import DAO.DaoChat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa un mensaje de chat en el sistema.
 * 
 * <p>
 * Esta clase está mapeada a la tabla 'Mensajes' en la base de datos mediante Hibernate.
 * Utiliza las siguientes anotaciones:
 * </p>
 * <ul>
 *   <li>{@literal @Entity} indica que la clase es una entidad de la base de datos.</li>
 *   <li>{@literal @Table(name = "Mensajes")} define el nombre de la tabla en la base de datos.</li>
 *   <li>{@literal @Id} especifica la clave primaria.</li>
 *   <li>{@literal @GeneratedValue(strategy = GenerationType.IDENTITY)} define el campo como autoincremental.</li>
 *   <li>{@literal @Column} personaliza los atributos en la base de datos.</li>
 * </ul>
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
@Entity
@Table(name = "Mensajes")
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "contenido", nullable = false)
	private String contenido;

	@Column(name = "usuario", nullable = false)
	private String usuario;

	@Column(name = "fecha", nullable = false)
	private LocalDateTime fecha;

	/**
	 * Constructor por defecto.
	 */
	public Chat() {
	}

	/**
	 * Constructor completo de Chat.
	 * 
	 * @param id        Identificador único del mensaje.
	 * @param contenido Contenido del mensaje.
	 * @param usuario   Usuario que envió el mensaje.
	 * @param fecha     Fecha y hora del mensaje.
	 */
	public Chat(int id, String contenido, String usuario, LocalDateTime fecha) {
		this.id = id;
		this.contenido = contenido;
		this.usuario = usuario;
		this.fecha = fecha;
	}

	/**
	 * Constructor para crear un mensaje sin ID (autogenerado en la base de datos).
	 * 
	 * @param contenido Contenido del mensaje.
	 * @param usuario   Usuario que envió el mensaje.
	 * @param fecha     Fecha y hora del mensaje.
	 */
	public Chat(String contenido, String usuario, LocalDateTime fecha) {
		this.contenido = contenido;
		this.usuario = usuario;
		this.fecha = fecha;
	}

	/**
	 * Obtiene el ID del mensaje.
	 * 
	 * @return El identificador único del mensaje.
	 */
	public int getId() {
		return id;
	}

	/**
     * Establece el ID del mensaje.
     * 
     * @param id Identificador único del mensaje.
     */
	public void setId(int id) {
		this.id = id;
	}

	/**
     * Obtiene el contenido del mensaje.
     * 
     * @return El contenido del mensaje.
     */
	public String getContenido() {
		return contenido;
	}

	/**
     * Establece el contenido del mensaje.
     * 
     * @param contenido Contenido del mensaje.
     */
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

    /**
     * Obtiene el usuario que envio el mensaje.
     * 
     * @return El usuario que envio el mensaje.
     */
	public String getUsuario() {
		return usuario;
	}

	/**
     * Establece el usuario que envio el mensaje.
     * 
     * @param usuario Usuario que envio el mensaje.
     */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
     * Obtiene la fecha y hora del mensaje.
     * 
     * @return La fecha y hora del mensaje.
     */
	public LocalDateTime getFecha() {
		return fecha;
	}

	 /**
     * Establece la fecha y hora del mensaje.
     * 
     * @param fecha Fecha y hora del mensaje.
     */
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	/**
     * Registra un nuevo mensaje en la base de datos utilizando Hibernate.
     */
	public void insertarMensaje() {
		DaoChat daoChat = new DaoChat();
		daoChat.insertar(this);
    }
	
	/**
     * Obtiene todos los mensajes de chat almacenados en la base de datos utilizando
     * Hibernate.
     * 
     * @return Lista de mensajes registrados.
     */
	public List<Chat> listarMensajes() {
		DaoChat daoChat = new DaoChat();
        return daoChat.listar();
    }

}
