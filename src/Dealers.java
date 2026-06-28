public class Dealers {
    String dealerCode;
    String name;
    String phone;
    String location;

    public Dealers(String dealerCode, String name, String phone, String location) {
        this.dealerCode = dealerCode;
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public String text() {
        return dealerCode + " , " + name + " , " + phone + " , " + location;
    }
}

