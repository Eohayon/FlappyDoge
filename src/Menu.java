/* Program Name: Flappy Doge (FST)
   File Name: Menu.java
   Author: Ethan Ohayon
   Date Began: March 15, 2017
   Date Due: June 2, 2017
   Purpose: Creates the main menu with a play and highscore button. Creates the highscore menu when the button is clicked
*/

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.Collections;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import java.time.format.DateTimeFormatter;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import java.io.FileOutputStream;
import javafx.scene.input.MouseEvent;
import java.util.Arrays;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.collections.FXCollections;
import java.time.YearMonth;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Menu {

	static int frameCounter = 0;
	static Image[] startFrames = new Image[6];
	static ImageView startView = new ImageView();
	static Timeline menuAnimation;
	static Button startButton = new Button();
	static Button highscoreButton = new Button();
	static Button bronzeButton = new Button();
	static Button silverButton = new Button();
	static Button goldButton = new Button();
	static Button platinumButton = new Button();
	static Button muteButton = new Button();
	static Button homeButton = new Button();
	static boolean muted = false;
	static boolean openScore = true;
	static boolean openDelete = true;
	static boolean openEditNum = true;
	static boolean openSearch = true;
	static boolean gameLoop = false;
	static boolean onInstructions;
	static boolean onMenu = true;
	static boolean onHighscoreMenu = false;
	static int deleteNum = 0;
	static String[] deleteString;
	static double yOffset;
	static double xOffset;
	static int editNum = 0;
	static String newDate = "";
	static String newName = "";
	static String stringDate = "";
	static int newScore = 0;
	static int newFlaps = 0;
	static String medal = "None";
	static boolean editScore = false;
	static Pane editPane = new Pane();

	public static void mainMenu() {

//-------------------------------------------------------------------------------------------------------------------------
//Creates the Start and Highscore buttons

		startButton.setGraphic(new ImageView(new Image("/Resources/Start Button.png")));
		startButton.setStyle("-fx-background-radius: 10; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10;");

		highscoreButton.setGraphic(new ImageView(new Image("/Resources/Highscore Button.png")));
		highscoreButton.setStyle("-fx-background-radius: 10; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 10;");
		
		if (muted == false)
		muteButton.setGraphic(new ImageView(new Image("/Resources/Mute Button.png")));
		muteButton.setStyle("-fx-background-radius: 10; -fx-background-color: transparent;");
		
		homeButton.setStyle("-fx-background-radius: 10; -fx-background-color: transparent;");
		homeButton.graphicProperty().bind(Bindings.when(homeButton.hoverProperty())
			.then(new ImageView(new Image("/Resources/Home Button Hover.png")))
			.otherwise(new ImageView(new Image("/Resources/Home Button.png"))));
		
		muteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				FlappyDoge.root.requestFocus();
				
				if (muted == true) {
				muteButton.setGraphic(new ImageView(new Image("/Resources/Mute Button.png")));
				muted = false;

				} else {
				muteButton.setGraphic(new ImageView(new Image("/Resources/Mute Button Clicked.png")));
				muted = true;
				}
			}
		});
		
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
						
		homeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
							
				if (!onMenu && !onHighscoreMenu) {
					
				if (!onInstructions)
				BackgroundScrolling.bgAnimate.getKeyFrames().clear();
				
				if (gameLoop) {
				InitializeGame.gameLoop.getKeyFrames().clear();
				InitializeGame.dogeAnimate.getKeyFrames().clear();
				}				
				InitializeGame.instructionTimeline.getKeyFrames().clear();
				InitializeGame.instructionDogeAnimate.getKeyFrames().clear();
				PipeScrolling.pipeAnimate.getKeyFrames().clear();
				FlappyDoge.root.setOnKeyPressed(null);
				Dog.doge.setRotate(0);
				Dog.charHeight = 285;
				Score.scoreNum = 0;
				Dog.falling = true;
				EndGame.gameOver = false;
				EndGame.saveButtonCounter = 0;
				Dog.jumpCount = 1;
				FlappyDoge.root.getChildren().clear();
				FlappyDoge.root.requestFocus();
				mainMenu();
				onMenu = true;
			}
		}
	});
		
		highscoreButton.relocate(515, 350);
		startButton.relocate(365, 350);
		muteButton.relocate(-7, -4);
		homeButton.relocate(51, 0);

//-------------------------------------------------------------------------------------------------------------------------
//Initializes the frames of the title animation and plays it using a timeline

		startFrames[0] = new Image("/Resources/StartFrame0.png");
		startFrames[1] = new Image("/Resources/StartFrame1.png");
		startFrames[2] = new Image("/Resources/StartFrame2.png");
		startFrames[3] = new Image("/Resources/StartFrame3.png");
		startFrames[4] = new Image("/Resources/StartFrame4.png");
		startFrames[5] = new Image("/Resources/StartFrame5.png");

		try {
			InitializeGame.initialize();
		} catch (Exception e) {}
		PipeScrolling.pipeAnimate.stop();


		menuAnimation = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent actionEvent) {

				try {

					startView.setImage(startFrames[frameCounter++]);
					if (frameCounter == 6) {
						frameCounter = 0;
					}

					FlappyDoge.root.getChildren().addAll(startView, startButton, highscoreButton, muteButton, homeButton);
					startView.relocate(0, -120);

				} catch (Exception e) {

				}
			}

		}), new KeyFrame(Duration.seconds(0.1)));

		menuAnimation.setCycleCount(Animation.INDEFINITE);
		menuAnimation.play();

