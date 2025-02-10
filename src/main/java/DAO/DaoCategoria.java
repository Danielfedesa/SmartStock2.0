package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Categoria;

/**
 * Clase para realizar operaciones relacionadas con la tabla 'categorias' en la
 * base de datos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class DaoCategoria {

	// Insertar una nueva categoria en la base de datos
	public void insertar(Categoria c) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			session.persist(c);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Listar todas las categorias de la base de datos
	public List<Categoria> listar() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "FROM Categoria";
			Query<Categoria> query = session.createQuery(hql, Categoria.class);

			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Buscar una categoria por su ID para modificarla posteriormente
	public Categoria leerCategoria(int idCategoria) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.get(Categoria.class, idCategoria);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Actualizar una categoria en la base de datos
	public boolean actualizar(Categoria cate) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			session.merge(cate);

			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Eliminar una categoria de la base de datos
	public void eliminar(int idCategoria) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			Categoria c = session.get(Categoria.class, idCategoria);
			session.remove(c);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} // Class
