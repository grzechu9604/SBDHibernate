package DAOs;

import Model.Klient;
import org.hibernate.SessionFactory;
import sample.DatabaseException;

public class KlientDAO extends AbstractDAO<Klient> {
    public KlientDAO(SessionFactory factory) {
        super(factory, new Klient());
    }

    public Klient getKlientById(Long id) throws DatabaseException {
        return this.getSingleByOneEqualCondition("id", id.toString());
    }
}
