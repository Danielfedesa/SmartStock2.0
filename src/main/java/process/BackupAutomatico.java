package process;

import model.CopiaSeguridad;
import service.CopiaSeguridadService;

/**
 * Clase que implementa un proceso para realizar copias de seguridad automaticas
 * de la base de datos.
 * 
 * Implementa la interfaz 'Runnable' y ejecuta un hilo que, de forma continua,
 * realiza copias de seguridad de la base de datos utilizando la clase
 * CopiaSeguridad.
 * 
 * @see CopiaSeguridad
 * @author Daniel Fernandez Sanchez.
 * @version 3.0 03/2025
 */
public class BackupAutomatico implements Runnable {

	 private final CopiaSeguridadService copiaSeguridadService;
	 
		public BackupAutomatico() {
			this.copiaSeguridadService = new CopiaSeguridadService();
		}
		
	/**
	 * Metodo que ejecuta el proceso de copias de seguridad automaticas en un hilo.
	 * Este metodo realiza una copia de seguridad de la base de datos cada cierto
	 * intervalo de tiempo. El intervalo esta definido por el valor de
	 * Thread.sleep().
	 * 
	 * En caso de que ocurra un error durante el proceso de la copia de seguridad,
	 * se captura la excepcion y se imprime un mensaje de error.
	 */
	@Override
	public void run() {
        while (true) {
            try {
                // Ejecutar la copia de seguridad
                copiaSeguridadService.realizarBackup();

                // Intervalo de espera entre copias de seguridad
                Thread.sleep(60000); // 86400000 = 24 horas / 43200000 = 12 horas / 21600000 = 6 horas

            } catch (Exception e) {
                System.err.println("Error al crear la copia de seguridad automática: " + e.getMessage());
            }
        }
    }
}
