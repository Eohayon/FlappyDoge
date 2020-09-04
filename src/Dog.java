/* Program Name: Flappy Doge (FST)
   File Name: Dog.java
   Author: Ethan Ohayon
   Date Began: March 15, 2017
   Date Due: June 2, 2017
   Purpose: Contains methods to animate the character and check and control jumping and falling
*/

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.paint.ImagePattern;
import javafx.scene.media.AudioClip;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;


public class Dog {

	 static Image[] frames = new Image[3];
	 static Doge doge = new Doge(48, 34);
	 static int frameCounter = 0;
	 static int charHeight = 285;
	 static boolean falling = true;
	 static int jumpHeight;
	 static int jumpCount = 1;

//-------------------------------------------------------------------------------------------------------------------------
//Method which animates the doge 
	 public static Doge dog() {
				
		frames[0] = new Image("/Resources/DogFrame0.png");
		frames[1] = new Image("/Resources/DogFrame1.png");
		frames[2] = new Image("/Resources/DogFrame2.png");
		
		doge.setStroke(Color.TRANSPARENT);
		
		doge.setFill(new ImagePattern(frames[frameCounter++]));
		if (frameCounter == 3) {
		 frameCounter = 0;
		}

		return doge;
	 }
//-------------------------------------------------------------------------------------------------------------------------
//Method to check if the doge should jump and controls the pause button
	
	 public static void jumpCheck() throws URISyntaxException {		
		
		FlappyDoge.root.setOnKeyPressed(new EventHandler<KeyEvent> () {
		 @Override
		 public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.UP) {
            jumpHeight = charHeight - 73;
			falling = false;
			jumpCount++;
			
			if (!Menu.muted && !InitializeGame.gamePaused)
			InitializeGame.jumpSound.play();
			
			} if (event.getCode() == KeyCode.P) {
			
			if (!InitializeGame.gamePaused) {
				
			InitializeGame.pauseButton.setGraphic(new ImageView(new Image("/Resources/Play Button.png")));
			InitializeGame.gamePaused = true;
			FlappyDoge.root.requestFocus();

			PipeScrolling.pipeAnimate.stop();
			BackgroundScrolling.bgAnimate.stop();
			InitializeGame.gameLoop.stop();
			
			} else if (InitializeGame.gamePaused) {
				
			InitializeGame.pauseButton.setGraphic(new ImageView(new Image("/Resources/Pause Button.png")));
			InitializeGame.gamePaused = false;
			FlappyDoge.root.requestFocus();

			PipeScrolling.pipeAnimate.play();
			BackgroundScrolling.bgAnimate.play();
			InitializeGame.gameLoop.play();
			}
			
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
		
		InitializeGame.pauseButton.setOnAction(new EventHandler<ActionEvent> () {
		 @Override
		 public void handle(ActionEvent event) {
			
			if (!InitializeGame.gamePaused) {
				
			InitializeGame.pauseButton.setGraphic(new ImageView(new Image("/Resources/Play Button.png")));
			InitializeGame.gamePaused = true;
			FlappyDoge.root.requestFocus();
			
			PipeScrolling.pipeAnimate.stop();
			BackgroundScrolling.bgAnimate.stop();
			InitializeGame.gameLoop.stop();
			
			} else if (InitializeGame.gamePaused) {
				
			InitializeGame.pauseButton.setGraphic(new ImageView(new Image("/Resources/Pause Button.png")));
			InitializeGame.gamePaused = false;
			FlappyDoge.root.requestFocus();

			PipeScrolling.pipeAnimate.play();
			BackgroundScrolling.bgAnimate.play();
			InitializeGame.gameLoop.play();
			}
			
		}
	});
	}

//-------------------------------------------------------------------------------------------------------------------------
//Method to make the doge jump if he should be jumping and makes him fall otherwise

	 public static void jump() {
		
	 if (falling) {
		
		doge.relocate(360, charHeight += 1.7);
		if (doge.getRotate() < 30)
		doge.setRotate(doge.getRotate() + 0.3);
		
	 } else if (!falling) {
		
		 doge.relocate(360, charHeight -= 1);
		
		if (doge.getRotate() > -30)
		doge.setRotate(doge.getRotate() - 0.5);
		
		if (charHeight == jumpHeight) {
			falling = true;
			}		
		}
	}	
}