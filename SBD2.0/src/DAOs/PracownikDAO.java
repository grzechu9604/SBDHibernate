package DAOs;

import Model.Pracownik;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class PracownikDAO extends AbstractDAO<Pracownik> {
    private static final String getAllHql = "select p from Pracownik p";

    public List<Pracownik> findAll(SessionFactory factory){
        return this.execute(factory, getAllHql);
    }
}




