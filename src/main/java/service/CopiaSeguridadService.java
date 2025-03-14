package service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.CopiaSeguridad;
import repository.CopiaSeguridadRepository;

public class CopiaSeguridadService {
	
	private CopiaSeguridadRepository copiaSeguridadRepository;
	
	public CopiaSeguridadService() {
		this.copiaSeguridadRepository = new CopiaSeguridadRepository();
	}
	
	/**
	 * Genera un archivo de copia de seguridad de la base de datos utilizando la
	 * herramienta 'mysqldump'.
	 * <p>
	 * La copia se guarda en una ubicación especifica con un nombre basado en la
	 * fecha y hora actual. Si la copia se genera correctamente, se registra en la
	 * base de datos utilizando Hibernate.
	 * </p>
	 * 
	 */
	public void realizarBackup() {
        try {
            // Formatear la fecha para el nombre del archivo
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy_HH.mm");
            String fechaHora = formatter.format(new Date());

            // Ruta donde se guardará el backup
            String rutaBackup = "C:\\Users\\Daniel\\Desktop\\SmartStock 2.0\\SmartStock2.0_Backups\\SmartStock_" + fechaHora + ".sql";

            // Comando para ejecutar mysqldump
            String comando = "C:\\xampp\\mysql\\bin\\mysqldump.exe";
            String usuario = "Daniel";
            String contrasena = "Daniel1234";
            String baseDeDatos = "smartstockdb";

            // Comprobar si el archivo de mysqldump existe
            if (!new java.io.File(comando).exists()) {
                throw new IOException("No se encontró mysqldump en la ruta especificada: " + comando);
            }

            // Construir el proceso
            ProcessBuilder processBuilder = new ProcessBuilder(comando, "-u", usuario, "-p" + contrasena, baseDeDatos, "--result-file=" + rutaBackup);
            processBuilder.redirectErrorStream(true);

            // Iniciar el proceso
            Process proceso = processBuilder.start();
            int resultado = proceso.waitFor();

            // Verificar el resultado del proceso
            if (resultado == 0) {
                System.out.println("Copia de seguridad creada correctamente en: " + rutaBackup);

                // Crear la instancia de la copia de seguridad
                CopiaSeguridad copia = new CopiaSeguridad();
                copia.setFechaBackup(new Timestamp(System.currentTimeMillis())); 
                copia.setRutaArchivo(rutaBackup);

                // Guardar en la base de datos
                copiaSeguridadRepository.insertar(copia);
            } else {
                throw new IOException("Error al ejecutar mysqldump. Código de salida: " + resultado);
            }
        } catch (IOException e) {
            System.err.println("Error al acceder al archivo de mysqldump: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Proceso interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restaurar el estado interrumpido del hilo
        } catch (Exception e) {
            System.err.println("Error inesperado al realizar la copia de seguridad: " + e.getMessage());
        }
    }

	/**
	 * Metodo para listar mediante un objeto dao todos los registros de copias de
	 * seguridad almacenados en la base de datos utilizando Hibernate.
	 * 
	 * @return Lista de objetos CopiaSeguridad que representa todos los registros
	 *         almacenados en la base de datos.
	 */
	public List<CopiaSeguridad> listarCopias() {
        return copiaSeguridadRepository.listar();
    }
	
}
