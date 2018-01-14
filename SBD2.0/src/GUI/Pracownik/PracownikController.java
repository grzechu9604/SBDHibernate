package GUI.Pracownik;

import Model.Pracownik;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Main;

public class PracownikController {
    public TableView<Pracownik> PracownikTab;
    public TableColumn<Pracownik, String> NazwiskoColumn;
    public TableColumn<Pracownik, String> ImieColumn;
    public Label ImieDataLabel;
    public Label NazwiskoDataLabel;
    public Label NrTelefonuDataLabel;
    public Label DzialDataLabel;
    public Label EtatLabel;
    public Button NewKlientButton;
    public Button EditKlientButton;
    public Button DeleteKlientButton;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        this.refreshList();

        initialize();
        clearDataAllLabels();

        this.PracownikTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPracownikData(newValue)
        );
    }

    private void initialize() {
        this.ImieColumn.setCellValueFactory(cellData -> cellData.getValue().getImieProperty());
        this.NazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().getNazwiskoProperty());
    }

    public void handleNewPracownik(MouseEvent mouseEvent) {
        Pracownik p = new Pracownik();
        if (mainApp.showPracownikEditDialog(p))
            mainApp.getDataBaseConnector().getPracownikDAO().insert(p);
        this.refreshList();
    }

    public void handleEditPracownik(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            Pracownik p = PracownikTab.getSelectionModel().getSelectedItem();
            if (p != null) {
                if (mainApp.showPracownikEditDialog(p)) {
                    showPracownikData(p);
                    this.mainApp.getDataBaseConnector().getPracownikDAO().update(p);
                }
            }
            this.refreshList();
        }
    }

    public void handleDeletePracownik(MouseEvent mouseEvent) {
        if (isValidSelection()) {
            this.mainApp.getDataBaseConnector().getPracownikDAO().delete(PracownikTab.getSelectionModel().getSelectedItem());
            this.refreshList();
        }
    }

    private void clearDataAllLabels() {
        this.ImieDataLabel.setText("");
        this.NazwiskoDataLabel.setText("");
        this.NrTelefonuDataLabel.setText("");
        this.EtatLabel.setText("");
        this.DzialDataLabel.setText("");
    }

    private void refreshList() {
        this.PracownikTab.setItems(this.mainApp.getDataBaseConnector().GetAllPracownik());
    }

    private boolean isValidSelection() {
        int selectedIndex = PracownikTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= PracownikTab.getItems().size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak zaznaczenia");
            alert.setHeaderText("Nie zaznaczono pracownika");
            alert.setContentText("Proszę wybrać pracownika z listy.");

            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void showPracownikData(Pracownik p) {
        if (p != null && p.getNazwisko() != null) {
            this.ImieDataLabel.setText(p.getImie());
            this.NazwiskoDataLabel.setText(p.getNazwisko());
            this.NrTelefonuDataLabel.setText(p.getNr_telefonu());
            this.EtatLabel.setText(p.getNazwa_etatu());
            this.DzialDataLabel.setText(this.mainApp.getDataBaseConnector().GetDzialById(p.getNr_dzialu()).getNazwa());
        } else
            this.clearDataAllLabels();
    }

}