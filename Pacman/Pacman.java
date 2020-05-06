import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Pacman extends Application{
	private GridPane map = new GridPane();
	private Map m = new Map();
	private char[][] grid = m.getGrid();
	private Scene scene; 
	private VBox vBox;
	private HBox hBox;
	private BorderPane borderPane;
	
	private Character player = new Player(m.getWidth());

	private Label score = new Label("Score: " + Integer.toString(((Player)player).getPoints()));
	private Label gameStatus = new Label("");
	
	private Character enemy1 = new Enemy("images//ghost2.gif", m.getWidth(), 1);
	private Character enemy2 = new Enemy("images//redghost.gif", m.getWidth(), 2);
	
//	private Character enemy1 = new Enemy1(m.getWidth());
//	private Character enemy2 = new Enemy2(m.getWidth());
	private Wall wall;
	private Wall smallDot;
	private Wall bigDot;
	private Wall empty;
	
	private boolean stop = false;
	
	private EnemyControls enemy1Timer = new EnemyControls(enemy1);
	private EnemyControls enemy2Timer = new EnemyControls(enemy2);
	
	private ImageView playerImageView = player.getImageView();

	public static void main(String[] args) {
	      // Launch the application.
	      launch(args);
	}
	
	public void start(Stage primaryStage) {
		
		// Create the menu bar.
		MenuBar menuBar = new MenuBar();
 
		// Create the File menu.
		Menu fileMenu = new Menu("Game");
		MenuItem save = new MenuItem("Save Game");
		MenuItem load = new MenuItem("Load Game");
		fileMenu.getItems().add(save);
		fileMenu.getItems().add(load);
		
		// Register an event handler for the exit item.
	    save.setOnAction(event ->
	    {
	    	try {
	    		FileChooser fileChooser = new FileChooser();
	    		fileChooser.setTitle("Save Game!");
	    		fileChooser.setInitialDirectory(new File("save"));
	    		File file = fileChooser.showSaveDialog(primaryStage);
	    		String fileName = "";
	    		if(file != null) {
	    			fileName = file.getAbsolutePath();
	    		}
	    		ObjectOutputStream saveGame = new ObjectOutputStream(new FileOutputStream(fileName));
				
	    		saveGame.writeObject(m); // save map
	    		saveGame.writeObject(player); // save the player info
	    		// not necessary
//	    	    saveGame.writeObject(enemy1); // save enemy1 info
//	    	    saveGame.writeObject(enemy2); // save enemy2 info
	    	    
	    		System.out.println("Game Saved");
	    	    saveGame.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	   

	    });
	    
	    load.setOnAction(event ->
	    {
	       try {
	    	   FileChooser fileChooser = new FileChooser();
	    	   fileChooser.setTitle("Load Game!");
	    	   fileChooser.setInitialDirectory(new File("save"));
	    	   File selectedFile = fileChooser.showOpenDialog(primaryStage);
	    	   
	    	   String fileName = "";
	    	   if(selectedFile != null) {
	    		   fileName = selectedFile.getPath();
	    	   }
	    	   
	    	   ObjectInputStream loadGame = new ObjectInputStream(new FileInputStream(fileName));
	    	   
	    	   try {
	    		   m = (Map)loadGame.readObject();
	    		   grid = m.getGrid();
	    		   player = (Character)loadGame.readObject();
	    			
//	    			enemy1Timer.stop();
//					enemy2Timer.stop();
//	    			for (Node node : map.getChildren())	
//	    			    map.getChildren().remove(node);

	    		   
	    		  // if gameStatus is non empty string
	    		   	// start timers and key event 
	    		  
	    		   map = new GridPane();
	    		   fillGrid();
	    		   vBox.getChildren().setAll(borderPane, hBox, map);
	    		   score.setText(("Score: " + Integer.toString(((Player)player).getPoints())));
	    		   
	    		   System.out.println("Game Loaded");
	    		   
	    		   
	    	   } catch (ClassNotFoundException e) {
	    		   e.printStackTrace();
	    	   }
	    	   
	    	   loadGame.close();
	    	   
	       } catch (IOException e) {
	    	   e.printStackTrace();
	       }
	    });

	    // Add the File menu to the menu bar.
	    menuBar.getMenus().addAll(fileMenu);
	         
	    // Add the menu bar to a BorderPane.
	    borderPane = new BorderPane();
	    borderPane.setTop(menuBar);
		
		
		hBox = new HBox(score, gameStatus);

	    vBox = new VBox(borderPane, hBox, map);
	    vBox.setBackground(Background.EMPTY);
	    gameStatus.setTextFill(Color.RED);
	    score.setTextFill(Color.web("#ffffff"));

	    scene = new Scene(vBox, Color.BLACK);
		
		fillGrid();
		controls();
		
		
		enemy1Timer.start();
		enemy2Timer.start();
		
		primaryStage.setScene(scene);
		
		// Set the stage title.
		primaryStage.setTitle("Pacman");
  
		// Show the window.
		primaryStage.show();
	}
	
	private class EnemyControls extends AnimationTimer {
		private long prevTime;
		private char currPos;
		private Character enemy;
		private char name;
		
		// adjust enemy controls to run into player
		public EnemyControls(Character e) {
			enemy = e;
			prevTime = 0;
			currPos = 'E';
			
			if(((Enemy)e).getID() == 1)
				name = '1';
			
			else name = '2';
				
		}
		
		
		public void handle(long now) {
			long dt = now - prevTime;
			
			if(dt > 0.3e9) {
				int r = enemy.getR();
				int c = enemy.getC();
				char direction = randomValidDirection(enemy);
				
				prevTime = now;

				// up
				if(direction == 'U') {
					grid[r][c] = currPos;
					currPos = grid[r-1][c];
					grid[r-1][c] = name;
					enemy.setR(r-1);
					update(r, c, grid[r][c], r-1, c, grid[r-1][c], "");
				}
				
				// down
				else if(direction == 'D') {
					grid[r][c] = currPos;
					currPos = grid[r+1][c];
					grid[r+1][c] = name;
					enemy.setR(r+1);
					update(r, c, grid[r][c], r+1, c, grid[r+1][c], "");
				}
				
				// left
				else if(direction == 'L') {
					grid[r][c] = currPos;
					currPos = grid[r][c-1];
					grid[r][c-1] = name;
					enemy.setC(c-1);
					update(r, c, grid[r][c], r, c-1, grid[r][c-1], "");
				}
				
				// right
				else if(direction == 'R'){
					grid[r][c] = currPos;
					currPos = grid[r][c+1];
					grid[r][c+1] = name;
					enemy.setC(c+1);
					update(r, c, grid[r][c], r, c+1, grid[r][c+1], "");
				}
			}
		}
	}
	
	public char randomValidDirection(Character e) {
		int r = e.getR();
		int c = e.getC();
		char direction = 'z';
		int randomNum;	// [1, 4], up, down, left, right
		
		// W, 1, 2, P

		while(true) {
			randomNum = (int)(Math.random()*4) + 1;

			if(randomNum == 1) {	//up
				if(grid[r-1][c] == 'S' || grid[r-1][c] == 'B' || grid[r-1][c] == 'E') {
					direction = 'U';
					break;
				}
				
				//else if(grid[][] == 'P') stop timers stop keyboard even
				else if(grid[r-1][c] == 'P') {
					enemy1Timer.stop();
					enemy2Timer.stop();
					stop = true;
					gameStatus.setText("GAME OVER");
					break;
				}
				
				else continue;
			}
			
			else if(randomNum == 2) { // down
				if(grid[r+1][c] == 'S' || grid[r+1][c] == 'B' || grid[r+1][c] == 'E') {
					direction = 'D';
					break;
				}
				
				else if(grid[r+1][c] == 'P') {
					enemy1Timer.stop();
					enemy2Timer.stop();
					stop = true;
					gameStatus.setText("GAME OVER");
					break;
				}
				else continue;
			}
			
			else if(randomNum == 3) { // left
				if(grid[r][c-1] == 'S' || grid[r][c-1] == 'B' || grid[r][c-1] == 'E') {
					direction = 'L';
					break;
				}
				
				else if(grid[r][c-1] == 'P') {
					enemy1Timer.stop();
					enemy2Timer.stop();
					stop = true;
					gameStatus.setText("GAME OVER");
					break;
				}
				
				else continue;
			}
			
			else { // right
				if(grid[r][c+1] == 'S' || grid[r][c+1] == 'B' || grid[r][c+1] == 'E') {
					direction = 'R';
					break;
				}
				
				else if(grid[r][c+1] == 'P') {
					enemy1Timer.stop();
					enemy2Timer.stop();
					stop = true;
					gameStatus.setText("GAME OVER");
					break;
				}
				
				else continue;
			}
		}
		
		return direction;
	}
	
	public void controls() {
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			int r = player.getR();
			int c = player.getC();	// change c
			String orientation;
			//System.out.println("Player: " + r + " " + c);
			
			if(stop) {
				key.consume();
				return;
			}
			
			if(key.getCode() == KeyCode.RIGHT) {
				orientation = "pacmanRight.gif";
				
				if(grid[r][c+1] == 'S') { // if small dot
					grid[r][c] = 'E';
					grid[r][c+1] = 'P';
					player.setC(c+1);
					((Player)player).setPoints(((Player)player).getPoints() + 5);
					update(r, c, grid[r][c], r, c+1, grid[r][c+1], orientation);
				}
				
				else if(grid[r][c+1] == 'B') { // if big dot
					grid[r][c] = 'E';
					grid[r][c+1] = 'P';
					player.setC(c+1);
					((Player)player).setPoints(((Player)player).getPoints() + 10);
					update(r, c, grid[r][c], r, c+1, grid[r][c+1], orientation);
				}
				
				else if(grid[r][c+1] == 'E') { // if empty space
					grid[r][c] = 'E';
					grid[r][c+1] = 'P';
					player.setC(c+1);
					update(r, c, grid[r][c], r, c+1, grid[r][c+1], orientation);
				}
				
				else if(grid[r][c+1] > '0' && grid[r][c+1] < '9') {
					// enemy ahead
					enemy1Timer.stop();
					enemy2Timer.stop();
					//scene.removeEventHandler(KeyEvent.KEY_PRESSED, (key));
					stop = true;
					gameStatus.setText("GAME OVER");
				}
				
				System.out.println("Right Key Pressed");
				System.out.println("Points so far: " + ((Player)player).getPoints());
			}
			
			else if(key.getCode()==KeyCode.LEFT) {
				orientation = "pacmanLeft.gif";
                if(grid[r][c-1]=='S')
                { //if it is a small dot
                    grid[r][c]='E';
                    grid[r][c-1]='P';
                    player.setC(c-1);
                    ((Player)player).setPoints(((Player)player).getPoints() + 5);
					update(r, c, grid[r][c], r, c-1, grid[r][c-1], orientation);
                }
                
				else if(grid[r][c-1] == 'B') { // if big dot
					grid[r][c] = 'E';
					grid[r][c-1] = 'P';
					player.setC(c-1);
					((Player)player).setPoints(((Player)player).getPoints() + 10);
					update(r, c, grid[r][c], r, c-1, grid[r][c-1], orientation);
				}
				
				else if(grid[r][c-1] == 'E') { // if empty space
					grid[r][c] = 'E';
					grid[r][c-1] = 'P';
					player.setC(c-1);
					update(r, c, grid[r][c], r, c-1, grid[r][c-1], orientation);
				}
                
				else if(grid[r][c-1] > '0' && grid[r][c-1] < '9') {
					// enemy ahead
					enemy1Timer.stop();
					enemy2Timer.stop();
					stop = true;
					gameStatus.setText("GAME OVER");
				}
				
				System.out.println("Right Key Pressed");
				System.out.println("Points so far: " + ((Player)player).getPoints());
			}
			
			else if(key.getCode()==KeyCode.DOWN) {
				orientation = "pacmanDown.gif";
                if(grid[r+1][c]=='S')
                { //if it is a small dot
                    grid[r][c]='E';
                    grid[r+1][c]='P';
                    player.setR(r+1);
                    ((Player)player).setPoints(((Player)player).getPoints() + 5);
					update(r, c, grid[r][c], r+1, c, grid[r+1][c], orientation);
                }
                
				else if(grid[r+1][c] == 'B') { // if big dot
                    grid[r][c]='E';
                    grid[r+1][c]='P';
                    player.setR(r+1);
                    ((Player)player).setPoints(((Player)player).getPoints() + 10);
					update(r, c, grid[r][c], r+1, c, grid[r+1][c], orientation);
				}
				
				else if(grid[r+1][c] == 'E') { // if empty space
                    grid[r][c]='E';
                    grid[r+1][c]='P';
                    player.setR(r+1);
					update(r, c, grid[r][c], r+1, c, grid[r+1][c], orientation);
				}
                
				else if(grid[r+1][c] > '0' && grid[r+1][c] < '9') {
					// enemy ahead
					enemy1Timer.stop();
					enemy2Timer.stop();
					stop = true;
					gameStatus.setText("GAME OVER");
				}
				
				System.out.println("Down Key Pressed");
				System.out.println("Points so far: " + ((Player)player).getPoints());
			}
			
			else if(key.getCode()==KeyCode.UP) {
				orientation = "pacmanUp.gif";
                if(grid[r-1][c]=='S')
                { //if it is a small dot
                    grid[r][c]='E';
                    grid[r-1][c]='P';
                    player.setR(r-1);
                    ((Player)player).setPoints(((Player)player).getPoints() + 5);
					update(r, c, grid[r][c], r-1, c, grid[r-1][c], orientation);
                }
                
				else if(grid[r-1][c] == 'B') { // if big dot
                    grid[r][c]='E';
                    grid[r-1][c]='P';
                    player.setR(r-1);
                    ((Player)player).setPoints(((Player)player).getPoints() + 10);
					update(r, c, grid[r][c], r-1, c, grid[r-1][c], orientation);
				}
				
				else if(grid[r-1][c] == 'E') { // if empty space
                    grid[r][c]='E';
                    grid[r-1][c]='P';
                    player.setR(r-1);
					update(r, c, grid[r][c], r-1, c, grid[r-1][c], orientation);
				}
                
				else if(grid[r-1][c] > '0' && grid[r-1][c] < '9') {
					// enemy ahead
					enemy1Timer.stop();
					enemy2Timer.stop();
					stop = true;
					gameStatus.setText("GAME OVER");
				}
                	
				System.out.println("Up Key Pressed");
				System.out.println("Points so far: " + ((Player)player).getPoints());
			}
			
			score.setText(("Score: " + Integer.toString(((Player)player).getPoints())));
			
		});
	}
	
	public void update(int r1, int c1, char a, int r2, int c2, char b, String orientation) {
		updateHelper(r1, c1, a, orientation);
		updateHelper(r2, c2, b, orientation);
		if(won()) {
			enemy1Timer.stop();
			enemy2Timer.stop();
			stop = true;
			
			gameStatus.setText("YOU WON");
			gameStatus.setTextFill(Color.GREEN);
		}
	}
	
	@SuppressWarnings("static-access")
	public void updateHelper(int i, int j, int x, String orientation) {
//		map.getChildren().remove(j+1, i+1);
		//remove node
		for (Node node : map.getChildren()) {
		    if (map.getColumnIndex(node) == (j+1) && map.getRowIndex(node) == (i+1)) {
		        map.getChildren().remove(node);
		        break;
		    }
		}
		
		if(x == 'S') {
			smallDot = new Wall("images//smalldot.png", m.getWidth());
			ImageView imageView = smallDot.getImageView();
            map.add(imageView, j+1, i+1);
		}
		
		else if(x == 'B') {
			bigDot = new Wall("images//whitedot.png", m.getWidth());
			ImageView imageView = bigDot.getImageView();
            map.add(imageView, j+1, i+1);
		}
		
		else if(x == '1') {
			//enemy1 = new Enemy("images//ghost2.gif", m.getWidth());
			ImageView imageView = enemy1.getImageView();
			enemy1.setR(i);
			enemy1.setC(j);
            map.add(imageView, j+1, i+1);
		}
		
		else if(x == '2') {
			//enemy2 = new Enemy("images//redghost.gif", m.getWidth());
			ImageView imageView = enemy2.getImageView();
			enemy2.setR(i);
			enemy2.setC(j);
            map.add(imageView, j+1, i+1);
		}
		
		else if(x == 'P'){
			Image image = new Image("images/" + orientation);
			player.setImageView(new ImageView(image), m.getWidth());
			ImageView imageView = player.getImageView();
			player.setR(i);
			player.setC(j);
			System.out.println(player.getR() + " " + player.getC());
            map.add(imageView, j+1, i+1);
		}
		
		else {
			empty = new Wall("images//empty.png", m.getWidth());
			ImageView imageView = empty.getImageView();
            map.add(imageView, j+1, i+1);
		}
		
	}
	
	public boolean won() {
		for(int i = 0; i < m.getRows(); i++) {
			for(int j = 0; j < m.getCols(); j++) {
				if(grid[i][j] == 'S' || grid[i][j] == 'B')
					return false;
			}
		}
		return true;
	}
	
	// KEEP TRACK OF ENEMIES, PACMAN, ETC
	public void fillGrid() {
		for(int i = 0; i < m.getRows(); i++) {
			for(int j = 0; j < m.getCols(); j++) {
				if(grid[i][j] == 'W') {
					wall = new Wall("images//wall.png", m.getWidth());
					ImageView imageView = wall.getImageView();
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'S') {
					smallDot = new Wall("images//smalldot.png", m.getWidth());
					ImageView imageView = smallDot.getImageView();
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'B') {
					bigDot = new Wall("images//whitedot.png", m.getWidth());
					ImageView imageView = bigDot.getImageView();
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == '1') {
					ImageView imageView = enemy1.getImageView();
					enemy1.setR(i);
					enemy1.setC(j);
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == '2') {
					ImageView imageView = enemy2.getImageView();
					enemy2.setR(i);
					enemy2.setC(j);
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'P') {
					player.setR(i);
					player.setC(j);
					System.out.println(player.getR() + " " + player.getC());
                    map.add(playerImageView, j+1, i+1);
				}
				
				else {
					empty = new Wall("images//empty.png", m.getWidth());
					ImageView imageView = empty.getImageView();
//                    imageView.setX((double)j * m.getWidth());
//                    imageView.setY((double)i * m.getWidth());
//                    imageView.setFitWidth(m.getWidth());
//                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
			}
		}
	}
}
