package DAOs;

import Model.Klient;
import org.hibernate.SessionFactory;

import java.util.List;

public class KlientDAO extends AbstractDAO<Klient> {
    public KlientDAO() {
        super(new Klient());
    }
}
