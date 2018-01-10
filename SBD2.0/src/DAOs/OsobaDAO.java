package DAOs;

import Model.Osoba;
import org.hibernate.SessionFactory;

public class OsobaDAO extends AbstractDAO<Osoba>{
    public OsobaDAO(SessionFactory factory) {
        super(factory, new Osoba());
    }
}
