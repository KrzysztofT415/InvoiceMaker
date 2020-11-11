package app.components.invoicemenu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    Invoice invoice;

    @BeforeEach
    void setUp() {
        this.invoice = new Invoice(0);
    }

    @Test
    void testAddAndRemove() {
        this.invoice.addArticle(1,10);
        this.invoice.addArticle(4,23);
        assertEquals(1,invoice.getArticles().get(0)[0]);
        assertEquals(10,invoice.getArticles().get(0)[1]);
        this.invoice.removeArticle(0);
        assertNotEquals(1,invoice.getArticles().get(0)[0]);
        assertNotEquals(10,invoice.getArticles().get(0)[1]);
    }

    @Test
    void testAddAndChange() {
        this.invoice.addArticle(2,3);
        assertEquals(2,invoice.getArticles().get(0)[0]);
        assertEquals(3,invoice.getArticles().get(0)[1]);
        this.invoice.changeArticleQuantity(0, 6);
        assertEquals(6,invoice.getArticles().get(0)[1]);
    }
}