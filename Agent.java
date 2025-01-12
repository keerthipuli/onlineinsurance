package Projectt;

public class Agent {
    private String name;
    private String status;
    private String contact;

    public Agent(String name, String status, String contact) {
        this.name = name;
        this.status = status;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getContact() {
        return contact;
    }
}
