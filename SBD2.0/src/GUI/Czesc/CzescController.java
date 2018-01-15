package GUI.Czesc;

import GUI.AlertHandler;
import Model.Czesc;
import Model.Kategoria;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import sample.DatabaseException;
import sample.Main;

public class CzescController {
    public TableView<Czesc> CzesciTab;
    public Label KategoriaDataLabel;
    public Label OpisDataLabel;
    public Label CenaZakupuDataLabel;
    public Label CenaSprzedazyDataLabel;
    public TableColumn<Czesc, String> OpisColumn;
    public TableColumn<Czesc, String> CenaZakupuColumn;
    private Main mainApp;

    @FXML
    private void handleNewCzesc(MouseEvent mouseEvent) {
        Czesc c = new Czesc();
        if (mainApp.showCzescEditDialog(c)) {
            try {
                mainApp.getDataBaseConnector().getCzescDAO().insert(c);
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
        }
        this.refreshList();
    }

    @FXML
    private void handleEditCzesc(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            Czesc c = CzesciTab.getSelectionModel().getSelectedItem();
            if (c != null) {
                if (mainApp.showCzescEditDialog(c)) {
                    showCzescData(c);
                    try {
                        this.mainApp.getDataBaseConnector().getCzescDAO().update(c);
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
                this.mainApp.getDataBaseConnector().getCzescDAO().delete(CzesciTab.getSelectionModel().getSelectedItem());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
            this.refreshList();
        }
    }

    private void refreshList() {
        try {
            this.CzesciTab.setItems(this.mainApp.getDataBaseConnector().GetAllCzesc());
        } catch (DatabaseException e) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(e.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    private void clearDataAllLabels() {
        this.KategoriaDataLabel.setText("");
        this.OpisDataLabel.setText("");
        this.CenaZakupuDataLabel.setText("");
        this.CenaSprzedazyDataLabel.setText("");
    }

    private void showCzescData(Czesc c) {
        if (c != null && c.getCenaSprzedazy() != null) {
            Kategoria kategoriaOfCzesc = this.mainApp.getDataBaseConnector().getKategoriaDAO().getKategoriaByNazwa(c.getNazwaKategorii());
            this.KategoriaDataLabel.setText(kategoriaOfCzesc.getNazwa());
            this.OpisDataLabel.setText(kategoriaOfCzesc.getOpis());
            this.CenaZakupuDataLabel.setText(c.getCenaZakupu().toString());
            if (c.getCenaSprzedazy() != null) {
                this.CenaSprzedazyDataLabel.setText(c.getCenaSprzedazy().toString());
            } else
                this.CenaSprzedazyDataLabel.setText("");
        } else
            this.clearDataAllLabels();
    }

    private boolean isValidSelection() {
        int selectedIndex = CzesciTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= CzesciTab.getItems().size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak zaznaczenia");
            alert.setHeaderText("Nie zaznaczono części");
            alert.setContentText("Proszę wybrać część z listy.");

            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void initialize() {

        this.OpisColumn.setCellValueFactory(cellData -> cellData.getValue()
                .getKategoria(this.mainApp.getDataBaseConnector().getKategoriaDAO()).getNazwaProperty());
        this.CenaZakupuColumn.setCellValueFactory(cellData -> cellData.getValue().getCenaZakupuProperty());
    }


    public void setApp(Main app) {
        this.mainApp = app;
        this.refreshList();
        initialize();

        clearDataAllLabels();

        this.CzesciTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCzescData(newValue)
        );
    }

    public void handleBackToMainMenu(MouseEvent mouseEvent) {
        this.mainApp.initMainMenu();
    }
}

