import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Pacman extends Application{
	private GridPane map = new GridPane();
	private Map m = new Map();
	private char[][] grid = m.getGrid();
	private Character player;
	private Character enemy1;
	private Character enemy2;
	private int points = 0;

	public static void main(String[] args) {
	      // Launch the application.
	      launch(args);
	}
	
	
	public void start(Stage primaryStage) {
		
		update();
		
		map.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.RIGHT) {
				int r = player.getR();
				int c = player.getC();	// change c
				
				//check walls
//				if(c + 1 == m.getRows()) { // out of bounds
//					return;
//				}
				
				if(grid[r][c+1] == 'W') {
					return;
				}
				
				if(grid[r][c+1] == 'S') {
					grid[r][c] = 'E';
					grid[r][c+1] = 'P';
					player.setC(c+1);
					
				}
				System.out.println("Key Pressed");
				update();
			}
		});
		
		Scene scene = new Scene(map, Color.BLACK);
		primaryStage.setScene(scene);
		
		// Set the stage title.
		primaryStage.setTitle("Pacman");
  
		// Show the window.
		primaryStage.show();
	}
	
	// KEEP TRACK OF ENEMIES, PACMAN, ETC
	public void update() {
		for(int i = 0; i < m.getRows(); i++) {
			for(int j = 0; j < m.getCols(); j++) {
				if(grid[i][j] == 'W') {
					Wall wall = new Wall("images//wall.png");
					ImageView imageView = wall.getImageView();
                    imageView.setX((double)j * m.getWidth());
                    imageView.setY((double)i * m.getWidth());
                    imageView.setFitWidth(m.getWidth());
                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'S') {
					Wall wall = new Wall("images//smalldot.png");
					ImageView imageView = wall.getImageView();
                    imageView.setX((double)j * m.getWidth());
                    imageView.setY((double)i * m.getWidth());
                    imageView.setFitWidth(m.getWidth());
                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'B') {
					Wall wall = new Wall("images//whitedot.png");
					ImageView imageView = wall.getImageView();
                    imageView.setX((double)j * m.getWidth());
                    imageView.setY((double)i * m.getWidth());
                    imageView.setFitWidth(m.getWidth());
                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == '1') {
//					Wall wall = new Wall("images//ghost2.gif");
//					ImageView imageView = wall.getImageView();
					enemy1 = new Enemy1(i, j);
					ImageView imageView = enemy1.getImageView();
                    imageView.setX((double)j * m.getWidth());
                    imageView.setY((double)i * m.getWidth());
                    imageView.setFitWidth(m.getWidth());
                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == '2') {
//					Wall wall = new Wall("images//redghost.gif");
//					ImageView imageView = wall.getImageView();
					enemy2 = new Enemy2(i, j);
					ImageView imageView = enemy2.getImageView();
                    imageView.setX((double)j * m.getWidth());
                    imageView.setY((double)i * m.getWidth());
                    imageView.setFitWidth(m.getWidth());
                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'P') {
//					Wall wall = new Wall("images//pacmanRight.gif");
//					ImageView imageView = wall.getImageView();
					player = new Player(i, j);
					System.out.println(player.getR() + " " + player.getC());
					ImageView imageView = player.getImageView();
                    imageView.setX((double)j * m.getWidth());
                    imageView.setY((double)i * m.getWidth());
                    imageView.setFitWidth(m.getWidth());
                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
			}
		}
	}
}	

