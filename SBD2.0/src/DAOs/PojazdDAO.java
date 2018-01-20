package DAOs;

import Model.Pojazd;
import org.hibernate.SessionFactory;
import sample.DatabaseException;

public class PojazdDAO extends AbstractDAO<Pojazd> {
    public PojazdDAO(SessionFactory factory) {
        super(factory, new Pojazd());
    }

    public Pojazd getPojazdByNrPojazdu(Long id) {
        return this.getPojazdBy("nr_pojazdu", id.toString());
    }

    public Pojazd getPojazdByNrRejestracyny(String rejestracja) {
        return this.getPojazdBy("nr_rejestracyjny", rejestracja);
    }

    private Pojazd getPojazdBy(String key, String value) {
        try {
            return this.getSingleByOneEqualCondition(key, value);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
