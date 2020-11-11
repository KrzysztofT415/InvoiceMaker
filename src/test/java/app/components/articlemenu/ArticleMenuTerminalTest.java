package app.components.articlemenu;

import app.DataBaseAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class ArticleMenuTerminalTest {

    LinkedList<Article> articlesDB;

    @Mock
    DataBaseAccess dataBaseMock;
    ArticleMenuTerminal articleMenuTerminal;
    ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        articlesDB = new LinkedList<>();

        articlesDB.addLast(new Article("Banany", 4.39));//0
        articlesDB.addLast(new Article("Jabłka", 3.20));//1
        articlesDB.addLast(new Article("Cytryny", 8.45));//2
        articlesDB.addLast(new Article("Pomarańcze", 7.59));//3
        articlesDB.addLast(new Article("Pomidory", 5.19));//4
        articlesDB.addLast(new Article("Ziemniaki", 1.49));//5
        articlesDB.addLast(new Article("Papryka", 7.30));//6
        articlesDB.addLast(new Article("Pieczarki", 8.99));//7

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
        when(dataBaseMock.getArticles()).thenReturn(articlesDB.toArray(new Article[0]));

        articleMenuTerminal = new ArticleMenuTerminal();
        articleMenuTerminal.connect(dataBaseMock);

        output = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(output);
        System.setOut(ps);

    }

    @Test
    void testAddArticle() {
        articleMenuTerminal.execute("create.Mango.10,59" , new Scanner(System.in));

        assertEquals("Mango", articlesDB.getLast().getName());
        assertEquals(10.59, articlesDB.getLast().getCost());
    }

    @Test
    void testRemoveArticle() {
        articleMenuTerminal.execute("remove.1" , new Scanner(System.in));

        assertNotEquals("Jabłka", articlesDB.get(1).getName());
        assertNotEquals(3.20, articlesDB.get(1).getCost());

        boolean contains = false;
        for (Article article : articlesDB) {
            if (article.getName().equals("Jabłka") && article.getCost() == 3.20) {
                contains = true;
                break;
            }
        }
        assertFalse(contains);
    }

    @Test
    void testChangeCost() {
        articleMenuTerminal.execute("change.1.cost.4,20", new Scanner(System.in));

        assertEquals(4.20, articlesDB.get(1).getCost());
    }

    @Test
    void testChangeName() {
        articleMenuTerminal.execute("change.1.name.Jabłka Polskie", new Scanner(System.in));

        assertEquals("Jabłka Polskie", articlesDB.get(1).getName());
    }
}