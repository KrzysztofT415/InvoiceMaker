package app.components.invoicemenu;

import app.AppTerminalStates;
import app.DataBaseAccess;
import app.TerminalInterface;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;

public class InvoiceMenuTerminal implements TerminalInterface {

    private InvoiceMenu invoiceMenu;
    private boolean watchingMode = false;

    public InvoiceMenuTerminal() {
        this.invoiceMenu = new InvoiceMenu();
    }

    @Override
    public void connect(DataBaseAccess dataBaseAccess) {
        this.invoiceMenu = new InvoiceMenuWithDBConnection(dataBaseAccess);
    }

    @Override
    public void showContent() {
        System.out.print("Invoice Menu :\n    Mode - " + (watchingMode ? "Spectating" : "Creating") + "\n");
        if (!watchingMode) {
            System.out.print("~~~~~~~~~~~~~~~~~~~~~\n   Possible products to choose from ");
            AppTerminalStates.ARTICLES.showContent();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~\n");
        }
        System.out.print("Current Invoice : \n");
        LinkedList<int[]> articles = invoiceMenu.getCurrentInvoice().getArticles();
        for (int[] article : articles) {

            ByteArrayOutputStream catcher = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(catcher);
            PrintStream old = System.out;
            System.setOut(ps);
            AppTerminalStates.ARTICLES.execute("cost." + article[0], null);
            System.out.flush();
            System.setOut(old);

            double cost = Double.parseDouble(catcher.toString()) * article[1];

            System.out.print((articles.indexOf(article) + 1) + " ");
            AppTerminalStates.ARTICLES.execute("name." + article[0], null);
            System.out.print(" - q : " + article[1] + ", total : " + cost + "\n");
        }
        if (!watchingMode) {
            System.out.println(" +\n");
        }
    }

    @Override
    public int execute(String commandString, Scanner userInput) {
        String[] command = commandString.split("\\.");

        switch (command[0]) {

            case "help":
                System.out.print("Invoice menu mode : " + (watchingMode ? "Spectating" : "Creating") + "\nCurrent mode options :\n");
                if (watchingMode) {
                    System.out.print(" > CANCEL close invoice menu and go back to clients menu\n");
                } else {
                    System.out.print(" > CANCEL creating invoice and go back to clients menu\n > ADD new article\n > REMOVE added article\n > CHANGE added article quantity\n > ACCEPT made invoice and store it\n");
                }
                return 0;

            case "see":
                this.watchingMode = true;
                invoiceMenu.getInvoice(Integer.parseInt(command[1]));
                return 0;

            case "make":
                this.watchingMode = false;
                invoiceMenu.newInvoice(Integer.parseInt(command[1]));
                return 0;

            case "add":
                int idNew;
                int quantityNew;

                if (command.length == 3) {
                    idNew = Integer.parseInt(command[1]);
                    quantityNew = Integer.parseInt(command[2]);
                } else {
                    System.out.print("Chosen article ID : ");
                    idNew = userInput.nextInt() - 1;
                    userInput.nextLine();
                    System.out.print("Set quantity : ");
                    quantityNew = userInput.nextInt();
                    userInput.nextLine();
                }

                int newArticleQ = 0;
                for (int[] article : invoiceMenu.getCurrentInvoice().getArticles()) {
                    if (article[0] == idNew) {
                        newArticleQ = article[1];
                    }
                }

                if (newArticleQ == 0) { invoiceMenu.addArticle(idNew, quantityNew); }
                else { invoiceMenu.changeArticleQuantity(idNew - 1, quantityNew + newArticleQ); }
                return 0;

            case "remove":
                if (command.length == 2) {
                    invoiceMenu.removeArticle(Integer.parseInt(command[1]));
                } else {
                    System.out.print("Client ID : ");
                    int idRemove = userInput.nextInt() - 1;
                    userInput.nextLine();
                    invoiceMenu.removeArticle(idRemove);
                }
                return 0;

            case "change":
                if (command.length == 3) {
                    invoiceMenu.changeArticleQuantity(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                } else {
                    System.out.print("Article on list ID : ");
                    int idChange = userInput.nextInt() - 1;
                    userInput.nextLine();
                    System.out.print("New quantity : ");
                    int quantityChange = userInput.nextInt();
                    userInput.nextLine();
                    invoiceMenu.changeArticleQuantity(idChange, quantityChange);
                }
                return 0;

            case "accept":
                if (invoiceMenu.getCurrentInvoice().getArticles().size() == 0) {
                    System.out.println("Cannot create empty invoice");
                    return 0;
                }
                String inv = invoiceMenu.saveCurrentInvoice();
                AppTerminalStates.CLIENTS.execute("update." + inv, userInput);
                return 1;

            case "cancel":
                return 1;

            default:
                System.out.println("Command not recognized");
                return 0;
        }
    }

}
