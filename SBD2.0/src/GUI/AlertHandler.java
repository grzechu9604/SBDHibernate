package GUI;

import javafx.scene.control.Alert;
import sample.Main;

public class AlertHandler {
    public void setAlert(String msg, Main mainApp) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Błąd");
        alert.setHeaderText("Błąd");
        alert.setContentText(msg);

        alert.showAndWait();
    }
}
