/* Program Name: Flappy Doge (FST)
   File Name: InitializeGame.java
   Author: Ethan Ohayon
   Date Began: March 15, 2017
   Date Due: June 2, 2017
   Purpose: Contains methods initialize all the elements of the stage, play the instructions and run the game
*/

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import java.net.*;
import javafx.scene.control.Button;

public class InitializeGame {

 static Timeline dogeAnimate;
 static Timeline gameLoop;
 static Timeline instructionTimeline;
 static Timeline instructionDogeAnimate;
 static PipeScrolling bottomPipeScroll;
 static PipeScrolling topPipeScroll;
 static BackgroundScrolling groundScroll;
 static ImageView city;
 static TopPipe[] topPipes;
 static BottomPipe[] bottomPipes;
 static Ground[] ground;
 static Image[] instructionFrames = new Image[2];
 static ImageView instructionView = new ImageView();
 static int frameCounter = 0;
 static boolean gameStart = false;
 static boolean gamePaused = false;
 static Rectangle rectangle;
 static Rectangle topLimit;
 static AudioClip loseSound;
 static AudioClip scoreSound;
 static AudioClip floorCollideSound;
 static AudioClip jumpSound;
 static Button pauseButton = new Button();

	 public static void initialize() throws URISyntaxException {

		scoreSound = new AudioClip(FlappyDoge.class.getResource("/Resources/Score Sound.m4a").toURI().toString());
		loseSound = new AudioClip(FlappyDoge.class.getResource("/Resources/Lose Sound.m4a").toURI().toString());
		jumpSound = new AudioClip(FlappyDoge.class.getResource("/Resources/Jump Sound.m4a").toURI().toString());
		floorCollideSound = new AudioClip(FlappyDoge.class.getResource("/Resources/Floor Collision Sound.m4a").toURI().toString());

//-------------------------------------------------------------------------------------------------------------------------
//Creates the the top limit for the Doge

		topLimit = new Rectangle(600, 0);
		topLimit.relocate(0, 0);
		topLimit.setFill(Color.BLACK);

//-------------------------------------------------------------------------------------------------------------------------
//Creates the ground as an image

		rectangle = new Rectangle(1000, 75);
		rectangle.relocate(0, 513);

		city = new ImageView(new Image("/Resources/City.png"));

		ground = new Ground[] {new Ground(0, 513), new Ground(1000, 513)};

		groundScroll = new BackgroundScrolling(ground, FlappyDoge.width);

//-------------------------------------------------------------------------------------------------------------------------
//Creates the first pipe centered and the rest at random heights

		int[] topHeights = new int[20];
		int[] bottomHeights = new int[20];
		int location = 1250;

		topPipes = new TopPipe[20];
		bottomPipes = new BottomPipe[20];

		for (int i = 0; i < 20; i++)
		 bottomHeights[i] = (int)(Math.random() * (151) + 250);

		topPipes[0] = new TopPipe(1000, -225);
		bottomPipes[0] = new BottomPipe(1000, 325);

		for (int i = 1; i < 20; i++) {

		 topPipes[i] = new TopPipe(location, bottomHeights[i] - 540);
		 bottomPipes[i] = new BottomPipe(location, bottomHeights[i]);

		 location += 250;
		}
		topPipeScroll = new PipeScrolling(topPipes, FlappyDoge.width);
		bottomPipeScroll = new PipeScrolling(bottomPipes, FlappyDoge.width);

		FlappyDoge.root.getChildren().addAll(city, topPipeScroll, bottomPipeScroll, rectangle, groundScroll, topLimit);
	 }

//-------------------------------------------------------------------------------------------------------------------------
//Creates the animation for the instructions using a timeline

	 public static void instructions() {
		
		Menu.onInstructions = true;

		instructionFrames[0] = new Image("/Resources/InstructionFrame0.png");
		instructionFrames[1] = new Image("/Resources/InstructionFrame1.png");

		PipeScrolling.pipeAnimate.stop();
		FlappyDoge.root.getChildren().removeAll(Menu.startView, Menu.startButton, Menu.highscoreButton);

		instructionDogeAnimate = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler < ActionEvent > () {

		 @Override
		 public void handle(ActionEvent actionEvent) {

			try {
			 FlappyDoge.root.getChildren().add(Dog.dog());
			 Dog.dog().relocate(360, 285);
			} catch (Exception e) {}
		 }

		}), new KeyFrame(Duration.seconds(0.1)));

		instructionDogeAnimate.setCycleCount(Animation.INDEFINITE);
		instructionDogeAnimate.play();

		instructionTimeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {

		 @Override
		 public void handle(ActionEvent actionEvent) {

			try {

			 instructionView.setImage(instructionFrames[frameCounter++]);
			 if (frameCounter == 2) {
				frameCounter = 0;
			 }

			 FlappyDoge.root.getChildren().add(instructionView);
			 instructionView.relocate(320, 172);

			} catch (Exception e) {}
		 }

		}), new KeyFrame(Duration.seconds(0.5)));

		instructionTimeline.setCycleCount(Animation.INDEFINITE);
		instructionTimeline.play();

//-------------------------------------------------------------------------------------------------------------------------
//Starts the game if space is pressed 

		FlappyDoge.root.setOnKeyPressed(new EventHandler<KeyEvent>() {
		 @Override
		 public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.UP) {
			 startGame();
			 FlappyDoge.root.getChildren().remove(instructionView);
			 instructionTimeline.stop();
			 instructionDogeAnimate.stop();
			 Menu.gameLoop = true;
			
			} if (event.getCode() == KeyCode.M) {
							
				FlappyDoge.root.requestFocus();
				
				if (Menu.muted == true) {
				Menu.muteButton.setGraphic(new ImageView(new Image("/Resources/Mute Button.png")));
				Menu.muted = false;

				} else {
				Menu.muteButton.setGraphic(new ImageView(new Image("/Resources/Mute Button Clicked.png")));
				Menu.muted = true;
				}
			}	
		 }
	});
 }

	 public static void startGame() {
		
		pauseButton.setGraphic(new ImageView(new Image("/Resources/Pause Button.png")));
		pauseButton.setStyle("-fx-background-radius: 10; -fx-background-color: transparent;");
		
		FlappyDoge.root.requestFocus();
		FlappyDoge.root.getChildren().add(pauseButton);
		pauseButton.relocate(109, 2);
		
//-------------------------------------------------------------------------------------------------------------------------
//Adds the bird to the stage and repeats the animations flying using a Timeline

		PipeScrolling.pipeAnimate.play();

		dogeAnimate = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler < ActionEvent > () {

		 @Override
		 public void handle(ActionEvent actionEvent) {

			try {
			 FlappyDoge.root.getChildren().add(Dog.dog());
			} catch (Exception e) {}
		 }

		}), new KeyFrame(Duration.seconds(0.06)));

		dogeAnimate.setCycleCount(Animation.INDEFINITE);
		dogeAnimate.play();


//-------------------------------------------------------------------------------------------------------------------------
//Timeline which recalls the jumpCheck(), jumping() and  updateScore() Methods

		gameLoop = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler < ActionEvent > () {

		 @Override
		 public void handle(ActionEvent actionEvent) {

			try {

			 if (EndGame.gameOver == false) {

				Dog.jumpCheck();
				Dog.jump();
				Score.updateScore();

			 } else if (EndGame.gameOver == true)
				EndGame.gameOver();

			} catch (Exception e) {

			}
		 }

		}), new KeyFrame(Duration.seconds(0.004)));

		gameLoop.setCycleCount(Animation.INDEFINITE);
		gameLoop.play();

	 }
	}