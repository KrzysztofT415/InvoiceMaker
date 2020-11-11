package app;

import app.components.articlemenu.ArticleMenuTerminal;
import app.components.clientsmenu.ClientsMenuTerminal;
import app.components.invoicemenu.InvoiceMenuTerminal;

import java.util.Scanner;

public enum AppTerminalStates {
    CLIENTS(new ClientsMenuTerminal()),
    ARTICLES(new ArticleMenuTerminal()),
    INVOICE(new InvoiceMenuTerminal());

    private final TerminalInterface terminal;

    AppTerminalStates(TerminalInterface terminal) {
        this.terminal = terminal;
    }

    public void showContent() {
        this.terminal.showContent();
    }

    public int execute(String command, Scanner userInput) {
        return this.terminal.execute(command, userInput);
    }

    public void connect(DataBaseAccess dataBaseAccess) {
        this.terminal.connect(dataBaseAccess);
    }
}
