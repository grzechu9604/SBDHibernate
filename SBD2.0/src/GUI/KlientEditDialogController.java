package GUI;

import Model.Klient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class KlientEditDialogController {
    @FXML
    private TextField ImieTexfField;
    @FXML
    private TextField NazwiskoTexfField;
    @FXML
    private TextField NrTelefonuTexfField;
    @FXML
    private TextField RabatTexfField;
    @FXML
    private Button OKButton;
    @FXML
    private Button AnulujButton;
    private Klient klient;
    private Stage dialogStage;
    private boolean isOKClicked = false;

    public boolean isOKClicked() {
        return isOKClicked;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient k) {
        this.klient = k;
        if (this.klient != null) {
            this.ImieTexfField.setText(this.klient.getImie());
            this.NazwiskoTexfField.setText(this.klient.getNazwisko());
            this.NrTelefonuTexfField.setText(this.klient.getNr_telefonu());
            this.RabatTexfField.setText(this.klient.getRabat().toString());
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private boolean validateInputs() {
        String validationMessage = "";
        if (!(ImieTexfField.getText().length() > 0
                && NazwiskoTexfField.getText().length() > 0
                && NrTelefonuTexfField.getText().length() > 0
                && RabatTexfField.getText().length() > 0))
            validationMessage += "Proszę wypełnić wszystkie pola";

        try {
            if (!(0 <= Double.valueOf(RabatTexfField.getText())
                    && Double.valueOf(RabatTexfField.getText()) <= 1))
                validationMessage += "Rabat powinien być liczbą z zakresu 0 do 1";
        } catch (Exception e) {
            validationMessage += "Proszę wprowadzić poprawną liczbę";
        }

        if (validationMessage.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(validationMessage);

            alert.showAndWait();

            return false;
        } else
            return true;
    }

    @FXML
    private void buttonOKClicked() {
        if (validateInputs()) {

            this.klient.setImie(ImieTexfField.getText());
            this.klient.setNazwisko(NazwiskoTexfField.getText());
            this.klient.setNr_telefonu(NrTelefonuTexfField.getText());
            this.klient.setRabat(Double.valueOf(RabatTexfField.getText()));

            this.isOKClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void buttonAnulujClicked() {
        dialogStage.close();
    }
}
