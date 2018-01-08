package DAOs;

import Model.Klient;
import org.hibernate.SessionFactory;

import java.util.List;

public class KlientDAO extends AbstractDAO<Klient> {
    private static final String getAllHql = "select k from Klient k";

    public List<Klient> findAll(SessionFactory factory){
        return this.getResultList(factory, getAllHql);
    }
}
