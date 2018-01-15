package GUI.Kategoria;

import GUI.AlertHandler;
import Model.Kategoria;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import sample.DatabaseException;
import sample.Main;

public class KategoriaController {
    public TableView<Kategoria> KategoriaTab;
    public TableColumn<Kategoria, String> NazwaColumn;
    public Label NazwaDataLabel;
    public Label OpisDataLabel;
    private Main mainApp;

    @FXML
    private void handleNewKategoria(MouseEvent mouseEvent) {
        Kategoria k = new Kategoria();
        if (mainApp.showKategoriaEditDialog(k)) {
            try {
                mainApp.getDataBaseConnector().getKategoriaDAO().insert(k);
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
        }
        this.refreshList();
    }

    @FXML
    private void handleEditKategoria(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            Kategoria k = KategoriaTab.getSelectionModel().getSelectedItem();
            if (k != null) {
                if (mainApp.showKategoriaEditDialog(k)) {
                    showKategoriaData(k);
                    try {
                        this.mainApp.getDataBaseConnector().getKategoriaDAO().update(k);
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
    private void handleDeleteKategoria(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            try {
                this.mainApp.getDataBaseConnector().getKategoriaDAO().delete(KategoriaTab.getSelectionModel().getSelectedItem());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
            this.refreshList();
        }
    }

    private void refreshList() {
        try {
            this.KategoriaTab.setItems(this.mainApp.getDataBaseConnector().GetAllKategoria());
        } catch (DatabaseException e) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(e.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    private void clearDataAllLabels() {
        this.NazwaDataLabel.setText("");
        this.OpisDataLabel.setText("");
    }

    private void showKategoriaData(Kategoria k) {
        if (k != null && k.getNazwa() != null) {
            this.NazwaDataLabel.setText(k.getNazwa());
            this.OpisDataLabel.setText(k.getOpis());
        } else
            this.clearDataAllLabels();
    }

    private boolean isValidSelection() {
        int selectedIndex = KategoriaTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= KategoriaTab.getItems().size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak zaznaczenia");
            alert.setHeaderText("Nie zaznaczono Kategorii");
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

        this.KategoriaTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showKategoriaData(newValue)
        );
    }

    public void handleBackToMainMenu(MouseEvent mouseEvent) {
        this.mainApp.initMainMenu();
    }

}
