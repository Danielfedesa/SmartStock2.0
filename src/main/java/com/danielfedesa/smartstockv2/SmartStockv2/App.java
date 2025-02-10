package com.danielfedesa.smartstockv2.SmartStockv2;

import javax.swing.SwingUtilities;

import process.BackupAutomatico;
import controller.Login;
import view.ScreenLogin;
import process.SupervisorStock;

public class App {
	public static void main(String[] args) {
		// Ejecuta la creación de la UI en el hilo de eventos de Swing
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

		// Crear y ejecutar el hilo de copias automáticas
		Thread backupThread = new Thread(new BackupAutomatico());
		backupThread.start();
		System.out.println("Hilo de copia de seguridad INICIADO.");

		// Crear y ejecutar el hilo de supervisión de stock
		Thread superThread = new Thread(new SupervisorStock());
		superThread.start();
		System.out.println("Hilo de supervisión de stock bajo INICIADO");

		/*
		// Esperar que los hilos terminen antes de cerrar la aplicación
		try {
			// Espera a que los hilos de copia de seguridad y supervisión de stock finalicen
			backupThread.join();
			superThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
	}
}
