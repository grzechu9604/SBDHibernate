package GUI.Dzial;

import GUI.AlertHandler;
import Model.Dzial;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import sample.DatabaseException;
import sample.Main;

public class DzialController {
    public TableView<Dzial> DzialTab;
    public TableColumn<Dzial, String> NazwaColumn;
    public Label NazwaDataLabel;
    public Label SzefDataLabel;
    private Main mainApp;

    @FXML
    private void handleNewDzial(MouseEvent mouseEvent) {
        Dzial d = new Dzial();
        if (mainApp.showDzialEditDialog(d)) {
            try {
                mainApp.getDataBaseConnector().getDzialDAO().insert(d);
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
        }
        this.refreshList();
    }

    @FXML
    private void handleEditDzial(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            Dzial d = DzialTab.getSelectionModel().getSelectedItem();
            if (d != null) {
                if (mainApp.showDzialEditDialog(d)) {
                    showDzialData(d);
                    try {
                        this.mainApp.getDataBaseConnector().getDzialDAO().update(d);
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
    private void handleDeleteDzial(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            try {
                this.mainApp.getDataBaseConnector().getDzialDAO().delete(DzialTab.getSelectionModel().getSelectedItem());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
            this.refreshList();
        }
    }

    private void refreshList() {
        try {
            this.DzialTab.setItems(this.mainApp.getDataBaseConnector().GetAllDzial());
        } catch (DatabaseException e) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(e.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    private void clearDataAllLabels() {
        this.NazwaDataLabel.setText("");
        this.SzefDataLabel.setText("");
    }

    private void showDzialData(Dzial d) {
        try {
            if (d != null && d.getNazwa() != null) {
                this.NazwaDataLabel.setText(d.getNazwa());
                this.SzefDataLabel.setText(this.mainApp.getDataBaseConnector().getPracownikDAO().getPracownikById(d.getIdSzefa()).getImieINazwisko());
            } else
                this.clearDataAllLabels();
        } catch (DatabaseException de) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(de.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    private boolean isValidSelection() {
        int selectedIndex = DzialTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= DzialTab.getItems().size()) {
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
    }


    public void setApp(Main app) {
        this.mainApp = app;
        this.refreshList();
        initialize();

        clearDataAllLabels();

        this.DzialTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDzialData(newValue)
        );
    }

    public void handleBackToMainMenu(MouseEvent mouseEvent) {
        this.mainApp.initMainMenu();
    }
}
