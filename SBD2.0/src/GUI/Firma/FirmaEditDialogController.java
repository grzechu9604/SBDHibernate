package GUI.Firma;

import Model.Firma;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FirmaEditDialogController {
    public TextField NazwaTexfField;
    public TextField AdresTexfField;
    public TextField NIPTexfField;
    private Firma firma;
    private Stage dialogStage;
    private boolean isOKClicked = false;

    public boolean isOKClicked() {
        return isOKClicked;
    }

    public Firma getFirma() {
        return firma;
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setFirma(Firma f) {
        this.firma = f;
        if (this.firma != null) {
            this.NazwaTexfField.setText(this.firma.getNazwa());
            this.AdresTexfField.setText(this.firma.getAdres());
            this.NIPTexfField.setText(this.firma.getNip());
            this.NIPTexfField.setDisable(true);
        } else
            this.NIPTexfField.setDisable(false);
    }

    private boolean validateInputs() {
        String validationMessage = "";
        if (!(NazwaTexfField.getText().length() > 0
                && AdresTexfField.getText().length() > 0
                && NIPTexfField.getText().length() > 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Proszę wypełnić wszystkie pola");

            alert.showAndWait();

            return false;
        } else
            return true;
    }

    public void buttonAnulujClicked(MouseEvent mouseEvent) {
        dialogStage.close();
    }

    public void buttonOKClicked(MouseEvent mouseEvent) {
        if (validateInputs()) {

            this.firma.setNazwa(NazwaTexfField.getText());
            this.firma.setAdres(AdresTexfField.getText());
            this.firma.setNip(NIPTexfField.getText());

            this.isOKClicked = true;
            dialogStage.close();
        }
    }
}
