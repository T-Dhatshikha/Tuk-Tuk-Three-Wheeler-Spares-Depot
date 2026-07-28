import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class InventoryManagerTest {

    private InventoryManager inv;

    @Before
    public void setUp() {
        inv = new InventoryManager(new FileParser());
    }

    @Test
    public void addValidSpare() {
        int before = inv.getSpareCount();
        Spares s = new Spares("P040","Test","Brand", 500.0,10,"Engine","2024","img.jpg",5);
        assertTrue(inv.addSpare(s));
        assertEquals(before + 1, inv.getSpareCount());
    }

    @Test
    public void addDuplicateCode() {
        Spares s1 = new Spares("P012","Part1","Brand", 500.0,10,"Engine","2024","img.jpg",10);
        Spares s2 = new Spares("P012","Part2","Brand", 600.0,5,"Brakes","2024","img.jpg",10);
        inv.addSpare(s1);
        assertFalse(inv.addSpare(s2));
    }

    @Test
    public void addNegativePrice() {
        Spares s = new Spares("P013","Test","Brand", -100.0,10,"Engine","2024","img.jpg",10);
        assertFalse(inv.addSpare(s));
    }

    @Test
    public void addNegativeQuantity() {
        Spares s = new Spares("P014","Test","Brand", 100.0,-5,"Engine","2024","img.jpg",10);
        assertFalse(inv.addSpare(s));
    }

    @Test
    public void deleteSpare() {
        Spares s = new Spares("P015","Delete Me","Brand", 500.0,10,"Engine","2024","img.jpg",10);
        inv.addSpare(s);
        assertTrue(inv.deleteSpare("P015"));
    }

    @Test
    public void deleteNonExist() {
        assertFalse(inv.deleteSpare("XXXXX"));
    }

    @Test
    public void updateQuantity() {
        Spares s = new Spares("P011","Test","Brand", 500.0,10,"Engine","2024","img.jpg",10);
        inv.addSpare(s);
        assertTrue(inv.updateQuantity("P011", 25));
        assertEquals(25, inv.searchByCode("P011").getQuantity());
    }

    @Test
    public void updateNegativeQuantity() {
        Spares s = new Spares("P016","Test","Brand", 500.0,10,"Engine","2024","img.jpg",10);
        inv.addSpare(s);
        assertFalse(inv.updateQuantity("P011", -5));
    }

    @Test
    public void UpdatePrice() {
        Spares s = new Spares("P017","Test","Brand", 500.0,10,"Engine","2024","img.jpg",10);
        inv.addSpare(s);
        assertTrue(inv.updatePrice("P012", 750.0));
        assertEquals(750.0, inv.searchByCode("P012").getPrice(), 0.01);
    }

    @Test
    public void updateNegativePrice() {
        Spares s = new Spares("P018","Test","Brand", 500.0,10,"Engine","2024","img.jpg",10);
        inv.addSpare(s);
        assertFalse(inv.updatePrice("P011", -100.0));
    }

    @Test
    public void fountSearchByCode() {
        Spares s = new Spares("P025","Find Me","Brand", 500.0,10,"Engine","2024","img.jpg",5);
        inv.addSpare(s);
        Spares found = inv.searchByCode("P025");
        assertNotNull(found);
        assertEquals("Find Me", found.getName());
    }

    @Test
    public void notFoundSearchByCode() {
        assertNull(inv.searchByCode("NOTEXIST"));
    }

    @Test
    public void PositiveTotalValue() {
        assertTrue(inv.getTotalValue() > 0);
    }
}