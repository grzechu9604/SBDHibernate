package GUI.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.Main;

public class MainMenuController {
    @FXML
    private Button ClientsButton;
    @FXML
    private Button FirmsButton;
    @FXML
    private Button WorkersButton;
    @FXML
    private Button RepairsButton;
    @FXML
    private Button CategoriesButton;
    @FXML
    private Button DepartmentsButton;
    @FXML
    private Button PartsButton;
    @FXML
    private Button VehiclesButton;
    @FXML
    private Button TimeButton;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void ClientsButtonClick() {
        this.mainApp.showKlientOverview();
    }

    @FXML
    private void WorkersButtonClick() {
        this.mainApp.showPracownikOverview();
    }

    @FXML
    private void FirmsButtonClick() {
        this.mainApp.showFirmaOverview();
    }

    @FXML
    private void EtatsButtonClick() {
        this.mainApp.showEtatOverview();
    }

    @FXML
    private void DzialsButtonClick() {
        this.mainApp.showDzialOverview();
    }

    @FXML
    private void KategoriasButtonClick() {
        this.mainApp.showKategoriaOverview();
    }

    @FXML
    private void CzescsButtonClick() {
        this.mainApp.showCzescOverview();
    }
}
