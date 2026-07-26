import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CartTest {

    private Cart cart;

    @Before
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void addValidItem() {
        Spares s = new Spares("P001","Piston","Bajaj", 4500.0,15,"Engine","2024","img.jpg");
        assertTrue(cart.addItem(s, 2));
        assertEquals(1, cart.getItemCount());
    }

    @Test
    public void addingZero() {
        Spares s = new Spares("P001","Piston","Bajaj", 4500.0,15,"Engine","2024","img.jpg");
        assertFalse(cart.addItem(s, 0));
    }

    @Test
    public void negativeQuantity() {
        Spares s = new Spares("P001","Piston","Bajaj", 4500.0,15,"Engine","2024","img.jpg");
        assertFalse(cart.addItem(s, -1));
    }

    @Test
    public void exceedStock() {
        Spares s = new Spares("P001","Piston","Bajaj", 4500.0,5,"Engine","2024","img.jpg");
        assertFalse(cart.addItem(s, 10));
    }

    @Test
    public void removeItem() {
        Spares s = new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg");
        cart.addItem(s, 2);
        cart.removeItem("P001");
        assertEquals(0, cart.getItemCount());
    }

    @Test
    public void emptyCart() {
        assertTrue(cart.isEmpty());
    }

    @Test
    public void rawTotal() {
        Spares s = new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg");
        cart.addItem(s, 2);
        assertEquals(2000.0, cart.rawTotal(), 0.01);
    }

    @Test
    public void emptyCartCheckout() {
        AuditLogger logger = new AuditLogger();
        FileParser fp = new FileParser();
        InventoryManager inv = new InventoryManager(fp);
        assertFalse(cart.checkout(inv, logger));
    }

    @Test
    public void clearCart() {
        Spares s = new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg");
        cart.addItem(s, 2);
        cart.clearCart();
        assertEquals(0, cart.getItemCount());
        assertTrue(cart.isEmpty());
    }
}