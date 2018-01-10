package DAOs;

import Model.Firma;
import org.hibernate.SessionFactory;

public class FirmaDAO extends AbstractDAO<Firma>{
    public FirmaDAO(SessionFactory factory) {
        super(factory, new Firma());
    }
}
