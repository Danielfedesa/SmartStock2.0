package repository;

import model.Chat;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para realizar operaciones relacionadas con la tabla 'chat' en la base
 * de datos utilizando Hibernate. Permite insertar y listar mensajes de chat
 * almacenados en la base de datos.
 * 
 * @author Daniel Fernandez Sanchez.
 * @version 3.0 03/2025
 */
public class ChatRepository {

	/**
	 * Inserta un mensaje en la base de datos utilizando Hibernate.
	 *
	 * @param mensaje Objeto de tipo Chat que representa el mensaje a insertar.
	 */
	public void insertar(Chat mensaje) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			session.persist(mensaje);

			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todos los mensajes almacenados en la base de datos utilizando
	 * Hibernate. Los mensajes se ordenan por fecha en orden ascendente.
	 *
	 * @return Lista de objetos Chat si la consulta es exitosa, en caso de error
	 *         retorna una lista vacia.
	 */
	public List<Chat> listar() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			String hql = "FROM Chat ORDER BY fecha ASC";

			return session.createQuery(hql, Chat.class).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>(); // Devuelve una lista vacía en caso de error
		}
	}
}