//-------------------------------------------------------------------------------------------------------------------------
//Button Listener for the start button which removes all the homescreen elements and starts the game

		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				InitializeGame.instructions();
				FlappyDoge.root.requestFocus();
				onInstructions = true;
				onMenu = false;
				
				menuAnimation.stop();
			}
		});

//-------------------------------------------------------------------------------------------------------------------------
//Button listener for the highscore button which reads the file into 'entries' if it isn't null and sorts it

		Menu.highscoreButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				onHighscoreMenu = true;

				try {
					File file = new File("scores.fds");

					if (file.length() != 0) {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
						EndGame.entries = (ArrayList<Record>) ois.readObject();
					}

					Collections.sort(EndGame.entries);

				} catch (Exception e) {
					e.printStackTrace();

				}
//-------------------------------------------------------------------------------------------------------------------------
//Adds the hover function for the close button and adds, repositions and styles the edit, delete, search and add buttons

		Pane root = new Pane();
		Button menuCloseButton = new Button();
		Button dummyCloseButton = new Button();
		Button deleteButton = new Button();
		Button editButton = new Button();
		Button searchButton = new Button();
		ImageView highScoreBG = new ImageView(new Image("/Resources/Highscore Menu Fill.png"));
		ImageView highScoreFG = new ImageView(new Image("/Resources/Highscore Menu Border.png"));
		ImageView[] scoreDisplay = new ImageView[EndGame.entries.size()];
		
		Score.highScorePane.setTranslateY(0);
		Score.namePane.setTranslateY(0);
		Score.scorePane.setTranslateY(0);
		Score.flapsPane.setTranslateY(0);

		startButton.setDisable(true);
		EndGame.startButton2.setDisable(true);

		FlappyDoge.root.setOnKeyPressed(null);
		root.setOnKeyPressed(null);

		menuCloseButton.graphicProperty().bind(Bindings.when(menuCloseButton.hoverProperty())
			.then(new ImageView(new Image("/Resources/CloseButton Hover.png")))
			.otherwise(new ImageView(new Image("/Resources/CloseButton.png"))));

		menuCloseButton.relocate(14, 14);
		menuCloseButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 5em;	-fx-min-width: 3px; -fx-min-height: 3px; -fx-max-width: 3px; -fx-max-height: 3px;");
		
		dummyCloseButton.graphicProperty().bind(Bindings.when(dummyCloseButton.hoverProperty())
			.then(new ImageView(new Image("/Resources/CloseButton Hover.png")))
			.otherwise(new ImageView(new Image("/Resources/CloseButton.png"))));

		dummyCloseButton.relocate(14, 14);
		dummyCloseButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 5em;	-fx-min-width: 3px; -fx-min-height: 3px; -fx-max-width: 3px; -fx-max-height: 3px;");

		deleteButton.setGraphic(new ImageView(new Image("/Resources/Delete Button.png")));
		deleteButton.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 4; -fx-background-radius: 2; -fx-border-radius: 2;");

		editButton.setGraphic(new ImageView(new Image("/Resources/Edit Button.png")));
		editButton.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 4; -fx-background-radius: 2; -fx-border-radius: 2;");
		
		searchButton.setGraphic(new ImageView(new Image("/Resources/Search Button.png")));
		searchButton.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 4; -fx-background-radius: 2; -fx-border-radius: 2;");

		root.setPrefSize(513, 513);
		root.relocate(244, 0);
		searchButton.relocate(10, 104);
		deleteButton.relocate(10, 131);
		editButton.relocate(10, 158);
		root.getChildren().add(highScoreBG);

		Score.highScorePane.relocate(25, 10);

		if (openScore) {
			FlappyDoge.root.getChildren().remove(InitializeGame.groundScroll);
			FlappyDoge.root.getChildren().add(root);
			FlappyDoge.root.getChildren().add(InitializeGame.groundScroll);
			root.getChildren().addAll(Score.highScorePane, Score.namePane, Score.scorePane, Score.flapsPane, highScoreFG, menuCloseButton, deleteButton, editButton, searchButton);
			openScore = false;
		}
//-------------------------------------------------------------------------------------------------------------------------
//Event handler for close button
		
		menuCloseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openScore = true;
				onHighscoreMenu = false;
				startButton.setDisable(false);
				FlappyDoge.root.getChildren().remove(root);
				EndGame.startButton2.setDisable(false);
				FlappyDoge.root.requestFocus();
				
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
			}
		});
