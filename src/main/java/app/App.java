package app;

import java.util.Scanner;

/**
 * Application making invoices
 *
 * @author Krzysztof Tałałaj
 */
public class App {

    private final Scanner userInput;
    private AppTerminalStates terminalState;

    App(DataBaseAccess dataBaseAccess) {
        this(true);
        AppTerminalStates.ARTICLES.connect(dataBaseAccess);
        AppTerminalStates.CLIENTS.connect(dataBaseAccess);
        AppTerminalStates.INVOICE.connect(dataBaseAccess);
    }

    App(boolean connected) {
        this.userInput = new Scanner(System.in);
        this.terminalState = AppTerminalStates.CLIENTS;

        if (!connected) {
            System.out.println("---------------------");
            System.out.println("Welcome, u can now execute commands, for help type 'help' :");
            System.out.println("---------------------");
            while (true) {
                String input = this.userInput.nextLine();

                if (!input.isEmpty()) {
                    this.execute(input);
                }
            }
        }
    }

    public void execute(String input) {
        switch (input) {
            case "exit":
                System.exit(1);

            case "show":
                System.out.print("---------------------\n");
                terminalState.showContent();
                if (terminalState != AppTerminalStates.INVOICE) {
                    System.out.print(" +\n");
                }
                System.out.print("---------------------\n");
                break;

            case "help":
                System.out.print("Program options:\n > EXIT program\n > SHOW current menu\n");

            default:
                int result = terminalState.execute(input, userInput);

                switch (result) {
                    case 1:
                        terminalState = AppTerminalStates.CLIENTS;
                        break;
                    case 2:
                        terminalState = AppTerminalStates.ARTICLES;
                        break;
                    case 3:
                        terminalState = AppTerminalStates.INVOICE;
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public static void main(final String[] args) {
        new App(false);
    }
}
