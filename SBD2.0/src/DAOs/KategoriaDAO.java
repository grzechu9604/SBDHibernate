package DAOs;

import Model.Kategoria;
import org.hibernate.SessionFactory;

import java.util.List;

public class KategoriaDAO extends AbstractDAO<Kategoria> {
    private static final String getAllHql = "select k from Kategoria k";

    public KategoriaDAO() {
        super(new Kategoria());
    }

    public List<Kategoria> findAll(SessionFactory factory){
        return this.getResultList(factory, getAllHql);
    }
}
