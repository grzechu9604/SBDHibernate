package DAOs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import sample.DatabaseException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractDAO<T> {

    public AbstractDAO(SessionFactory factory, T bean) {
        this.entityBean = bean.getClass();
        this.factory = factory;
    }

    private final Class entityBean;
    final SessionFactory factory;

    public List<T> findAll() throws DatabaseException {

        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            @SuppressWarnings("unchecked")
            CriteriaQuery<T> query = builder.createQuery(entityBean);
            @SuppressWarnings("unchecked")
            Root<T> root = query.from(entityBean);
            query.select(root);
            Query<T> q = session.createQuery(query);

            List<T> elements = q.getResultList();
            session.getTransaction().commit();

            return elements;
        } catch (Exception e) {
            session.close();
            throw new DatabaseException("Nastąpił błąd przy pobraniu listy");
        }
    }

    T getSingleByOneEqualCondition(String key, String value) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            @SuppressWarnings("unchecked")
            CriteriaQuery<T> query = builder.createQuery(entityBean);
            @SuppressWarnings("unchecked")
            Root<T> root = query.from(entityBean);

            query.select(root).where(builder.equal(root.get(key), value));
            Query<T> q = session.createQuery(query);

            T element = q.getSingleResult();
            session.getTransaction().commit();

            return element;
        } catch (Exception e) {
            session.close();
            throw new DatabaseException("Nastąpił błąd przy pobraniu listy");
        }
    }

    protected List<T> getListByOneEqualCondition(String key, String value) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            @SuppressWarnings("unchecked")
            CriteriaQuery<T> query = builder.createQuery(entityBean);
            @SuppressWarnings("unchecked")
            Root<T> root = query.from(entityBean);

            query.select(root).where(builder.equal(root.get(key), value));
            Query<T> q = session.createQuery(query);

            List<T> elements = q.getResultList();
            session.getTransaction().commit();


            return elements;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się dodać obiektu do bazy danych.");
        } finally {
            session.close();
        }

    }

    public boolean insert(T element) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.save(element);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się dodać obiektu do bazy danych.");
        } finally {
            session.close();
        }
    }

    public boolean update(T element) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.update(element);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się wykonać akcji edycji.");
        } finally {
            session.close();
        }
    }

    public boolean delete(T element) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.delete(element);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Znaleziono powiązanie z obiektem, który próbujesz usunąć. Nie można wykonać akcji usunięcia");
        } finally {
            session.close();
        }
    }
}
