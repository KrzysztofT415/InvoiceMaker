package app.components.articlemenu;

import java.util.LinkedList;

public interface ArticleManagement {
    void create(String name, double cost);
    void remove(int articleId);
    void changeArticleName(int articleId, String name);
    void changeArticleCost(int articleId, double cost);
    LinkedList<Article> getArticles();
}
