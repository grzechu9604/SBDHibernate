package GUI.Zlecenie;

import Model.Czesc;
import Model.Dzial;
import Model.Pojazd;
import Model.Zlecenie;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import sample.DatabaseException;
import sample.Main;

import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ZlecenieEditDialogController {
    public ComboBox<Pojazd> RejestracjaComboBox;
    public DatePicker DataWplywuPicker;
    public DatePicker DataZakPicker;
    public DatePicker DataOdbPicker;
    public CheckComboBox<Dzial> DzialyComboBox;
    public CheckComboBox<Czesc> CzesciComboBox;
    public TextField CenaField;
    private Zlecenie zlecenie;
    private Main main;
    private Stage dialogStage;
    private boolean isOKClicked = false;

    public boolean isOKClicked() {
        return isOKClicked;
    }

    public void setZlecenie(Zlecenie zlecenie) {
        this.zlecenie = zlecenie;
        try {
            this.DzialyComboBox.getItems().addAll(this.main.getDataBaseConnector().GetAllDzial());
            this.RejestracjaComboBox.getItems().addAll(this.main.getDataBaseConnector().GetAllPojazd());
            this.CzesciComboBox.getItems().addAll(this.main.getDataBaseConnector().getCzescDAO().GetUnassignedCzesc());
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        if (zlecenie.getId() != null) {
            this.RejestracjaComboBox.setValue(this.main.getDataBaseConnector().getPojazdDAO().getPojazdByNrPojazdu(this.zlecenie.getIdPojazdu()));
            this.DataWplywuPicker.setValue(this.zlecenie.getDataWplywu().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.DataZakPicker.setValue(this.zlecenie.getPlanowaneZakonczenie().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            if (this.zlecenie.getDataOdbioru() != null)
                this.DataOdbPicker.setValue(this.zlecenie.getDataOdbioru().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.CenaField.setText(this.zlecenie.getCena().toString());
            try {
                List<Czesc> asignedCzesc = this.main.getDataBaseConnector().getCzescDAO().GetAssignedCzescToZlecenie(zlecenie.getId());
                this.CzesciComboBox.getItems().addAll(asignedCzesc);
                asignedCzesc.forEach(c -> this.CzesciComboBox.getCheckModel().check(c));

                List<Dzial> assignedDzials = this.main.getDataBaseConnector().getDzialDAO().getDzialsConnectedToZlecenie(zlecenie.getId());
                assignedDzials.forEach(d -> this.DzialyComboBox.getCheckModel().check(d));
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void buttonAnulujClicked(MouseEvent mouseEvent) {
        getDialogStage().close();
    }

    public void buttonOKClicked(MouseEvent mouseEvent) {
        if (validateInputs()) {

            this.zlecenie.setIdPojazdu(this.RejestracjaComboBox.getValue().getNr_pojazdu());
            this.zlecenie.setCena(Double.valueOf(CenaField.getText()));
            this.zlecenie.setPlanowaneZakonczenie(Date.from(DataZakPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            this.zlecenie.setDataWplywu(Date.from(DataWplywuPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            this.zlecenie.setConnectorsToDzial(getOdpowiedzialneDzialy());
            this.zlecenie.setUzyteCzesci(getUzyteCzesci());

            if (DataOdbPicker.getValue() != null)
                this.zlecenie.setDataOdbioru(Date.from(DataOdbPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            this.isOKClicked = true;
            dialogStage.close();
        }
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private boolean validateInputs() {
        String validationMessage = "";

        if (!(this.RejestracjaComboBox.getValue() != null &&
                this.DataWplywuPicker.getValue() != null &&
                this.DataZakPicker.getValue() != null &&
                this.CenaField.getText().length() > 0 &&
                this.DataWplywuPicker.getValue().isBefore(this.DataZakPicker.getValue()) &&
                (this.DataOdbPicker.getValue() == null
                        ||
                        (this.DataWplywuPicker.getValue().isBefore(this.DataOdbPicker.getValue())))
        ))
            validationMessage += "Proszę wprowadzić porpawne dane";

        try {
            if (0 > Double.valueOf(CenaField.getText()))
                validationMessage += "Proszę wprowadzić poprawną cenę";
        } catch (Exception e) {
            validationMessage += "Proszę wprowadzić poprawną cenę";
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

    private LinkedList<Czesc> getUzyteCzesci() {
        LinkedList<Czesc> assignedCzesci = new LinkedList<>();
        assignedCzesci.addAll(this.CzesciComboBox.getCheckModel().getCheckedItems());
        return assignedCzesci;
    }

    private LinkedList<Dzial> getOdpowiedzialneDzialy() {
        LinkedList<Dzial> connectedDzials = new LinkedList<>();
        connectedDzials.addAll(this.DzialyComboBox.getCheckModel().getCheckedItems());
        return connectedDzials;
    }
}
