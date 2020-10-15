package org.openjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Application
 */
public class App extends Application {

    @Override
    public void start(final Stage stage) {
        final MainView mainView = new MainView();
        final Scene scene = new Scene(mainView, 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch();
    }

}
