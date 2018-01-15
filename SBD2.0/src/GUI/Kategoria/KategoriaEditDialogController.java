package GUI.Kategoria;

import Model.Kategoria;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;

public class KategoriaEditDialogController {
    public TextField NazwaTexfField;
    public TextField OpisTexfField;
    private Kategoria kategoria;
    private Stage dialogStage;
    private boolean isOKClicked = false;
    private Main main;

    public boolean isOKClicked() {
        return isOKClicked;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
        if (this.kategoria != null && this.kategoria.getNazwa() != null) {
            this.NazwaTexfField.setText(this.kategoria.getNazwa());
            this.OpisTexfField.setText(this.kategoria.getOpis());
            this.NazwaTexfField.setDisable(true);
        } else
            this.NazwaTexfField.setDisable(false);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void buttonAnulujClicked(MouseEvent mouseEvent) {
        getDialogStage().close();
    }

    public void buttonOKClicked(MouseEvent mouseEvent) {
        if (validateInputs()) {
            this.kategoria.setNazwa(this.NazwaTexfField.getText());
            this.kategoria.setOpis(this.OpisTexfField.getText());
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
        if (!(this.NazwaTexfField.getText().length() > 0 &&
                this.OpisTexfField.getText().length() > 0)) {
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
