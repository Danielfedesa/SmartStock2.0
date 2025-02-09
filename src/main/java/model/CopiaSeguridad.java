package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import DAO.DaoCopiaSeguridad;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase CopiaSeguridad que representa la informacion, constructores y metodos
 * referentes a las copias de seguridad del sistema.
 * 
 * @author Daniel Fernandez Sanchez.
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
	 * @return idBackup Identificador de la copia.
	 */
	public int getIdBackup() {
		return idBackup;
	}

	/**
	 * Establece el identificador de la copia.
	 * 
	 * @param idBackup Identificador de la copia.
	 */
	public void setIdBackup(int idBackup) {
		this.idBackup = idBackup;
	}

	/**
	 * Obtiene la fecha y hora de la realizacion de la copia.
	 * 
	 * @return fechaBackup Fecha y hora de realizacion de la copia.
	 */
	public Timestamp getFechaBackup() {
		return fechaBackup;
	}

	/**
	 * Establece la fecha y hora de la realizacion de la copia.
	 * 
	 * @param fechaBackup Fecha y hora de realizacion de la copia.
	 */
	public void setFechaBackup(Timestamp fechaBackup) {
		this.fechaBackup = fechaBackup;
	}

	/**
	 * Obtiene la ruta de almacenamiento del archivo.
	 * 
	 * @return rutaArchivo Ruta de almacenamiento del archivo.
	 */
	public String getRutaArchivo() {
		return rutaArchivo;
	}

	/**
	 * Establece la ruta de almacenamiento del archivo.
	 * 
	 * @param rutaArchivo Ruta de almacenamiento del archivo.
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

	/**
	 * Metodo para generar un archivo de copia de seguridad de la base de datos
	 * utilizando la herramienta 'mysqldump'. La copia se guarda en una ubicación
	 * especifica, cuyo nombre incluye la fecha y la hora actual.
	 * 
	 * Si el proceso se ejecuta correctamente, también se registra la ruta del
	 * archivo de copia de seguridad en la base de datos.
	 * 
	 * @throws SQLException Si ocurre un error al ejecutar el comando para crear la
	 *                      copia de seguridad o al registrar la ruta en la base de
	 *                      datos.
	 */
	public void realizarBackup() throws SQLException {

		// Formatear la fecha y hora actual para incluirla en el nombre del archivo
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy_HH.mm");
		String fechaHora = formatter.format(new Date());

		// Ruta y nombre
		String rutaBackup = "C:\\Users\\Daniel\\Desktop\\SmartStock\\SmartStock_Backups\\SmartStock_" + fechaHora
				+ ".sql";
		String comando = "cmd /c \"C:\\xampp\\mysql\\bin\\mysqldump.exe -u Daniel -pDaniel1234 smartstockdb > "
				+ rutaBackup + "\"";

		try {
			// Ejecuta el comando para realizar la copia
			Process proceso = Runtime.getRuntime().exec(new String[] { "cmd", "/c", comando });

			// Capturar errores del comando
			InputStream errorStream = proceso.getErrorStream();
			InputStreamReader isr = new InputStreamReader(errorStream);
			BufferedReader br = new BufferedReader(isr);
			String linea;
			while ((linea = br.readLine()) != null) {
				System.err.println("Error mysqldump: " + linea);
			}

			int resultado = proceso.waitFor(); // Espera a que termine el proceso

			if (resultado == 0) {
				System.out.println("Copia de seguridad creada correctamente en la ruta: " + rutaBackup);

				// Registra la copia de seguridad en la base de datos
				DaoCopiaSeguridad daoCopia = new DaoCopiaSeguridad();
				daoCopia.registrarBackup(rutaBackup);
			} else {
				System.err.println("Error al ejecutar el comando mysqldump. Código de salida: " + resultado);
			}
		} catch (Exception e) {
			throw new SQLException("Error al realizar la copia de seguridad: " + e.getMessage());
		}
	}

	/**
	 * Metodo para listar mediante un objeto dao todos los registros de copias de
	 * seguridad almacenados en la base de datos.
	 * 
	 * @return Lista de objetos CopiaSeguridad que representa todos los registros
	 *         almacenados en la base de datos.
	 * @throws SQLException
	 */
	public List<CopiaSeguridad> listarCopias() throws SQLException {
		DaoCopiaSeguridad daoCopia = new DaoCopiaSeguridad();

		return daoCopia.listar();
	}

} // Class
