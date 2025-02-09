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
		// Crear y ejecutar el hilo de copias automáticas
		Thread backupThread = new Thread(new BackupAutomatico());
		backupThread.start();

		System.out.println("Hilo de copia de seguridad INICIADO.");

		// Crear y ejecutar el hilo de supervisión de stock.
		Thread superThread = new Thread(new SupervisorStock());
		superThread.start();

		System.out.println("Hilo de supervisión de stock bajo INICIADO");
		*/
	}
}


/*
 * StandardServiceRegistry sr = new
 * StandardServiceRegistryBuilder().configure().build();
 * 
 * SessionFactory sf = new
 * MetadataSources(sr).buildMetadata().buildSessionFactory();
 * 
 * 
 * // Apertura de la sesion Session session = sf.openSession();
 * 
 * Usuario user1 = new Usuario(); user1.setIdUsuario(39);
 * user1.setNombreUsuario("Daniel"); user1.setApellido1("Fernandez");
 * user1.setApellido2("Sanz"); user1.setTelefono(123456789);
 * user1.setEmail("prueba11@gmail.com"); user1.setContrasena("1234");
 * user1.setRol("admin");
 * 
 * Usuario user2 = new Usuario(); user2.setIdUsuario(40);
 * user2.setNombreUsuario("Miguel"); user2.setApellido1("Fernandez");
 * user2.setApellido2("Sanz"); user2.setTelefono(123456789);
 * user2.setEmail("prueba2@gmail.com"); user2.setContrasena("1234");
 * user2.setRol("empleado");
 * 
 * // Inicio de la transacción session.beginTransaction();
 * 
 * // Guardar los usuarios en la base de datos session.persist(user1);
 * session.persist(user2);
 * 
 * // Confirmar (commit) la transacción session.getTransaction().commit(); //
 * Aquí es donde realmente se hace el commit de los cambios
 * 
 * // Cierre de la sesión session.close(); // Cierre de la SessionFactory
 * sf.close();
 * 
 * System.out.println("Usuarios guardados correctamente."); } }
 */