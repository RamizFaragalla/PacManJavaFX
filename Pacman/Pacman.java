import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Pacman extends Application {
	private Map m = new Map(); // create the new map
	private Player player = new Player(m); // create the new plater
	private GridPane map = m.getGridPane(); //
	
	private Enemy enemy1 = new Enemy(m, "images//ghost2.gif", '1');
	private Enemy enemy2 = new Enemy(m, "images//redghost.gif", '2');
	
	private VBox vBox;
	private HBox hBox;
	private BorderPane borderPane;
	private Scene scene;
	private Label score = new Label("Score: " + Integer.toString(player.getPoints()));
	private Label gameStatus = new Label("");
	
	public static void main(String[] args) {
	      // Launch the application.
	      launch(args);
	}

	public void start(Stage primaryStage) {
		
		
		player.setLabels(score, gameStatus);
		enemy1.setCharacters(player, enemy2);
		enemy2.setCharacters(player, enemy1);
	
		player.setEnemies(enemy1, enemy2);
		
		m.setCharacters(player, enemy1, enemy2);
		m.fillGridPane();
		//m.getGridPane().setBackground(Background.EMPTY);
		
		
		fileSystem(primaryStage);
		hBox = new HBox(score, gameStatus);
		vBox = new VBox(borderPane, hBox, map);
		vBox.setBackground(Background.EMPTY);
		gameStatus.setTextFill(Color.RED);
		score.setTextFill(Color.web("#ffffff"));
		scene = new Scene(vBox, Color.BLACK);
		
		player.setScene(scene);
		player.controls();
		enemy1.controls();
		enemy2.controls();
	
			
		primaryStage.setScene(scene);
		
		// Set the stage title.
		primaryStage.setTitle("Pacman");
	 
		// Show the window.
		primaryStage.show();
	}

	public void fileSystem(Stage primaryStage) {
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
//		    	    saveGame.writeObject(enemy1); // save enemy1 info
//		    	    saveGame.writeObject(enemy2); // save enemy2 info
	    	    
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
	    	  player.stopControls();
	    	   enemy1.stopControls();
	    	   enemy2.stopControls();
	    	   
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
	    		   player = (Player)loadGame.readObject();
	    		   

	    		   score.setText(("Score: " + Integer.toString(player.getPoints())));
	    		   
	    		   // add to player
	    		   ImageView imageView = new ImageView("images//pacmanRight.gif");
	    		   player.setImageView(imageView, m.getWidth());
	    		   player.setScene(scene);
	    		   
	    		   player.setLabels(score, gameStatus);
	    		   player.setMap(m);
	    		   System.out.println("***" + player.getR() + "*" + player.getC());
	    		   //enemy1.setMap(m);
	    		   
	    		   Enemy enemy1 = new Enemy(m, "images//ghost2.gif", '1');
	    		   Enemy enemy2 = new Enemy(m, "images//redghost.gif", '2');
	    		   enemy1.setCharacters(player, enemy2);
	    		   //enemy2.setMap(m);
	    		   enemy2.setCharacters(player, enemy1);
	    		   player.setEnemies(enemy1, enemy2);
	    		   
	    		   m.setCharacters(player, enemy1, enemy2);
	    		   m.setGridPane(map);
	    		   map.getChildren().clear();
	    		   
	    		   m.fillGridPane();
	    		   
	    		   player.controls();
	    		   enemy1.controls();
	    		   enemy2.controls();
	    		   
	    		   System.out.println("Game Loaded");
	    		   
	    			for(int i = 0; i < m.getRows(); i++) {
	    				for(int j = 0; j < m.getCols(); j++) {
	    					System.out.print(m.getGrid()[i][j]);
	    				}
	    				
	    				System.out.println();
	    			} 
	    		   
	    		   
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
	}
}
