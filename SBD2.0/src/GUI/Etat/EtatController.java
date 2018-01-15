package GUI.Etat;

import GUI.AlertHandler;
import Model.Etat;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import sample.DatabaseException;
import sample.Main;

public class EtatController {
    public TableView<Etat> EtatTab;
    public TableColumn<Etat, String> NazwaColumn;
    public TableColumn<Etat, String> StawkaColumn;
    public Label NazwaDataLabel;
    public Label StawkaDataLabel;
    private Main mainApp;

    public void handleNewEtat(MouseEvent mouseEvent) {
        Etat e = new Etat();
        if (mainApp.showEtatEditDialog(e)) {
            try {
                mainApp.getDataBaseConnector().getEtatDAO().insert(e);
            } catch (DatabaseException de) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(de.getMessage(), this.mainApp);
            }
        }
        this.refreshList();
    }

    public void handleEditEtat(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            Etat e = EtatTab.getSelectionModel().getSelectedItem();
            if (e != null) {
                if (mainApp.showEtatEditDialog(e)) {
                    showEtatData(e);
                    try {
                        this.mainApp.getDataBaseConnector().getEtatDAO().update(e);
                    } catch (DatabaseException de) {
                        AlertHandler ah = new AlertHandler();
                        ah.setAlert(de.getMessage(), this.mainApp);
                    }
                }
            }
            this.refreshList();
        }
    }

    public void handleDeleteEtat(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            try {
                this.mainApp.getDataBaseConnector().getEtatDAO().delete(EtatTab.getSelectionModel().getSelectedItem());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
            this.refreshList();
        }
    }

    private void refreshList() {
        try {
            this.EtatTab.setItems(this.mainApp.getDataBaseConnector().GetAllEtat());
        } catch (DatabaseException e) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(e.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    private void clearDataAllLabels() {
        this.NazwaDataLabel.setText("");
        this.StawkaDataLabel.setText("");
    }

    private void showEtatData(Etat e) {
        if (e != null && e.getNazwa() != null) {
            this.NazwaDataLabel.setText(e.getNazwa());
            this.StawkaDataLabel.setText(e.getStawkaGodzinowa().toString());
        } else
            this.clearDataAllLabels();
    }

    private boolean isValidSelection() {
        int selectedIndex = EtatTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= EtatTab.getItems().size()) {
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
        this.StawkaColumn.setCellValueFactory(cellData -> cellData.getValue().getStawkaProperty());
    }


    public void setApp(Main app) {
        this.mainApp = app;
        this.refreshList();
        initialize();

        clearDataAllLabels();

        this.EtatTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEtatData(newValue)
        );
    }

    public void handleBackToMainMenu(MouseEvent mouseEvent) {
        this.mainApp.initMainMenu();
    }
}
