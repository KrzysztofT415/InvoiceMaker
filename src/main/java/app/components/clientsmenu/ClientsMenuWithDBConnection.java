package app.components.clientsmenu;

import app.DataBaseAccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ClientsMenuWithDBConnection extends ClientsMenu {

    private final DataBaseAccess dataBaseAccess;
    private final LinkedList<Pair<Client, ArrayList<Integer>>> clients;

    public ClientsMenuWithDBConnection(DataBaseAccess dataBaseAccess) {
        this.dataBaseAccess = dataBaseAccess;
        this.clients = this.getClients();
    }

    @Override
    public void addInvoiceTag(int clientId, int invoiceId) {
        this.clients.get(clientId).getValue().add(invoiceId);
    }

    @Override
    public void create(String firstname, String lastname) {
        this.dataBaseAccess.addClient(new Client(firstname, lastname));
    }

    @Override
    public void remove(int clientId) {
        this.dataBaseAccess.removeClient(clientId);
    }

    @Override
    public LinkedList<Pair<Client, ArrayList<Integer>>> getClients() {

        LinkedList<Client> clientsReload = new LinkedList<>(Arrays.asList(this.dataBaseAccess.getClients()));
        LinkedList<Pair<Client, ArrayList<Integer>>> newClientsList = new LinkedList<>();

        for (Client client : clientsReload) {
            Integer[] invoices = dataBaseAccess.getClientInvoices(clientsReload.indexOf(client));
            ArrayList<Integer> invoicesList = new ArrayList<>(Arrays.asList(invoices));
            newClientsList.add(clientsReload.indexOf(client),new Pair<>(client,invoicesList));
        }

        return newClientsList;
    }
}
