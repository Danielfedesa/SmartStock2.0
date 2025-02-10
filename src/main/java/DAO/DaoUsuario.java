package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Usuario;

/**
 * Clase para realizar operaciones relacionadas con la tabla 'usuarios' en la
 * base de datos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class DaoUsuario {

	// Insertar usuario nuevo en la base de datos
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

	// Listar todos los usuarios de la base de datos
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

	// Buscar un usuario por su ID para modificarlo posteriormente
	public Usuario leerUsuario(int idUsuario) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Recuperar el usuario por el id
			return session.get(Usuario.class, idUsuario);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Actualizar un usuario en la base de datos
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

	// Eliminar un usuario de la base de datos
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
        return null;  // Si no se encuentra el usuario, devolver null
    }
}
