package app.components.clientsmenu;

import java.util.ArrayList;
import java.util.LinkedList;

public class ClientsMenu implements ClientsManagement {

    private final LinkedList<Pair<Client, ArrayList<Integer>>> clients;

    public ClientsMenu() {
        this.clients = new LinkedList<>();
    }

    @Override
    public void addInvoiceTag(int clientId, int invoiceId) {
        this.clients.get(clientId).getValue().add(invoiceId);
    }

    @Override
    public void create(String firstname, String lastname) {
        this.clients.addLast(new Pair<>(new Client(firstname, lastname),new ArrayList<>()));
    }

    @Override
    public void remove(int clientId) {
        this.clients.remove(clientId);
    }

    @Override
    public LinkedList<Pair<Client, ArrayList<Integer>>> getClients() {
        return clients;
    }

}
