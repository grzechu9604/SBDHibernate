package GUI.Zlecenie;

import GUI.AlertHandler;
import Model.Czesc;
import Model.Dzial;
import Model.Zlecenie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.CheckComboBox;
import sample.DatabaseException;
import sample.Main;

import java.util.LinkedList;
import java.util.List;

public class ZlecenieController {
    public TableView<Zlecenie> NaprawaTab;
    public TableColumn<Zlecenie, String> NrRejestracyjnyColumn;
    public TableColumn<Zlecenie, String> DataWplywuColumn;
    public Label NrRejestracyjnyDataLabel;
    public Label DataWplywuDataLabel;
    public Label DataPlanowanegoZakonczniaDataLabel;
    public Label DataOdbioruDataLabel;
    public CheckComboBox<Dzial> DzialyBox;
    public CheckComboBox<Czesc> CzesciBox;
    public Label CenaLabel;
    private Main mainApp;

    @FXML
    private void handleNewZlecenie(MouseEvent mouseEvent) {
        Zlecenie z = new Zlecenie();
        if (mainApp.showZlecenieEditDialog(z)) {
            try {
                mainApp.getDataBaseConnector().getZlecenieDAO().insert(z);
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
        }
        this.refreshList();
    }

    @FXML
    private void handleEditZlecenie(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            Zlecenie z = NaprawaTab.getSelectionModel().getSelectedItem();
            if (z != null) {
                if (mainApp.showZlecenieEditDialog(z)) {
                    try {
                        this.mainApp.getDataBaseConnector().getZlecenieDAO().update(z);
                    } catch (DatabaseException e) {
                        AlertHandler ah = new AlertHandler();
                        ah.setAlert(e.getMessage(), this.mainApp);
                    }
                    showZlecenieData(z);
                }
            }
            this.refreshList();
        }
    }

    @FXML
    private void handleDeleteZlecenie(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            try {
                this.mainApp.getDataBaseConnector().getZlecenieDAO().delete(NaprawaTab.getSelectionModel().getSelectedItem());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }
            this.refreshList();
        }
    }

    private void refreshList() {
        try {
            this.NaprawaTab.setItems(this.mainApp.getDataBaseConnector().GetAllZlecenie());
        } catch (DatabaseException e) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(e.getMessage(), this.mainApp);
            this.clearDataAllLabels();
        }
    }

    private void clearDataAllLabels() {
        this.NrRejestracyjnyDataLabel.setText("");
        this.DataWplywuDataLabel.setText("");
        this.DataPlanowanegoZakonczniaDataLabel.setText("");
        this.DataOdbioruDataLabel.setText("");
        this.CenaLabel.setText("");
    }

    private void showZlecenieData(Zlecenie z) {
        this.DzialyBox.getCheckModel().clearChecks();
        this.DzialyBox.getItems().clear();

        this.CzesciBox.getCheckModel().clearChecks();
        this.CzesciBox.getItems().clear();

        if (z != null && z.getId() != null) {

            this.NrRejestracyjnyDataLabel.setText(this.mainApp.getDataBaseConnector().getPojazdDAO().getPojazdByNrPojazdu(z.getIdPojazdu()).getNr_rejestracyjny());
            this.DataWplywuDataLabel.setText(z.getDataWplywu().toString());
            this.DataPlanowanegoZakonczniaDataLabel.setText(z.getPlanowaneZakonczenie().toString());
            if (z.getDataOdbioru() != null)
                this.DataOdbioruDataLabel.setText(z.getDataOdbioru().toString());
            else
                this.DataOdbioruDataLabel.setText("");
            this.CenaLabel.setText(z.getCena().toString());

            try {
                List<Dzial> connectedDzials = new LinkedList<>();
                List<Czesc> connectedCzesc = new LinkedList<>();

                connectedDzials.addAll(this.mainApp.getDataBaseConnector().getDzialDAO().getDzialsConnectedToZlecenie(z.getId()));
                connectedCzesc.addAll(this.mainApp.getDataBaseConnector().getCzescDAO().GetAssignedCzescToZlecenie(z.getId()));

                for (Dzial dzial : connectedDzials) {
                    this.DzialyBox.getItems().add(dzial);
                    this.DzialyBox.getCheckModel().checkAll();
                }

                for (Czesc czesc : connectedCzesc) {
                    this.CzesciBox.getItems().add(czesc);
                    this.CzesciBox.getCheckModel().checkAll();
                }

                this.DzialyBox.setDisable(true);
                this.CzesciBox.setDisable(true);

            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.mainApp);
            }

        } else
            this.clearDataAllLabels();
    }

    private boolean isValidSelection() {
        int selectedIndex = NaprawaTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= NaprawaTab.getItems().size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak zaznaczenia");
            alert.setHeaderText("Nie zaznaczono zlecenia");
            alert.setContentText("Proszę wybrać naprawę z listy.");

            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void initialize() {
        this.DataWplywuColumn.setCellValueFactory(cellData -> cellData.getValue().getDataWplywyProperty());
        this.NrRejestracyjnyColumn.setCellValueFactory(cellData -> this.mainApp.getDataBaseConnector().getPojazdDAO().getPojazdByNrPojazdu(cellData.getValue().getIdPojazdu()).getNrRejestracyjnyProperty());
    }


    public void setApp(Main app) {
        this.mainApp = app;
        this.refreshList();
        initialize();

        clearDataAllLabels();

        this.NaprawaTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showZlecenieData(newValue)
        );
    }

    public void handleBackToMainMenu(MouseEvent mouseEvent) {
        this.mainApp.initMainMenu();
    }

}
