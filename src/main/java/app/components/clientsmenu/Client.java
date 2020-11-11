package app.components.clientsmenu;

public class Client {
    private final String firstname;
    private final String lastname;

    public Client(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
