package DAOs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractDAO<T>{

    public AbstractDAO(T bean){
        this.entityBean = bean.getClass();
    }

    private final Class entityBean;

    public List<T> findAll(SessionFactory factory){
        Session session = factory.getCurrentSession();
        session.getTransaction().begin();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        @SuppressWarnings("unchecked")
        CriteriaQuery<T> query = builder.createQuery(entityBean);
        @SuppressWarnings("unchecked")
        Root<T> root = query.from(entityBean);
        query.select(root);
        Query<T> q=session.createQuery(query);

        List<T> elements =q.getResultList();
        session.getTransaction().commit();

        return elements;
    }

    T getSingleByOneEqualCondition(SessionFactory factory, String key, String value) {
        Session session = factory.getCurrentSession();
        session.getTransaction().begin();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        @SuppressWarnings("unchecked")
        CriteriaQuery<T> query = builder.createQuery(entityBean);
        @SuppressWarnings("unchecked")
        Root<T> root = query.from(entityBean);

        query.select(root).where(builder.equal(root.get(key), value));
        Query<T> q=session.createQuery(query);

        T element = q.getSingleResult();
        session.getTransaction().commit();

        return element;
    }

    protected List<T> getListByOneEqualCondition(SessionFactory factory, String key, String value){
        Session session = factory.getCurrentSession();
        session.getTransaction().begin();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        @SuppressWarnings("unchecked")
        CriteriaQuery<T> query = builder.createQuery(entityBean);
        @SuppressWarnings("unchecked")
        Root<T> root = query.from(entityBean);

        query.select(root).where(builder.equal(root.get(key), value));
        Query<T> q=session.createQuery(query);

        List<T> elements = q.getResultList();
        session.getTransaction().commit();

        return elements;
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
