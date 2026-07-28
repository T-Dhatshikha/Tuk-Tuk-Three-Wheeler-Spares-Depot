import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class DataCleanerTest {
    private DataCleaner cleaner;

    @Before
    public void setUp() {
        cleaner = new DataCleaner();
    }

    @Test
    public void detectComma() {
        String line = "P001,Piston,Bajaj,4500,15,Engine,2023,img.jpg";
        assertEquals(",", cleaner.detectSeparator(line));
    }

    @Test
    public void detectSemicolon() {
        String line = "P003;Tyre;Brand;6500;24;Bodywork;2023;img.jpg";
        assertEquals(";", cleaner.detectSeparator(line));
    }

    @Test
    public void detectPipe() {
        String line = "P002|Brake Pad|TVS|1250|8|Brakes|2023|img.png";
        assertEquals("|", cleaner.detectSeparator(line));
    }

    @Test
    public void cleanPriceWithRS() {
        assertEquals(4500.0,
                cleaner.cleanPrice("Rs. 4500.00"), 0.01);
    }

    @Test
    public void cleanPriceWithRSNoDot() {
        assertEquals(850.0,
                cleaner.cleanPrice("Rs850"), 0.01);
    }

    @Test
    public void cleanPriceNumber() {
        assertEquals(1250.0,
                cleaner.cleanPrice("1250"), 0.01);
    }

    @Test
    public void cleanPriceWithSpaces() {
        assertEquals(450.0,
                cleaner.cleanPrice("  Rs. 450  "), 0.01);
    }

    @Test
    public void cleanCategoryUpper() {
        assertEquals("Engine",
                cleaner.cleanCategory("ENGINE"));
    }

    @Test
    public void cleanCategoryLower() {
        assertEquals("Electrical",
                cleaner.cleanCategory("electrical"));
    }
    @Test
    public void parsePartComma() {
        String line = "P001,Bajaj Piston,Bajaj,4500.00,15,Engine,2023-10-12,piston.jpg";
        Spares s = cleaner.parsePart(line);
        assertNotNull(s);
        assertEquals("P001", s.getCode());
        assertEquals("Bajaj Piston", s.getName());
        assertEquals(4500.0, s.getPrice(), 0.01);
        assertEquals(15, s.getQuantity());
        assertEquals("Engine", s.getCategory());
    }

    @Test
    public void parsePartPipe() {
        String line = "P002|Brake Pad|TVS|1250|8|Brakes|2023|img.png";
        Spares s = cleaner.parsePart(line);
        assertNotNull(s);
        assertEquals("P002", s.getCode());
        assertEquals(1250.0, s.getPrice(), 0.01);
        assertEquals(8, s.getQuantity());
    }

    @Test
    public void parseSemicolon() {
        String line = "P003;Tyre;Brand;6500;24;Bodywork;2023;img.jpg";
        Spares s = cleaner.parsePart(line);
        assertNotNull(s);
        assertEquals("P003", s.getCode());
        assertEquals(24, s.getQuantity());
    }

    @Test
    public void missingBrandField() {
        String line =
                "P003;Tyre;;6500;24;Bodywork;2023;img.jpg";
        Spares s = cleaner.parsePart(line);
        assertNotNull(s);
        assertEquals("P003", s.getCode());
    }

    @Test
    public void testEmptyLineReturnsNull() {
        assertNull(cleaner.parsePart(""));
    }

    @Test
    public void testNullLineReturnsNull() {
        assertNull(cleaner.parsePart(null));
    }

}