package DAOs;

import Model.Osoba;
import org.hibernate.SessionFactory;

import java.util.List;

public class OsobaDAO extends AbstractDAO<Osoba>{
    private static final String getAllHql = "select o from Osoba o";

    public List<Osoba> findAll(SessionFactory factory){
        return this.getResultList(factory, getAllHql);
    }
}