//-------------------------------------------------------------------------------------------------------------------------
//Allows the user to scroll between 0 and the bottom of the score list

		root.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {

				double newY = Score.highScorePane.getTranslateY() + event.getDeltaY();

				if (newY < 0 && newY > -1 * (Score.highScorePane.getBoundsInParent().getHeight() - 482)) {
					Score.highScorePane.setTranslateY(newY);
					Score.namePane.setTranslateY(newY);
					Score.scorePane.setTranslateY(newY);
					Score.flapsPane.setTranslateY(newY);

				}
			}
		});
		
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
			openDelete = false;
			openEditNum = false;
			editScore = false;

			Pane searchPane = new Pane();
			Button closeButton = new Button();
			Button undoButton = new Button();
			Button submitButton = new Button("Submit");
			TextField parameterField = new TextField();
			ImageView prompt = new ImageView(new Image("/Resources/Prompt.png"));
								
			closeButton.graphicProperty().bind(Bindings.when(closeButton.hoverProperty())
				.then(new ImageView(new Image("/Resources/CloseButton Hover.png")))
				.otherwise(new ImageView(new Image("/Resources/CloseButton.png"))));

			closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 5em; -fx-min-width: 3px; -fx-min-height: 3px; -fx-max-width: 3px; -fx-max-height: 3px;");
			
			undoButton.setGraphic(new ImageView(new Image("/Resources/Undo Button.png")));
			undoButton.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 4; -fx-background-radius: 2; -fx-border-radius: 2;");
			
			parameterField.setPromptText("Search");
			parameterField.setPrefSize(175, 10);
			
			parameterField.relocate(20, 23);
			searchPane.relocate(355, 126);
			submitButton.relocate(221, 23);
			closeButton.relocate(14, 14);
			undoButton.relocate(10, 185);
			
			if (openSearch) {
			searchPane.getChildren().addAll(prompt, parameterField, submitButton, closeButton);
			FlappyDoge.root.getChildren().add(searchPane);
			root.getChildren().add(dummyCloseButton);
			openSearch = false;

			}
			submitButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					String parameter = parameterField.getText();
										
					if (search(parameter, EndGame.entries).size() == 0) {
											
					parameterField.clear();	
					parameterField.setPromptText("No Results Found");
					parameterField.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 121, 15; -fx-background-repeat: no-repeat; ");
					root.requestFocus();
					
					parameterField.setOnMousePressed(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							
							parameterField.setStyle(null);
						}
					});
					
				} else if (search(parameter, EndGame.entries).size() == EndGame.entries.size()) {
					
					FlappyDoge.root.getChildren().remove(searchPane);
					root.getChildren().remove(dummyCloseButton);
					openSearch = true;
					openDelete = true;
					openEditNum = true;
					editScore = true;
									
				} else {
					
					Score.createScoreMenu(search(parameter, EndGame.entries));
					root.getChildren().add(undoButton);
					root.getChildren().remove(dummyCloseButton);
					FlappyDoge.root.getChildren().remove(searchPane);
					}
					Score.highScorePane.setTranslateY(0);
					Score.namePane.setTranslateY(0);
					Score.scorePane.setTranslateY(0);
					Score.flapsPane.setTranslateY(0);
				}
			});	
			
			searchPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {	
					
					if (event.getCode().equals(KeyCode.ENTER)) {	
					
					String parameter = parameterField.getText();
										
					if (search(parameter, EndGame.entries).size() == 0) {
											
					parameterField.clear();	
					parameterField.setPromptText("No Results Found");
					parameterField.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 121, 15; -fx-background-repeat: no-repeat; ");
					root.requestFocus();
					
					parameterField.setOnMousePressed(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							
							parameterField.setStyle(null);
						}
					});
					
				} else if (search(parameter, EndGame.entries).size() == EndGame.entries.size()) {
					
					FlappyDoge.root.getChildren().remove(searchPane);
					root.getChildren().remove(dummyCloseButton);
					openSearch = true;
					openDelete = true;
					openEditNum = true;
					editScore = true;
													
				} else {
					
					Score.createScoreMenu(search(parameter, EndGame.entries));
					root.getChildren().add(undoButton);
					root.getChildren().remove(dummyCloseButton);
					FlappyDoge.root.getChildren().remove(searchPane);
					}
					Score.highScorePane.setTranslateY(0);
					Score.namePane.setTranslateY(0);
					Score.scorePane.setTranslateY(0);
					Score.flapsPane.setTranslateY(0);
					}
				}
			});	
			
			undoButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					root.getChildren().remove(undoButton);
					Score.createScoreMenu(EndGame.entries);
					
					openSearch = true;
					openDelete = true;
					openEditNum = true;
					editScore = true;
				}
			});	
													
			closeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					openSearch = true;
					openDelete = true;
					openEditNum = true;
					editScore = true;
					
					FlappyDoge.root.getChildren().remove(searchPane);
					root.getChildren().remove(dummyCloseButton);
				}
			});
			
			searchPane.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					
					xOffset = searchPane.getTranslateX() - event.getScreenX();
					yOffset = searchPane.getTranslateY() - event.getScreenY();
				}
			});
							
			searchPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
									
					searchPane.setTranslateX(event.getScreenX() + xOffset);
					searchPane.setTranslateY(event.getScreenY() + yOffset);
										
					if (searchPane.getTranslateY() <= -126)
					searchPane.setTranslateY(-126);
					
					if (searchPane.getTranslateY() >= 402)
					searchPane.setTranslateY(402);
					
					if (searchPane.getTranslateX() <= -355)
					searchPane.setTranslateX(-355);
					
					if (searchPane.getTranslateX() >= 345)
					searchPane.setTranslateX(345);

				}
			});
			
			}
		});
			
