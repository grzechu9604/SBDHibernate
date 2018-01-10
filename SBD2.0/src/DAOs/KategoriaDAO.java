package DAOs;

import Model.Kategoria;
import org.hibernate.SessionFactory;

public class KategoriaDAO extends AbstractDAO<Kategoria> {
    public KategoriaDAO(SessionFactory factory) {
        super(factory, new Kategoria());
    }
}
