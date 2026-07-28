import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileParser {
    private String inventoryFile = "data/inventory_legacy.txt";
    private String dealersFile = "data/dealers_legacy.txt";
    private String auditFile = "data/audit_log.txt";

    private DataCleaner cleaner = new DataCleaner();

    public Spares[] loadParts() {
        int count = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().equals("")) {
                    count += 1;
                }
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException error) {
            System.out.println("ERROR reading inventory file: " + error.getMessage());
            return new Spares[0];
        }

        Spares[] Spare = new Spares[count];
        int index = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().equals("")) {
                    Spares spare = cleaner.parsePart(line);
                    if (spare != null) {
                        Spare[index] = spare;
                        index = index + 1;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException error) {
            System.out.println("ERROR reading inventory: " + error.getMessage());
        }
        return Spare;
    }

    public Dealers[] loadDealers() {
        int count = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(dealersFile));
            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().equals("")) {
                    count = count + 1;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException error) {
            System.out.println("ERROR reading dealers file: " + error.getMessage());
            return new Dealers[0];
        }

        Dealers[] Dealer = new Dealers[count];
        int index = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(dealersFile));
            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().equals("")) {
                    Dealers dealer = cleaner.parseDealer(line);
                    if (dealer != null) {
                        Dealer[index] = dealer;
                        index = index + 1;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException error) {
            System.out.println("ERROR reading dealers: " + error.getMessage());
        }
        return Dealer;
    }

    public void saveParts(Spares[] spares, int spareCount) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(inventoryFile, false));

            for (int i = 0; i < spareCount; i++) {
                if (spares[i] != null) {

                    String line = spares[i].getCode() + ","
                            + spares[i].getName() + ","
                            + spares[i].getBrand() + ","
                            + spares[i].getPrice() + ","
                            + spares[i].getQuantity() + ","
                            + spares[i].getCategory() + ","
                            + spares[i].getDateAdded() + ","
                            + spares[i].getImageName();
                    writer.write(line);
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException error) {
            System.out.println("ERROR saving inventory: " + error.getMessage());
        }
    }

    public void saveThreshold(int threshold) {
        try {
            java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("data/threshold.txt", false));

            writer.write(String.valueOf(threshold));
            writer.newLine();
            writer.close();

            System.out.println("Threshold saved: " + threshold);

        } catch (java.io.IOException error) {System.out.println("ERROR saving threshold: " + error.getMessage());
        }
    }

    public int loadThreshold() {
        try {java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("data/threshold.txt"));

            String line = reader.readLine();
            reader.close();

            if (line != null && !line.trim().equals("")) {
                return Integer.parseInt(line.trim());
            }
        } catch (java.io.IOException error) {
            System.out.println("No threshold file found." + " Using default of 10.");
        } catch (NumberFormatException error) {
            System.out.println("Invalid threshold in file." +
                    " Using default of 10.");
        }
        return 10;
    }
}


