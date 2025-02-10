package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.CopiaSeguridad;

/**
 * Clase para realizar operaciones relacionadas con la tabla 'copiasseguridad'
 * en la base de datos.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 2.0 02/2025
 */
public class DaoCopiaSeguridad {

	// Insertar registro en de la copia de seguridad en la base de datos.
	public void insertar(CopiaSeguridad cs) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			session.persist(cs);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Listar todos los registros de copias de seguridad
	public List<CopiaSeguridad> listar() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			String hql = "FROM CopiaSeguridad";
			Query<CopiaSeguridad> query = session.createQuery(hql, CopiaSeguridad.class);

			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

} // Class
