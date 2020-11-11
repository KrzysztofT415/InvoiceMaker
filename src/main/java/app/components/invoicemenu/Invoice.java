package app.components.invoicemenu;

import java.util.LinkedList;

public class Invoice {
    private final int clientId;
    private final LinkedList<int[]> articles;

    public Invoice(int clientId) {
        this.clientId = clientId;
        this.articles = new LinkedList<>();
    }

    public int getClientId() {
        return clientId;
    }

    public void addArticle(int articleId, int quantity) {
        this.articles.add(new int[] {articleId, quantity} );
    }

    public void removeArticle(int articleId) {
        this.articles.remove(articleId);
    }

    public void changeArticleQuantity(int articleId, int quantity) {
        this.articles.set(articleId, new int[] {this.articles.get(articleId)[0], quantity});
    }

    public LinkedList<int[]> getArticles() {
        return articles;
    }
}
