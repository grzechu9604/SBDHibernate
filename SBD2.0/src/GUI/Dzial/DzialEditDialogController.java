package GUI.Dzial;

import GUI.AlertHandler;
import Model.Dzial;
import Model.Pracownik;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.DatabaseException;
import sample.Main;

public class DzialEditDialogController {
    public TextField NazwaTexfField;
    public ComboBox<String> SzefComboBox = new ComboBox<>();
    private Dzial dzial;
    private Stage dialogStage;
    private boolean isOKClicked = false;
    private Main main;
    private ObservableList<Pracownik> pracownikObservableList;

    public boolean isOKClicked() {
        return isOKClicked;
    }

    public void setDzial(Dzial dzial) {
        this.dzial = dzial;
        if (this.dzial != null && this.dzial.getId() != null) {
            try {
                this.NazwaTexfField.setText(this.dzial.getNazwa());
                this.SzefComboBox.setValue(this.main.getDataBaseConnector().getPracownikDAO().getPracownikById(this.dzial.getIdSzefa()).getImieINazwisko());
            } catch (DatabaseException e) {
                AlertHandler ah = new AlertHandler();
                ah.setAlert(e.getMessage(), this.main);
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
        try {
            this.pracownikObservableList = this.main.getDataBaseConnector().GetAllPracownik();
            this.pracownikObservableList.forEach(p -> SzefComboBox.getItems().add(p.getImieINazwisko()));
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
            this.dzial.setNazwa(this.NazwaTexfField.getText());
            this.dzial.setIdSzefa(this.pracownikObservableList.stream().filter(p -> p.getImieINazwisko().equals(this.SzefComboBox.getValue())).findFirst().get().getId());
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
                this.SzefComboBox.getValue() != null)) {
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
