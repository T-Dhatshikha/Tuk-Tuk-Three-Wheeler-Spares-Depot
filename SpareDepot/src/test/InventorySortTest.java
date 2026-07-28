import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class InventorySortTest {

    private InventoryManager inv;
    private int preLoadedCount;

    @Before
    public void setUp() {
        inv = new InventoryManager(new FileParser());
        preLoadedCount = inv.getSpareCount();

        inv.addSpare(new Spares("P051", "A", "B", 100, 10, "Brakes", "2024", "i.jpg", 10));
        inv.addSpare(new Spares("P050", "C", "B", 300, 10, "Engine", "2024", "i.jpg", 10));
        inv.addSpare(new Spares("P052", "B", "B", 200, 10, "Electrical", "2024", "i.jpg", 10));
        inv.addSpare(new Spares("P054", "D", "B", 400, 10, "Bodywork", "2024", "i.jpg", 10));
    }

    @After
    public void tearDown() {
        inv.deleteSpare("P051");
        inv.deleteSpare("P050");
        inv.deleteSpare("P052");
        inv.deleteSpare("P054");
    }

    @Test
    public void categorySort() {
        inv.sort();
        Spares[] parts = inv.getSpares();
        assertEquals("Bodywork", parts[0].getCategory());
    }

    @Test
    public void categorySortByCode() {
        inv.sort();
        Spares[] parts = inv.getSpares();
        int pos51 = -1;
        int pos50 = -1;

        for (int i = 0; i < inv.getSpareCount(); i++) {
            if (parts[i] != null) {
                if ("P051".equals(parts[i].getCode())) pos51 = i;
                if ("P050".equals(parts[i].getCode())) pos50 = i;
            }
        }

        assertTrue("P051 found", pos51 != -1);
        assertTrue("P050 found", pos50 != -1);
        assertTrue("P051 (Brakes) should come before P050 (Engine)", pos51 < pos50);
    }

    @Test
    public void allSpares() {
        inv.sort();
        assertEquals(preLoadedCount + 4, inv.getSpareCount());
    }
}