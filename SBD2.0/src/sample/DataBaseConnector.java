package sample;

import DAOs.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.SessionFactory;

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

        klientObservableList.addAll(klientDAO.findAll());

        return klientObservableList;
    }

    public ObservableList<Pracownik> GetAllPracownik() throws DatabaseException {
        ObservableList<Pracownik> pracownikObservableList = FXCollections.observableArrayList();

        pracownikObservableList.addAll(pracownikDAO.findAll());

        return pracownikObservableList;
    }

    public ObservableList<Firma> GetAllFirma() throws DatabaseException {
        ObservableList<Firma> firmaObservableList = FXCollections.observableArrayList();

        firmaObservableList.addAll(firmaDAO.findAll());

        return firmaObservableList;
    }

    public Dzial GetDzialById(Long id) throws DatabaseException {
        return dzialDAO.getDzialById(id);
    }

    public ObservableList<Dzial> GetAllDzial() throws DatabaseException {
        ObservableList<Dzial> dzialObservableList = FXCollections.observableArrayList();

        dzialObservableList.addAll(dzialDAO.findAll());

        return dzialObservableList;
    }

    public ObservableList<Etat> GetAllEtat() throws DatabaseException {
        ObservableList<Etat> etatObservableList = FXCollections.observableArrayList();

        etatObservableList.addAll(etatDAO.findAll());

        return etatObservableList;
    }

    public ObservableList<Kategoria> GetAllKategoria() throws DatabaseException {
        ObservableList<Kategoria> kategoriaObservableList = FXCollections.observableArrayList();

        kategoriaObservableList.addAll(kategoriaDAO.findAll());

        return kategoriaObservableList;
    }

    public ObservableList<Czesc> GetAllCzesc() throws DatabaseException {
        ObservableList<Czesc> czescObservableList = FXCollections.observableArrayList();

        czescObservableList.addAll(czescDAO.findAll());

        return czescObservableList;
    }

    public ObservableList<Pojazd> GetAllPojazd() throws DatabaseException {
        ObservableList<Pojazd> pojazdObservableList = FXCollections.observableArrayList();

        pojazdObservableList.addAll(pojazdDAO.findAll());

        return pojazdObservableList;
    }

    public ObservableList<Zlecenie> GetAllZlecenie() throws DatabaseException {
        ObservableList<Zlecenie> zlecenieObservableList = FXCollections.observableArrayList();

        zlecenieObservableList.addAll(zlecenieDAO.findAll());

        return zlecenieObservableList;
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
