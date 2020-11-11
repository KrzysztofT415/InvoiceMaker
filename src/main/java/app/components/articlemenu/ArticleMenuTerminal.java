package app.components.articlemenu;

import app.DataBaseAccess;
import app.TerminalInterface;
import java.util.Scanner;

public class ArticleMenuTerminal implements TerminalInterface {

    private ArticleMenu articleMenu;

    public ArticleMenuTerminal() {
        this.articleMenu = new ArticleMenu();
    }

    @Override
    public void connect(DataBaseAccess dataBaseAccess) {
        this.articleMenu = new ArticleMenuWithDBConnection(dataBaseAccess);
    }

    @Override
    public void showContent() {
        String articleMenuString = "Article Menu :\n";
        for (Article article : this.articleMenu.getArticles()) {
            articleMenuString = articleMenuString.concat(" " + (articleMenu.getArticles().indexOf(article) + 1)+ " " + article.getName() + " " + article.getCost() + "\n");
        }
        System.out.print(articleMenuString);
    }

    @Override
    public int execute(String commandString, Scanner userInput) {
        String[] command = commandString.split("\\.");

        switch (command[0]) {

            case "help":
                System.out.print("Article menu options:\n > CLOSE article menu and go back to clients menu\n > CREATE new article\n > REMOVE existing article\n > CHANGE chosen article properties\n");
                break;

            case "close":
                return 1;

            case "create":
                if (command.length == 3) {
                    articleMenu.create(command[1], Double.parseDouble(command[2].replace(",", ".")));
                } else {
                    int idNew = articleMenu.getArticles().size() + 1;
                    System.out.println("Creating new article on position : " + idNew);
                    System.out.print("Name : ");
                    String nameNew = userInput.nextLine();
                    System.out.print("Cost : ");
                    double costNew = userInput.nextDouble();
                    userInput.nextLine();
                    articleMenu.create(nameNew, costNew);
                }
                break;

            case "remove":
                if (command.length == 2) {
                    articleMenu.remove(Integer.parseInt(command[1]));
                } else {
                    System.out.print("ID : ");
                    int idRemove = userInput.nextInt() - 1;
                    userInput.nextLine();
                    articleMenu.remove(idRemove);
                }
                break;

            case "change":
                if (command.length == 4) {
                    if (command[2].equals("name")) {
                        this.articleMenu.changeArticleName(Integer.parseInt(command[1]), command[3]);
                    } else if (command[2].equals("cost")) {
                        this.articleMenu.changeArticleCost(Integer.parseInt(command[1]), Double.parseDouble(command[3].replace(",", ".")));
                    }
                } else {
                    System.out.print("ID : ");
                    int idChange = userInput.nextInt() - 1;
                    userInput.nextLine();
                    System.out.print("option (name, cost): ");
                    String option = userInput.nextLine();

                    switch (option) {
                        case "name":
                            System.out.print("New name : ");
                            String nameChange = userInput.nextLine();
                            this.articleMenu.changeArticleName(idChange, nameChange);
                            break;

                        case "cost":
                            System.out.print("New cost : ");
                            double costChange = userInput.nextDouble();
                            userInput.nextLine();
                            this.articleMenu.changeArticleCost(idChange, costChange);
                            break;

                        default:
                            System.out.println("Option unrecognized");
                            break;
                    }
                }
                break;

            case "name":
                System.out.print(articleMenu.getArticles().get(Integer.parseInt(command[1])).getName());
                break;

            case "cost":
                System.out.print(articleMenu.getArticles().get(Integer.parseInt(command[1])).getCost());
                break;

            default:
                System.out.println("Command not recognized");
                break;
        }
        return 0;
    }

}
