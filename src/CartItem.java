public class CartItem {
    private Spares spare;
    private int buyingQuantity;

    public CartItem(Spares spare, int buyingQuantity) {
        this.spare = spare;
        this.buyingQuantity = buyingQuantity;
    }

    public Spares getPart() {
        return spare;
    }

    public int getQuantityToBuy() {
        return buyingQuantity;
    }

    public void setQuantityToBuy(int quantity) {
        this.buyingQuantity = quantity;
    }

    public double subtotal() {
        return spare.getPrice() * buyingQuantity;
    }

    public boolean bulkDiscount() {
        if (buyingQuantity >= 3) {
            return true;
        } else {
            return false;
        }
    }

    public double discountedSubtotal() {
        double subtotal = subtotal();
        if (bulkDiscount()) {
            double discount = subtotal * 0.05;
            return subtotal - discount;
        } else {
            return subtotal;
        }
    }

    public String toString() {
        return spare.getCode() + " , " + spare.getName() + " , Quantity: " + buyingQuantity + " , Rs." + spare.getPrice() + " , Subtotal: Rs." + discountedSubtotal();
    }
}