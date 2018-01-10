package DAOs;

import Model.Pracownik;
import org.hibernate.SessionFactory;

public class PracownikDAO extends AbstractDAO<Pracownik> {

    public PracownikDAO(SessionFactory factory) {
        super(factory, new Pracownik());
    }

    public Pracownik getPracownikById(Long id) {
        return this.getSingleByOneEqualCondition("id", id.toString());
    }
}




