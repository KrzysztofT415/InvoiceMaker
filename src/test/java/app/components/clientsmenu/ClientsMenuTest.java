package app.components.clientsmenu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ClientsMenuTest {

    ClientsMenu clientsMenu;

    @BeforeEach
    void setUp() {
        this.clientsMenu = new ClientsMenu();
    }

    @Test
    void testCreateAndGet() {
        this.clientsMenu.create("John", "Brick");
        assertEquals("John", this.clientsMenu.getClients().getLast().getKey().getFirstname());
        assertEquals("Wick", this.clientsMenu.getClients().getLast().getKey().getLastname());
    }

    @Test
    void testRemove() {
        this.clientsMenu.create("John", "Wolf");
        this.clientsMenu.create("Jim", "Baron");
        this.clientsMenu.remove(0);
        boolean contains = false;
        for (Pair<Client, ArrayList<Integer>> client : this.clientsMenu.getClients()) {
            if (client.getKey().getFirstname().equals("John") && client.getKey().getLastname().equals("Wick")) {
                contains = true;
                break;
            }
        }
        assertFalse(contains);
    }

}