package org.openjfx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import java.util.Date;
import javafx.util.Duration;
import java.text.SimpleDateFormat;


public class MainView extends BorderPane {

    final String dateString = "'Today is:' yyyy -MM -dd '\nClock:' HH:mm:ss";
    final SimpleDateFormat dateFormat = new SimpleDateFormat(dateString);
    private final Label label2;

    MainView() {
        this.setStyle("-fx-background-color: linear-gradient(to right, #f56a2a, #2251d2);");

        final String javaVersion = SystemInfo.javaVersion();
        final String javafxVersion = SystemInfo.javafxVersion();
        final Label label = new Label("Hello, u are using JavaFX " + javafxVersion + " application, running on Java " + javaVersion + ".");
        label.setStyle("-fx-text-fill: white;-fx-font-family: Centaur;-fx-font-size: 20px");

        label2 = new Label();
        label2.setStyle("-fx-text-fill: white;-fx-font-family: 'Harlow Solid Italic';-fx-font-size: 40px;-fx-text-alignment: center");
        this.updateDate(null);

        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateDate));
        timeline.setCycleCount(Animation.INDEFINITE);

        this.setTop(label);
        this.setCenter(label2);
        timeline.play();
    }

    private void updateDate(final ActionEvent actionEvent) {
        this.label2.setText(dateFormat.format(new Date()));
    }

}
