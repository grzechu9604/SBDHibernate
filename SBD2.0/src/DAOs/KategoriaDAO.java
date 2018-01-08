package DAOs;

import Model.Kategoria;
import org.hibernate.SessionFactory;

import java.util.List;

public class KategoriaDAO extends AbstractDAO<Kategoria> {
    private static final String getAllHql = "select k from Kategoria k";

    public List<Kategoria> findAll(SessionFactory factory){
        return this.execute(factory, getAllHql);
    }
}
