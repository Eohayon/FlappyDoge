/* Program Name: Flappy Doge (FST)
   File Name: Scrolling.java
   Author: Ethan Ohayon
   Date Began: March 15, 2017
   Date Due: June 2, 2017
   Purpose: Creates an object to scroll the background and to scroll the pipes. Checks collisions in PipeScrolling
*/

import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

//-------------------------------------------------------------------------------------------------------------------------
//Timeline which loops the background

class BackgroundScrolling extends Group {

		 final Node[] content;
		 public static Timeline bgAnimate = new Timeline();

		 public BackgroundScrolling(final Node[] content, final int width) {

			this.content = content;
			this.getChildren().addAll(content);
			bgAnimate.setCycleCount(Timeline.INDEFINITE);

			EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent event) {
				for (Node node: content) {

				 node.setTranslateX(node.getTranslateX() - 1.0);

				 if (node.getLayoutX() + node.getTranslateX() + node.getBoundsInLocal().getWidth() <= 0)
					node.setTranslateX(width - node.getLayoutX());

				}
			 }
			};
			KeyFrame keyFrame = new KeyFrame(new Duration(6), onFinished);
			bgAnimate.getKeyFrames().add(keyFrame);
			bgAnimate.play();
		 }
		}

//-------------------------------------------------------------------------------------------------------------------------
//Timeline which loops the pipes

class PipeScrolling extends Group {

		 final Node[] content;
		 public static Timeline pipeAnimate = new Timeline();
		 public static boolean playSound = true;

		 public PipeScrolling(final Node[] content, final int width) {

			this.content = content;
			this.getChildren().addAll(content);
			pipeAnimate.setCycleCount(Timeline.INDEFINITE);

			EventHandler < ActionEvent > onFinished = new EventHandler < ActionEvent > () {
			 public void handle(ActionEvent event) {
				for (Node node: content) {

				 int location = 4650;

				 node.setTranslateX(node.getTranslateX() - 1.0);

				 if (node.getTranslateX() == 360) {

					if (!Menu.muted)
					 InitializeGame.scoreSound.play();
					Score.scoreNum++;

				 } else if (Dog.doge.getBoundsInParent().intersects(node.getBoundsInParent())) {

					if (!Menu.muted && playSound)
					 InitializeGame.loseSound.play();
					EndGame.gameOver = true;
					playSound = false;

				 } else if (Dog.doge.getBoundsInParent().intersects(InitializeGame.rectangle.getBoundsInParent())) {

					if (!Menu.muted && playSound)
					 InitializeGame.floorCollideSound.play();
					EndGame.gameOver = true;
					playSound = false;

				 } else if (Dog.doge.getBoundsInParent().intersects(InitializeGame.topLimit.getBoundsInParent())) {

					if (!Menu.muted && playSound)
					 InitializeGame.loseSound.play();
					EndGame.gameOver = true;
					playSound = false;

				 }
				 if (node.getLayoutX() + node.getTranslateX() + node.getBoundsInLocal().getWidth() <= 0) {
					location += 250;
					node.setTranslateX(location);
				 }

				}
			 }
			};
			KeyFrame keyFrame = new KeyFrame(new Duration(6), onFinished);
			pipeAnimate.getKeyFrames().add(keyFrame);
			pipeAnimate.play();
		 }
		}