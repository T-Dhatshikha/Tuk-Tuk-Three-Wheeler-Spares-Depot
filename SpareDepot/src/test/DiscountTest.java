import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class DiscountTest {

    private Cart cart;

    @Before
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void synergyRequire() {
        Spares engine = new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg", 10);
        Spares electrical  = new Spares("P004","Bulb","Philips", 500.0,20,"Electrical","2024","img.jpg", 10);
        cart.addItem(engine, 1);
        cart.addItem(electrical, 1);
        assertTrue(cart.hasSynergyDiscount());
    }

    @Test
    public void NoElectricalSynergy() {
        Spares engine = new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg",10);
        cart.addItem(engine, 1);
        assertFalse(cart.hasSynergyDiscount());
    }

    @Test
    public void NoEngineSynergy() {
        Spares electrical = new Spares("P004","Bulb","Philips", 500.0,20,"Electrical","2024","img.jpg",10);
        cart.addItem(electrical, 1);
        assertFalse(cart.hasSynergyDiscount());
    }

    @Test
    public void synergyDiscount() {
        Spares engine = new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg",10);
        Spares electrical  = new Spares("P004","Bulb","Philips", 500.0,20,"Electrical","2024","img.jpg",10);
        cart.addItem(engine, 1);
        cart.addItem(electrical, 1);
        assertEquals(1350.0, cart.finalTotal(), 0.01);
    }

    @Test
    public void testBulkAndSynergyTogether() {
        Spares engine = new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg",10);
        Spares electrical  = new Spares("P004","Bulb","Philips", 500.0,20,"Electrical","2024","img.jpg",10);
        cart.addItem(engine, 3);
        cart.addItem(electrical, 1);
        assertEquals(3015.0, cart.finalTotal(), 0.01);
    }

    @Test
    public void bulkDiscountSynergy() {
        Spares engine = new Spares("P001","Piston","Bajaj", 1000.0,15,"Engine","2024","img.jpg",10);
        Spares electrical   = new Spares("P004","Bulb","Philips", 500.0,20,"Electrical","2024","img.jpg",10);
        cart.addItem(engine, 3);
        cart.addItem(electrical, 1);

        double rawTotal = cart.rawTotal();
        double finalTotal = cart.finalTotal();

        assertTrue(finalTotal < rawTotal);
    }
}