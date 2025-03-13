package model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa una copia de seguridad del sistema.
 * 
 * <p>
 * Esta clase está mapeada a la tabla 'CopiasSeguridad' en la base de datos
 * mediante Hibernate. Se encarga de almacenar información sobre las copias de
 * seguridad, incluyendo la fecha de creación y la ruta del archivo.
 * </p>
 * 
 * <ul>
 * <li>{@literal @Entity} indica que la clase es una entidad de la base de datos.</li>
 * <li>{@literal @Table(name = "CopiasSeguridad")} define el nombre de la tabla en la base de datos.</li>
 * <li>{@literal @Id} especifica la clave primaria.</li>
 * <li>{@literal @GeneratedValue(strategy = GenerationType.IDENTITY)} define el campo como autoincremental.</li>
 * <li>{@literal @Column} personaliza los atributos en la base de datos.</li>
 * </ul>
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
@Entity
@Table(name = "CopiasSeguridad")
public class CopiaSeguridad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Backup")
	private int idBackup;

	@Column(name = "fecha_Backup", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp fechaBackup;

	@Column(name = "ruta_Archivo", nullable = false)
	private String rutaArchivo;

	/**
	 * Constructor por defecto (vacio).
	 */
	public CopiaSeguridad() {
	}

	/**
	 * Constructor completo CopiaSeguridad.
	 * 
	 * @param idBackup    Identificador unico de la copia de seguridad.
	 * @param fechaBackup Fecha y hora de realizacion de la copia.
	 * @param rutaArchivo Ruta donde se almacena el archivo.
	 */
	public CopiaSeguridad(int idBackup, Timestamp fechaBackup, String rutaArchivo) {
		super();
		this.idBackup = idBackup;
		this.fechaBackup = fechaBackup;
		this.rutaArchivo = rutaArchivo;
	}

	/**
	 * Obtiene el identificador de la copia.
	 * 
	 * @return Identificador de la copia.
	 */
	public int getIdBackup() {
		return idBackup;
	}

	/**
	 * Establece el identificador de la copia.
	 * 
	 * @param Identificador de la copia.
	 */
	public void setIdBackup(int idBackup) {
		this.idBackup = idBackup;
	}

	/**
	 * Obtiene la fecha y hora de la realizacion de la copia.
	 * 
	 * @return Fecha y hora de realizacion de la copia.
	 */
	public Timestamp getFechaBackup() {
		return fechaBackup;
	}

	/**
	 * Establece la fecha y hora de la realizacion de la copia.
	 * 
	 * @param Fecha y hora de realizacion de la copia.
	 */
	public void setFechaBackup(Timestamp fechaBackup) {
		this.fechaBackup = fechaBackup;
	}

	/**
	 * Obtiene la ruta de almacenamiento del archivo.
	 * 
	 * @return Ruta de almacenamiento del archivo.
	 */
	public String getRutaArchivo() {
		return rutaArchivo;
	}

	/**
	 * Establece la ruta de almacenamiento del archivo.
	 * 
	 * @param Ruta de almacenamiento del archivo.
	 */
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	/**
	 * Devuelve una representacion en forma de cadena del objeto CopiaSeguridad.
	 * 
	 * @return String Representacion en cadena del objeto CopiaSeguridad.
	 */
	@Override
	public String toString() {
		return "CopiaSeguridad [idBackup=" + idBackup + ", fechaBackup=" + fechaBackup + ", rutaArchivo=" + rutaArchivo
				+ "]";
	}


} // Class
