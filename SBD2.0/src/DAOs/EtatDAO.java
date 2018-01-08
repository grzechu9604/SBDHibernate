package DAOs;

import Model.Etat;
import org.hibernate.SessionFactory;

import java.util.List;

public class EtatDAO extends AbstractDAO<Etat> {
    private static final String getAllHql = "select e from Etat e";

    public List<Etat> findAll(SessionFactory factory){
        return this.getResultList(factory, getAllHql);
    }
}
