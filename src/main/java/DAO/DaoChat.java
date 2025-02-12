package DAO;

import model.Chat;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DaoChat {

	// Método para insertar un mensaje en la base de datos
    public void insertar(Chat mensaje) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            
            session.persist(mensaje);
            
            transaction.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos los mensajes
    public List<Chat> listar() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	
            String hql = "FROM Mensaje ORDER BY fecha DESC";
            
            return session.createQuery(hql, Chat.class).getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}