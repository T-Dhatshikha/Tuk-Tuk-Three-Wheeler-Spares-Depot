public class DealerService {

    // METHOD 1 - Select exactly 4 random unique dealers
    public Dealers[] randomDealers(Dealers[] allDealers, int dealerCount) {
        if (dealerCount < 4) {
            System.out.println("Need at least 4 dealers!");
            return new Dealers[0];
        }
        Dealers[] selected = new Dealers[4];
        int count = 0;
        long timeValue = System.currentTimeMillis();
        while (count < 4) {
            int index = (int)(timeValue % dealerCount);
            if (index < 0) {
                index *= -1;
            }
            timeValue = timeValue * 1103515245 + 12345;
            Dealers candidate = allDealers[index];
            boolean picked = false;
            for (int i = 0; i < count; i++) {
                if (selected[i].getDealerID().equals(candidate.getDealerID())) {
                    picked = true;
                }
            }

            if (!picked) {
                selected[count] = candidate;
                count += 1;
            }
        }
        return selected;
    }

    // METHOD 2 - Sort 4 dealers by location alphabetically
    public void sortByLocation(Dealers[] dealers) {
        for (int i = 0; i < dealers.length - 1; i++) {
            for (int j = 0; j < dealers.length - 1 - i; j++) {
                if (dealers[j].getLocation().compareTo(
                        dealers[j + 1].getLocation()) > 0) {

                    Dealers temp = dealers[j];
                    dealers[j] = dealers[j + 1];
                    dealers[j + 1] = temp;
                }
            }
        }
    }

    // METHOD 3 - Print the 4 selected dealers
    public void printDealers(Dealers[] dealers) {
        System.out.println("Selected Dealers");
        for (int i = 0; i < dealers.length; i++) {
            System.out.println((i + 1) + ". " + dealers[i].text());
        }
    }
}