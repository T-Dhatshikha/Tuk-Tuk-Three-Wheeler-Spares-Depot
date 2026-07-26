public class DataCleaner {

    public String detectSeparator(String line) {
        int comma     = 0;
        int pipe      = 0;
        int semicolon = 0;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ',')      { comma     = comma + 1; }
            else if (c == '|') { pipe      = pipe + 1; }
            else if (c == ';') { semicolon = semicolon + 1; }
        }

        if (pipe >= comma && pipe >= semicolon) {
            return "|";
        } else if (semicolon >= comma) {
            return ";";
        } else {
            return ",";
        }
    }

    // Split line - handles mixed separators
    public String[] separate(String line) {

        // Remove Windows line endings first
        line = line.replace("\r", "").replace("\n", "");

        boolean hasPipe      = line.contains("|");
        boolean hasSemicolon = line.contains(";");
        boolean hasComma     = line.contains(",");

        // Mixed separators - normalise everything to comma
        if ((hasPipe && hasSemicolon)
                || (hasPipe && hasComma)
                || (hasSemicolon && hasComma)) {
            String normalised = line
                    .replace("|", ",")
                    .replace(";", ",");
            return normalised.split(",");
        }

        // Single separator
        if (hasPipe) {
            return line.split("\\|");
        } else if (hasSemicolon) {
            return line.split(";");
        } else {
            return line.split(",");
        }
    }

    // Clean one text field safely
    // Handles null, empty, extra spaces
    public String cleanField(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.trim();
    }

    // Clean price - handles Rs., Rs, plain number
    // Also handles null and non-numeric values
    public double cleanPrice(String rawPrice) {

        // Null check
        if (rawPrice == null) {
            System.out.println("WARNING: Null price - using 0");
            return 0.0;
        }

        String cleaned = rawPrice.trim();

        // Empty check
        if (cleaned.equals("")) {
            System.out.println("WARNING: Empty price - using 0");
            return 0.0;
        }

        // Remove currency symbols and spaces
        cleaned = cleaned.replace("Rs.", "");
        cleaned = cleaned.replace("Rs",  "");
        cleaned = cleaned.replace("rs.", "");
        cleaned = cleaned.replace("rs",  "");
        cleaned = cleaned.replace(" ",   "");

        // Empty after cleaning
        if (cleaned.equals("")) {
            return 0.0;
        }

        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Price Value: "
                    + rawPrice);
            return 0.0;
        }
    }

    // Clean quantity - handles null, text, decimals
    public int cleanQuantity(String rawQty) {

        if (rawQty == null) {
            System.out.println(
                    "WARNING: Null quantity - using 0");
            return 0;
        }

        String cleaned = rawQty.trim();

        if (cleaned.equals("")) {
            return 0;
        }

        // Remove any non-numeric chars except minus
        // Some fields might have text mixed in
        try {
            // Try direct parse first
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException e) {
            // Try to extract just the digits
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
                } catch (NumberFormatException e2) {
                    // Still failed
                }
            }
            System.out.println("Invalid quantity: "
                    + rawQty);
            return 0;
        }
    }

    // Clean category - fix capitalisation
    public String cleanCategory(String rawCategory) {
        if (rawCategory == null) {
            return "Unknown";
        }

        String cleaned = rawCategory.trim();

        if (cleaned.equals("")) {
            return "Unknown";
        }

        // Convert to title case
        String lower = cleaned.toLowerCase();
        return Character.toUpperCase(lower.charAt(0))
                + lower.substring(1);
    }

    // Get field safely - returns empty string if index
    // is out of bounds or field is null
    // This prevents ArrayIndexOutOfBoundsException
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

    // Parse one raw dirty line into a Spares object
    // Handles all dirty data cases from legacy file
    public Spares parsePart(String rawLine) {

        // Null and empty check
        if (rawLine == null
                || rawLine.trim().equals("")) {
            return null;
        }

        // Remove line endings
        rawLine = rawLine.replace("\r", "")
                .replace("\n", "");

        // Skip comment lines
        if (rawLine.trim().startsWith("#")) {
            return null;
        }

        String[] fields = separate(rawLine);

        // Need at least partCode and name
        // (fields 0 and 1)
        if (fields.length < 2) {
            System.out.println(
                    "Invalid line: " + rawLine);
            return null;
        }

        // Get each field safely using getField()
        // so missing fields return "" not crash
        String partCode  = cleanField(getField(fields, 0));
        String name      = cleanField(getField(fields, 1));
        String brand     = cleanField(getField(fields, 2));
        double price     = cleanPrice(getField(fields, 3));
        int    quantity  = cleanQuantity(
                getField(fields, 4));
        String category  = cleanCategory(
                getField(fields, 5));
        String dateAdded = cleanField(getField(fields, 6));
        String imageName = cleanField(getField(fields, 7));

        // Must have at least a part code to be valid
        if (partCode.equals("")) {
            System.out.println(
                    "Invalid line - no part code: " + rawLine);
            return null;
        }

        return new Spares(partCode, name, brand, price,
                quantity, category,
                dateAdded, imageName);
    }

    // Parse one raw dirty line into a Dealers object
    public Dealers parseDealer(String rawLine) {

        if (rawLine == null
                || rawLine.trim().equals("")) {
            return null;
        }

        // Remove line endings
        rawLine = rawLine.replace("\r", "")
                .replace("\n", "");

        if (rawLine.trim().startsWith("#")) {
            return null;
        }

        String[] fields = separate(rawLine);

        if (fields.length < 2) {
            System.out.println(
                    "Invalid dealer line: " + rawLine);
            return null;
        }

        // Get fields safely
        String dealerID = cleanField(getField(fields, 0));
        String name     = cleanField(getField(fields, 1));
        String phone    = cleanField(getField(fields, 2));
        String location = cleanField(getField(fields, 3));

        // Must have dealer ID
        if (dealerID.equals("")) {
            System.out.println(
                    "Invalid dealer - no ID: " + rawLine);
            return null;
        }

        return new Dealers(dealerID, name, phone, location);
    }
}