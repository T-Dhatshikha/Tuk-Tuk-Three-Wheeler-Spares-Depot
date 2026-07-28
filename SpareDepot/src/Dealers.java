public class Dealers {
    private String dealerCode;
    private String name;
    private String phone;
    private String location;

    public Dealers(String dealerCode, String name, String phone, String location) {
        this.dealerCode = dealerCode;
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public String getDealerCode() {
        return dealerCode;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getLocation() {
        return location;
    }

    public String text() {
        return dealerCode + " , " + name + " , " + phone + " , " + location;
    }
}


