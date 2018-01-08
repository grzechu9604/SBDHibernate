package DAOs;

import Model.Czesc;
import org.hibernate.SessionFactory;

import java.util.List;

public class CzescDAO extends AbstractDAO<Czesc> {
    private static final String getAllHql = "select c from Czesc c";

    public List<Czesc> findAll(SessionFactory factory){
        return this.execute(factory, getAllHql);
    }
}