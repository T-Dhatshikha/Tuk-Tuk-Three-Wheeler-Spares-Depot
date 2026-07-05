public class LowStockMonitor {
    int threshold = 10;

    public boolean isLowStock(Spares part){
        if(part.quantity < threshold){
            return true;
        } else {
            return false;
        }
    }

    public void checkAll(Spares[] parts) {
        System.out.println("Low Stock Report");
        int lowStockCount = 0;
        for(int i=0; i<parts.length; i++){
            if (isLowStock(parts[i])){
                System.out.println("WARNING !!! This Product is low in stock.");
                System.out.println("Code: " + parts[i].code +
                                   "Name: " + parts[i].name +
                                   "Quantity: " + parts[i].quantity +
                                   "Category: " + parts[i].category);

                lowStockCount += 1;
            }
        }

        if(lowStockCount == 0){
            System.out.println("All parts have sufficient stock.");
        } else{
            System.out.println("Total low stock items: "+ lowStockCount);
        }
    }

    public Spares[] getLowStockSpares(Spares[] parts){
        int count = 0;
        for(int i=0; i<parts.length; i++) {
            if(isLowStock(parts[i])){
                count += 1;
            }
        }

        Spares[] lowStockSpares = new Spares[count];
        int index = 0;
        for(int i=0; i<parts.length; i++){
            if(isLowStock(parts[i])){
                lowStockSpares[index] = parts[i];
                index += 1;
            }
        }
        return lowStockSpares;
    }
}