//-------------------------------------------------------------------------------------------------------------------------
//Event handler for delete button which lets the user input the number of the score they would like to delete
		
		Score.createScoreMenu(EndGame.entries);
		
		if (EndGame.entries.size() != 0) {

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				Pane deletePane = new Pane();
				ImageView prompt = new ImageView(new Image("/Resources/Prompt.png"));
				TextField deleteInput = new TextField();
				Button closeButton = new Button();
				Button submitButton = new Button("Submit");
								
				closeButton.graphicProperty().bind(Bindings.when(closeButton.hoverProperty())
					.then(new ImageView(new Image("/Resources/CloseButton Hover.png")))
					.otherwise(new ImageView(new Image("/Resources/CloseButton.png"))));

				closeButton.relocate(14, 14);
				closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 5em;	-fx-min-width: 3px; -fx-min-height: 3px; -fx-max-width: 3px; -fx-max-height: 3px;");

				deleteInput.setPromptText("Enter Record # To Delete");
				deleteInput.relocate(20, 23);
				submitButton.relocate(221, 23);
				deletePane.relocate(355, 126);
				deleteInput.setPrefSize(175, 10);
				
				if (EndGame.entries.size() == 0)
				openDelete = false;
				
				if (openDelete) {
					deletePane.getChildren().addAll(prompt, deleteInput, closeButton, submitButton);
					FlappyDoge.root.getChildren().add(deletePane);
					root.getChildren().add(dummyCloseButton);
					
					openDelete = false;
					openEditNum = false;
					openSearch = false;
					
				}
//-------------------------------------------------------------------------------------------------------------------------
//Event handler for close button				

			closeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					FlappyDoge.root.getChildren().removeAll(deletePane);
					root.getChildren().remove(dummyCloseButton);
					openDelete = true;
					openEditNum = true;
					openSearch = true;
					
				}
			});
			
			
//-------------------------------------------------------------------------------------------------------------------------
//Allows the user to drag the name input pane and sets limits at the borders	
					
			deletePane.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					
					xOffset = deletePane.getTranslateX() - event.getScreenX();
					yOffset = deletePane.getTranslateY() - event.getScreenY();
				}
			});
			
			deletePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
									
					deletePane.setTranslateX(event.getScreenX() + xOffset);
					deletePane.setTranslateY(event.getScreenY() + yOffset);
										
					if (deletePane.getTranslateY() <= -126)
					deletePane.setTranslateY(-126);
					
					if (deletePane.getTranslateY() >= 402)
					deletePane.setTranslateY(402);
					
					if (deletePane.getTranslateX() <= -355)
					deletePane.setTranslateX(-355);
					
					if (deletePane.getTranslateX() >= 345)
					deletePane.setTranslateX(345);

				}
			});
