import org.junit.Test;
import static org.junit.Assert.*;

public class DealerServiceTest {

    private Dealers[] Dealers() {
        return new Dealers[]{
                new Dealers("D101","A","0771","Negombo"),
                new Dealers("D102","B","0772","Colombo"),
                new Dealers("D103","C","0773","Malabe"),
                new Dealers("D104","D","0774","Gampaha"),
                new Dealers("D105","E","0775","Kandy")
        };
    }

    @Test
    public void selectFour() {
        DealerService ds = new DealerService();
        Dealers[] all = Dealers();
        Dealers[] selected = ds.randomDealers(all, all.length);
        assertEquals(4, selected.length);
    }

    @Test
    public void noDuplicates() {
        DealerService ds = new DealerService();
        Dealers[] all = Dealers();
        Dealers[] selected = ds.randomDealers(all, all.length);
        for (int i = 0; i < selected.length; i++) {
            for (int j = i + 1; j < selected.length; j++) {
                assertNotEquals(
                        selected[i].getDealerCode(),
                        selected[j].getDealerCode());
            }
        }
    }

    @Test
    public void sortByLocation() {
        DealerService ds = new DealerService();
        Dealers[] dealers = Dealers();
        ds.sortByLocation(dealers);
        assertEquals("Colombo", dealers[0].getLocation());
        assertEquals("Gampaha", dealers[1].getLocation());
        assertEquals("Kandy",   dealers[2].getLocation());
        assertEquals("Malabe",  dealers[3].getLocation());
        assertEquals("Negombo", dealers[4].getLocation());
    }

    @Test
    public void sort() {
        DealerService ds = new DealerService();
        Dealers[] dealers = {new Dealers("D101","A","0771","Malabe")};
        ds.sortByLocation(dealers);
        assertEquals("Malabe", dealers[0].getLocation());
    }

    @Test
    public void alreadySorted() {
        DealerService ds = new DealerService();
        Dealers[] dealers = {
                new Dealers("D101","A","0771","Colombo"),
                new Dealers("D102","B","0772","Malabe"),
                new Dealers("D103","C","0773","Negombo")
        };
        ds.sortByLocation(dealers);
        assertEquals("Colombo", dealers[0].getLocation());
        assertEquals("Malabe",  dealers[1].getLocation());
        assertEquals("Negombo", dealers[2].getLocation());
    }
}