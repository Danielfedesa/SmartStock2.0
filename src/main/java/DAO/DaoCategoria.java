package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Categoria;

/**
 * Clase para realizar operaciones relacionadas con la tabla 'categorias' en la
 * base de datos utilizando Hibernate.
 * Permite insertar, listar, leer por ID, actualizar y eliminar.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class DaoCategoria {

	/**
	 * Inserta una nueva categoria en la base de datos utilizando Hibernate.
	 *
	 * @param c Objeto de tipo Categoria a insertar en la base de datos.
	 */
	public void insertar(Categoria c) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			session.persist(c);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todas las categorias almacenadas en la base de datos utilizando
	 * Hibernate.
	 *
	 * @return Lista de objetos Categoria si la consulta es exitosa, de lo contrario
	 *         retorna null.
	 */
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

	/**
	 * Busca una categoria en la base de datos segun su ID utilizando Hibernate.
	 * 
	 * @param idCategoria ID de la categoria a buscar.
	 * @return Objeto Categoria si se encuentra en la base de datos, de lo contrario
	 *         retorna null.
	 */
	public Categoria leerCategoria(int idCategoria) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.get(Categoria.class, idCategoria);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Actualiza una categoria en la base de datos utilizando Hibernate.
	 *
	 * @param cate Objeto Categoria con los datos actualizados.
	 * @return true si la actualizacion es exitosa, false en caso de error.
	 */
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

	/**
	 * Elimina una categoria de la base de datos segun su ID utilizando Hibernate.
	 *
	 * @param idCategoria ID de la categoria a eliminar.
	 */
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