//-------------------------------------------------------------------------------------------------------------------------
//Event handler for submit button and enter which closes the prompt, deletes the score then rewrites the text file and score menu
						
			submitButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {	
					
					openEditNum = true;
					openSearch = true;
					
					try {
						deleteNum = Integer.valueOf(deleteInput.getText()) - 1;
					} catch (Exception e) {
						deleteInput.clear();
						deleteInput.setPromptText("Invalid");
						deleteInput.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 53, 15; -fx-background-repeat: no-repeat; ");
						root.requestFocus();
						
						deleteInput.setOnMousePressed(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								
								deleteInput.setStyle(null);
							}
						});

						
					}				
					
					if ((deleteInput.getText() != null && !deleteInput.getText().isEmpty() && Integer.valueOf(deleteInput.getText()) >= 1 && Integer.valueOf(deleteInput.getText()) <= EndGame.entries.size())) {

						deleteInput.clear();

						openDelete = true;
						EndGame.entries.remove(deleteNum);
						
						try {
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("scores.fds")));
							oos.writeObject(EndGame.entries);
							oos.flush();
							oos.close();
							
							root.getChildren().removeAll(deletePane, Score.highScorePane, Score.namePane, Score.flapsPane, Score.scorePane, highScoreFG, menuCloseButton, editButton, deleteButton, searchButton, dummyCloseButton);
							FlappyDoge.root.getChildren().remove(deletePane);
							
							Score.highScorePane.getChildren().clear();
							Score.namePane.getChildren().clear();
							Score.flapsPane.getChildren().clear();
							Score.scorePane.getChildren().clear();
							
							Score.highScorePane.setTranslateY(0);
							Score.namePane.setTranslateY(0);
							Score.scorePane.setTranslateY(0);
							Score.flapsPane.setTranslateY(0);
																					
						Score.createScoreMenu(EndGame.entries);
						
						root.getChildren().addAll(Score.highScorePane, Score.namePane, Score.scorePane, Score.flapsPane, highScoreFG, menuCloseButton, editButton, deleteButton, searchButton);

						} catch (Exception e) {
							e.printStackTrace();
						}					
			
			closeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					FlappyDoge.root.getChildren().remove(root);
					root.getChildren().remove(dummyCloseButton);
					
						}
					});
				} else {
					deleteInput.clear();
					deleteInput.setPromptText("Invalid");
					deleteInput.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 53, 15; -fx-background-repeat: no-repeat; ");
					root.requestFocus();
					
					deleteInput.setOnMousePressed(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							
							deleteInput.setStyle(null);
						}
					});

				}
			}
		});
		
		deletePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {	
				
				if (event.getCode().equals(KeyCode.ENTER)) {	
					
					openEditNum = true;
					openSearch = true;
				
					try {
						deleteNum = Integer.valueOf(deleteInput.getText()) - 1;
					} catch (Exception e) {
						deleteInput.clear();
						deleteInput.setPromptText("Invalid");
						deleteInput.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 53, 15; -fx-background-repeat: no-repeat; ");
						root.requestFocus();
						
						deleteInput.setOnMousePressed(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								
								deleteInput.setStyle(null);
							}
						});

					}				
				
				if ((deleteInput.getText() != null && !deleteInput.getText().isEmpty() && Integer.valueOf(deleteInput.getText()) >= 1 && Integer.valueOf(deleteInput.getText()) <= EndGame.entries.size())) {
					
					deleteInput.clear();

					openDelete = true;
					EndGame.entries.remove(deleteNum);
										
					try {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("scores.fds")));
						oos.writeObject(EndGame.entries);
						oos.flush();
						oos.close();
						
						root.getChildren().removeAll(deletePane, Score.highScorePane, Score.namePane, Score.flapsPane, Score.scorePane, highScoreFG, menuCloseButton, editButton, deleteButton, searchButton, dummyCloseButton);
						FlappyDoge.root.getChildren().remove(deletePane);
						
						Score.highScorePane.getChildren().clear();
						Score.namePane.getChildren().clear();
						Score.flapsPane.getChildren().clear();
						Score.scorePane.getChildren().clear();
						
						Score.highScorePane.setTranslateY(0);
						Score.namePane.setTranslateY(0);
						Score.scorePane.setTranslateY(0);
						Score.flapsPane.setTranslateY(0);
																				
					Score.createScoreMenu(EndGame.entries);
					
					root.getChildren().addAll(Score.highScorePane, Score.namePane, Score.scorePane, Score.flapsPane, highScoreFG, menuCloseButton, editButton, deleteButton, searchButton);

					} catch (Exception e) {
						e.printStackTrace();
					}					
		
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FlappyDoge.root.getChildren().remove(root);
				root.getChildren().remove(dummyCloseButton);
				
					}
				});
			} else {
				
				deleteInput.clear();
				deleteInput.setPromptText("Invalid");
				deleteInput.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 53, 15; -fx-background-repeat: no-repeat; ");
				root.requestFocus();
				
				deleteInput.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						
						deleteInput.setStyle(null);
					}
				});

				
					}
				}
			}
		});
		}
	});
}
//-------------------------------------------------------------------------------------------------------------------------
//Event handler for edit button which opens the pane to get the element to edit
		
			editButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					Pane editNumPane = new Pane();
					Button closeButton = new Button();
					Button submitButton = new Button("Submit");
					TextField editNumField = new TextField();
					ImageView prompt = new ImageView(new Image("/Resources/Prompt.png"));
										
					closeButton.graphicProperty().bind(Bindings.when(closeButton.hoverProperty())
						.then(new ImageView(new Image("/Resources/CloseButton Hover.png")))
						.otherwise(new ImageView(new Image("/Resources/CloseButton.png"))));

					closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 5em; -fx-min-width: 3px; -fx-min-height: 3px; -fx-max-width: 3px; -fx-max-height: 3px;");
					
					editNumField.setPromptText("Enter Record # To Edit");
					editNumField.setPrefSize(175, 10);
					
					if (openEditNum) {
					editNumPane.getChildren().addAll(prompt, closeButton, editNumField, submitButton);
					FlappyDoge.root.getChildren().add(editNumPane);
					openEditNum = false;
					openDelete = false;
					openSearch = false;
					
					root.getChildren().add(dummyCloseButton);
					}
					
					editNumField.relocate(20, 23);
					editNumPane.relocate(355, 126);
					submitButton.relocate(221, 23);
					closeButton.relocate(14, 14);
					
//-----------------------------------------------------------------------------------------------------------------------
//Event handler for editNumPane which allows it to be dragged
					
					editNumPane.setOnMousePressed(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							
							xOffset = editNumPane.getTranslateX() - event.getScreenX();
							yOffset = editNumPane.getTranslateY() - event.getScreenY();
						}
					});
									
					editNumPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
											
							editNumPane.setTranslateX(event.getScreenX() + xOffset);
							editNumPane.setTranslateY(event.getScreenY() + yOffset);
												
							if (editNumPane.getTranslateY() <= -126)
							editNumPane.setTranslateY(-126);
							
							if (editNumPane.getTranslateY() >= 402)
							editNumPane.setTranslateY(402);
							
							if (editNumPane.getTranslateX() <= -355)
							editNumPane.setTranslateX(-355);
							
							if (editNumPane.getTranslateX() >= 345)
							editNumPane.setTranslateX(345);

						}
					});
					
