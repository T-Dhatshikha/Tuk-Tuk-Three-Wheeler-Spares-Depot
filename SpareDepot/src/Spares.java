public class Spares {
    private String code;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String category;
    private String addedDate;
    private String imageName;
    private int threshold;

    public Spares(String code, String name, String brand, double price, int quantity, String category, String addedDate, String imageName, int threshold){
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.addedDate = addedDate;
        this.imageName =imageName;
        this.threshold = threshold;
    }

    public String getCode()  {
        return code;
    }
    public String getName() {
        return name;
    }
    public String getBrand() {
        return brand;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getCategory() {
        return category;
    }
    public String getDateAdded() {
        return addedDate;
    }
    public String getImageName() {
        return imageName;
    }
    public int getThreshold() {
        return threshold;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String text(){
        return code + " , " + name + " , " + brand + " , Rs. " + price + " , Qty: " + quantity + " , " + category + " , " + addedDate + " , " + imageName;
    }

    public boolean lowStock(int threshold) {
        if (quantity < threshold){
            return true;
        } else {
            return false;
        }
    }
}
