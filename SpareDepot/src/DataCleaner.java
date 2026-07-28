public class DataCleaner {

    public String detectSeparator(String line) {
        int comma = 0;
        int pipe = 0;
        int semicolon = 0;

        for (int i = 0; i < line.length(); i++) {
            char seperator = line.charAt(i);
            if (seperator == ',') {
                comma += 1;
            } else if (seperator == '|') {
                pipe += 1;
            } else if (seperator == ';') {
                semicolon += 1;
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

    public String[] separate(String line) {

        line = line.replace("\r", "").replace("\n", "");

        boolean hasPipe      = line.contains("|");
        boolean hasSemicolon = line.contains(";");
        boolean hasComma     = line.contains(",");

        if ((hasPipe && hasSemicolon) || (hasPipe && hasComma) || (hasSemicolon && hasComma)) {
            String normalised = line.replace("|", ",").replace(";", ",");
            return normalised.split(",");
        }

        if (hasPipe) {
            return line.split("\\|");
        } else if (hasSemicolon) {
            return line.split(";");
        } else {
            return line.split(",");
        }
    }

    public String cleanField(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.trim();
    }

    public double cleanPrice(String rawPrice) {

        if (rawPrice == null) {
            System.out.println("WARNING: Null price - using 0");
            return 0.0;
        }

        String cleaned = rawPrice.trim();

        if (cleaned.equals("")) {
            System.out.println("WARNING: Empty price - using 0");
            return 0.0;
        }

        cleaned = cleaned.replace("Rs.", "");
        cleaned = cleaned.replace("Rs",  "");
        cleaned = cleaned.replace("rs.", "");
        cleaned = cleaned.replace("rs",  "");
        cleaned = cleaned.replace(" ",   "");

        if (cleaned.equals("")) {
            return 0.0;
        }

        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException error) {
            System.out.println("Invalid Price Value: " + rawPrice);
            return 0.0;
        }
    }

    public int cleanQuantity(String rawQty) {

        if (rawQty == null) {
            System.out.println("WARNING: Null quantity - using 0");
            return 0;
        }

        String cleaned = rawQty.trim();

        if (cleaned.equals("")) {
            return 0;
        }


        try {
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException error) {
            String digitsOnly = "";
            for (int i = 0; i < cleaned.length(); i++) {
                char c = cleaned.charAt(i);
                if (Character.isDigit(c)) {
                    digitsOnly = digitsOnly + c;
                }
            }
            if (!digitsOnly.equals("")) {
                try {
                    return Integer.parseInt(digitsOnly);
                } catch (NumberFormatException error2) {

                }
            }
            System.out.println("Invalid quantity: " + rawQty);
            return 0;
        }
    }

    public String cleanCategory(String rawCategory) {
        if (rawCategory == null) {
            return "Unknown";
        }

        String cleaned = rawCategory.trim();

        if (cleaned.equals("")) {
            return "Unknown";
        }

        String lower = cleaned.toLowerCase();
        return Character.toUpperCase(lower.charAt(0))
                + lower.substring(1);
    }

    public int cleanThreshold(String rawNo) {

        if (rawNo == null) {
            System.out.println("WARNING: Null Threshold - using 0");
            return 0;
        }

        String cleaned = rawNo.trim();

        if (cleaned.equals("")) {
            return 0;
        }


        try {
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException error) {
            String digitsOnly = "";
            for (int i = 0; i < cleaned.length(); i++) {
                char c = cleaned.charAt(i);
                if (Character.isDigit(c)) {
                    digitsOnly = digitsOnly + c;
                }
            }
            if (!digitsOnly.equals("")) {
                try {
                    return Integer.parseInt(digitsOnly);
                } catch (NumberFormatException error2) {

                }
            }
            System.out.println("Invalid threshold: " + rawNo);
            return 0;
        }
    }

    private String getField(String[] fields, int index) {
        if (fields == null) {
            return "";
        }
        if (index >= fields.length) {
            return "";
        }
        if (fields[index] == null) {
            return "";
        }
        return fields[index];
    }

    public Spares parsePart(String rawLine) {

        if (rawLine == null
                || rawLine.trim().equals("")) {
            return null;
        }

        rawLine = rawLine.replace("\r", "")
                .replace("\n", "");

        if (rawLine.trim().startsWith("#")) {
            return null;
        }

        String[] fields = separate(rawLine);

        if (fields.length < 2) {
            System.out.println(
                    "Invalid line: " + rawLine);
            return null;
        }

        String partCode  = cleanField(getField(fields, 0));
        String name      = cleanField(getField(fields, 1));
        String brand     = cleanField(getField(fields, 2));
        double price     = cleanPrice(getField(fields, 3));
        int quantity  = cleanQuantity(getField(fields, 4));
        String category  = cleanCategory(getField(fields, 5));
        String dateAdded = cleanField(getField(fields, 6));
        String imageName = cleanField(getField(fields, 7));
        int threshold  = cleanQuantity(getField(fields, 8));

        if (partCode.equals("")) {
            System.out.println("Invalid line - no part code: " + rawLine);
            return null;
        }

        return new Spares(partCode, name, brand, price,
                quantity, category,
                dateAdded, imageName,threshold);
    }

    public Dealers parseDealer(String rawLine) {

        if (rawLine == null || rawLine.trim().equals("")) {
            return null;
        }

        rawLine = rawLine.replace("\r", "").replace("\n", "");

        if (rawLine.trim().startsWith("#")) {
            return null;
        }

        String[] fields = separate(rawLine);

        if (fields.length < 2) {
            System.out.println("Invalid dealer line: " + rawLine);
            return null;
        }

        String dealerCode = cleanField(getField(fields, 0));
        String name = cleanField(getField(fields, 1));
        String phone = cleanField(getField(fields, 2));
        String location = cleanField(getField(fields, 3));

        if (dealerCode.equals("")) {
            System.out.println("Invalid dealer - no ID: " + rawLine);
            return null;
        }

        return new Dealers(dealerCode, name, phone, location);
    }
}