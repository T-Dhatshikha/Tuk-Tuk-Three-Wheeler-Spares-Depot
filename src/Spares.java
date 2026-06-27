public class Spares {
    String code;
    String name;
    String brand;
    double price;
    int quantity;
    String category;
    String addedDate;
    String imageName;

    public Spares(String code, String name, String brand, double price, int quantity, String category, String addedDate, String imageName) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.addedDate = addedDate;
        this.imageName =imageName;
    }

    public String text(){
        return code + " , " + name + " , " + brand + " , Rs. " + price + " , Qty: " + quantity + " , " + category + " , " + addedDate + " , " + imageName;
    }

    public boolean lowStock() {
        if (quantity < 10){
            return true;
        } else {
            return false;
        }
    }
}
