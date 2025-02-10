package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Producto;

/**
 * Clase para realizar operaciones relacionadas con la tabla 'pruductos' en la
 * base de datos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class DaoProducto {

	// Insertar producto nuevo en la base de datos
	public void insertar(Producto p) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			session.persist(p);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Listar todos los productos de la base de datos
	public List<Producto> listar() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "FROM Producto";
			Query<Producto> query = session.createQuery(hql, Producto.class);

			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Listar los productos con stock inferior al minimo
	public List<Producto> listarMinimo() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "FROM Producto WHERE stock < stockMinimo";
			Query<Producto> query = session.createQuery(hql, Producto.class);

			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Leer un producto por su ID para modificarlo posteriormente
	public Producto leerProducto(int idProducto) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.get(Producto.class, idProducto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Actualizar un producto en la base de datos
	public boolean actualizar(Producto p) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			session.merge(p);

			transaction.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Eliminar un producto de la base de datos
	public void eliminar(int idProducto) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction();

			Producto p = session.get(Producto.class, idProducto);
			session.remove(p);

			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} // Class
