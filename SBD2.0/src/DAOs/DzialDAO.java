package DAOs;

import Model.Dzial;
import org.hibernate.SessionFactory;
import sample.DatabaseException;

public class DzialDAO extends AbstractDAO<Dzial> {
    public DzialDAO(SessionFactory factory) {
        super(factory, new Dzial());
    }

    public Dzial getDzialById(Long id) throws DatabaseException {
        return this.getSingleByOneEqualCondition("id", id.toString());
    }
}
