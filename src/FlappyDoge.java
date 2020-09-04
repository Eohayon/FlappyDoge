/* Program Name: Flappy Doge (FST)
   File Name: FlappyDoge.java
   Author: Ethan Ohayon
   Date Began: March 15, 2017
   Date Due: June 2, 2017
   Purpose: Creates the main window and plays the game intro
*/

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.animation.FadeTransition;

public class FlappyDoge extends Application {

    static int width = 1000;
    static int height = 600;
    static Group root = new Group();
    static Scene scene;

//Initializes the stage
    @Override
    public void start(Stage stage) {

        scene = new Scene(root, width, height, Color.BLACK);

        root.setFocusTraversable(true);
        root.requestFocus();

        stage.setTitle("Flappy Doge");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

//-------------------------------------------------------------------------------------------------------------------------
//Creates the game intro

        ImageView intro = new ImageView(new Image("/Resources/Intro.png"));

        root.getChildren().add(intro);

        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), intro);
        fade.setOnFinished(e -> {

            Menu.mainMenu();

        });

        fade.setFromValue(1.0);
        fade.setToValue(0);
        fade.play();

    }

// Main
    public static void main(String[] args) {
        launch(args);
    }
}