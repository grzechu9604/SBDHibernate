package DAOs;

import Model.Pracownik;
import org.hibernate.Criteria;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.List;

public class PracownikDAO extends AbstractDAO<Pracownik> {
    private static final String getAllHql = "select p from Pracownik p";
    private static final String getByIdHql = "select p from Pracownik p where p.id = id";

    public PracownikDAO() {
        super(new Pracownik());
    }

    public Pracownik getPracownikById(SessionFactory factory, Long id){
        Session session = factory.getCurrentSession();
        session.getTransaction().begin();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Pracownik> query = builder.createQuery(Pracownik.class);
        Root<Pracownik> root = query.from(Pracownik.class);

        query.select(root).where(builder.equal(root.get("id"), id));
        Query<Pracownik> q=session.createQuery(query);

        Pracownik p = q.getSingleResult();
        session.getTransaction().commit();

        return p;
    }
}




