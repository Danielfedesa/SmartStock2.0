package repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.CopiaSeguridad;

/**
 * Clase para realizar operaciones relacionadas con la tabla 'copiasseguridad'
 * en la base de datos.
 * Permite insertar y listar registros de copias de seguridad utilizando Hibernate.
 * 
 * @author Daniel Fernandez Sanchez
 * @version 3.0 03/2025
 */
public class CopiaSeguridadRepository {

	/**
	 * Inserta un nuevo registro de copia de seguridad en la base de datos
	 * utilizando Hibernate.
	 *
	 * @param cs Objeto de tipo CopiaSeguridad que representa la copia a insertar.
	 */
	public void insertar(CopiaSeguridad cs) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();

			session.persist(cs);

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todos los registros de copias de seguridad almacenados en la base de
	 * datos utilizando Hibernate.
	 *
	 * @return Lista de objetos CopiaSeguridad si la consulta es exitosa, de lo
	 *         contrario retorna null.
	 */
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
