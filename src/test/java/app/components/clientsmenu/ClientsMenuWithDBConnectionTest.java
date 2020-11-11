package app.components.clientsmenu;

import app.DataBaseAccess;
import app.components.invoicemenu.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;

public class ClientsMenuWithDBConnectionTest {

    @Mock
    DataBaseAccess dataBaseMock;

    LinkedList<Client> clientsDB;
    ClientsMenuWithDBConnection clientsMenu;

    @BeforeEach
    void setUp() {
        clientsDB = new LinkedList<>();

        clientsDB.addLast(new Client("John", "Smith"));//0
        clientsDB.addLast(new Client("Leonard", "Jaha"));//1
        clientsDB.addLast(new Client("Raven", "Reyes"));//2

        this.dataBaseMock = mock(DataBaseAccess.class);

//        void addClient(Client client);
        doAnswer(invocationOnMock -> {
            clientsDB.addLast(invocationOnMock.getArgumentAt(0, Client.class));
            return null;
        }).when(dataBaseMock).addClient(any(Client.class));

//        void removeClient(int clientId);
        doAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgumentAt(0, int.class);
            clientsDB.remove(id);
            return null;
        }).when(dataBaseMock).removeClient(any(int.class));

//        Client[] getClients();
        doAnswer(invocationOnMock -> clientsDB.toArray(new Client[0])).when(dataBaseMock).getClients();

//        Integer[] getClientInvoices(int clientId);
        when(dataBaseMock.getClientInvoices(any(int.class))).thenReturn(new Integer[]{});

        this.clientsMenu = new ClientsMenuWithDBConnection(this.dataBaseMock);
    }

    @Test
    void testCreateAndGet() {
        this.clientsMenu.create("John", "Brick");
        assertEquals("John", this.clientsDB.getLast().getFirstname());
        assertEquals("Brick", this.clientsDB.getLast().getLastname());

        assertEquals("John", this.clientsMenu.getClients().getLast().getKey().getFirstname());
        assertEquals("Brick", this.clientsMenu.getClients().getLast().getKey().getLastname());
    }

    @Test
    void testRemove() {
        this.clientsMenu.create("John", "Brick");
        this.clientsMenu.remove(3);
        boolean contains = false;
        for (Client client : clientsDB) {
            if (client.getFirstname().equals("John") && client.getLastname().equals("Brick")) {
                contains = true;
                break;
            }
        }
        assertFalse(contains);
    }

}