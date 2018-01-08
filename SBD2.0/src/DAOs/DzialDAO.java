package DAOs;

import Model.Dzial;
import org.hibernate.SessionFactory;

import java.util.List;

public class DzialDAO extends AbstractDAO<Dzial> {
    private static final String getAllHql = "select d from Dzial d";

    public List<Dzial> findAll(SessionFactory factory){
        return this.execute(factory, getAllHql);
    }
}
