package DAO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

	// Crear la factoria de sesiones de Hibernate
	private static SessionFactory buildSessionFactory() {
		try {
			// Cargar la configuracion del archivo hibernate.cfg.xml
			return new Configuration().configure("hibernate.cfg.xml")
					.addAnnotatedClass(model.Usuario.class)
					.addAnnotatedClass(model.Categoria.class)
					.addAnnotatedClass(model.Producto.class)
					.addAnnotatedClass(model.HistorialInventario.class)
			        .addAnnotatedClass(model.CopiaSeguridad.class)
			        .buildSessionFactory();		         
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError("Error al inicializar SessionFactory" +ex);
		}
	}
	
	// Obtener la sesion de Hibernate
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	// Cerrar la sesion de Hibernate
	public static void shutdown() {
		getSessionFactory().close();
	}

}
