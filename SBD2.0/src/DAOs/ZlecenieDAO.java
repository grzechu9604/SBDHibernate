package DAOs;

import Model.Czesc;
import Model.Zlecenie;
import Model.ZlecenieDzialConnector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import sample.DatabaseException;

import java.util.LinkedList;

public class ZlecenieDAO extends AbstractDAO<Zlecenie> {
    public ZlecenieDAO(SessionFactory factory) {
        super(factory, new Zlecenie());
    }


    @Override
    public boolean insert(Zlecenie element) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.save(element);

            element.getUzyteCzesci().forEach(c -> c.setZlecenieNrNaprawy(element.getId().toString()));
            element.getUzyteCzesci().forEach(c -> session.update(c));

            LinkedList<ZlecenieDzialConnector> zdcs = new LinkedList<>();
            element.getConnectorsToDzial().forEach(cd -> zdcs.add(new ZlecenieDzialConnector(element, cd)));
            zdcs.forEach(zdc -> session.save(zdc));

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się dodać obiektu do bazy danych.");
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Zlecenie element) throws DatabaseException {
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();
        try {
            session.update(element);

            LinkedList<Czesc> czescsConnectedBefore = this.GetAssignedCzescToZlecenie(element.getId());
            LinkedList<ZlecenieDzialConnector> connsctorsBefore = this.getZlecenieDzialConnectorForZlecenieId(element.getId());

            czescsConnectedBefore.removeAll(element.getUzyteCzesci());
            czescsConnectedBefore.forEach(c -> c.setZlecenieNrNaprawy(null));

            czescsConnectedBefore.forEach(c -> session.update(c));
            element.getUzyteCzesci().forEach(c -> c.setZlecenieNrNaprawy(element.getId().toString()));
            element.getUzyteCzesci().forEach(c -> session.update(c));

            LinkedList<ZlecenieDzialConnector> newConnectors = new LinkedList<>();
            element.getConnectorsToDzial().forEach(cd -> newConnectors.add(new ZlecenieDzialConnector(element, cd)));

            LinkedList<ZlecenieDzialConnector> connectorsToDelete = new LinkedList<>();
            connectorsToDelete.addAll(connsctorsBefore);
            connectorsToDelete.removeAll(newConnectors);

            LinkedList<ZlecenieDzialConnector> connectorsToAdd = new LinkedList<>();
            connectorsToAdd.addAll(newConnectors);
            connectorsToAdd.removeAll(connsctorsBefore);

            connectorsToDelete.forEach(c -> session.delete(c));
            connectorsToAdd.forEach(c -> session.save(c));

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Nie udało się dodać obiektu do bazy danych.");
        } finally {
            session.close();
        }
    }

    private LinkedList<Czesc> GetAssignedCzescToZlecenie(Long zlecenieId) throws DatabaseException {
        LinkedList<Czesc> results = new LinkedList<>();

        Query<Object> query = this.factory.getCurrentSession().createQuery("select c from Czesc as c " +
                " where c.zlecenieNrNaprawy = :id ");
        query.setParameter("id", zlecenieId.toString());

        for (Object result : query.list()) {
            Czesc c = (Czesc) result;
            results.add(c);
        }
        return results;
    }

    private LinkedList<ZlecenieDzialConnector> getZlecenieDzialConnectorForZlecenieId(Long zlecenieId) {
        LinkedList<ZlecenieDzialConnector> results = new LinkedList<>();

        Query<ZlecenieDzialConnector> query = this.factory.getCurrentSession().createQuery("select z from ZlecenieDzialConnector as z " +
                " where z.idZlecenia = :id ");
        query.setParameter("id", zlecenieId);

        for (Object result : query.list()) {
            ZlecenieDzialConnector zdc = (ZlecenieDzialConnector) result;
            results.add(zdc);
        }
        return results;
    }
}
