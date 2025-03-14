package repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Usuario;

/**
 * Clase para realizar operaciones relacionadas con la tabla 'usuarios' en la
 * base de datos utilizando Hibernate. Permite insertar, listar, leer por ID,
 * actualizar y eliminar
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class UsuarioRepository {

	/**
	 * Inserta un nuevo usuario en la base de datos utilizando Hibernate.
	 *
	 * @param u Objeto de tipo Usuario que representa el usuario a insertar.
	 */
	public void insertar(Usuario u) {
		// Abrir la sesion de Hibernate
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Iniciar una transaccion
			Transaction transaction = session.beginTransaction();

			// Guardar el objeto en la base de datos
			session.persist(u);

			// Commit de la transaccion
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todos los usuarios almacenados en la base de datos utilizando
	 * Hibernate.
	 *
	 * @return Lista de objetos Usuario si la consulta es exitosa, de lo contrario
	 *         retorna null.
	 */
	public List<Usuario> listar() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Crear la consulta
			String hql = "FROM Usuario";
			Query<Usuario> query = session.createQuery(hql, Usuario.class);

			// Ejecutar la consulta y obtener la lista
			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Busca un usuario en la base de datos por su ID utilizando Hibernate.
	 *
	 * @param idUsuario ID del usuario a buscar.
	 * @return Objeto Usuario si se encuentra en la base de datos, de lo contrario
	 *         retorna null.
	 */
	public Usuario leerUsuario(int idUsuario) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Recuperar el usuario por el id
			return session.get(Usuario.class, idUsuario);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Actualiza un usuario en la base de datos utilizando Hibernate.
	 *
	 * @param user Objeto Usuario con los datos actualizados.
	 * @return true si la actualizacion es exitosa, false en caso de error.
	 */
	public boolean actualizar(Usuario user) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Iniciar una transaccion
			Transaction transaction = session.beginTransaction();

			// Actualizar el usuario
			session.merge(user);

			// Commit de la transaccion
			transaction.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Elimina un usuario de la base de datos segun su ID utilizando Hibernate.
	 *
	 * @param idUsuario ID del usuario a eliminar.
	 */
	public void eliminar(int idUsuario) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Iniciar una transaccion
			Transaction transaction = session.beginTransaction();

			// Eliminar el usuario
			Usuario u = session.get(Usuario.class, idUsuario);
			session.remove(u);

			// Commit de la transaccion
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Busca un usuario en la base de datos por su correo electronico utilizando
	 * Hibernate. Se utiliza para validar el inicio de sesion con el correo
	 * electronico.
	 *
	 * @param email Correo electronico del usuario a buscar.
	 * @return Objeto Usuario si se encuentra en la base de datos, de lo contrario
	 *         retorna null.
	 */
	public Usuario leerUsuarioPorEmail(String email) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Crear la consulta
			String hql = "FROM Usuario WHERE email = :email";
			Query<Usuario> query = session.createQuery(hql, Usuario.class);
			query.setParameter("email", email);

			// Ejecutar la consulta y obtener el usuario
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // Si no se encuentra el usuario, devolver null
	}
}
