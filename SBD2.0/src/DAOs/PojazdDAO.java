package DAOs;

import Model.Pojazd;
import org.hibernate.SessionFactory;

public class PojazdDAO extends AbstractDAO<Pojazd> {
    public PojazdDAO(SessionFactory factory) {
        super(factory, new Pojazd());
    }
}
