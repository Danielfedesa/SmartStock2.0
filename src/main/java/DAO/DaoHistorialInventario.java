package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.HistorialInventario;

/**
 * Clase para realizar operaciones relacionadas con la tabla
 * 'historialinventario' en la base de datos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class DaoHistorialInventario {

	// insertar un nuevo movimiento en la base de datos
	public void insertar(HistorialInventario h) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			session.persist(h);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// listar todos los movimientos de la base de datos
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
