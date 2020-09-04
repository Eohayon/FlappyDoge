/* Program Name: Flappy Doge (FST)
   File Name: EndGame.java
   Author: Ethan Ohayon
   Date Began: March 15, 2017
   Date Due: June 2, 2017
   Purpose: Ends all animations and the game loop and shows buttons for the user to view highscores, restart or save their score
*/

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javafx.application.Platform;
import javafx.stage.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.*;
import java.util.Collections;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.beans.binding.Bindings;
import javafx.scene.input.MouseEvent;

public class EndGame {

 static boolean gameOver = false;
 static ImageView bronzeMedal = new ImageView(new Image("/Resources/Bronze Medal.png"));
 static ImageView silverMedal = new ImageView(new Image("/Resources/Silver Medal.png"));
 static ImageView goldMedal = new ImageView(new Image("/Resources/Gold Medal.png"));
 static ImageView platinumMedal = new ImageView(new Image("/Resources/Platinum Medal.png"));
 static ImageView gameOverScreen = new ImageView(new Image("/Resources/GameOver.png"));
 static Button startButton2 = new Button();
 static Button saveButton = new Button();
 static Button dummyStart = new Button();
 static Button dummyHighScore = new Button();
 static ArrayList<Record> entries = new ArrayList<Record> ();
 static String medal = "None";
 static String userName;
 static Image medalImage;
 static String dateString;
 static int saveButtonCounter = 0;
 static double yOffset = 0;
 static double xOffset = 0;

