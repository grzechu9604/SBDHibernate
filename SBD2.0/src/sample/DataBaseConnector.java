package sample;

import DAOs.KlientDAO;
import Model.Klient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataBaseConnector {
    public DataBaseConnector(SessionFactory factory) {
        this.factory = factory;
    }

    private SessionFactory factory;

    public void AddKlient(Klient k) {
        KlientDAO klientDAO = new KlientDAO(factory);
        klientDAO.insert(k);
    }

    public void DeleteKlient(Klient k) {
        KlientDAO klientDAO = new KlientDAO(factory);
        klientDAO.delete(k);
    }

    public void UpdateKlient(Klient k) {
        KlientDAO klientDAO = new KlientDAO(factory);
        klientDAO.update(k);
    }

    public ObservableList<Klient> GetAllKlient() {
        ObservableList<Klient> klientObservableList = FXCollections.observableArrayList();

        KlientDAO klientDAO = new KlientDAO(factory);
        List<Klient> klientList = klientDAO.findAll();
        klientObservableList.addAll(klientList);

        return klientObservableList;
    }

}
