package com.danielfedesa.smartstockv2.SmartStockv2;

import javax.swing.SwingUtilities;

import chatservice.Server;
import process.BackupAutomatico;
import controller.Login;
import view.ScreenLogin;
import process.SupervisorStock;

/**
 * Clase principal de la aplicacion SmartStock. Se encarga de inicializar la
 * interfaz grafica, ejecutar procesos en segundo plano como la copia de
 * seguridad automatica, la supervision del stock y el servidor de chat.
 * 
 * @autor Daniel Fernandez Sanchez
 * @version 1.0 02/2025
 */
public class App {

	/**
	 * Metodo principal que inicia la aplicacion.
	 *
	 * @param args Argumentos de linea de comandos (no utilizados).
	 */
	public static void main(String[] args) {
		// Ejecuta la creación de la UI en el hilo de eventos de Swing
		SwingUtilities.invokeLater(() -> {
			try {
				// Crea el modelo Login
				Login loginControlador = new Login();

				// Crea y muestra la pantalla de inicio de sesión
				new ScreenLogin(loginControlador).setVisible(true);
			} catch (Exception e) {
				System.err.println("Error al inicializar la aplicación: " + e.getMessage());
				e.printStackTrace();
			}
		});

		// Crear y ejecutar el hilo de copias automáticas
		Thread backupThread = new Thread(new BackupAutomatico());
		backupThread.start();
		System.out.println("Hilo de copia de seguridad INICIADO");

		// Crear y ejecutar el hilo de supervisión de stock
		Thread superThread = new Thread(new SupervisorStock());
		superThread.start();
		System.out.println("Hilo de supervisión de stock bajo INICIADO");

		// Crear y ejecutar el servidor de chat en un hilo independiente
		Thread chatServerThread = new Thread(() -> {
			try {
				Server.main(new String[] {});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		chatServerThread.start(); // Iniciar el servidor en un hilo independiente
		System.out.println("Hilo de servidor de chat INICIADO");

		/*
		 * // Esperar que los hilos terminen antes de cerrar la aplicación try { //
		 * Espera a que los hilos de copia de seguridad y supervisión de stock finalicen
		 * backupThread.join(); superThread.join(); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */
	}
}
