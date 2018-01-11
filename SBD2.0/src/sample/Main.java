package sample;

import DAOs.KlientDAO;
import GUI.KlientController;
import Model.Klient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private ObservableList<Klient> klientObservableList = FXCollections.observableArrayList();
    private static SessionFactory factory = HibernateUtils.getSessionFactory();
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.getPrimaryStage().setTitle("AddressApp");

        initRootLayout();

        showPersonOverview();
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Klient.fxml"));
            AnchorPane personOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            KlientController controller = loader.getController();
            controller.setApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Klient> getKlientsList() {
        klientObservableList.clear();

        KlientDAO klientDAO = new KlientDAO(factory);
        List<Klient> klientList = klientDAO.findAll();
        klientObservableList.addAll(klientList);

        return klientObservableList;
    }


    public static void main(String[] args) {
        /*
        SessionFactory factory = HibernateUtils.getSessionFactory();

        try{
            CzescDAO czescDAO = new CzescDAO(factory);
            List<Czesc> czescList = czescDAO.findAll();
            for ( Czesc c : czescList){
                System.out.println(c.getCenaZakupu());
            }

            DzialDAO dzialDAO = new DzialDAO(factory);
            List<Dzial> dzialList = dzialDAO.findAll();
            for ( Dzial d : dzialList){
                System.out.println(d.getNazwa());
                System.out.println(d.getSzef(factory).getImie());
            }

            EtatDAO etatDAO = new EtatDAO(factory);
            List<Etat> etatList = etatDAO.findAll();
            for (Etat e : etatList){
                System.out.println(e.getStawkaGodzinowa());
            }

            FirmaDAO firmaDAO = new FirmaDAO(factory);
            List<Firma> firmaList = firmaDAO.findAll();
            for ( Firma f : firmaList){
                System.out.println(f.getNazwa());
            }

            KategoriaDAO kategoriaDAO = new KategoriaDAO(factory);
            List<Kategoria> kategoriaList = kategoriaDAO.findAll();
            for ( Kategoria k : kategoriaList){
                System.out.println(k.getOpis());
            }

            KlientDAO klientDAO = new KlientDAO(factory);
            List<Klient> klientList = klientDAO.findAll();
            for ( Klient k : klientList){
                System.out.println(k.getRabat());
            }

            OsobaDAO osobaDAO = new OsobaDAO(factory);
            List<Osoba> osobaList = osobaDAO.findAll();
            for ( Osoba o : osobaList){
                System.out.println(o.getImie());
            }

            PojazdDAO pojazdDAO = new PojazdDAO(factory);
            List<Pojazd> pojazdy = pojazdDAO.findAll();
            for ( Pojazd p : pojazdy){
                System.out.println(p.getMarka());
            }

            PracownikDAO pracownikDAO = new PracownikDAO(factory);
            List<Pracownik> pracownikList = pracownikDAO.findAll();
            for ( Pracownik x : pracownikList){
                System.out.println(x.getImie() + " " + x.getNazwisko());
            }

            ZlecenieDAO zlecenieDAO = new ZlecenieDAO(factory);
            List<Zlecenie> zlecenieList = zlecenieDAO.findAll();
            for ( Zlecenie z : zlecenieList){
                System.out.println(z.getDataWplywu());
            }

            ZlecenieDzialConnectorDAO zlecenieDzialConnectorDAO = new ZlecenieDzialConnectorDAO(factory);
            List<ZlecenieDzialConnector> connectors = zlecenieDzialConnectorDAO.findAll();
            for (ZlecenieDzialConnector c : connectors) {
                System.out.println(c.getIdZlecenia());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            factory.close();
        }
        */
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
