public class Main {
    public static void main(String[] args) {
        System.out.println(" Malabe Tuk-Tuk & Three-Wheeler Spares Depot");
        System.out.println();

        //Spares Details
        FileParser fileParser = new FileParser();
        AuditLogger logger = new AuditLogger();
        InventoryManager inventory = new InventoryManager(fileParser);
        inventory.sort();
        System.out.println("Total Value: Rs." + inventory.getTotalValue() + "\n");

        LowStockMonitor monitor = new LowStockMonitor();
        monitor.printReport(inventory.getSpares(), inventory.getSpareCount());
        System.out.println();

        // Random dealer selection
        Dealers[] allDealers = fileParser.loadDealers();
        DealerService dealerService = new DealerService();
        Dealers[] randomFour = dealerService.randomDealers(allDealers, allDealers.length);

        if (randomFour.length == 4) {
            dealerService.sortByLocation(randomFour);
            dealerService.printDealers(randomFour);
        }
        System.out.println();

        Cart cart = new Cart();
        Spares spare1 = inventory.searchByCode("P001");
        Spares spare2 = inventory.searchByCode("P002");

        if (spare1 != null) cart.addItem(spare1, 3);
        if (spare2 != null) cart.addItem(spare2, 1);

        cart.printCart();
        cart.checkout(inventory, logger);

        System.out.println("Done!");
    }
}