//-------------------------------------------------------------------------------------------------------------------------
//Event handler for close button 
									
					closeButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							
							FlappyDoge.root.getChildren().remove(editNumPane);
							openDelete = true;
							openEditNum = true;
							openSearch = true;
							
							root.getChildren().remove(dummyCloseButton);
							
						}
					});
//-------------------------------------------------------------------------------------------------------------------------
//Event handler for submit button which accepts the input and opens the pane to edit the score
										
				submitButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {	
						
						
						openDelete = false;
						openEditNum = false;
						openSearch = false;
						

						try {
							editNum = Integer.valueOf(editNumField.getText())-1;
							
						} catch (Exception e) {
							
							editNumField.clear();
							editNumField.setPromptText("Invalid");
							editNumField.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 53, 15; -fx-background-repeat: no-repeat; ");
							root.requestFocus();
							
							editNumField.setOnMousePressed(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									
									editNumField.setStyle(null);
								}
							});

						}
						
						if ((editNumField.getText() != null && !editNumField.getText().isEmpty() && Integer.valueOf(editNumField.getText()) >= 1 && Integer.valueOf(editNumField.getText()) <= EndGame.entries.size())) {
							
							FlappyDoge.root.getChildren().removeAll(editNumField, editNumPane, submitButton);
							
							TextField nameField = new TextField();
							TextField scoreField = new TextField();
							TextField flapsField = new TextField();
							Button submitButton = new Button("Submit");
							ImageView bigPrompt = new ImageView(new Image("/Resources/Big Prompt.png"));
							DatePicker datePicker = new DatePicker();
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							
							bronzeButton.setGraphic(new ImageView(new Image("/Resources/Bronze Medal Button.png")));
							silverButton.setGraphic(new ImageView(new Image("/Resources/Silver Medal Button.png")));
							goldButton.setGraphic(new ImageView(new Image("/Resources/Gold Medal Button.png")));	
							platinumButton.setGraphic(new ImageView(new Image("/Resources/Platinum Medal Button.png")));

							bronzeButton.setStyle("-fx-background-color: transparent;");
							silverButton.setStyle("-fx-background-color: transparent;");
							goldButton.setStyle("-fx-background-color: transparent;");
							platinumButton.setStyle("-fx-background-color: transparent;");
							datePicker.setStyle("-fx-pref-width: 167;");

							nameField.setPromptText("Name");
							scoreField.setPromptText("Score");
							flapsField.setPromptText("Flaps");
							datePicker.setPromptText("dd/MM/yyyy");	
							
							FlappyDoge.root.getChildren().add(editPane);
							editPane.getChildren().addAll(bigPrompt, nameField, scoreField, flapsField, bronzeButton, silverButton, goldButton, platinumButton, submitButton, closeButton, datePicker);
							editPane.relocate(355, 126);
							closeButton.relocate(14, 14);
							datePicker.relocate(62, 24);
							nameField.relocate(62, 74);
							scoreField.relocate(62, 124);
							flapsField.relocate(62, 174);
							bronzeButton.relocate(7, 206);
							silverButton.relocate(78, 206);
							goldButton.relocate(149, 206);
							platinumButton.relocate(219, 206);
							submitButton.relocate(119, 272);
							
							datePicker.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
									
									stringDate = df.format(date);
									}
								});					
														
							submitButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {	
									
									openDelete = true;
									openEditNum = true;
									editScore = true;
									openSearch = true;
									
									
									String name = nameField.getText();
									root.getChildren().remove(dummyCloseButton);

									
								if (stringDate.equals("")) {	
								datePicker.setPromptText("Invalid");
								root.requestFocus();
								editScore = false;
								}
									
								try {
									newScore = Integer.valueOf(scoreField.getText());
								} catch (Exception e) {
									scoreField.clear();
									scoreField.setPromptText("Invalid");
									root.requestFocus();
									editScore = false;
									
								} try {
									newFlaps = Integer.valueOf(flapsField.getText());
								} catch (Exception e) {
									flapsField.clear();
									flapsField.setPromptText("Invalid");
									root.requestFocus();
									editScore = false;
								}								
								
								if (editScore) {
								EndGame.entries.set(editNum, (new Record(name, newScore, newFlaps, medal, stringDate)));
								editPane.getChildren().clear();
								FlappyDoge.root.getChildren().remove(editPane);
								
								try {
									
								File file = new File("scores.fds");
								
								ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
								ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

							 	oos.writeObject(EndGame.entries);
								oos.flush();
								oos.close();
								
								EndGame.entries = (ArrayList<Record>)ois.readObject();
								Collections.sort(EndGame.entries);
								Score.createScoreMenu(EndGame.entries);

								} catch (Exception e) {
									e.printStackTrace();
								}
								}
								}
							});	
									
							closeButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									FlappyDoge.root.getChildren().remove(editPane);
									editPane.getChildren().clear();
									openDelete = true;
									openEditNum = true;
									openSearch = true;
									
									root.getChildren().remove(dummyCloseButton);

									}
								});
								
								editPane.setOnMousePressed(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent event) {
										
										xOffset = editPane.getTranslateX() - event.getScreenX();
										yOffset = editPane.getTranslateY() - event.getScreenY();
									}
								});
												
								editPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent event) {
														
										editPane.setTranslateX(event.getScreenX() + xOffset);
										editPane.setTranslateY(event.getScreenY() + yOffset);
															
										if (editPane.getTranslateY() <= -126)
										editPane.setTranslateY(-126);
										
										if (editPane.getTranslateY() >= 161)
										editPane.setTranslateY(161);
										
										if (editPane.getTranslateX() <= -355)
										editPane.setTranslateX(-355);
										
										if (editPane.getTranslateX() >= 345)
										editPane.setTranslateX(345);

									}
								});
								
						} else {
							
							editNumField.clear();
							editNumField.setPromptText("Invalid");
							editNumField.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 53, 15; -fx-background-repeat: no-repeat; ");
							root.requestFocus();
							
							editNumField.setOnMousePressed(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									
									editNumField.setStyle(null);
								}
							});						}
					}
				});
				
