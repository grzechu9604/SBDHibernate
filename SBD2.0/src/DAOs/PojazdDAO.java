package DAOs;

import Model.Pojazd;
import org.hibernate.SessionFactory;

import java.util.List;

public class PojazdDAO extends AbstractDAO<Pojazd> {
    private static final String getAllHql = "select p from Pojazd p";

    public List<Pojazd> findAll(SessionFactory factory){
        return this.getResultList(factory, getAllHql);
    }
}
