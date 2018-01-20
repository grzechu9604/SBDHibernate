package DAOs;

import Model.Dzial;
import Model.ZlecenieDzialConnector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import sample.DatabaseException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class DzialDAO extends AbstractDAO<Dzial> {
    public DzialDAO(SessionFactory factory) {
        super(factory, new Dzial());
    }

    public Dzial getDzialById(Long id) throws DatabaseException {
        return this.getSingleByOneEqualCondition("id", id.toString());
    }

    public List<Dzial> getDzialsConnectedToZlecenie(Long zlecenieId) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();

        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Dzial> query = builder.createQuery(Dzial.class);

            Root<Dzial> dzialRoot = query.from(Dzial.class);
            Root<ZlecenieDzialConnector> zlecenieDzialConnectorRoot = query.from(ZlecenieDzialConnector.class);

            Predicate joinCondition = builder.equal(dzialRoot.get("id"), zlecenieDzialConnectorRoot.get("idDzialu"));
            Predicate zlecenieIdCondition = builder.equal(zlecenieDzialConnectorRoot.get("idZlecenia"), zlecenieId);

            query.select(dzialRoot).where(builder.and(joinCondition, zlecenieIdCondition));
            Query<Dzial> q = session.createQuery(query);

            List<Dzial> elements = q.getResultList();
            session.getTransaction().commit();

            return elements;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się dodać obiektu do bazy danych.");
        } finally {
            session.close();
        }
    }

    public List<Dzial> GetAssignedDzialsToZlecenie(Long dzialId) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();

        try {
            List<Dzial> results = new LinkedList<>();

            Query<Object> query = session.createQuery("select d from Dzial as d " +
                    " inner join ZlecenieDzialConnector z on d.id = z.idDzialu " +
                    " where z.idZlecenia = :id ");
            query.setParameter("id", dzialId.toString());

            for (Object result : query.list()) {
                Dzial d = (Dzial) result;
                results.add(d);
            }

            session.getTransaction().commit();

            return results;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się dodać obiektu do bazy danych.");
        } finally {
            session.close();
        }
    }
}
