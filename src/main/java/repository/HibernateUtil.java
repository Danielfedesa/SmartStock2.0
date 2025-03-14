package repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase para gestionar la configuracion de Hibernate y la creacion de la SessionFactory.
 * Se encarga de inicializar, obtener y cerrar la SessionFactory.
 * 
 * @autor Daniel Fernandez Sanchez
 * @version 1.0 02/2025
 */
public class HibernateUtil {
	
	/**
     * Instancia unica de la SessionFactory, inicializada al cargar la clase.
     */
	private static final SessionFactory sessionFactory = buildSessionFactory();

	/**
     * Crea la SessionFactory de Hibernate cargando la configuracion desde el archivo hibernate.cfg.xml.
     *
     * @return Objeto SessionFactory configurado con las entidades anotadas.
     * @throws ExceptionInInitializerError si ocurre un error al inicializar la SessionFactory.
     */
	private static SessionFactory buildSessionFactory() {
		try {
			// Cargar la configuracion del archivo hibernate.cfg.xml
			return new Configuration().configure("hibernate.cfg.xml")
					.addAnnotatedClass(model.Usuario.class)
					.addAnnotatedClass(model.Categoria.class)
					.addAnnotatedClass(model.Producto.class)
					.addAnnotatedClass(model.HistorialInventario.class)
			        .addAnnotatedClass(model.CopiaSeguridad.class)
			        .addAnnotatedClass(model.Chat.class)
			        .buildSessionFactory();		         
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError("Error al inicializar SessionFactory" +ex);
		}
	}
	
	/**
     * Obtiene la SessionFactory de Hibernate para interactuar con la base de datos.
     *
     * @return Instancia unica de SessionFactory.
     */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
     * Cierra la SessionFactory de Hibernate, liberando los recursos asociados.
     */
	public static void shutdown() {
		getSessionFactory().close();
	}

}