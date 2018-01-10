package DAOs;

import Model.Etat;
import org.hibernate.SessionFactory;

public class EtatDAO extends AbstractDAO<Etat> {
    public EtatDAO(SessionFactory factory) {
        super(factory, new Etat());
    }
}
