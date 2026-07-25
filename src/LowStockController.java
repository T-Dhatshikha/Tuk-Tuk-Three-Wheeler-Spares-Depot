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

public class LowStockController implements Initializable {

    @FXML private TableView<Spares> lowStockTable;
    @FXML private TableColumn<Spares, String> codeCol;
    @FXML private TableColumn<Spares, String> nameCol;
    @FXML private TableColumn<Spares, String> qtyCol;
    @FXML private TableColumn<Spares, String> catCol;
    @FXML private TableColumn<Spares, String> priceCol;
    @FXML private TextField thresholdField;
    @FXML private Label thresholdLabel;
    @FXML private Label summaryLabel;

    private LowStockMonitor monitor = new LowStockMonitor();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        codeCol.setCellValueFactory(
                new PropertyValueFactory<>("partCode"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        qtyCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
        catCol.setCellValueFactory(
                new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        refreshList(null);
    }

    @FXML
    private void setThreshold(ActionEvent event) {
        try {
            int newThreshold = Integer.parseInt(thresholdField.getText().trim());
            monitor.setThreshold(newThreshold);
            thresholdLabel.setText("Current threshold: " + newThreshold);
            refreshList(event);
        } catch (NumberFormatException e) {
            summaryLabel.setText("ERROR: Invalid threshold!");
            summaryLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void refreshList(ActionEvent event) {

        lowStockTable.getItems().clear();

        Spares[] lowStock = monitor.getLowStock(
                InventoryController.inventory.getSpares(),
                InventoryController.inventory.getSpareCount());

        for (int i = 0; i < lowStock.length; i++) {
            lowStockTable.getItems().add(lowStock[i]);
        }

        if (lowStock.length == 0) {
            summaryLabel.setText("All parts have sufficient stock.");
            summaryLabel.setStyle("-fx-text-fill: green;");
        } else {
            summaryLabel.setText(
                    "WARNING: " + lowStock.length
                            + " items below threshold of "
                            + monitor.getThreshold());
            summaryLabel.setStyle(
                    "-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToInventory(ActionEvent e)
            throws Exception {
        navigateTo("fxml/inventory.fxml", e); }

    @FXML
    private void goToLowStock(ActionEvent e)
            throws Exception {
        navigateTo("fxml/lowstock.fxml", e); }

    @FXML
    private void goToDealers(ActionEvent e)
            throws Exception {
        navigateTo("fxml/dealer.fxml", e); }

    @FXML
    private void goToPOS(ActionEvent e)
            throws Exception {
        navigateTo("fxml/pos.fxml", e); }

    @FXML
    private void goToAudit(ActionEvent e)
            throws Exception {
        navigateTo("fxml/audit.fxml", e); }

    private void navigateTo(String path,
                            ActionEvent event)
            throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource(path));
        Stage stage = (Stage)((Node) event.getSource())
                .getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 700));
    }
}
