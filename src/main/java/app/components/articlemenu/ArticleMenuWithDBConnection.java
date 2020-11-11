package app.components.articlemenu;

import app.DataBaseAccess;
import java.util.Arrays;
import java.util.LinkedList;

public class ArticleMenuWithDBConnection extends ArticleMenu {

    private final DataBaseAccess dataBaseAccess;
    private final LinkedList<Article> articles;

    public ArticleMenuWithDBConnection(DataBaseAccess dataBaseAccess) {
        this.dataBaseAccess = dataBaseAccess;
        this.articles = this.getArticles();
    }

    @Override
    public void create(String name, double cost) {
        this.dataBaseAccess.addArticle(new Article(name, cost));
    }

    @Override
    public void remove(int articleId) {
        this.dataBaseAccess.removeArticle(articleId);
    }

    @Override
    public void changeArticleName(int articleId, String name) {
        this.dataBaseAccess.changeArticleName(articleId, name);
    }

    @Override
    public void changeArticleCost(int articleId, double cost) {
        this.dataBaseAccess.changeArticleCost(articleId, cost);
    }

    @Override
    public LinkedList<Article> getArticles() {
        return new LinkedList<>(Arrays.asList(this.dataBaseAccess.getArticles()));
    }
}
