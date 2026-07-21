public class InventoryManager {
    private Spares[] spares;
    private int spareCount;
    private int maxSize = 500;
    private FileParser fileParser;

    public InventoryManager(FileParser fileParser) {
        this.fileParser = fileParser;
        this.spares     = new Spares[maxSize];
        this.spareCount  = 0;

        Spares[] load = fileParser.loadSpares();

        for (int i = 0; i < load.length; i++) {
            if (load[i] != null) {
                spares[spareCount] = load[i];
                spareCount += 1;
            }
        }
    }

    public Spares[] getSpares()  {
        return spares;
    }
    public int getSpareCount() {
        return spareCount;
    }

    // METHOD 1 - Add a new spare part
    public boolean addPart(Spares newSpare) {
        if (newSpare.getCode().equals("")) {
            System.out.println("Part code cannot be empty!");
            return false;
        }

        if (newSpare.getPrice() < 0) {
            System.out.println("Price cannot be negative!");
            return false;
        }

        if (newSpare.getQuantity() < 0) {
            System.out.println("Quantity cannot be negative!");
            return false;
        }

        for (int i = 0; i < spareCount; i++) {
            if (spares[i].getCode().equals(
                    newSpare.getCode())) {
                System.out.println("Part code " + newSpare.getCode() + " already exists!");
                return false;
            }
        }

        if (spareCount >= maxSize) {
            System.out.println("Inventory is full!");
            return false;
        }
        spares[spareCount] = newSpare;
        spareCount += 1;

        fileParser.saveParts(spares, spareCount);

        System.out.println(newSpare.getName() + " added to inventory!");
        return true;
    }

    // METHOD 2 - Delete a spare part by code
    public boolean deletePart(String code) {
        for (int i = 0; i < spareCount; i++) {
            if (spares[i].getCode().equals(code)) {

                String deletedName = spares[i].getName();
                for (int j = i; j < spareCount - 1; j++) {
                    spares[j] = spares[j + 1];
                }
                spares[spareCount - 1] = null;
                spareCount -= 1;
                fileParser.saveParts(spares, spareCount);

                System.out.println( deletedName + " deleted!");
                return true;
            }
        }
        System.out.println("Spare code " + code + " not found!");
        return false;
    }

    // METHOD 3 - Update quantity
    public boolean updateQuantity(String code, int newQty) {
        if (newQty < 0) {
            System.out.println("Quantity cannot be negative!");
            return false;
        }
        for (int i = 0; i < spareCount; i++) {
            if (spares[i].getCode().equals(code)) {
                spares[i].setQuantity(newQty);
                fileParser.saveParts(spares, spareCount);
                System.out.println("Quantity updated!");
                return true;
            }
        }
        System.out.println("Part not found!");
        return false;
    }

    // METHOD 4 - Update price
    public boolean updatePrice(String code, double newPrice) {
        if (newPrice < 0) {
            System.out.println("Price cannot be negative!");
            return false;
        }

        for (int i = 0; i < spareCount; i++) {
            if (spares[i].getCode().equals(code)) {
                spares[i].setPrice(newPrice);
                fileParser.saveParts(spares, spareCount);
                System.out.println("Price updated!");
                return true;
            }
        }
        System.out.println("Part not found!");
        return false;
    }

    // METHOD 5 - Search spare parts by multiple criteria
    public Spares[] searchSpares(String keyword, String category, double minPrice, double maxPrice) {
        int matchCount = 0;
        for (int i = 0; i < spareCount; i++) {
            if (matchesCriteria(spares[i], keyword, category, minPrice, maxPrice)) {
                matchCount += + 1;
            }
        }
        Spares[] results = new Spares[matchCount];
        int index = 0;
        for (int i = 0; i < spareCount; i++) {
            if (matchesCriteria(spares[i], keyword,
                    category, minPrice, maxPrice)) {
                results[index] = spares[i];
                index = index + 1;
            }
        }
        return results;
    }

    private boolean matchesCriteria(Spares spares, String keyword, String category, double minPrice, double maxPrice) {
        boolean keywordMatch = spares.getName().toLowerCase().contains(
                keyword.toLowerCase()) || spares.getBrand().toLowerCase().contains(
                keyword.toLowerCase()) || spares.getCode().toLowerCase().contains(
                keyword.toLowerCase());

        boolean categoryMatch;
        if (category.equals("")) {
            categoryMatch = true;
        } else {
            categoryMatch = spares.getCategory().equalsIgnoreCase(category);
        }
        boolean priceMatch = spares.getPrice() >= minPrice && spares.getPrice() <= maxPrice;
        return keywordMatch && categoryMatch && priceMatch;
    }

    // METHOD 6 - Sort by category then by spare part code
    public void sort() {
        for (int i = 0; i < spareCount - 1; i++) {
            for (int j = 0; j < spareCount - 1 - i; j++) {
                Spares current = spares[j];
                Spares next= spares[j + 1];

                int catCompare = current.getCategory().compareTo(next.getCategory());
                boolean shouldSwap = false;
                if (catCompare > 0) {
                    shouldSwap = true;
                } else if (catCompare == 0) {
                    if (current.getCode().compareTo(
                            next.getCode()) > 0) {
                        shouldSwap = true;
                    }
                }
                if (shouldSwap) {
                    spares[j]     = next;
                    spares[j + 1] = current;
                }
            }
        }
    }

    // METHOD 7 - Get total inventory value
    public double getTotalValue() {
        double total = 0;
        for (int i = 0; i < spareCount; i++) {
            // price multiplied by quantity = item value
            double itemValue = spares[i].getPrice() * spares[i].getQuantity();
            total = total + itemValue;
        }
        return total;
    }

    // METHOD 8 - Find one spare part by code
    public Spares searchByCode(String partCode) {
        for (int i = 0; i < spareCount; i++) {
            if (spares[i].getCode().equals(partCode)) {
                return spares[i];
            }
        }
        return null;
    }
}