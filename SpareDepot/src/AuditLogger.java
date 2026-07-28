import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    private String logFilePath = "data/audit_log.txt";

    public void log(String action, String itemCode, int quantity) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true));
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(format);
            String logLine = timestamp + " , " + action + " , " + itemCode + " , Quantity: " + quantity;
            writer.write(logLine);
            writer.newLine();
            writer.close();
            System.out.println("LOG: " + logLine);
        } catch (IOException error) {
            System.out.println("Error in writing to audit log: " + error.getMessage());
        }
    }

    public void log(String action, String itemCode) {
        log(action, itemCode, 0);
    }
}