	public static void gameOver() {

	startButton2.setGraphic(new ImageView(new Image("/Resources/Start Button.png")));
	startButton2.setStyle("-fx-background-radius: 10; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10;");
	saveButton.setGraphic(new ImageView(new Image("/Resources/Save Button.png")));
	saveButton.setStyle("-fx-background-radius: 10; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10;");
	
	dummyStart.setGraphic(new ImageView(new Image("/Resources/Start Button.png")));
	dummyStart.setStyle("-fx-background-radius: 10; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10;");
	dummyHighScore.setGraphic(new ImageView(new Image("/Resources/HighScore Button.png")));
	dummyHighScore.setStyle("-fx-background-radius: 10; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10;");

	if (gameOver == true) {

//-------------------------------------------------------------------------------------------------------------------------
//Stops all scrolling and makes the bird fall to the ground then adds the game over screen 

	 BackgroundScrolling.bgAnimate.stop();
	 PipeScrolling.pipeAnimate.stop();
	 InitializeGame.dogeAnimate.stop();
	 FlappyDoge.root.setOnKeyPressed(null);
	 PipeScrolling.playSound = true;
	 FlappyDoge.root.getChildren().remove(InitializeGame.pauseButton);
	
		FlappyDoge.root.setOnKeyPressed(new EventHandler<KeyEvent>() {
		 @Override
		 public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.M) {
							
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

	 Dog.doge.relocate(360, Dog.charHeight += 3);

	 if (Dog.doge.getRotate() < 90)
		Dog.doge.setRotate(Dog.doge.getRotate() + 1);

	 if (Dog.doge.getBoundsInParent().intersects(InitializeGame.ground[1].getBoundsInParent()) || (Dog.doge.getBoundsInParent().intersects(InitializeGame.ground[0].getBoundsInParent()))) {

		Rectangle textRectangle = new Rectangle(75, 40, Color.TRANSPARENT);
		StackPane stack = new StackPane(textRectangle, Score.scoreText);

		stack.relocate(585, 238);
		stack.setAlignment(Pos.CENTER_RIGHT);

		InitializeGame.gameLoop.stop();
		FlappyDoge.root.getChildren().remove(Score.scoreText);
		FlappyDoge.root.getChildren().addAll(gameOverScreen, stack);

		Score.scoreText.setStyle("-fx-font-size: 40; -fx-stroke: black; -fx-stroke-width: 1.5px;");
		
//-------------------------------------------------------------------------------------------------------------------------
//Changes the medal dislayed depending on the score   
		 
		Score.scoreNum = Score.scoreNum / 2;
		
		 if (Score.scoreNum >= 40) {
		
		 FlappyDoge.root.getChildren().add(platinumMedal);
		 platinumMedal.relocate(355, 256);
		 medal = "Platinum";
		 medalImage = new Image("/Resources/Platinum Medal.png");
			
		} else if (Score.scoreNum >= 30) {
			
		 FlappyDoge.root.getChildren().add(goldMedal);
		 goldMedal.relocate(355, 256);
		 medal = "Gold";
		 medalImage = new Image("/Resources/Gold Medal.png");
		
		} else if (Score.scoreNum >= 20) {
			
		 FlappyDoge.root.getChildren().add(silverMedal);
		 silverMedal.relocate(355, 256);
		 medal = "Silver";
		 medalImage = new Image("/Resources/Silver Medal.png");
				
		} else if (Score.scoreNum >= 10) {
			
		 FlappyDoge.root.getChildren().add(bronzeMedal);
		 bronzeMedal.relocate(355, 256);
		 medal = "Bronze";
		 medalImage = new Image("/Resources/Bronze Medal.png");
		
		} else {
			
			medal = "None";
		}

		FlappyDoge.root.getChildren().addAll(startButton2, Menu.highscoreButton, saveButton);
		Menu.highscoreButton.relocate(515, 430);
		startButton2.relocate(345, 430);
		saveButton.relocate(450, 382);

//-------------------------------------------------------------------------------------------------------------------------
//Reads the 'scores.fds' file to 'entries' in order to able to initialize the high score then adds the high score to the menu
  
		try {

		 File file = new File("scores.fds");

		 if (file.length() != 0) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			entries = (ArrayList<Record>)ois.readObject();
			
			try {
				
			Collections.sort(entries);
			Score.best = entries.get(0).getScore();
				
			} catch (Exception e) {
				
			}
		 }

		} catch (Exception ex) {
		 ex.printStackTrace();
		}
		
	if (Score.scoreNum > Score.best)			
	Score.bestText = new Text(Integer.toString(Score.scoreNum));
	
	else 		
	Score.bestText = new Text(Integer.toString(Score.best));
	
	Score.bestText.setStyle("-fx-font-size: 40; -fx-stroke: black; -fx-stroke-width: 1.5px;");
	Score.bestText.setFill(Color.WHITE);
	Score.bestText.setFont(Font.loadFont("file:Resources/FlappyBird", 40));
	
	Rectangle textRectangle2 = new Rectangle(75, 40, Color.TRANSPARENT);
	StackPane stack2 = new StackPane(textRectangle2, Score.bestText);
	
	stack2.relocate(585, 310);
	stack2.setAlignment(Pos.CENTER_RIGHT);
	FlappyDoge.root.getChildren().add(stack2);
	
	}
}

//-------------------------------------------------------------------------------------------------------------------------
//Initializes a new game when the start button is pressed 

	startButton2.setOnAction(new EventHandler<ActionEvent>() {
	 @Override public void handle(ActionEvent e) {

		FlappyDoge.root.getChildren().clear();
		Dog.doge.setRotate(0);
		gameOver = false;
		PipeScrolling.pipeAnimate.getKeyFrames().clear();
		BackgroundScrolling.bgAnimate.getKeyFrames().clear();
		Dog.charHeight = 285;
		Score.scoreNum = 0;
		Dog.falling = true;
		try {
		InitializeGame.initialize();
		} catch(Exception ex) {}
		InitializeGame.instructions();
		FlappyDoge.root.requestFocus();
		saveButtonCounter = 0;
		Dog.jumpCount = 1;
		FlappyDoge.root.getChildren().addAll(Menu.muteButton, Menu.homeButton);
	 }
	});

//-------------------------------------------------------------------------------------------------------------------------
//Event handler for save button which gets the players name and adds their data to the array of entries

	saveButton.setOnAction(new EventHandler<ActionEvent>() {
	 @Override public void handle(ActionEvent e) {
		
		Pane root = new Pane();
		ImageView namePrompt = new ImageView(new Image("/Resources/Prompt.png"));
		TextField nameInput = new TextField();
		Button submitButton = new Button("Submit");
		Button closeButton = new Button();
		Date date = new Date();
		
		closeButton.graphicProperty().bind(Bindings.when(closeButton.hoverProperty())
		.then(new ImageView(new Image("/Resources/CloseButton Hover.png")))
		.otherwise(new ImageView(new Image("/Resources/CloseButton.png"))));
		
		closeButton.relocate(14, 14);
		closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 5em;	-fx-min-width: 3px; -fx-min-height: 3px; -fx-max-width: 3px; -fx-max-height: 3px;");

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			FlappyDoge.root.getChildren().remove(root);
			FlappyDoge.root.getChildren().removeAll(dummyHighScore, dummyStart);
			saveButtonCounter = 0;
			}
		});	
		
		dummyHighScore.relocate(515, 430);
		dummyStart.relocate(345, 430);

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
		dateString = df.format(date);

		nameInput.setPromptText("Enter Your Name");
		nameInput.relocate(20, 23);
		submitButton.relocate(221, 23);
		nameInput.setPrefSize(175, 10);
				
		root.getChildren().addAll(namePrompt, nameInput, submitButton, closeButton);
		
		if (saveButtonCounter == 0) {
		FlappyDoge.root.getChildren().add(root);
		FlappyDoge.root.getChildren().addAll(dummyHighScore, dummyStart);
		saveButtonCounter++;
		}

		root.relocate(351, 202);
	
		nameInput.setFocusTraversable(true);
		submitButton.setFocusTraversable(false);
		
//-------------------------------------------------------------------------------------------------------------------------
//Button handlers for submit button and enter pressed which adds the score to 'entries' writes it to 'scores.fds'
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
		 @Override
		 public void handle(ActionEvent event) {

			if ((nameInput.getText() != null && !nameInput.getText().isEmpty())) {
				
			userName = nameInput.getText();
			nameInput.clear();
			
			FlappyDoge.root.getChildren().removeAll(root, dummyHighScore, dummyStart);
			saveButton.setOnAction(null);
			
			Score.writeScore();

		 }
		}
		});

		nameInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
		 @Override
		 public void handle(KeyEvent event) {

			if (event.getCode().equals(KeyCode.ENTER)) {

			 if ((nameInput.getText() != null && !nameInput.getText().isEmpty())) {
				userName = nameInput.getText();
				nameInput.clear();
				
				FlappyDoge.root.getChildren().removeAll(root, dummyHighScore, dummyStart);
				
				saveButton.setOnAction(null);

				Score.writeScore();
			}
		 }
		}
		});
		
//-------------------------------------------------------------------------------------------------------------------------
//Allows the user to drag the name input pane and sets limits at the borders
			
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				xOffset = root.getTranslateX() - event.getScreenX();
				yOffset = root.getTranslateY() - event.getScreenY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
								
				root.setTranslateX(event.getScreenX() + xOffset);
				root.setTranslateY(event.getScreenY() + yOffset);
				
				if (root.getTranslateY() <= -202)
				root.setTranslateY(-202);
				
				if (root.getTranslateY() >= 326)
				root.setTranslateY(326);
				
				if (root.getTranslateX() <= -351)
				root.setTranslateX(-351);
				
				if (root.getTranslateX() >= 349)
				root.setTranslateX(349);

			}
		});
		
	 }
	});

//------------------------------------------------------------------------------------------------------------------------- 
 }
}