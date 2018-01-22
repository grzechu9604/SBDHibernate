package sample;

import GUI.Czesc.CzescController;
import GUI.Czesc.CzescEditDialogController;
import GUI.Dzial.DzialController;
import GUI.Dzial.DzialEditDialogController;
import GUI.Etat.EtatController;
import GUI.Etat.EtatEditDialogController;
import GUI.Firma.FirmaController;
import GUI.Firma.FirmaEditDialogController;
import GUI.Kategoria.KategoriaController;
import GUI.Kategoria.KategoriaEditDialogController;
import GUI.Klient.KlientController;
import GUI.Klient.KlientEditDialogController;
import GUI.Main.MainMenuController;
import GUI.Pojazd.PojazdController;
import GUI.Pojazd.PojazdEditDialogController;
import GUI.Pracownik.PracownikController;
import GUI.Pracownik.PracownikEditDialogController;
import GUI.Zlecenie.ZlecenieController;
import GUI.Zlecenie.ZlecenieCriteria;
import GUI.Zlecenie.ZlecenieEditDialogController;
import GUI.Zlecenie.ZlecenieFilterController;
import Model.*;
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

    public void initMainMenu() {
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

    public void showEtatOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Etat/Etat.fxml"));
            AnchorPane etatOverview = loader.load();

            rootLayout.setCenter(etatOverview);

            EtatController controller = loader.getController();
            controller.setApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDzialOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Dzial/Dzial.fxml"));
            AnchorPane dzialOverview = loader.load();

            rootLayout.setCenter(dzialOverview);

            DzialController controller = loader.getController();
            controller.setApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showKategoriaOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Kategoria/Kategoria.fxml"));
            AnchorPane kategoriaOverview = loader.load();

            rootLayout.setCenter(kategoriaOverview);

            KategoriaController controller = loader.getController();
            controller.setApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCzescOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Czesc/Czesc.fxml"));
            AnchorPane czescOverview = loader.load();

            rootLayout.setCenter(czescOverview);

            CzescController controller = loader.getController();
            controller.setApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPojazdOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Pojazd/Pojazd.fxml"));
            AnchorPane pojazdOverview = loader.load();

            rootLayout.setCenter(pojazdOverview);

            PojazdController controller = loader.getController();
            controller.setApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showZlecenieOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Zlecenie/Zlecenie.fxml"));
            AnchorPane zlecenieOverview = loader.load();

            rootLayout.setCenter(zlecenieOverview);

            ZlecenieController controller = loader.getController();
            controller.setApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showKlientEditDialog(Klient klient, Main main) {
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
            controller.setMain(main);
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

    public boolean showEtatEditDialog(Etat etat) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Etat/EtatEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Etat");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EtatEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEtat(etat);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showDzialEditDialog(Dzial dzial) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Dzial/DzialEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Dział");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            DzialEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setDzial(dzial);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showKategoriaEditDialog(Kategoria kategoria) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Kategoria/KategoriaEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Kategoria");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            KategoriaEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setKategoria(kategoria);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showCzescEditDialog(Czesc czesc) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Czesc/CzescEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Część");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CzescEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setCzesc(czesc);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showPojazdEditDialog(Pojazd pojazd) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Pojazd/PojazdEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Pojazd");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PojazdEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setPojazd(pojazd);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showZlecenieEditDialog(Zlecenie zlecenie) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Zlecenie/ZlecenieEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Zlecenie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ZlecenieEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setZlecenie(zlecenie);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOKClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ZlecenieCriteria showZleceniaFilter() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/Zlecenie/ZlecenieFilter.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Filtruj");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ZlecenieFilterController controller = loader.getController();
            controller.init(this, dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getCriteria();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
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
