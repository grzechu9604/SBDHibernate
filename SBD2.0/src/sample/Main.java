package sample;

import GUI.Firma.FirmaController;
import GUI.Firma.FirmaEditDialogController;
import GUI.Klient.KlientController;
import GUI.Klient.KlientEditDialogController;
import GUI.Main.MainMenuController;
import GUI.Pracownik.PracownikController;
import GUI.Pracownik.PracownikEditDialogController;
import Model.Firma;
import Model.Klient;
import Model.Pracownik;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;

public class Main extends Application {

    private static SessionFactory factory = HibernateUtils.getSessionFactory();
    private Stage primaryStage;
    private BorderPane rootLayout;
    private DataBaseConnector dataBaseConnector;

    public DataBaseConnector getDataBaseConnector() {
        return dataBaseConnector;
    }

    @Override
    public void start(Stage primaryStage) {
        this.dataBaseConnector = new DataBaseConnector(factory);

        this.primaryStage = primaryStage;
        this.getPrimaryStage().setTitle("AddressApp");

        initRootLayout();

        initMainMenu();
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

    private void initMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Main/MainMenu.fxml"));
            AnchorPane MainMenu = loader.load();

            rootLayout.setCenter(MainMenu);

            MainMenuController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showKlientOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Klient/Klient.fxml"));
            AnchorPane KlientOverview = loader.load();

            rootLayout.setCenter(KlientOverview);

            KlientController controller = loader.getController();
            controller.setApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPracownikOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Pracownik/Pracownik.fxml"));
            AnchorPane PracownikOverview = loader.load();

            rootLayout.setCenter(PracownikOverview);

            PracownikController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFirmaOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Firma/Firma.fxml"));
            AnchorPane firmaOverview = loader.load();

            rootLayout.setCenter(firmaOverview);

            FirmaController controller = loader.getController();
            controller.setApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showKlientEditDialog(Klient klient) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Klient/KlientEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Klient");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            KlientEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setKlient(klient);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showFirmaEditDialog(Firma firma) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Firma/FirmaEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Firma");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            FirmaEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setFirma(firma);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showPracownikEditDialog(Pracownik pracownik) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Pracownik/PracownikEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("PRACOWNIK");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PracownikEditDialogController controller = loader.getController();
            controller.setMain(this);
            controller.setDialogStage(dialogStage);
            controller.setPracownik(pracownik);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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


        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
