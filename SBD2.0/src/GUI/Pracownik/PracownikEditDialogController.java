package GUI.Pracownik;

import GUI.AlertHandler;
import Model.Dzial;
import Model.Etat;
import Model.Pracownik;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.DatabaseException;
import sample.Main;

public class PracownikEditDialogController {
    public TextField ImieTexfField;
    public TextField NazwiskoTexfField;
    public TextField NrTelefonuTexfField;
    public ComboBox<String> DzialComboBox;
    public ComboBox<String> EtatComboBox;
    private Pracownik pracownik;
    private Stage dialogStage;
    private boolean isOKClicked = false;
    private Main main;
    private ObservableList<Dzial> dzialObservableList;
    private ObservableList<Etat> etatObservableList;

    public boolean isOKClicked() {
        return isOKClicked;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
        if (this.pracownik != null && this.pracownik.getId() != null) {
            try {
                this.ImieTexfField.setText(this.pracownik.getImie());
                this.NazwiskoTexfField.setText(this.pracownik.getNazwisko());
                this.NrTelefonuTexfField.setText(this.pracownik.getNr_telefonu());
                this.DzialComboBox.setValue(this.main.getDataBaseConnector().GetDzialById(this.pracownik.getNr_dzialu()).getNazwa());
                this.EtatComboBox.setValue(this.pracownik.getNazwa_etatu());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.main);
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
        try {
            this.etatObservableList = this.main.getDataBaseConnector().GetAllEtat();
            this.dzialObservableList = this.main.getDataBaseConnector().GetAllDzial();
            this.etatObservableList.forEach(e -> EtatComboBox.getItems().add(e.getNazwa()));
            this.dzialObservableList.forEach(d -> DzialComboBox.getItems().add(d.getNazwa()));
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
            this.pracownik.setImie(this.ImieTexfField.getText());
            this.pracownik.setNazwisko(this.NazwiskoTexfField.getText());
            this.pracownik.setNr_telefonu(this.NrTelefonuTexfField.getText());
            this.pracownik.setNazwa_etatu(this.EtatComboBox.getValue());
            this.pracownik.setNr_dzialu(this.dzialObservableList.stream().filter(d -> d.getNazwa().equals(this.DzialComboBox.getValue())).findFirst().get().getId());
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
        if (!(this.ImieTexfField.getText().length() > 0 &&
                this.NazwiskoTexfField.getText().length() > 0 &&
                this.NrTelefonuTexfField.getText().length() > 0 &&
                this.DzialComboBox.getValue() != null &&
                this.EtatComboBox.getValue() != null)) {
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
}
