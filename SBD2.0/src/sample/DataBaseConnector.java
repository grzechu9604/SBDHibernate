package sample;

import DAOs.*;
import Model.Dzial;
import Model.Etat;
import Model.Klient;
import Model.Pracownik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataBaseConnector {

    private final CzescDAO czescDAO;
    private final DzialDAO dzialDAO;
    private final EtatDAO etatDAO;
    private final FirmaDAO firmaDAO;
    private final KategoriaDAO kategoriaDAO;
    private final KlientDAO klientDAO;
    private final PojazdDAO pojazdDAO;
    private final PracownikDAO pracownikDAO;
    private final ZlecenieDAO zlecenieDAO;
    private final SessionFactory factory;

    public DataBaseConnector(SessionFactory factory) {
        this.factory = factory;
        this.czescDAO = new CzescDAO(this.factory);
        this.dzialDAO = new DzialDAO(this.factory);
        this.etatDAO = new EtatDAO(this.factory);
        this.firmaDAO = new FirmaDAO(this.factory);
        this.kategoriaDAO = new KategoriaDAO(this.factory);
        this.klientDAO = new KlientDAO(this.factory);
        this.pojazdDAO = new PojazdDAO(this.factory);
        this.pracownikDAO = new PracownikDAO(this.factory);
        this.zlecenieDAO = new ZlecenieDAO(this.factory);
    }

    public ObservableList<Klient> GetAllKlient() throws DatabaseException {
        ObservableList<Klient> klientObservableList = FXCollections.observableArrayList();

        KlientDAO klientDAO = new KlientDAO(getFactory());
        List<Klient> klientList = klientDAO.findAll();
        klientObservableList.addAll(klientList);

        return klientObservableList;
    }

    public ObservableList<Pracownik> GetAllPracownik() throws DatabaseException {
        ObservableList<Pracownik> pracownikObservableList = FXCollections.observableArrayList();

        PracownikDAO pracownikDAO = new PracownikDAO(getFactory());
        List<Pracownik> pracownikList = pracownikDAO.findAll();
        pracownikObservableList.addAll(pracownikList);

        return pracownikObservableList;
    }

    public Dzial GetDzialById(Long id) throws DatabaseException {
        DzialDAO dzialDAO = new DzialDAO(getFactory());
        return dzialDAO.getDzialById(id);
    }

    public ObservableList<Dzial> GetAllDzial() throws DatabaseException {
        ObservableList<Dzial> dzialObservableList = FXCollections.observableArrayList();

        DzialDAO dzialDAO = new DzialDAO(getFactory());
        List<Dzial> dzialList = dzialDAO.findAll();
        dzialObservableList.addAll(dzialList);

        return dzialObservableList;
    }

    public ObservableList<Etat> GetAllEtat() throws DatabaseException {
        ObservableList<Etat> etatObservableList = FXCollections.observableArrayList();

        EtatDAO etatDAO = new EtatDAO(getFactory());
        List<Etat> etatList = etatDAO.findAll();
        etatObservableList.addAll(etatList);

        return etatObservableList;
    }

    public CzescDAO getCzescDAO() {
        return czescDAO;
    }

    public DzialDAO getDzialDAO() {
        return dzialDAO;
    }

    public EtatDAO getEtatDAO() {
        return etatDAO;
    }

    public FirmaDAO getFirmaDAO() {
        return firmaDAO;
    }

    public KategoriaDAO getKategoriaDAO() {
        return kategoriaDAO;
    }

    public KlientDAO getKlientDAO() {
        return klientDAO;
    }

    public PojazdDAO getPojazdDAO() {
        return pojazdDAO;
    }

    public PracownikDAO getPracownikDAO() {
        return pracownikDAO;
    }

    public ZlecenieDAO getZlecenieDAO() {
        return zlecenieDAO;
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
