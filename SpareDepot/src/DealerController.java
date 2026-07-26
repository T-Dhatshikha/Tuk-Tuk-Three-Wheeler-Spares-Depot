import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class DealerController implements Initializable {

    @FXML private TableView<Dealers> dealerTable;
    @FXML private TableColumn<Dealers, String> idCol;
    @FXML private TableColumn<Dealers, String> nameCol;
    @FXML private TableColumn<Dealers, String> phoneCol;
    @FXML private TableColumn<Dealers, String> locationCol;
    @FXML private Label statusLabel;

    private DealerService dealerService = new DealerService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        idCol.setCellValueFactory(
                new PropertyValueFactory<>("dealerCode"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<>("phone"));
        locationCol.setCellValueFactory(
                new PropertyValueFactory<>("location"));

        statusLabel.setText("Press the button to select 4 random dealers.");
    }

    @FXML
    private void selectDealers(ActionEvent event) {

        Dealers[] allDealers = Main.fileParser.loadDealers();

        if (allDealers.length < 4) {
            statusLabel.setText("ERROR: Need at least 4 dealers in file!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Dealers[] selected = dealerService.randomDealers(allDealers, allDealers.length);

        dealerService.sortByLocation(selected);

        dealerTable.getItems().clear();

        for (int i = 0; i < selected.length; i++) {
            dealerTable.getItems().add(selected[i]);
        }

        statusLabel.setText("4 dealers selected and sorted by location.");
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    @FXML private void goToInventory(ActionEvent event) throws Exception {
        navigateTo("fxml/inventory.fxml", event);
    }

    @FXML private void goToLowStock(ActionEvent event) throws Exception {
        navigateTo("fxml/lowstock.fxml", event);
    }

    @FXML private void goToDealers(ActionEvent event) throws Exception {
        navigateTo("fxml/dealer.fxml", event);
    }

    @FXML private void goToPOS(ActionEvent event) throws Exception {
        navigateTo("fxml/pos.fxml", event);
    }

    @FXML private void goToAudit(ActionEvent event) throws Exception {
        navigateTo("fxml/audit.fxml", event);
    }

    private void navigateTo(String path, ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 700));
    }
}

