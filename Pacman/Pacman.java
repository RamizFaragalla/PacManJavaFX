import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
//import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Pacman extends Application{
	private GridPane map = new GridPane();
	private Map m = new Map();
	private char[][] grid = m.getGrid();
	private int points = 0;
	private Scene scene = new Scene(map, Color.BLACK);
	
	private Character player = new Player(m.getWidth());
	private Character enemy1 = new Enemy1(m.getWidth());
	private Character enemy2 = new Enemy2(m.getWidth());
	private Wall wall;
	private Wall smallDot;
	private Wall bigDot;
	private Wall empty;

	public static void main(String[] args) {
	      // Launch the application.
	      launch(args);
	}
	
	
	public void start(Stage primaryStage) {
		
		fillGrid();
		controls();
		primaryStage.setScene(scene);
		
		// Set the stage title.
		primaryStage.setTitle("Pacman");
  
		// Show the window.
		primaryStage.show();
	}
	
	public void controls() {
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode() == KeyCode.RIGHT) {
				int r = player.getR();
				int c = player.getC();	// change c
				
				
				if(grid[r][c+1] == 'S') {
					grid[r][c] = 'E';
					grid[r][c+1] = 'P';
					player.setC(c+1);
					
				}
				update(r, c, grid[r][c], r, c+1, grid[r][c+1]);
				System.out.println("Key Pressed");
				
			}
		});
	}
	
	public void update(int r1, int c1, char a, int r2, int c2, char b) {
		updateHelper(r1, c1, a);
		updateHelper(r2, c2, b);
		
	}
	
	public void updateHelper(int i, int j, int x) {
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
			ImageView imageView = enemy1.getImageView();
			enemy1.setR(i);
			enemy1.setC(j);
            map.add(imageView, j+1, i+1);
		}
		
		else if(x == '2') {
			ImageView imageView = enemy2.getImageView();
			enemy2.setR(i);
			enemy2.setC(j);
            map.add(imageView, j+1, i+1);
		}
		
		else if(x == 'P'){
			
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
	
	// KEEP TRACK OF ENEMIES, PACMAN, ETC
	public void fillGrid() {
		for(int i = 0; i < m.getRows(); i++) {
			for(int j = 0; j < m.getCols(); j++) {
				if(grid[i][j] == 'W') {
					wall = new Wall("images//wall.png", m.getWidth());
					//Wall wall = new Wall("images//wall.png");
					ImageView imageView = wall.getImageView();
                    //imageView.setX((double)j * m.getWidth());
                    //imageView.setY((double)i * m.getWidth());
//                    imageView.setFitWidth(m.getWidth());
//                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'S') {
					smallDot = new Wall("images//smalldot.png", m.getWidth());
					ImageView imageView = smallDot.getImageView();
//                    imageView.setX((double)j * m.getWidth());
//                    imageView.setY((double)i * m.getWidth());
//                    imageView.setFitWidth(m.getWidth());
//                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'B') {
					bigDot = new Wall("images//whitedot.png", m.getWidth());
					ImageView imageView = bigDot.getImageView();
//                    imageView.setX((double)j * m.getWidth());
//                    imageView.setY((double)i * m.getWidth());
//                    imageView.setFitWidth(m.getWidth());
//                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == '1') {
//					Wall wall = new Wall("images//ghost2.gif");
					ImageView imageView = enemy1.getImageView();
					enemy1.setR(i);
					enemy1.setC(j);
					//enemy1 = new Enemy1(i, j);
//					ImageView imageView = enemy1.getImageView();
//                    imageView.setX((double)j * m.getWidth());
//                    imageView.setY((double)i * m.getWidth());
//                    imageView.setFitWidth(m.getWidth());
//                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == '2') {
//					Wall wall = new Wall("images//redghost.gif");
					ImageView imageView = enemy2.getImageView();
					//enemy2 = new Enemy2(i, j);
					enemy2.setR(i);
					enemy2.setC(j);
//					ImageView imageView = enemy2.getImageView();
//                    imageView.setX((double)j * m.getWidth());
//                    imageView.setY((double)i * m.getWidth());
//                    imageView.setFitWidth(m.getWidth());
//                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'P') {
//					Wall wall = new Wall("images//pacmanRight.gif");
//					ImageView imageView = wall.getImageView();
					//player = new Player(i, j);
					
					ImageView imageView = player.getImageView();
					player.setR(i);
					player.setC(j);
					System.out.println(player.getR() + " " + player.getC());
//                    imageView.setX((double)j * m.getWidth());
//                    imageView.setY((double)i * m.getWidth());
//                    imageView.setFitWidth(m.getWidth());
//                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
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

