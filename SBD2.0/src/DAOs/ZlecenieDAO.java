package DAOs;

import Model.Zlecenie;
import org.hibernate.SessionFactory;

import java.util.List;

public class ZlecenieDAO extends AbstractDAO<Zlecenie> {
    private static final String getAllHql = "select z from Zlecenie z";

    public List<Zlecenie> findAll(SessionFactory factory){
        return this.getResultList(factory, getAllHql);
    }
}