//-------------------------------------------------------------------------------------------------------------------------
//Identical event handler for enter pressed
							
				editNumPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {	
						
						if (event.getCode().equals(KeyCode.ENTER)) {	
						
						openDelete = false;
						openEditNum = false;
						openSearch = false;
						
						
						try {
							editNum = Integer.valueOf(editNumField.getText())-1;
							
						} catch (Exception e) {
							
							editNumField.clear();
							editNumField.setPromptText("Invalid");
							editNumField.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 53, 15; -fx-background-repeat: no-repeat; ");
							root.requestFocus();
							
							editNumField.setOnMousePressed(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									
									editNumField.setStyle(null);
								}
							});
						}
						
						if ((editNumField.getText() != null && !editNumField.getText().isEmpty() && Integer.valueOf(editNumField.getText()) >= 1 && Integer.valueOf(editNumField.getText()) <= EndGame.entries.size())) {
							
							FlappyDoge.root.getChildren().removeAll(editNumField, editNumPane, submitButton);
							
							Pane editPane = new Pane();
							TextField nameField = new TextField();
							TextField scoreField = new TextField();
							TextField flapsField = new TextField();
							Button submitButton = new Button("Submit");
							ImageView bigPrompt = new ImageView(new Image("/Resources/Big Prompt.png"));
							DatePicker datePicker = new DatePicker();
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							medal = "None";
							
							bronzeButton.setGraphic(new ImageView(new Image("/Resources/Bronze Medal Button.png")));
							silverButton.setGraphic(new ImageView(new Image("/Resources/Silver Medal Button.png")));
							goldButton.setGraphic(new ImageView(new Image("/Resources/Gold Medal Button.png")));	
							platinumButton.setGraphic(new ImageView(new Image("/Resources/Platinum Medal Button.png")));

							bronzeButton.setStyle("-fx-background-color: transparent;");
							silverButton.setStyle("-fx-background-color: transparent;");
							goldButton.setStyle("-fx-background-color: transparent;");
							platinumButton.setStyle("-fx-background-color: transparent;");
							datePicker.setStyle("-fx-pref-width: 167;");

							nameField.setPromptText("Name");
							scoreField.setPromptText("Score");
							flapsField.setPromptText("Flaps");
							datePicker.setPromptText("dd/MM/yyyy");	
							
							FlappyDoge.root.getChildren().add(editPane);
							editPane.getChildren().addAll(bigPrompt, nameField, scoreField, flapsField, bronzeButton, silverButton, goldButton, platinumButton, submitButton, closeButton, datePicker);
							editPane.relocate(355, 126);
							closeButton.relocate(14, 14);
							datePicker.relocate(62, 24);
							nameField.relocate(62, 74);
							scoreField.relocate(62, 124);
							flapsField.relocate(62, 174);
							bronzeButton.relocate(7, 206);
							silverButton.relocate(78, 206);
							goldButton.relocate(149, 206);
							platinumButton.relocate(219, 206);
							submitButton.relocate(119, 272);
							
							datePicker.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
									
									stringDate = df.format(date);
									}
								});					
														
							submitButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {	
									
									openDelete = true;
									openEditNum = true;
									editScore = true;
									openSearch = true;
									
									
									String name = nameField.getText();
									root.getChildren().remove(dummyCloseButton);

									
								if (stringDate.equals("")) {	
								datePicker.setPromptText("Invalid");
								root.requestFocus();
								editScore = false;
								}
									
								try {
									newScore = Integer.valueOf(scoreField.getText());
								} catch (Exception e) {
									scoreField.clear();
									scoreField.setPromptText("Invalid");
									root.requestFocus();
									editScore = false;
									
								} try {
									newFlaps = Integer.valueOf(flapsField.getText());
								} catch (Exception e) {
									flapsField.clear();
									flapsField.setPromptText("Invalid");
									root.requestFocus();
									editScore = false;
								}				
								
								if (editScore) {
								EndGame.entries.set(editNum, (new Record(name, newScore, newFlaps, medal, stringDate)));
								editPane.getChildren().clear();
								FlappyDoge.root.getChildren().remove(editPane);
								datePicker.setPromptText("DD/mm/yyyy");
								scoreField.clear();
								flapsField.clear();
								nameField.clear();
								root.requestFocus();
								
								try {
									
								File file = new File("scores.fds");
								
								ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
								ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

							 	oos.writeObject(EndGame.entries);
								oos.flush();
								oos.close();
								
								EndGame.entries = (ArrayList<Record>)ois.readObject();
								Collections.sort(EndGame.entries);
								Score.createScoreMenu(EndGame.entries);

								} catch (Exception e) {
									e.printStackTrace();
								}
								}
								}
							});
													
							closeButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									FlappyDoge.root.getChildren().remove(editPane);
									editPane.getChildren().clear();
									openDelete = true;
									openEditNum = true;
									openSearch = true;
									root.getChildren().remove(dummyCloseButton);

									}
								});
								
								editPane.setOnMousePressed(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent event) {
										
										xOffset = editPane.getTranslateX() - event.getScreenX();
										yOffset = editPane.getTranslateY() - event.getScreenY();
										
										bronzeButton.setGraphic(new ImageView(new Image("/Resources/Bronze Medal Button.png")));
										silverButton.setGraphic(new ImageView(new Image("/Resources/Silver Medal Button.png")));
										goldButton.setGraphic(new ImageView(new Image("/Resources/Gold Medal Button.png")));	
										platinumButton.setGraphic(new ImageView(new Image("/Resources/Platinum Medal Button.png")));
										medal = "None";
									}
								});
												
								editPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent event) {
														
										editPane.setTranslateX(event.getScreenX() + xOffset);
										editPane.setTranslateY(event.getScreenY() + yOffset);
															
										if (editPane.getTranslateY() <= -126)
										editPane.setTranslateY(-126);
										
										if (editPane.getTranslateY() >= 161)
										editPane.setTranslateY(161);
										
										if (editPane.getTranslateX() <= -355)
										editPane.setTranslateX(-355);
										
										if (editPane.getTranslateX() >= 345)
										editPane.setTranslateX(345);

									}
								});
								
						} else {
							
							editNumField.clear();
							editNumField.setPromptText("Invalid");
							editNumField.setStyle("-fx-background-image:url('Resources/Warning.png'); -fx-background-position: 53, 15; -fx-background-repeat: no-repeat; ");
							root.requestFocus();
							
							editNumField.setOnMousePressed(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									
									editNumField.setStyle(null);
								}
							});
						}
					}
				}
			});
			
