package DAOs;

import Model.Dzial;
import org.hibernate.SessionFactory;

public class DzialDAO extends AbstractDAO<Dzial> {
    public DzialDAO(SessionFactory factory) {
        super(factory, new Dzial());
    }

    public Dzial getDzialById(SessionFactory factory, Long id){
        return new Dzial();
    }
}
