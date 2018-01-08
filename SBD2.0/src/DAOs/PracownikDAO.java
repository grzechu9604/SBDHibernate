package DAOs;

import Model.Pracownik;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class PracownikDAO extends AbstractDAO<Pracownik> {

    @Override
    public List<Pracownik> findAll(SessionFactory factory) {
        String hql = "select p from Pracownik p";
        Session session = factory.getCurrentSession();
        session.getTransaction().begin();
        Query pracownikQuery = session.createQuery(hql);
        List<Pracownik> pracownikList = pracownikQuery.getResultList();
        session.getTransaction().commit();
        return pracownikList;
    }
}




