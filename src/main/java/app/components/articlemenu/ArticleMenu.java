package app.components.articlemenu;

import java.util.LinkedList;

public class ArticleMenu implements ArticleManagement {

    private final LinkedList<Article> articles;

    public ArticleMenu() {
        this.articles = new LinkedList<>();
    }

    @Override
    public void create(String name, double cost) {
        this.articles.addLast(new Article(name, cost));
    }

    @Override
    public void remove(int articleId) {
        this.articles.remove(articleId);
    }

    @Override
    public void changeArticleName(int articleId, String name) {
        this.articles.get(articleId).setName(name);
    }

    @Override
    public void changeArticleCost(int articleId, double cost) {
        this.articles.get(articleId).setCost(cost);
    }

    @Override
    public LinkedList<Article> getArticles() {
        return articles;
    }
}
