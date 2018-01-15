package GUI.Pojazd;

import GUI.AlertHandler;
import Model.Firma;
import Model.Klient;
import Model.Pojazd;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.DatabaseException;
import sample.Main;

public class PojazdEditDialogController {
    public TextField MarkaTexfField;
    public TextField NrRejestracyjnyTexfField;
    public RadioButton FirmaRadioButton;
    public RadioButton KlientRadioButton;
    public ComboBox<String> FirmaComboBox;
    public ComboBox<String> KlientComboBox;
    private Pojazd pojazd;
    private Main main;
    private Stage dialogStage;
    private boolean isOKClicked = false;
    private boolean belongsToFirma;
    private ObservableList<Firma> firmaObservableList;
    private ObservableList<Klient> klientObservableList;

    public boolean isOKClicked() {
        return isOKClicked;
    }

    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
        if (this.pojazd != null && this.pojazd.getNr_pojazdu() != null) {
            try {
                this.MarkaTexfField.setText(this.pojazd.getMarka());
                this.NrRejestracyjnyTexfField.setText(this.pojazd.getNr_rejestracyjny());
                if (this.pojazd.getId_klienta() != null) {
                    this.FirmaComboBox.setVisible(false);
                    this.KlientComboBox.setVisible(true);
                    this.KlientRadioButton.fire();
                    this.KlientComboBox.setValue(this.main.getDataBaseConnector().getKlientDAO().getKlientById(this.pojazd.getId_klienta()).getImieINazwisko());
                } else {
                    this.FirmaComboBox.setVisible(true);
                    this.KlientComboBox.setVisible(false);
                    this.FirmaRadioButton.fire();
                    this.FirmaComboBox.setValue(this.main.getDataBaseConnector().getFirmaDAO().getFirmaByNIP(this.pojazd.getNip_firmy()).getNazwa());
                }
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.main);
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
        try {
            this.klientObservableList = this.main.getDataBaseConnector().GetAllKlient();
            this.klientObservableList.forEach(k -> KlientComboBox.getItems().add(k.getImieINazwisko()));

            this.firmaObservableList = this.main.getDataBaseConnector().GetAllFirma();
            this.firmaObservableList.forEach(f -> FirmaComboBox.getItems().add(f.getNazwa() + " " + f.getNip()));
        } catch (DatabaseException e) {
            AlertHandler ah = new AlertHandler();
            ah.setAlert(e.getMessage(), this.main);
        }
    }

    public void buttonAnulujClicked(MouseEvent mouseEvent) {
        getDialogStage().close();
    }

    public void buttonOKClicked(MouseEvent mouseEvent) {
        if (validateInputs()) {
            this.pojazd.setMarka(this.MarkaTexfField.getText());
            this.pojazd.setNr_rejestracyjny(this.NrRejestracyjnyTexfField.getText());

            if (this.KlientRadioButton.isSelected()) {
                this.pojazd.setNip_firmy(null);
                this.pojazd.setId_klienta(this.klientObservableList.stream().filter(k -> k.getImieINazwisko().equals(this.KlientComboBox.getValue())).findFirst().get().getId());
            }

            if (this.FirmaRadioButton.isSelected()) {
                this.pojazd.setId_klienta(null);
                this.pojazd.setNip_firmy(this.firmaObservableList.stream().filter(f -> (f.getNazwa() + " " + f.getNip()).equals(this.FirmaComboBox.getValue())).findFirst().get().getNip());
            }

            this.isOKClicked = true;
            this.dialogStage.close();
        }
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private boolean validateInputs() {
        if (!(this.MarkaTexfField.getText().length() > 0 &&
                this.NrRejestracyjnyTexfField.getText().length() > 0 &&
                ((this.FirmaRadioButton.isSelected() && this.FirmaComboBox.getValue() != null)
                        || (this.KlientRadioButton.isSelected() && this.KlientComboBox.getValue() != null)))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Proszę wypełnić wszystkie pola");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    private void handleToggleClick() {
        if (this.FirmaRadioButton.isSelected()) {
            this.FirmaComboBox.setVisible(true);
            this.KlientComboBox.setVisible(false);
        } else {
            this.FirmaComboBox.setVisible(false);
            this.KlientComboBox.setVisible(true);
        }
    }
}

