public class Main{
    public static void main(String[] args) {
        Spares demoSpares = new Spares(
                "P001", "Bajaj 4-Stroke Piston",
                "Bajaj", 4500.00,15,
                "Engine", "2023-10-12",
                "piston.jpg"
        );

        Dealers demoDealers = new Dealers(
                "D101", "Sunil Motors",
                "0771234567", "Malabe"
        );

        System.out.println("Inventory Details");
        System.out.println(demoSpares.text());

        System.out.println("Dealers Details");
        System.out.println(demoDealers.text());

        System.out.println("Low Stock Details");
        if (demoSpares.lowStock()) {
            System.out.println(demoSpares.name + " is low on stock.");
        } else{
            System.out.println(demoSpares.name + " stock is fine.");
        }
    }
}