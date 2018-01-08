package DAOs;

import Model.Firma;
import org.hibernate.SessionFactory;

import java.util.List;

public class FirmaDAO extends AbstractDAO<Firma>{
    private static final String getAllHql = "select f from Firma f";

    public List<Firma> findAll(SessionFactory factory){
        return this.execute(factory, getAllHql);
    }
}
