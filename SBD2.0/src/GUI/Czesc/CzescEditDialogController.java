package GUI.Czesc;

import GUI.AlertHandler;
import Model.Czesc;
import Model.Kategoria;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.DatabaseException;
import sample.Main;

public class CzescEditDialogController {
    public ComboBox<String> KategoriaComboBox = new ComboBox<>();
    public TextField OpisTexfField;
    public TextField CenaZakupuTexfField;
    public TextField CenaSprzedazyTexfField;
    private Czesc czesc;
    private Stage dialogStage;
    private boolean isOKClicked = false;
    private Main main;
    private ObservableList<Kategoria> kategoriaObservableList;

    public boolean isOKClicked() {
        return isOKClicked;
    }

    public void setCzesc(Czesc czesc) {
        this.czesc = czesc;
        if (this.czesc != null && this.czesc.getId() != null) {
            Kategoria kategoriaOfCzesc = this.main.getDataBaseConnector().getKategoriaDAO().getKategoriaByNazwa(czesc.getNazwaKategorii());

            this.KategoriaComboBox.setValue(kategoriaOfCzesc.getNazwa());
            this.OpisTexfField.setText(kategoriaOfCzesc.getOpis());
            this.CenaZakupuTexfField.setText(this.czesc.getCenaZakupu().toString());
            if (this.czesc.getCenaSprzedazy() != null) {
                this.CenaSprzedazyTexfField.setText(this.czesc.getCenaSprzedazy().toString());
            } else {
                this.CenaSprzedazyTexfField.setText("");
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
        try {
            this.kategoriaObservableList = this.main.getDataBaseConnector().GetAllKategoria();
            this.kategoriaObservableList.forEach(k -> KategoriaComboBox.getItems().add(k.getNazwa()));
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
            this.czesc.setNazwaKategorii(this.kategoriaObservableList.stream().filter(k -> k.getNazwa().equals(this.KategoriaComboBox.getValue())).findFirst().get().getNazwa());
            this.czesc.setCenaZakupu(Double.valueOf(CenaZakupuTexfField.getText()));
            if (CenaSprzedazyTexfField.getText().length() > 0)
                this.czesc.setCenaSprzedazy(Double.valueOf(CenaSprzedazyTexfField.getText()));

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
        String errorMsg = "";
        if (!(this.CenaZakupuTexfField.getText().length() > 0 &&
                this.KategoriaComboBox.getValue() != null))
            errorMsg += "Proszę wypełnić kategorię i cenę zakupu. ";

        try {
            Double cenaZakupu = Double.valueOf(this.CenaZakupuTexfField.getText());
            Double cenaSprzedazy = null;

            if (this.CenaSprzedazyTexfField.getText().length() > 0)
                cenaSprzedazy = Double.valueOf(this.CenaSprzedazyTexfField.getText());

            if (cenaZakupu < 0 || (cenaSprzedazy != null && cenaSprzedazy < 0))
                errorMsg += "Ceny muszą być większe od zera. ";

        } catch (Exception e) {
            errorMsg += "Proszę wprowadzić prawidłowe wartości w pola ceny. ";
        }

        if (errorMsg.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Niepoprawne parametry");
            alert.setHeaderText("Wprowadzone nieprawidłowe dane");
            alert.setContentText(errorMsg);
            alert.showAndWait();
            return false;
        }
        return true;

    }

    public void setOpis(ActionEvent actionEvent) {
        if (this.KategoriaComboBox.getValue() != null)
            this.OpisTexfField.setText(this.kategoriaObservableList.stream().filter(k -> k.getNazwa().equals(this.KategoriaComboBox.getValue())).findFirst().get().getOpis());
    }
}
