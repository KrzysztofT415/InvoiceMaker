package app.components.invoicemenu;

import java.util.LinkedList;

public interface InvoiceManagement {
    String saveCurrentInvoice();
    void newInvoice(int clientId);
    void getInvoice(int invoiceId);
    Invoice getCurrentInvoice();
    LinkedList<Invoice> getInvoices();
    void addArticle(int articleId, int quantity);
    void changeArticleQuantity(int articleId, int quantity);
    void removeArticle(int articleId);
}
