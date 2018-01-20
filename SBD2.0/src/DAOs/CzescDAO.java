package DAOs;

import Model.Czesc;
import Model.Kategoria;
import javafx.util.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import sample.DatabaseException;

import java.util.LinkedList;
import java.util.List;

public class CzescDAO extends AbstractDAO<Czesc> {
    public CzescDAO(SessionFactory factory) {
        super(factory, new Czesc());
    }

    public List<Czesc> GetUnassignedCzesc() throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();

        try {
            List<Czesc> results = new LinkedList<>();

            Query<Object> query = session.createQuery("select c from Czesc as c " +
                    "where c.zlecenieNrNaprawy is null");
            for (Object result : query.list()) {
                Czesc c = (Czesc) result;
                results.add(c);
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

    public List<Czesc> GetAssignedCzescToZlecenie(Long zlecenieId) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();

        try {
            List<Czesc> results = new LinkedList<>();

            Query<Object> query = session.createQuery("select c from Czesc as c " +
                    " where c.zlecenieNrNaprawy = :id ");
            query.setParameter("id", zlecenieId.toString());

            for (Object result : query.list()) {
                Czesc c = (Czesc) result;
                results.add(c);
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

    public List<Pair<Czesc, Kategoria>> GetAssignedCzescToZlecenieX(Long zlecenieId) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();

        try {
            List<Pair<Czesc, Kategoria>> results = new LinkedList<>();

            Query<Object[]> query = session.createQuery("select c, k from Czesc as c, " +
                    "Kategoria as k where c.zlecenieNrNaprawy = :id and c.nazwaKategorii = k.nazwa ");
            query.setParameter("id", zlecenieId.toString());

            for (Object[] result : query.list()) {
                Czesc c = (Czesc) result[0];
                Kategoria k = (Kategoria) result[1];
                results.add(new Pair<>(c, k));
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