//-------------------------------------------------------------------------------------------------------------------------
				
				bronzeButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
												
						bronzeButton.setGraphic(new ImageView(new Image("/Resources/Bronze Medal Pressed.png")));
						silverButton.setGraphic(new ImageView(new Image("/Resources/Silver Medal Button.png")));
						goldButton.setGraphic(new ImageView(new Image("/Resources/Gold Medal Button.png")));	
						platinumButton.setGraphic(new ImageView(new Image("/Resources/Platinum Medal Button.png")));
						medal = "Bronze";
						
						}
					});
				
				silverButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						
						bronzeButton.setGraphic(new ImageView(new Image("/Resources/Bronze Medal Button.png")));
						silverButton.setGraphic(new ImageView(new Image("/Resources/Silver Medal Pressed.png")));
						goldButton.setGraphic(new ImageView(new Image("/Resources/Gold Medal Button.png")));	
						platinumButton.setGraphic(new ImageView(new Image("/Resources/Platinum Medal Button.png")));
						medal = "Silver";
						}
					});	
				
				goldButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						
						bronzeButton.setGraphic(new ImageView(new Image("/Resources/Bronze Medal Button.png")));
						silverButton.setGraphic(new ImageView(new Image("/Resources/Silver Medal Button.png")));
						goldButton.setGraphic(new ImageView(new Image("/Resources/Gold Medal Pressed.png")));	
						platinumButton.setGraphic(new ImageView(new Image("/Resources/Platinum Medal Button.png")));
						medal = "Gold";
						}
					});	
					
				platinumButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						
						bronzeButton.setGraphic(new ImageView(new Image("/Resources/Bronze Medal Button.png")));
						silverButton.setGraphic(new ImageView(new Image("/Resources/Silver Medal Button.png")));
						goldButton.setGraphic(new ImageView(new Image("/Resources/Gold Medal Button.png")));	
						platinumButton.setGraphic(new ImageView(new Image("/Resources/Platinum Medal Pressed.png")));
						medal = "Platinum";
					}
				});
			}
		});
	}
});
//-------------------------------------------------------------------------------------------------------------------------
	}
	
	public static ArrayList<Record> search(String parameter, ArrayList<Record> src) {
		ArrayList<Record> results = new ArrayList<Record>();
		
		for (int i = 0; i < src.size(); i++) {
			
			if (src.get(i).toUpperCase().contains(parameter.toUpperCase())) {
				results.add(src.get(i));
			}
		}
		return results;
	}
}