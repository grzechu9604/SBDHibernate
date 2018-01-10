package DAOs;

import Model.Klient;
import org.hibernate.SessionFactory;

public class KlientDAO extends AbstractDAO<Klient> {
    public KlientDAO(SessionFactory factory) {
        super(factory, new Klient());
    }
}
