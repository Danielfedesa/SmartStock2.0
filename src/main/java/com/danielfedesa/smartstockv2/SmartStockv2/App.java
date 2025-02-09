package com.danielfedesa.smartstockv2.SmartStockv2;

import javax.swing.SwingUtilities;

//import view.BackupAutomatico;
import controller.Login;
import view.ScreenLogin;
//import view.SupervisorStock;

public class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				// Crea el modelo Login (el DAO se inicializa dentro de Login)
				Login loginControlador = new Login();

				// Crea y muestra la pantalla de inicio de sesión
				new ScreenLogin(loginControlador).setVisible(true);
			} catch (Exception e) {
				System.err.println("Error al inicializar la aplicación: " + e.getMessage());
				e.printStackTrace();
			}
		});

		/*
		 * // Crear y ejecutar el hilo de copias automáticas Thread backupThread = new
		 * Thread(new BackupAutomatico()); backupThread.start();
		 * 
		 * System.out.println("Hilo de copia de seguridad INICIADO.");
		 * 
		 * // Crear y ejecutar el hilo de supervisión de stock. Thread superThread = new
		 * Thread(new SupervisorStock()); superThread.start();
		 * 
		 * System.out.println("Hilo de supervisión de stock bajo INICIADO");
		 */
	}
}
