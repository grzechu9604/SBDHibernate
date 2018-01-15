package GUI.Pojazd;

import GUI.AlertHandler;
import Model.Pojazd;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import sample.DatabaseException;
import sample.Main;

public class PojazdController {
    public TableView<Pojazd> PojazdyTab;
    public TableColumn<Pojazd, String> MarkaColumn;
    public TableColumn<Pojazd, String> NrRejestracyjnyColumn;
    public Label MarkaDataLabel;
    public Label NrRejestracyjnyDataLabel;
    public Label WlascicielDataLabel;
    private Main mainApp;

    @FXML
    private void handleNewPojazd(MouseEvent mouseEvent) {
        Pojazd p = new Pojazd();
        if (mainApp.showPojazdEditDialog(p)) {
            try {
                mainApp.getDataBaseConnector().getPojazdDAO().insert(p);
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
        }
        this.refreshList();
    }

    @FXML
    private void handleEditPojazd(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            Pojazd p = PojazdyTab.getSelectionModel().getSelectedItem();
            if (p != null) {
                if (mainApp.showPojazdEditDialog(p)) {
                    showPojazdData(p);
                    try {
                        this.mainApp.getDataBaseConnector().getPojazdDAO().update(p);
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
    private void handleDeletePojazd(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            try {
                this.mainApp.getDataBaseConnector().getPojazdDAO().delete(PojazdyTab.getSelectionModel().getSelectedItem());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
            this.refreshList();
        }
    }

    private void refreshList() {
        try {
            this.PojazdyTab.setItems(this.mainApp.getDataBaseConnector().GetAllPojazd());
        } catch (DatabaseException e) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(e.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    private void clearDataAllLabels() {
        this.MarkaDataLabel.setText("");
        this.NrRejestracyjnyDataLabel.setText("");
        this.WlascicielDataLabel.setText("");
    }

    private void showPojazdData(Pojazd p) {
        try {
            if (p != null && p.getNr_pojazdu() != null) {
                this.MarkaDataLabel.setText(p.getMarka());
                this.NrRejestracyjnyDataLabel.setText(p.getNr_rejestracyjny());

                String wlascicielName = p.getId_klienta() != null ?
                        this.mainApp.getDataBaseConnector().getKlientDAO().getKlientById(p.getId_klienta()).getImieINazwisko()
                        : this.mainApp.getDataBaseConnector().getFirmaDAO().getFirmaByNIP(p.getNip_firmy()).getNazwa();

                this.WlascicielDataLabel.setText(wlascicielName);
            } else
                this.clearDataAllLabels();
        } catch (DatabaseException de) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(de.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    private boolean isValidSelection() {
        int selectedIndex = PojazdyTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= PojazdyTab.getItems().size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak zaznaczenia");
            alert.setHeaderText("Nie zaznaczono pojazdu");
            alert.setContentText("Proszę wybrać pojazd z listy.");

            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void initialize() {
        this.MarkaColumn.setCellValueFactory(cellData -> cellData.getValue().getMarkaProperty());
        this.NrRejestracyjnyColumn.setCellValueFactory(cellData -> cellData.getValue().getNrRejestracyjnyProperty());
    }


    public void setApp(Main app) {
        this.mainApp = app;
        this.refreshList();
        initialize();

        clearDataAllLabels();

        this.PojazdyTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPojazdData(newValue)
        );
    }

    public void handleBackToMainMenu(MouseEvent mouseEvent) {
        this.mainApp.initMainMenu();
    }
}
