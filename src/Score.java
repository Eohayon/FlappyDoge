/* Program Name: Flappy Doge (FST)
   File Name: Score.java
   Author: Ethan Ohayon
   Date Began: March 15, 2017
   Date Due: June 2, 2017
   Purpose: Contains methods to update the score during gameplay, write the scores file and create the highscore menu
*/

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Score {
	
	static int scoreNum = 0;
	static String scoreString;
	static Text scoreText = new Text();
	static int best;
	static Text bestText;
	static Pane highScorePane = new Pane();
	static Pane namePane = new Pane();
	static Pane scorePane = new Pane();
	static Pane flapsPane = new Pane();
	static Text[] nameAndDate;
	static Text[] flaps;
	static Text[] score;
	static ImageView[] scoreDisplay;
	static ImageView[] medal;
	
//-------------------------------------------------------------------------------------------------------------------------
//Method which creates the score and adds it to the stage

	public static void updateScore() {

		scoreString = String.valueOf(scoreNum/2);

		scoreText.setText(scoreString);
		scoreText.setFill(Color.WHITE);
		scoreText.relocate(500, 10);
		scoreText.setStyle("-fx-stroke: black; -fx-stroke-width: 2px;");
		scoreText.setFont(Font.loadFont("file:Resources/FlappyFont.ttf", 60));
		
		FlappyDoge.root.getChildren().add(scoreText);
	}
	
	public static void writeScore() {

	try {

	 File file = new File("scores.fds");

	 if (file.length() != 0) {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		EndGame.entries = (ArrayList<Record>)ois.readObject();
	 }

	 EndGame.entries.add(new Record(EndGame.userName, scoreNum, Dog.jumpCount, EndGame.medal, EndGame.dateString));
	 Collections.sort(EndGame.entries);
	 Score.best = EndGame.entries.get(0).getScore();

	 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("scores.fds")));
	 oos.writeObject(EndGame.entries);
	 oos.flush();
	 oos.close();

	} catch (Exception ex) {
	 ex.printStackTrace();
	}
	
	}
	
//-------------------------------------------------------------------------------------------------------------------------
//Method which creates the score menu when the scores updates
	
	public static void createScoreMenu(ArrayList<Record> entries) {
		
		scoreDisplay = new ImageView[entries.size()];
		medal = new ImageView[entries.size()];
		nameAndDate = new Text[entries.size()];
		flaps = new Text[entries.size()];
		score = new Text[entries.size()];
		
		highScorePane.getChildren().clear();
		flapsPane.getChildren().clear();
		scorePane.getChildren().clear();
		namePane.getChildren().clear();
		
		for (int i = 0; i < entries.size(); i++) {

			scoreDisplay[i] = new ImageView(new Image("/Resources/Score Display.png"));
			highScorePane.getChildren().add(scoreDisplay[i]);

			double height = 80 + (i * 210);
			double medalHeight = 165 + (i * 210);
			double nameDateHeight = 100 + (i * 210);
			double scoreHeight = 160 + (i * 210);

			scoreDisplay[i].relocate(42, height);

			nameAndDate[i] = new Text(String.valueOf("(" + (i + 1) + ") " + entries.get(i).getName().toUpperCase() + ", " + entries.get(i).getDate().toUpperCase()));
			score[i] = new Text(String.valueOf(entries.get(i).getScore()));
			flaps[i] = new Text(String.valueOf(entries.get(i).getFlaps()));

			nameAndDate[i].setFont(Font.loadFont("file:Resources/FlappyFont.ttf", 20));
			nameAndDate[i].setFill(Color.web("#FE7A5C"));

			if (nameAndDate[i].getBoundsInParent().getWidth() >= 373) {

				double textOffset = 513 - nameAndDate[i].getBoundsInParent().getWidth();
				nameAndDate[i].setFont(Font.loadFont("file:Resources/FlappyFont.ttf", 17));

			}

			score[i].setFont(Font.loadFont("file:Resources/FlappyFont.ttf", 40));
			score[i].setFill(Color.WHITE);
			score[i].setStyle("-fx-stroke: black; -fx-stroke-width: 2px;");

			flaps[i].setFont(Font.loadFont("file:Resources/FlappyFont.ttf", 40));
			flaps[i].setFill(Color.WHITE);
			flaps[i].setStyle("-fx-stroke: black; -fx-stroke-width: 2px;");


			if (entries.get(i).getMedal().equals("Bronze")) {

				medal[i] = new ImageView(new Image("/Resources/Bronze Medal.png"));
				highScorePane.getChildren().add(medal[i]);
				medal[i].relocate(65, medalHeight);

			}
			if (entries.get(i).getMedal().equals("Silver")) {

				medal[i] = new ImageView(new Image("/Resources/Silver Medal.png"));
				highScorePane.getChildren().add(medal[i]);
				medal[i].relocate(65, medalHeight);

			}
			if (entries.get(i).getMedal().equals("Gold")) {

				medal[i] = new ImageView(new Image("/Resources/Gold Medal.png"));
				highScorePane.getChildren().add(medal[i]);
				medal[i].relocate(65, medalHeight);

			}
			if (entries.get(i).getMedal().equals("Platinum")) {

				medal[i] = new ImageView(new Image("/Resources/Platinum Medal.png"));
				highScorePane.getChildren().add(medal[i]);
				medal[i].relocate(65, medalHeight);

			}

			namePane.getChildren().add(nameAndDate[i]);
			nameAndDate[i].relocate((513 - nameAndDate[i].getBoundsInParent().getWidth()) / 2, nameDateHeight);

			scorePane.getChildren().add(score[i]);
			score[i].relocate((513 - score[i].getBoundsInParent().getWidth()) / 2, scoreHeight);

			flapsPane.getChildren().add(flaps[i]);
			flaps[i].relocate((390 - flaps[i].getBoundsInParent().getWidth() / 2), scoreHeight);
		}
	}	
}