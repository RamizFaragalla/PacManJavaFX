import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Pacman extends Application{
	public GridPane map = new GridPane();
	public Map m = new Map();

	public static void main(String[] args) {
	      // Launch the application.
	      launch(args);
	}
	
	@Override
   /**
    * @param primaryStage
    * @return void
    */
	public void start(Stage primaryStage) {
		char[][] grid = m.getGrid();
		fillGridPane(map, grid);
		
		
		//Label l = new Label("hi");
		Scene scene = new Scene(map, Color.BLACK);
		
		primaryStage.setScene(scene);
		
		// Set the stage title.
		primaryStage.setTitle("Pacman");
  
		// Show the window.
		primaryStage.show();
	}
	
	public void fillGridPane(GridPane map, char[][] grid) {
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
					Wall wall = new Wall("images//ghost2.gif");
					ImageView imageView = wall.getImageView();
                    imageView.setX((double)j * m.getWidth());
                    imageView.setY((double)i * m.getWidth());
                    imageView.setFitWidth(m.getWidth());
                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == '2') {
					Wall wall = new Wall("images//redghost.gif");
					ImageView imageView = wall.getImageView();
                    imageView.setX((double)j * m.getWidth());
                    imageView.setY((double)i * m.getWidth());
                    imageView.setFitWidth(m.getWidth());
                    imageView.setFitHeight(m.getWidth());
                    map.add(imageView, j+1, i+1);
				}
				
				else if(grid[i][j] == 'P') {
					Wall wall = new Wall("images//pacmanRight.gif");
					ImageView imageView = wall.getImageView();
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