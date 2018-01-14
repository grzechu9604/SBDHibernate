package DAOs;

import Model.Dzial;
import org.hibernate.SessionFactory;

public class DzialDAO extends AbstractDAO<Dzial> {
    public DzialDAO(SessionFactory factory) {
        super(factory, new Dzial());
    }

    public Dzial getDzialById(Long id) {
        return this.getSingleByOneEqualCondition("id", id.toString());
    }
}
