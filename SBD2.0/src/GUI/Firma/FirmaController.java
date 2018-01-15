package GUI.Firma;

import GUI.AlertHandler;
import Model.Firma;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import sample.DatabaseException;
import sample.Main;

public class FirmaController {
    @FXML
    private TableView<Firma> FirmaTab;
    @FXML
    private TableColumn<Firma, String> NazwaColumn;
    @FXML
    private TableColumn<Firma, String> AdresColumn;
    @FXML
    private Label NazwaDataLabel;
    @FXML
    private Label AdresDataLabel;
    @FXML
    private Label NIPDataLabel;


    private Main mainApp;

    @FXML
    private void handleNewFrima(MouseEvent mouseEvent) {
        Firma f = new Firma();
        if (mainApp.showFirmaEditDialog(f)) {
            try {
                mainApp.getDataBaseConnector().getFirmaDAO().insert(f);
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
        }
        this.refreshList();
    }

    @FXML
    private void handleEditFrima(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            Firma f = FirmaTab.getSelectionModel().getSelectedItem();
            if (f != null) {
                if (mainApp.showFirmaEditDialog(f)) {
                    showFirmaData(f);
                    try {
                        this.mainApp.getDataBaseConnector().getFirmaDAO().update(f);
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
    private void handleDeleteFrima(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            try {
                this.mainApp.getDataBaseConnector().getFirmaDAO().delete(FirmaTab.getSelectionModel().getSelectedItem());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
            this.refreshList();
        }
    }

    private void refreshList() {
        try {
            this.FirmaTab.setItems(this.mainApp.getDataBaseConnector().GetAllFirma());
        } catch (DatabaseException e) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(e.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    private void clearDataAllLabels() {
        this.NazwaDataLabel.setText("");
        this.NIPDataLabel.setText("");
        this.AdresDataLabel.setText("");
    }

    private void showFirmaData(Firma f) {
        if (f != null && f.getNazwa() != null) {
            this.NazwaDataLabel.setText(f.getNazwa());
            this.NIPDataLabel.setText(f.getNip());
            this.AdresDataLabel.setText(f.getAdres());
        } else
            this.clearDataAllLabels();
    }

    private boolean isValidSelection() {
        int selectedIndex = FirmaTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= FirmaTab.getItems().size()) {
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

    private void initialize() {
        this.NazwaColumn.setCellValueFactory(cellData -> cellData.getValue().getNazwaProperty());
        this.AdresColumn.setCellValueFactory(cellData -> cellData.getValue().getAdresProperty());
    }


    public void setApp(Main app) {
        this.mainApp = app;
        this.refreshList();
        initialize();

        clearDataAllLabels();

        this.FirmaTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFirmaData(newValue)
        );
    }

    public void handleBackToMainMenu(MouseEvent mouseEvent) {
        this.mainApp.initMainMenu();
    }
}
