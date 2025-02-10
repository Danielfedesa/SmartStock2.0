package process;

import model.CopiaSeguridad;

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
 * @version 2.0 02/2025
 */
public class BackupAutomatico implements Runnable {

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
		// TODO Auto-generated method stub

		while (true) {
			try {
				CopiaSeguridad copia = new CopiaSeguridad();

				copia.realizarBackup();

				Thread.sleep(60000); // 86400000 Espera 24 horas / 43200000 Espera 12 horas / 21600000 espera 6 horas

			} catch (Exception e) {
				System.out.println("Error al crear la copia de seguridad");
			}
		}

	} // Fin proceso

} // Class
