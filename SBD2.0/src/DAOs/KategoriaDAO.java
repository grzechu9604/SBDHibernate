package DAOs;

import Model.Kategoria;
import org.hibernate.SessionFactory;
import sample.DatabaseException;

public class KategoriaDAO extends AbstractDAO<Kategoria> {
    public KategoriaDAO(SessionFactory factory) {
        super(factory, new Kategoria());
    }

    public Kategoria getKategoriaByNazwa(String nazwa) {
        try {
            return this.getSingleByOneEqualCondition("nazwa", nazwa);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
