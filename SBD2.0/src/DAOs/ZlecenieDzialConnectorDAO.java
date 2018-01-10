package DAOs;

import Model.ZlecenieDzialConnector;
import org.hibernate.SessionFactory;

public class ZlecenieDzialConnectorDAO extends AbstractDAO<ZlecenieDzialConnector> {
    public ZlecenieDzialConnectorDAO(SessionFactory factory) {
        super(factory, new ZlecenieDzialConnector());
    }


    /*public List<Zlecenie> getZleceniaDzialu(Long id){
        Session session = this.factory.getCurrentSession();
        session.getTransaction().begin();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        @SuppressWarnings("unchecked")
        CriteriaQuery<T> query = builder.createQuery(entityBean);
        @SuppressWarnings("unchecked")
        Root<T> root = query.from(entityBean);

        query.select(root).where(builder.equal(root.get(key), value));
        Query<T> q=session.createQuery(query);

        List<T> elements = q.getResultList();
        session.getTransaction().commit();

        return elements;
    }*/
}
