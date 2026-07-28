public class Cart {
    private CartItem[] items;
    private int itemCount;
    private int maxItems = 50;

    public Cart() {
        items     = new CartItem[maxItems];
        itemCount = 0;
    }

    public boolean addItem(Spares spare, int buyingQuantity) {
        if (buyingQuantity <= 0) {
            System.out.println("Quantity must be above 0!");
            return false;
        }

        if (buyingQuantity > spare.getQuantity()) {
            System.out.println("Not enough stock! Only " + spare.getQuantity() + " available.");
            return false;
        }

        for (int i = 0; i < itemCount; i++) {
            if (items[i].getSpare().getCode().equals(spare.getCode())) {
                int newQty = items[i].getBuyingQuantity() + buyingQuantity;
                if (newQty > spare.getQuantity()) {
                    System.out.println("Total would exceed stock!");
                    return false;
                }
                items[i].setQuantityToBuy(newQty);
                System.out.println("Updated quantity in cart!");
                return true;
            }
        }

        items[itemCount] = new CartItem(spare, buyingQuantity);
        itemCount = itemCount + 1;
        System.out.println(spare.getName() + " added to cart!");
        return true;
    }

    public boolean removeItem(String code) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getSpare().getCode().equals(code)) {
                for (int j = i; j < itemCount - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[itemCount - 1] = null;
                itemCount -=  1;
                System.out.println("Item removed from cart!");
                return true;
            }
        }
        System.out.println("Item not found in cart!");
        return false;
    }

    public boolean hasEngine() {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getSpare().getCategory().equalsIgnoreCase("Engine")) {
                return true;
            }
        }
        return false;
    }

    public boolean hasElectrical() {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getSpare().getCategory().equalsIgnoreCase("Electrical")) {
                return true;
            }
        }
        return false;
    }

    public double rawTotal() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total = total + items[i].subtotal();
        }
        return total;
    }

    public double finalTotal() {
        double totalAfterBulk = 0;
        for (int i = 0; i < itemCount; i++) {
            totalAfterBulk = totalAfterBulk + items[i].discountedSubtotal();
        }

        if (hasEngine() && hasElectrical()) {
            double synergyDiscount = totalAfterBulk * 0.10;
            return totalAfterBulk - synergyDiscount;
        }
        return totalAfterBulk;
    }

    public boolean hasSynergyDiscount() {
        return hasEngine() && hasElectrical();
    }

    public boolean checkout(InventoryManager inventory, AuditLogger logger) {
        if (itemCount == 0) {
            System.out.println("Cart is empty!");
            return false;
        }

        for (int i = 0; i < itemCount; i++) {
            CartItem item = items[i];
            Spares spare = item.getSpare();

            int newQty = spare.getQuantity() - item.getBuyingQuantity();

            inventory.updateQuantity(spare.getCode(), newQty);

            logger.log("Checkout: ", spare.getCode(), item.getBuyingQuantity());
        }

        System.out.println("Checkout complete! Total: Rs." + finalTotal());
        clearCart();
        return true;
    }

    public void clearCart() {
        for (int i = 0; i < itemCount; i++) {
            items[i] = null;
        }
        itemCount = 0;
    }

    public CartItem[] getItems() {
        return items;
    }
    public int getItemCount() {
        return itemCount;
    }
    public boolean isEmpty() {
        return itemCount == 0;
    }


    public void printCart() {
        System.out.println("Shopping Cart");
        if (itemCount == 0) {
            System.out.println("Cart is empty.");
            return;
        }
        for (int i = 0; i < itemCount; i++) {
            System.out.println((i + 1) + ". "
                    + items[i].toString());
        }
        System.out.println("Raw Total:   Rs." + rawTotal());
        System.out.println("Final Total: Rs." + finalTotal());
        if (hasSynergyDiscount()) {
            System.out.println("Synergy discount (10%) applied!");
        }
    }
}