import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuditController implements Initializable {

    @FXML private TextArea logArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load log on screen open
        loadLog();
    }

    @FXML
    private void refreshLog(ActionEvent event) {
        loadLog();
    }

    private void loadLog() {

        logArea.clear();

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("data/audit_log.txt"));

            String line = reader.readLine();

            if (line == null) {
                logArea.setText(
                        "No log entries yet.");
                reader.close();
                return;
            }

            while (line != null) {
                logArea.appendText(line);
                line = reader.readLine();
            }

            reader.close();

        } catch (IOException error) {
            logArea.setText(
                    "No audit log found yet."
                            + "Log entries will appear here "
                            + "after actions are performed.");
        }
    }

    @FXML private void goToInventory(ActionEvent error)
            throws Exception {
        navigateTo("fxml/inventory.fxml", error); }
    @FXML private void goToLowStock(ActionEvent error)
            throws Exception {
        navigateTo("fxml/lowstock.fxml", error); }
    @FXML private void goToDealers(ActionEvent error)
            throws Exception {
        navigateTo("fxml/dealer.fxml", error); }
    @FXML private void goToPOS(ActionEvent error)
            throws Exception {
        navigateTo("fxml/pos.fxml", error); }
    @FXML private void goToAudit(ActionEvent error)
            throws Exception {
        navigateTo("fxml/audit.fxml", error); }

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