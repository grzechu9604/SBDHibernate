package GUI.Klient;

import GUI.AlertHandler;
import Model.Klient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.DatabaseException;
import sample.Main;

public class KlientController {

    public AnchorPane MainAnchor;
    public SplitPane MainSplitPane;
    public AnchorPane LeftAnchorPane;
    public TableView<Klient> KlientsTab;
    public TableColumn<Klient, String> NazwiskoColumn;
    public TableColumn<Klient, String> ImieColumn;
    public AnchorPane RightAnchorPanne;
    public Label ImieDataLabel;
    public Label NazwiskoDataLabel;
    public Label NrTelefonuDataLabel;
    public Label RabatDataLabel;
    public Button NewKlientButton;
    public Button EditKlientButton;
    public Button DeleteKlientButton;
    private Main mainApp;

    public void setApp(Main app) {
        this.mainApp = app;
        this.refreshList();
        initialize();

        clearDataAllLabels();

        this.KlientsTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showKlientData(newValue)
        );
    }

    @FXML
    private void initialize() {
        this.ImieColumn.setCellValueFactory(cellData -> cellData.getValue().getImieProperty());
        this.NazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().getNazwiskoProperty());
    }

    @FXML
    private void handleDeleteKlient() {
        if (isValidSelection()) {
            try {
                this.mainApp.getDataBaseConnector().getKlientDAO().delete(KlientsTab.getSelectionModel().getSelectedItem());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
            this.refreshList();
        }
    }

    @FXML
    private void handleEditKlient() {
        if (isValidSelection()) {
            Klient k = KlientsTab.getSelectionModel().getSelectedItem();
            if (k != null) {
                if (mainApp.showKlientEditDialog(k)) {
                    showKlientData(k);
                    try {
                        this.mainApp.getDataBaseConnector().getKlientDAO().update(k);
                    } catch (DatabaseException e) {
                        AlertHandler ah = new AlertHandler();
                        ah.setAlert(e.getMessage(), this.mainApp);
                    }
                }
            }
            this.refreshList();
        }
    }

    @FXML
    private void handleNewKlient() {
        Klient k = new Klient();
        if (mainApp.showKlientEditDialog(k)) {
            try {
                mainApp.getDataBaseConnector().getKlientDAO().insert(k);
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
        }
        this.refreshList();
    }

    private boolean isValidSelection() {
        int selectedIndex = KlientsTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= KlientsTab.getItems().size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak zaznaczenia");
            alert.setHeaderText("Nie zaznaczono klienta");
            alert.setContentText("Proszę wybrać klienta z listy.");

            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void showKlientData(Klient k) {
        if (k != null && k.getNazwisko() != null) {
            this.ImieDataLabel.setText(k.getImie());
            this.NazwiskoDataLabel.setText(k.getNazwisko());
            this.NrTelefonuDataLabel.setText(k.getNr_telefonu());
            this.RabatDataLabel.setText(Double.toString(k.getRabat()));
        } else
            this.clearDataAllLabels();
    }

    private void clearDataAllLabels() {
        this.ImieDataLabel.setText("");
        this.NazwiskoDataLabel.setText("");
        this.NrTelefonuDataLabel.setText("");
        this.RabatDataLabel.setText("");
    }

    private void refreshList() {
        try {
            this.KlientsTab.setItems(this.mainApp.getDataBaseConnector().GetAllKlient());
        } catch (DatabaseException e) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(e.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    public void handleBackToMainMenu(MouseEvent mouseEvent) {
        this.mainApp.initMainMenu();
    }
}
