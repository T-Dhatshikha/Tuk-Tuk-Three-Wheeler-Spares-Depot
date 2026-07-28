import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static FileParser fileParser;
    public static AuditLogger logger;
    public static InventoryManager inventory;

    @Override
    public void start(Stage primaryStage) throws Exception {

        fileParser = new FileParser();
        logger = new AuditLogger();
        inventory  = new InventoryManager(fileParser);

        inventory.sort();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/inventory.fxml"));
        primaryStage.setTitle("Malabe Tuk-Tuk & Three-Wheeler Spares Depot");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}