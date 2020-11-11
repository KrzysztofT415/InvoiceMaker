package app;

import app.components.articlemenu.Article;
import app.components.clientsmenu.Client;
import app.components.invoicemenu.Invoice;

public interface DataBaseAccess {
    void addArticle(Article article);
    void removeArticle(int articleId);
    void changeArticleName(int articleId, String name);
    void changeArticleCost(int articleId, double cost);
    Article[] getArticles();

    void addClient(Client client);
    void removeClient(int clientId);
    Client[] getClients();
    Integer[] getClientInvoices(int clientId);

    void addInvoice(Invoice invoice);
    Invoice[] getInvoices();
    Invoice getInvoice(int invoiceId);
}
