package DAOs;

import Model.Zlecenie;
import Model.ZlecenieDzialConnector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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




}
