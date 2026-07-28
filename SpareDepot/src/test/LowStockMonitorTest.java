import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class LowStockMonitorTest {

    private LowStockMonitor monitor;

    @Before
    public void setUp() {
        monitor = new LowStockMonitor(10);
    }

    @Test
    public void defaultThreshold() {
        LowStockMonitor m = new LowStockMonitor();
        assertEquals(10, m.getThreshold());
    }

    @Test
    public void setThreshold() {
        monitor.setThreshold(20);
        assertEquals(20, monitor.getThreshold());
    }

    @Test
    public void lowThresholdStock() {
        Spares s = new Spares("P001", "Test", "Brand", 1000.0, 5, "Engine", "2024", "img.jpg",10);
        assertTrue(monitor.isLowStock(s));
    }

    @Test
    public void aboveThresholdStock() {
        Spares s = new Spares("P001", "Test", "Brand", 1000.0, 15, "Engine", "2024", "img.jpg",10);
        assertFalse(monitor.isLowStock(s));
    }

    @Test
    public void equalThresholdStock() {
        Spares s = new Spares("P001", "Test", "Brand", 1000.0, 10, "Engine", "2024", "img.jpg",10);
        assertFalse(monitor.isLowStock(s));
    }

    @Test
    public void zeroQuantityStock() {
        Spares s = new Spares("P001", "Test", "Brand", 1000.0, 0, "Engine", "2024", "img.jpg",10);
        assertTrue(monitor.isLowStock(s));
    }

    @Test
    public void customThreshold() {
        LowStockMonitor m = new LowStockMonitor(5);
        Spares s = new Spares("P001", "Test", "Brand", 500.0, 7, "Engine", "2024", "img.jpg",10);
        assertFalse(m.isLowStock(s));
    }

    @Test
    public void getLowStockCount() {
        Spares[] parts = {
                new Spares("P001","A","B",100,5, "Engine","2024","i.jpg",10),
                new Spares("P002","B","B",200,15, "Brakes","2024","i.jpg",10),
                new Spares("P003","C","B",300,3, "Electrical","2024","i.jpg",10)
        };
        Spares[] low = monitor.getLowStock(parts, parts.length);
        assertEquals(2, low.length);
    }

    @Test
    public void thresholdUpdates() {
        Spares[] parts = {
                new Spares("P001","A","B",100,8, "Engine","2024","i.jpg",10),
                new Spares("P002","B","B",200,15, "Brakes","2024","i.jpg",10),
                new Spares("P003","C","B",300,25, "Electrical","2024","i.jpg",10)
        };
        assertEquals(1, monitor.getLowStock(parts,parts.length).length);
        monitor.setThreshold(20);
        assertEquals(2, monitor.getLowStock(parts,parts.length).length);
        monitor.setThreshold(30);
        assertEquals(3, monitor.getLowStock(parts,parts.length).length);
    }
}