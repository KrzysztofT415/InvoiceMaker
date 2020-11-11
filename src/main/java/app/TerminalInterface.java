package app;

import java.util.Scanner;

public interface TerminalInterface {
    void showContent();
    int execute(String command, Scanner userInput);
    void connect(DataBaseAccess dataBaseAccess);
}
