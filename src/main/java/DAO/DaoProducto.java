package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Producto;

/**
 * Clase para realizar operaciones relacionadas con la tabla 'pruductos' en la
 * base de datos utilizando Hibernate. Permite insertar, listar, leer,
 * actualizar y eliminar productos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class DaoProducto {

	/**
	 * Inserta un nuevo producto en la base de datos utilizando Hibernate.
	 *
	 * @param p Objeto de tipo Producto que representa el producto a insertar.
	 */
	public void insertar(Producto p) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			session.persist(p);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todos los productos almacenados en la base de datos utilizando
	 * Hibernate.
	 *
	 * @return Lista de objetos Producto si la consulta es exitosa, de lo contrario
	 *         retorna null.
	 */
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

	/**
	 * Lista los productos cuyo stock es inferior al minimo definido en la base de
	 * datos utilizando Hibernate.
	 *
	 * @return Lista de productos con stock bajo si la consulta es exitosa, de lo
	 *         contrario retorna null.
	 */
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

	/**
	 * Busca un producto en la base de datos por su ID utilizando Hibernate.
	 *
	 * @param idProducto ID del producto a buscar.
	 * @return Objeto Producto si se encuentra en la base de datos, de lo contrario
	 *         retorna null.
	 */
	public Producto leerProducto(int idProducto) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			return session.get(Producto.class, idProducto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Actualiza un producto en la base de datos utilizando Hibernate.
	 *
	 * @param p Objeto Producto con los datos actualizados.
	 * @return true si la actualizacion es exitosa, false en caso de error.
	 */
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

	/**
	 * Elimina un producto de la base de datos segun su ID utilizando Hibernate.
	 *
	 * @param idProducto ID del producto a eliminar.
	 */
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
