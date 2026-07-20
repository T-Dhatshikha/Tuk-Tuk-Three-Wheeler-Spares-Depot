public class LowStockMonitor {
    private int threshold = 15;
    public LowStockMonitor(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    //Method 1
    public boolean isLowStock(Spares spare){
        if(spare.getQuantity() < threshold){
            return true;
        } else {
            return false;
        }
    }

    //Method 2
    public Spares[] getLowStock(Spares[] spares, int spareCount) {
        int count = 0;
        for (int i = 0; i < spareCount; i++) {
            if (spares[i] != null && isLowStock(spares[i])) {
                count += 1;
            }
        }
        Spares[] lowstock = new Spares[count];
        int index = 0;
        for (int i = 0; i < spareCount; i++) {
            if (spares[i] != null && isLowStock(spares[i])) {
                lowstock[index] = spares[i];
                index += 1;
            }
        }
        return lowstock;
    }

    public void checkAll(Spares[] spares, int spareCount) {
        System.out.println("Low Stock Report");
        int lowStockCount = 0;
        for(int i = 0; i < spareCount; i++) {
            if (spares[i] != null && isLowStock(spares[i])){
                System.out.println("WARNING !!! This Product is low in stock.");
                System.out.println("Code: " + spares[i].getCode() + " Name: " + spares[i].getName() + " Quantity: " + spares[i].getQuantity() + " Category: " + spares[i].getCategory());
                lowStockCount += 1;
            }
        }

        if(lowStockCount == 0){
            System.out.println("All parts have sufficient stock.");
        } else{
            System.out.println("Total low stock items: "+ lowStockCount);
        }
    }
}
