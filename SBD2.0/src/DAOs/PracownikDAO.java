package DAOs;

import Model.Pracownik;
import org.hibernate.SessionFactory;
import sample.DatabaseException;

public class PracownikDAO extends AbstractDAO<Pracownik> {

    public PracownikDAO(SessionFactory factory) {
        super(factory, new Pracownik());
    }

    public Pracownik getPracownikById(Long id) throws DatabaseException {
        return this.getSingleByOneEqualCondition("id", id.toString());
    }
}




