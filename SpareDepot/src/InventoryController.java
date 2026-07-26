import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML private TableView<Spares> inventoryTable;
    @FXML private TableColumn<Spares, String> codeCol;
    @FXML private TableColumn<Spares, String> nameCol;
    @FXML private TableColumn<Spares, String> brandCol;
    @FXML private TableColumn<Spares, String> priceCol;
    @FXML private TableColumn<Spares, String> qtyCol;
    @FXML private TableColumn<Spares, String> catCol;
    @FXML private TableColumn<Spares, String> dateCol;

    @FXML private TextField  keywordField;
    @FXML private ComboBox<String> categoryBox;
    @FXML private TextField  minPriceField;
    @FXML private TextField  maxPriceField;

    @FXML private TextField addCode;
    @FXML private TextField addName;
    @FXML private TextField addBrand;
    @FXML private TextField addPrice;
    @FXML private TextField addQty;
    @FXML private ComboBox<String> addCatBox;

    @FXML private TextField editCode;
    @FXML private TextField editQty;
    @FXML private TextField editPrice;

    @FXML private Label totalLabel;
    @FXML private Label feedbackLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryBox.getItems().addAll("All", "Engine", "Brakes", "Electrical", "Bodywork");
        categoryBox.setValue("All");

        addCatBox.getItems().addAll("Engine", "Brakes", "Electrical", "Bodywork");
        addCatBox.setValue("Engine");

        codeCol.setCellValueFactory(
                new PropertyValueFactory<>("code"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        brandCol.setCellValueFactory(
                new PropertyValueFactory<>("brand"));
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("price"));
        qtyCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
        catCol.setCellValueFactory(
                new PropertyValueFactory<>("category"));
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("dateAdded"));

        Main.inventory.sort();
        refreshTable();
    }

    private void refreshTable() {
        inventoryTable.getItems().clear();
        Spares[] spares = Main.inventory.getSpares();
        int count = Main.inventory.getSpareCount();

        for (int i = 0; i < count; i++) {
            if (spares[i] != null) {
                inventoryTable.getItems().add(spares[i]);
            }
        }

        totalLabel.setText(
                "Total Parts: " + Main.inventory.getSpareCount() + "   |   Total Value: Rs." + String.format("%.2f", Main.inventory.getTotalValue()));
    }

    @FXML
    private void addSpare(ActionEvent event) {
        String code  = addCode.getText().trim();
        String name  = addName.getText().trim();
        String brand = addBrand.getText().trim();
        String cat   = addCatBox.getValue();

        if (code.equals("") || name.equals("") || addPrice.getText().trim().equals("") || addQty.getText().trim().equals("")) {
            showError("All fields are required!");
            return;
        }

        double price = 0;
        int quantity = 0;

        try {
            price = Double.parseDouble(addPrice.getText().trim());
        } catch (NumberFormatException e) {
            showError("Invalid price value!");
            return;
        }

        try {
            quantity = Integer.parseInt(addQty.getText().trim());
        } catch (NumberFormatException e) {
            showError("Invalid quantity value!");
            return;
        }

        Spares newSpare = new Spares(code, name, brand, price, quantity, cat, "", "");

        boolean success = Main.inventory.addSpare(newSpare);

        if (success) {
            Main.logger.log("ADD", code, quantity);
            clearAddFields();
            Main.inventory.sort();
            refreshTable();
            showSuccess(name + " added successfully!");
        } else {
            showError("Could not add part!");
        }
    }

    @FXML
    private void updateQuantity(ActionEvent event) {

        String code = editCode.getText().trim();
        if (code.equals("")) {
            showError("Enter a part code!");
            return;
        }

        try {
            int newQty = Integer.parseInt(editQty.getText().trim());

            boolean success = Main.inventory.updateQuantity(
                    code, newQty);

            if (success) {
                Main.logger.log("UPDATE_QTY", code, newQty);
                refreshTable();
                showSuccess("Quantity updated!");
            } else {
                showError("Part code not found!");
            }

        } catch (NumberFormatException e) {
            showError("Invalid quantity!");
        }
    }

    @FXML
    private void updatePrice(ActionEvent event) {

        String code = editCode.getText().trim();

        if (code.equals("")) {
            showError("Enter a part code!");
            return;
        }

        try {
            double newPrice = Double.parseDouble(editPrice.getText().trim());

            boolean success = Main.inventory.updatePrice(
                    code, newPrice);

            if (success) {
                Main.logger.log("UPDATE_PRICE", code, 0);
                refreshTable();
                showSuccess("Price updated!");
            } else {
                showError("Part code not found!");
            }

        } catch (NumberFormatException e) {
            showError("Invalid price!");
        }
    }

    @FXML
    private void deleteSpares(ActionEvent event) {

        String code = editCode.getText().trim();

        if (code.equals("")) {
            showError("Enter a part code!");
            return;
        }

        boolean success = Main.inventory.deleteSpare(code);

        if (success) {
            Main.logger.log("DELETE", code, 0);
            refreshTable();
            showSuccess("Part deleted!");
        } else {
            showError("Part code not found!");
        }
    }

    @FXML
    private void searchSpares(ActionEvent event) {

        String keyword = keywordField.getText().trim();
        String cat     = categoryBox.getValue();

        if (cat.equals("All")) {
            cat = "";
        }

        double minPrice = 0;
        double maxPrice = 999999;

        try {
            if (!minPriceField.getText().trim().equals("")) {
                minPrice = Double.parseDouble(minPriceField.getText().trim());
            }
            if (!maxPriceField.getText().trim().equals("")) {
                maxPrice = Double.parseDouble(maxPriceField.getText().trim());
            }
        } catch (NumberFormatException e) {
            showError("Invalid price range!");
            return;
        }

        Spares[] results = Main.inventory.searchSpares(
                keyword, cat, minPrice, maxPrice);

        inventoryTable.getItems().clear();

        for (int i = 0; i < results.length; i++) {
            if (results[i] != null) {
                inventoryTable.getItems().add(results[i]);
            }
        }

        showFeedback(results.length + " results found.", "blue");
    }

    @FXML
    private void showAll(ActionEvent event) {
        Main.inventory.sort();
        refreshTable();
        showFeedback("Showing all parts.", "blue");
    }

    @FXML
    private void goToInventory(ActionEvent event) throws Exception {
        navigateTo("/fxml/inventory.fxml", event);
    }

    @FXML
    private void goToLowStock(ActionEvent event) throws Exception {
        navigateTo("/fxml/lowstock.fxml", event);
    }

    @FXML
    private void goToDealers(ActionEvent event) throws Exception {
        navigateTo("/fxml/dealer.fxml", event);
    }

    @FXML
    private void goToPOS(ActionEvent event) throws Exception {
        navigateTo("/fxml/pos.fxml", event);
    }

    @FXML
    private void goToAudit(ActionEvent event) throws Exception {
        navigateTo("/fxml/audit.fxml", event);
    }

    private void navigateTo(String fxmlPath, ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root, 1000, 700));
    }

    private void showSuccess(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setStyle("-fx-text-fill: green; -fx-font-size: 13px;");
    }

    private void showError(String message) {
        feedbackLabel.setText("X " + message);
        feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
    }

    private void showFeedback(String message,
                              String color) {
        feedbackLabel.setText(message);
        feedbackLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 13px;");
    }

    private void clearAddFields() {
        addCode.clear();
        addName.clear();
        addBrand.clear();
        addPrice.clear();
        addQty.clear();
    }
}
