package GUI.Zlecenie;

import Model.Dzial;
import Model.Pojazd;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import sample.DatabaseException;
import sample.Main;

import java.time.ZoneId;
import java.util.Date;

public class ZlecenieFilterController {
    public CheckComboBox<Pojazd> RejestracjaComboBox;
    public DatePicker DataWplywuOdPicker;
    public DatePicker DataWplywuDoPicker;
    public DatePicker DataZakOdPicker;
    public DatePicker DataZakDoPicker;
    public DatePicker DataOdbOdPicker;
    public DatePicker DataOdbDoPicker;
    public CheckComboBox<Dzial> DzialyComboBox;
    public TextField CenaOdField;
    public TextField CenaDoField;
    private Stage dialogStage;
    private ZlecenieCriteria criteria;
    private Main main;


    public void init(Main main, Stage dialogStage) {
        this.main = main;
        this.dialogStage = dialogStage;
        try {
            DzialyComboBox.getItems().addAll(this.main.getDataBaseConnector().GetAllDzial());
            RejestracjaComboBox.getItems().addAll(this.main.getDataBaseConnector().GetAllPojazd());
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }


    public ZlecenieCriteria getCriteria() {
        return criteria;
    }

    private void generateCriteria() {
        this.criteria = new ZlecenieCriteria();
        criteria.setCenaOd(tryParseDouble(CenaOdField));
        criteria.setCenaDo(tryParseDouble(CenaDoField));
        criteria.setDataOdbDo(tryParseDate(DataOdbDoPicker));
        criteria.setDataOdbOd(tryParseDate(DataOdbOdPicker));
        criteria.setDataWplywuDo(tryParseDate(DataWplywuDoPicker));
        criteria.setDataWplywuOd(tryParseDate(DataWplywuOdPicker));
        criteria.setDataZakDo(tryParseDate(DataZakDoPicker));
        criteria.setDataZakOd(tryParseDate(DataZakOdPicker));
        criteria.setDzialy(DzialyComboBox.getCheckModel().getCheckedItems());
        criteria.setPojazdy(RejestracjaComboBox.getCheckModel().getCheckedItems());
    }

    private Double tryParseDouble(TextField t) {
        try {
            return Double.valueOf(t.getText());
        } catch (Exception e) {
            return null;
        }
    }

    private Date tryParseDate(DatePicker dp) {
        try {
            return Date.from(dp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            return null;
        }

    }

    public void buttonOKClicked(MouseEvent mouseEvent) {
        this.generateCriteria();
        this.dialogStage.close();
    }

    public void buttonAnulujClicked(MouseEvent mouseEvent) {
        this.dialogStage.close();
    }
}
