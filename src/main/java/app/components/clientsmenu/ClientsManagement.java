package app.components.clientsmenu;

import java.util.ArrayList;
import java.util.LinkedList;

public interface ClientsManagement {
    void create(String firstname, String lastname);
    void remove(int clientId);
    LinkedList<Pair<Client, ArrayList<Integer>>> getClients();
    void addInvoiceTag(int clientId, int invoiceId);
}
