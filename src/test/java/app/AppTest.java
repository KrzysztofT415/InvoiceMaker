package app;

import app.components.articlemenu.Article;
import app.components.clientsmenu.Client;
import app.components.invoicemenu.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AppTest {

    LinkedList<Client> clientsDB;
    LinkedList<Article> articlesDB;
    LinkedList<Invoice> invoiceDB;

    @Mock
    DataBaseAccess dataBaseMock;
    App app;
    ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        clientsDB = new LinkedList<>();

        clientsDB.addLast(new Client("John", "Smith"));//0
        clientsDB.addLast(new Client("Leonard", "Jaha"));//1
        clientsDB.addLast(new Client("Raven", "Reyes"));//2

        articlesDB = new LinkedList<>();

        articlesDB.addLast(new Article("Banany", 4.39));//0
        articlesDB.addLast(new Article("Jabłka", 3.20));//1
        articlesDB.addLast(new Article("Cytryny", 8.45));//2
        articlesDB.addLast(new Article("Pomarańcze", 7.59));//3
        articlesDB.addLast(new Article("Pomidory", 5.19));//4
        articlesDB.addLast(new Article("Ziemniaki", 1.49));//5
        articlesDB.addLast(new Article("Papryka", 7.30));//6
        articlesDB.addLast(new Article("Pieczarki", 8.99));//7

        invoiceDB = new LinkedList<>();

        Invoice invoiceJS = new Invoice(0);
        invoiceJS.addArticle(0,3);
        invoiceJS.addArticle(1,5);
        invoiceJS.addArticle(3,4);
        invoiceDB.addLast(invoiceJS);//0

        Invoice invoiceLJ = new Invoice(1);
        invoiceJS.addArticle(2,1);
        invoiceJS.addArticle(4,3);
        invoiceJS.addArticle(5,10);
        invoiceJS.addArticle(1,4);
        invoiceDB.addLast(invoiceLJ);//1

        Invoice invoiceCR = new Invoice(1);
        invoiceJS.addArticle(7,12);
        invoiceJS.addArticle(6,6);
        invoiceDB.addLast(invoiceCR);//2



        dataBaseMock = Mockito.mock(DataBaseAccess.class);

//        void addArticle(Article article);
        doAnswer(invocationOnMock -> {
            articlesDB.addLast(invocationOnMock.getArgumentAt(0, Article.class));
            return null;
        }).when(dataBaseMock).addArticle(any(Article.class));

//        void removeArticle(int articleId);
        doAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgumentAt(0, int.class);
            articlesDB.remove(id);
            return null;
        }).when(dataBaseMock).removeArticle(any(int.class));

//        void changeArticleName(int articleId, String name);
        doAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgumentAt(0, int.class);
            articlesDB.get(id).setName(invocationOnMock.getArgumentAt(1, String.class));
            return null;
        }).when(dataBaseMock).changeArticleName(any(int.class), any(String.class));

//        void changeArticleCost(int articleId, double cost);
        doAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgumentAt(0, int.class);
            articlesDB.get(id).setCost(invocationOnMock.getArgumentAt(1, double.class));
            return null;
        }).when(dataBaseMock).changeArticleCost(any(int.class), any(double.class));

//        Article[] getArticles();
        doAnswer(invocationOnMock -> articlesDB.toArray(new Article[0])).when(dataBaseMock).getArticles();


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
        doAnswer(invocationOnMock -> {
            int id = invocationOnMock.getArgumentAt(0, int.class);
            ArrayList<Integer> ids = new ArrayList<>();
            for (Invoice invoice : invoiceDB) {
                if (invoice.getClientId() == id) {
                    ids.add(id);
                }
            }
            return ids.toArray(new Integer[0]);
        }).when(dataBaseMock).getClientInvoices(any(int.class));


//        void addInvoice(Invoice invoice);
        doAnswer(invocationOnMock -> {
            invoiceDB.addLast(invocationOnMock.getArgumentAt(0, Invoice.class));
            return null;
        }).when(dataBaseMock).addInvoice(any(Invoice.class));

//        Invoice[] getInvoices();
        doAnswer(invocationOnMock -> invoiceDB.toArray(new Invoice[0])).when(dataBaseMock).getInvoices();

//        Invoice getInvoice(int invoiceId);
        doAnswer(invocationOnMock -> invoiceDB.get(invocationOnMock.getArgumentAt(0, int.class))).when(dataBaseMock).getInvoice(any(int.class));



        app = new App(dataBaseMock);

        output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        System.setOut(ps);

    }

    @Test
    void testHelpClientMenu() {
        app.execute("help");
        assertEquals("Program options:\n > EXIT program\n > SHOW current menu\nClients menu options:\n > ARTICLES switches to article menu\n > ADD creates new client\n > REMOVE existing client\n > SEE invoice of chosen client\n > MAKE new invoice\n", output.toString());
    }

    @Test
    void testAddClient() {
        app.execute("add.Bellamy.Wick");
        assertEquals("Bellamy", clientsDB.getLast().getFirstname());
        assertEquals("Wick", clientsDB.getLast().getLastname());
    }

    @Test
    void testAddArticle() {
        app.execute("articles");
        app.execute("create.Mango.10,59");

        assertEquals("Mango", articlesDB.getLast().getName());
        assertEquals(10.59, articlesDB.getLast().getCost());
    }

    @Test
    void testShowArticles() {
        app.execute("articles");
        app.execute("show");

        String articleMenuString = "---------------------\nArticle Menu :\n";
        for (Article article : articlesDB) {
            articleMenuString = articleMenuString.concat(" " + (articlesDB.indexOf(article) + 1)+ " " + article.getName() + " " + article.getCost() + "\n");
        }
        assertEquals(articleMenuString + " +\n---------------------\n", output.toString());
    }

    @Test
    void testMakeInvoice() {
        app.execute("make.2");
        app.execute("add.1.10");
        app.execute("accept");

        assertNotNull(invoiceDB.get(3));
        assertEquals(1, invoiceDB.get(3).getArticles().get(0)[0]);
        assertEquals(10, invoiceDB.get(3).getArticles().get(0)[1]);

        assertEquals(1, dataBaseMock.getInvoices()[3].getArticles().get(0)[0]);
    }
}