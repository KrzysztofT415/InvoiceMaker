package com.tp1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX Application
 */
public class JavaFXApplication extends Application {

    @Override
    public void start(final Stage stage) {
        final MainView mainView = new MainView();
        final Scene scene = new Scene(mainView, 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    private void updateDate() {

    }

    public static void main(final String[] args) {
        launch();
    }

}