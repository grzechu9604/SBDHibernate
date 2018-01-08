package DAOs;

import Model.Pracownik;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public abstract class AbstractDAO<T>{

    protected List<T> getResultList(SessionFactory factory, String hql){
        Session session = factory.getCurrentSession();
        session.getTransaction().begin();
        Query query = session.createQuery(hql);
        List<T> list = query.getResultList();
        session.getTransaction().commit();
        return list;
    }

    public boolean insert(SessionFactory factory, T element){
        Session session = factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.save(element);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(SessionFactory factory, T element){
        Session session = factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.update(element);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(SessionFactory factory, T element){
        Session session = factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.delete(element);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
