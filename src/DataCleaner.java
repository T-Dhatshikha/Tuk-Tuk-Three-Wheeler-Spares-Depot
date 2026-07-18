public class DataCleaner {
    //Method 1
    public String findSeparator(String line) {
        int comma = 0;
        int semicolon = 0;
        int pipe = 0;

        for (int i = 0; i < line.length(); i++) {
            char separator = line.charAt(i);
            if (separator == ',') {
                comma += 1;
            } else if (separator == ';') {
                semicolon += 1;
            } else if (separator == '|') {
                pipe += 1;
            }
        }
        if (pipe >= comma && pipe >= semicolon) {
            return "|";
        } else if (semicolon >= comma) {
            return ";";
        } else {
            return ",";
        }
    }

    //Method 2
    public String[] separate(String line) {
        String symbol = findSeparator(line);

        if (symbol.equals("|")) {
            return line.split("\\|");
        } else if (symbol.equals(";")) {
            return line.split(";");
        } else {
            return line.split(",");
        }
    }

    //Method 3
    public String fieldClear(String space) {
        if (space == null) {
            return "";
        }
        return space.trim();
    }

    //Method 4
    public double priceClear(String price) {
        String clean = price.trim();
        clean = clean.replace("Rs. ", "");
        clean = clean.replace("Rs.", "");
        clean = clean.replace("Rs", "");
        clean = clean.replace(" ", "");
        if (clean.equals("")) {
            return 0.0;
        }

        try {
            return Double.parseDouble(clean);
        } catch (NumberFormatException error) {
            System.out.println("Invalid Price Value: " + price);
            return 0.0;
        }
    }

    //Method 5
    public int quantityClear(String quantity) {
        String clean = quantity.trim();
        if (clean.equals("")) {
            return 0;
        }

        try {
            return Integer.parseInt(clean);
        } catch (NumberFormatException error) {
            System.out.println("Invalid quantity: " + quantity);
            return 0;
        }

    }

    //Method 6
    public String categoryClear(String category) {
        String clean = category.trim();
        if (clean.equals("")) {
            return "Unknown";
        }
        String lower = clean.toLowerCase();
        return Character.toUpperCase(lower.charAt((0))) + lower.substring(1);
    }

    //Method 7
    public Spares parseSpare(String line) {
        if (line == null || line.trim().equals("")) {
            return null;
        }
        String[] fields = lineBreak(line);
        if (fields.length < 6) {
            System.out.println("Invalid line: " + line);
            return null;
        }

        String code = fieldClear(fields[0]);
        String name = fieldClear(fields[1]);
        String brand = fieldClear(fields[2]);
        double price     = priceClear(fields[3]);
        int quantity     = quantityClear(fields[4]);
        String category  = categoryClear(fields[5]);

        String addedDate = "";
        if (fields.length > 6) {
            addedDate = fieldClear(fields[6]);
        }

        String imageName = "";
        if (fields.length > 7) {
            imageName = fieldClear(fields[7]);
        }
        return new Spares(code, name, brand, price, quantity, category, addedDate, imageName);
    }

    //Method 8
    public Dealers parseDealer(String line) {
        if (line == null || line.trim().equals("")) {
            return null;
        }
        String[] fields = lineBreak(line);
        if (fields.length < 6) {
            System.out.println("Invalid dealer: " + line);
            return null;
        }

        String dealerCode = fieldClear(fields[0]);
        String name = fieldClear(fields[1]);

        String phone    = "";
        if (fields.length > 2) {
            phone = fieldClear(fields[2]);
        }

        // Location is optional - might be missing
        String location = "";
        if (fields.length > 3) {
            location = fieldClear(fields[3]);
        }

        return new Dealers(dealerCode, name, phone, location);
    }
}
