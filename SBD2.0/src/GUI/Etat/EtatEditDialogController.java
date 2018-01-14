package GUI.Etat;

import Model.Etat;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EtatEditDialogController {
    public TextField NazwaTexfField;
    public TextField StawkaTexfField;
    private Etat etat;
    private Stage dialogStage;
    private boolean isOKClicked = false;

    public boolean isOKClicked() {
        return isOKClicked;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat e) {
        this.etat = e;
        if (this.etat != null && this.etat.getStawkaGodzinowa() != null) {
            this.NazwaTexfField.setText(this.etat.getNazwa());
            this.StawkaTexfField.setText(this.etat.getStawkaGodzinowa().toString());
            this.NazwaTexfField.setDisable(true);
        } else
            this.NazwaTexfField.setDisable(false);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private boolean validateInputs() {
        String validationMessage = "";
        if (!(NazwaTexfField.getText().length() > 0
                && StawkaTexfField.getText().length() > 0))
            validationMessage += "Proszę wypełnić wszystkie pola ";

        try {
            Double.valueOf(StawkaTexfField.getText());
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

    public void buttonAnulujClicked(MouseEvent mouseEvent) {
        dialogStage.close();
    }

    public void buttonOKClicked(MouseEvent mouseEvent) {
        if (validateInputs()) {

            this.etat.setNazwa(NazwaTexfField.getText());
            this.etat.setStawkaGodzinowa(Double.valueOf(StawkaTexfField.getText()));

            this.isOKClicked = true;
            dialogStage.close();
        }
    }
}

