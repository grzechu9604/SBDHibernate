package DAOs;

import Model.Zlecenie;
import org.hibernate.SessionFactory;

public class ZlecenieDAO extends AbstractDAO<Zlecenie> {
    public ZlecenieDAO(SessionFactory factory) {
        super(factory, new Zlecenie());
    }
}
