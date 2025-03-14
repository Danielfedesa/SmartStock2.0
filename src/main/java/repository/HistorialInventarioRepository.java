package repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.HistorialInventario;

/**
 * Clase para realizar operaciones relacionadas con la tabla
 * 'historialinventario' en la base de datos utilizando Hibernate.
 * Permite insertar y listar movimientos de inventario.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 3.0 03/2025
 */
public class HistorialInventarioRepository {

	/**
     * Inserta un nuevo movimiento en la base de datos utilizando Hibernate.
     *
     * @param h Objeto de tipo HistorialInventario que representa el movimiento a insertar.
     */
	public void insertar(HistorialInventario h) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			session.persist(h);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * Lista todos los movimientos de inventario almacenados en la base de datos utilizando Hibernate.
     *
     * @return Lista de objetos HistorialInventario si la consulta es exitosa, de lo contrario retorna null.
     */
	public List<HistorialInventario> listar() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "FROM HistorialInventario";
			Query<HistorialInventario> query = session.createQuery(hql, HistorialInventario.class);

			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

} // Class
