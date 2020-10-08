package com.tp1;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainView extends BorderPane {

    final Label label;
    final Label label2;

    MainView() {
        final String javaVersion = SystemInfo.javaVersion();
        final String javafxVersion = SystemInfo.javafxVersion();
        final String date = dateToday();

        label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        label2 = new Label("Today is : " + date);

        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateDate));
        timeline.setCycleCount(Animation.INDEFINITE);

        this.setTop(label);
        this.setCenter(label2);
        timeline.play();
    }

    private String dateToday() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        return formatter.format(new Date());
    }

    private void updateDate(ActionEvent actionEvent) {
        this.label2.setText("Today is : " + dateToday());
    }

}
