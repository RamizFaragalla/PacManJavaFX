import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Pacman extends Application {
	private Map m = new Map();
	private Player player = new Player(m);
	private GridPane map = m.getGridPane();
	
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
	

//		btnNewGame.setOnAction( e -> {
//        	  System.out.println( "Restarting app!" );
//        	  primaryStage.close();
//        	  Platform.runLater(() -> new Pacman().start(new Stage()));
//        });

		
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
		MenuItem btnNewGame = new MenuItem("Restart Game");

		fileMenu.getItems().add(save);
		fileMenu.getItems().add(load);
		fileMenu.getItems().add(btnNewGame);

		
		// Register an event handler for the exit item.
	    save.setOnAction(event ->
	    {
	    	try {
//	    	    enemy1.stopControls();
//		    	enemy2.stopControls();
	    		FileChooser fileChooser = new FileChooser();
	    		fileChooser.setTitle("Save Game!");
	    		fileChooser.setInitialDirectory(new File("save"));
	    		File file = fileChooser.showSaveDialog(primaryStage);
	    		String fileName = "";
	    		if(file != null) {
	    			fileName = file.getAbsolutePath();
	    		}
	    		else return;
	    		
	    		ObjectOutputStream saveGame = new ObjectOutputStream(new FileOutputStream(fileName));
				
	    		saveGame.writeObject(m); // save map
	    		saveGame.writeObject(player); // save the player info
	    	    
	    		System.out.println("Game Saved");
//	    		enemy1.controls();
//	    		enemy2.controls();
	    	    saveGame.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	   
	
	    });
	    
	    btnNewGame.setOnAction( event ->
        {
        	System.out.println( "Restarting app!" );
            primaryStage.close();
            Platform.runLater( () -> new Pacman().start( new Stage() ) );
            primaryStage.show();
        } );
	    
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
	    	   else return; 
	    	   
	    	   ObjectInputStream loadGame = new ObjectInputStream(new FileInputStream(fileName));
	    	   
	    	   try {
	    		   m = (Map)loadGame.readObject();
	    		   player = (Player)loadGame.readObject();
	    		   

	    		   score.setText(("Score: " + Integer.toString(player.getPoints())));
	    		   m.won();
	    		   
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
	       load.setOnAction(null);
	    });
	    
	    
	
	    // Add the File menu to the menu bar.
	    menuBar.getMenus().addAll(fileMenu);
	    
	    // Add the menu bar to a BorderPane.
	    borderPane = new BorderPane();
	    borderPane.setTop(menuBar);
		
		
		hBox = new HBox(score, gameStatus);
	}
}
