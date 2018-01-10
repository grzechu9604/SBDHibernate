package DAOs;

import Model.Czesc;
import org.hibernate.SessionFactory;

public class CzescDAO extends AbstractDAO<Czesc> {
    public CzescDAO(SessionFactory factory) {
        super(factory, new Czesc());
    }
}
