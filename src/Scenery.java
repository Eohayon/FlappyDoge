/* Program Name: Flappy Doge (FST)
   File Name: Scenery.java
   Author: Ethan Ohayon
   Date Began: March 15, 2017
   Date Due: June 2, 2017
   Purpose: Creates objects for the pipes, the ground and the Doge
*/

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;

class TopPipe extends Rectangle {

	public TopPipe(int x, int y) {
		
		super(0, 0, 75, 410);
		
		Image pipeImage = new Image("/Resources/Top Pipe.png"); 
		
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setFill(new ImagePattern(pipeImage));
		
		}
}

class Ground extends Rectangle {

	public Ground(int x, int y) {
		
		super(0, 0, 1000, 100);
		
		Image groundImage = new Image("/Resources/Ground.png"); 
		
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setFill(new ImagePattern(groundImage));
		}
}

class BottomPipe extends Rectangle {

	public BottomPipe(int x, int y) {
		
		super(0, 0, 75, 410);
		
		Image pipeImage = new Image("/Resources/Bottom Pipe.png"); 
		
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setFill(new ImagePattern(pipeImage));
		
		}
}

class Doge extends Path {

	public Doge(int x, int y) {
				
		this.getElements().addAll(new MoveTo(0, 0),
								  new LineTo(5, 0),
								  new LineTo(5, 2),
								  new LineTo(13, 2),
								  new LineTo(13, -1),
								  new LineTo(19, -1),
								  new LineTo(19, 4),
								  new LineTo(21, 4),
							      new LineTo(21, 8),
								  new LineTo(24, 8),
								  new LineTo(24, 11),
								  new LineTo(26, 11),
								  new LineTo(26, 16.5),
								  new LineTo(29, 16.5),
								  new LineTo(29, 25),
								  new LineTo(29, 28),
							      new LineTo(27, 28),
								  new LineTo(27, 31),
								  new LineTo(23, 31),
								  new LineTo(23, 33),
								  new LineTo(-5, 33),
								  new LineTo(-5, 33),
							      new LineTo(-20, 33),
								  new LineTo(-20, 15),
								  new LineTo(-10, 15),
								  new LineTo(-10, 10),
								  new LineTo(-10, 10),
								  new LineTo(-10, 4),
							      new LineTo(-4, 4),
								  new LineTo(-4, 0),	
								  new ClosePath());

							
		this.setFill(Color.BLACK);		
		this.setTranslateX(x);
		this.setTranslateY(y);
		
		}
}