import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class POSController implements Initializable {

    @FXML private TableView<CartItem> cartTable;
    @FXML private TableColumn<CartItem, String>  codeCol;
    @FXML private TableColumn<CartItem, String>  nameCol;
    @FXML private TableColumn<CartItem, String>  priceCol;
    @FXML private TableColumn<CartItem, String>  qtyCol;
    @FXML private TableColumn<CartItem, String>  subtotalCol;
    @FXML private TableColumn<CartItem, String>  discountCol;

    @FXML private TextField partCodeField;
    @FXML private TextField qtyField;

    @FXML private Label rawTotalLabel;
    @FXML private Label discountLabel;
    @FXML private Label finalTotalLabel;
    @FXML private Label posStatusLabel;

    static Cart cart = new Cart();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        codeCol.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getSpare().getCode()));

        nameCol.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getSpare().getName()));

        priceCol.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        "Rs." + data.getValue().getSpare().getPrice()));

        qtyCol.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(data.getValue().getBuyingQuantity())));

        subtotalCol.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        "Rs." + String.format("%.2f", data.getValue().discountedSubtotal())));

        discountCol.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().bulkDiscount() ? "5% OFF" : "-"));

        refreshCart();
    }

    @FXML
    private void addToCart(ActionEvent event) {

        String code = partCodeField.getText().trim();

        if (code.equals("")) {
            showError("Enter a part code!");
            return;
        }

        Spares spare = Main.inventory.searchByCode(code);

        if (spare == null) {
            showError("Part " + code + " not found!");
            return;
        }

        int qty = 0;
        try {
            qty = Integer.parseInt(qtyField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Invalid quantity!");
            return;
        }

        boolean success = cart.addItem(spare, qty);

        if (success) {
            refreshCart();
            partCodeField.clear();
            qtyField.clear();
            showSuccess(spare.getName() + " added to cart!");
        } else {
            showError("Could not add item to cart!");
        }
    }

    @FXML
    private void removeFromCart(ActionEvent event) {
        String code = partCodeField.getText().trim();
        if (code.equals("")) {
            showError("Enter part code to remove!");
            return;
        }
        cart.removeItem(code);
        refreshCart();
        showFeedback("Item removed.", "blue");
    }

    @FXML
    private void checkout(ActionEvent event) {

        if (cart.isEmpty()) {
            showError("Cart is empty!");
            return;
        }

        boolean success = cart.checkout(
                Main.inventory,
                Main.logger);

        if (success) {
            refreshCart();
            showSuccess("Checkout complete!");
        }
    }

    @FXML
    private void clearCart(ActionEvent event) {
        cart.clearCart();
        refreshCart();
        showFeedback("Cart cleared.", "blue");
    }

    private void refreshCart() {

        cartTable.getItems().clear();

        CartItem[] items = cart.getItems();
        int count = cart.getItemCount();

        for (int i = 0; i < count; i++) {
            if (items[i] != null) {
                cartTable.getItems().add(items[i]);
            }
        }

        rawTotalLabel.setText("Raw Total: Rs." + String.format("%.2f", cart.rawTotal()));

        if (cart.hasSynergyDiscount()) {
            discountLabel.setText("Bulk discounts applied + " + "10% Synergy discount applied!");
            discountLabel.setStyle("-fx-text-fill: green;");
        } else {
            discountLabel.setText("Bulk discount: 5% off items with qty 3+");
            discountLabel.setStyle("-fx-text-fill: black;");
        }

        finalTotalLabel.setText("FINAL TOTAL: Rs." + String.format("%.2f", cart.finalTotal()));
    }

    private void showSuccess(String msg) {
        posStatusLabel.setText(msg);
        posStatusLabel.setStyle("-fx-text-fill: green;");
    }

    private void showError(String msg) {
        posStatusLabel.setText("X " + msg);
        posStatusLabel.setStyle("-fx-text-fill: red;");
    }

    private void showFeedback(String msg, String color) {
        posStatusLabel.setText(msg);
        posStatusLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    @FXML private void goToInventory(ActionEvent event) throws Exception {
        navigateTo("/fxml/inventory.fxml", event);
    }

    @FXML private void goToLowStock(ActionEvent event) throws Exception {
        navigateTo("/fxml/lowstock.fxml", event);
    }

    @FXML private void goToDealers(ActionEvent event) throws Exception {
        navigateTo("/fxml/dealer.fxml", event);
    }

    @FXML private void goToPOS(ActionEvent event) throws Exception {
        navigateTo("/fxml/pos.fxml", event);
    }

    @FXML private void goToAudit(ActionEvent event) throws Exception {
        navigateTo("/fxml/audit.fxml", event);
    }

    private void navigateTo(String path, ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 700));
    }
}