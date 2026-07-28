import org.junit.Test;
import static org.junit.Assert.*;

public class CartItemTest {

    @Test
    public void bulkDiscount() {
        CartItem item = new CartItem(new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg",10), 3);
        assertTrue(item.bulkDiscount());
    }

    @Test
    public void NoBulkDiscount() {
        CartItem item = new CartItem(new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg",10), 2);
        assertFalse(item.bulkDiscount());
    }

    @Test
    public void BulkDiscountCalculation() {
        CartItem item = new CartItem(new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg",10), 3);
        assertEquals(2850.0, item.discountedSubtotal(), 0.01);
    }

    @Test
    public void subtotalNoDiscount() {
        CartItem item = new CartItem(new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg",10), 2);
        assertEquals(2000.0, item.discountedSubtotal(), 0.01);
    }

    @Test
    public void subtotalMethod() {
        CartItem item = new CartItem(new Spares("P001","Piston","Bajaj", 500.0,15,"Engine","2024","img.jpg",10), 4);
        assertEquals(2000.0, item.subtotal(), 0.01);
    }
}