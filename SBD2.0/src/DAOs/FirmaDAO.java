package DAOs;

import Model.Firma;
import org.hibernate.SessionFactory;
import sample.DatabaseException;

public class FirmaDAO extends AbstractDAO<Firma>{
    public FirmaDAO(SessionFactory factory) {
        super(factory, new Firma());
    }

    public Firma getFirmaByNIP(String nip) throws DatabaseException {
        return this.getSingleByOneEqualCondition("nip", nip);
    }
}
