package app.components.clientsmenu;

import app.AppTerminalStates;
import app.DataBaseAccess;
import app.TerminalInterface;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientsMenuTerminal implements TerminalInterface {

    private ClientsMenu clientsMenu;

    public ClientsMenuTerminal() {
        this.clientsMenu = new ClientsMenu();
    }

    @Override
    public void connect(DataBaseAccess dataBaseAccess) {
        this.clientsMenu = new ClientsMenuWithDBConnection(dataBaseAccess);
    }

    @Override
    public void showContent() {
        String clientsMenuString = "Clients Menu:\n";
        for (Pair<Client, ArrayList<Integer>> client : this.clientsMenu.getClients()) {
            clientsMenuString = clientsMenuString.concat(" " + (clientsMenu.getClients().indexOf(client) + 1)+ " " + client.getKey().getFirstname() + " " + client.getKey().getLastname() + "\n    -> Invoices : ");
            if (client.getValue().size() == 0) {
                clientsMenuString = clientsMenuString.concat("none");
            } else {
                for (Integer invoiceId : client.getValue()) {
                    clientsMenuString = clientsMenuString.concat(" " + invoiceId);
                }
            }
            clientsMenuString = clientsMenuString.concat("\n");
        }
        System.out.print(clientsMenuString);
    }

    @Override
    public int execute(String commandString, Scanner userInput) {
        String[] command = commandString.split("\\.");

        switch (command[0]) {

            case "help":
                System.out.print("Clients menu options:\n > ARTICLES switches to article menu\n > ADD creates new client\n > REMOVE existing client\n > SEE invoice of chosen client\n > MAKE new invoice\n");
                break;

            case "articles":
                return 2;

            case "add":
                if (command.length == 3) {
                    clientsMenu.create(command[1], command[2]);
                } else {
                    int idNew = clientsMenu.getClients().size() + 1;
                    System.out.println("Creating new client using id : " + idNew);
                    System.out.print("Firstname : ");
                    String firstnameNew = userInput.nextLine();
                    System.out.print("Lastname : ");
                    String lastnameNew = userInput.nextLine();
                    clientsMenu.create(firstnameNew, lastnameNew);
                }
                break;

            case "remove":
                if (command.length == 2) {
                    clientsMenu.remove(Integer.parseInt(command[1]));
                } else {
                    System.out.print("ID : ");
                    int idRemove = userInput.nextInt() - 1;
                    userInput.nextLine();
                    clientsMenu.remove(idRemove);
                }
                break;

            case "see":
                if (command.length == 2) {
                    AppTerminalStates.INVOICE.execute("see." + command  [1], userInput);
                } else {
                    System.out.print("Invoice ID : ");
                    int idInv = userInput.nextInt() - 1;
                    userInput.nextLine();
                    AppTerminalStates.INVOICE.execute("see." + idInv, userInput);
                }
                return 3;

            case "make":
                if (command.length == 2) {
                    AppTerminalStates.INVOICE.execute("make." + command[1], userInput);
                } else {
                    System.out.print("Client ID : ");
                    int idMake = userInput.nextInt() - 1;
                    userInput.nextLine();
                    AppTerminalStates.INVOICE.execute("make." + idMake, userInput);
                }
                return 3;

            case "update":
                clientsMenu.addInvoiceTag(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                break;

            default:
                System.out.println("Command not recognized");
                break;
        }
        return 0;
    }

}
