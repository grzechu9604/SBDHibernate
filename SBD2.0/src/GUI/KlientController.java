package GUI;

import Model.Klient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
        this.KlientsTab.setItems(this.mainApp.getKlientsList());
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
        int selectedIndex = KlientsTab.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1 || selectedIndex >= KlientsTab.getItems().size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak zaznaczenia");
            alert.setHeaderText("Nie zaznaczono klienta");
            alert.setContentText("Proszę wybrać klienta z listy.");

            alert.showAndWait();
        } else {
            //TODO odwolanie do DAO i usunięcie klienta o ile to możliwe
            KlientsTab.getItems().remove(selectedIndex);
        }
    }

    private void showKlientData(Klient k) {
        if (k != null) {
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
}
