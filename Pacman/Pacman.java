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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Pacman extends Application {
	private Map m = new Map();	
	private Player player = new Player(m);
	private GridPane map = m.getGridPane();
	
	private Enemy enemy1 = new Enemy(m, "images//blue.png", '1');
	private Enemy enemy2 = new Enemy(m, "images//pinky.png", '2');
	private Enemy enemy3 = new Enemy(m, "images//ghost2.gif", '3');
	private Enemy enemy4 = new Enemy(m, "images//redghost.gif", '4');
	
	private VBox vBox;	// vBox stores a menu, an HBOX, and the GridPane
	private HBox hBox;	// stores the labels
	private BorderPane borderPane;	// the menu
	private Scene scene;
	private Label score = new Label("Score: " + Integer.toString(player.getPoints()));
	private Label gameStatus = new Label("");
	
	public static void main(String[] args) {
	      // Launch the application.
	      launch(args);
	}

	/**
	 * method starts the GUI
	 * @param primaryStage a Stage object
	 * @return void
	 */
	public void start(Stage primaryStage) {
		// set the characters and map of all the objects
		player.setLabels(score, gameStatus);
		enemy1.setCharacters(player, enemy2, enemy3, enemy4);
		enemy2.setCharacters(player, enemy1, enemy3, enemy4);
		enemy3.setCharacters(player, enemy1, enemy2, enemy4);
		enemy4.setCharacters(player, enemy1, enemy2, enemy3);
	
		player.setEnemies(enemy1, enemy2, enemy3, enemy4);
		
		m.setCharacters(player, enemy1, enemy2, enemy3, enemy4);
		m.fillGridPane();
		
		fileSystem(primaryStage);	// load / save game menu options
		
		hBox = new HBox(score, gameStatus);	// labels in hBox
		vBox = new VBox(borderPane, hBox, map);	// menu, hBox, GridPane in vBox
		vBox.setBackground(Background.EMPTY);
		score.setTextFill(Color.web("#ffffff"));
		scene = new Scene(vBox, Color.BLACK);
		
		player.setScene(scene);
		
		// start controls of all the characters
		player.controls();
		enemy1.controls();
		enemy2.controls();
		enemy3.controls();
		enemy4.controls();
		
		primaryStage.setScene(scene);
		
		// Set the stage title.
		primaryStage.setTitle("Pacman");
	 
		// Show the window.
		primaryStage.show();
	}

	/**
	 * load / save functionality 
	 * @param primaryStage a Stage
	 * @return void
	 */
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

		
		// if save is pressed
	    save.setOnAction(event ->
	    {
	    	try {
	    		
	    		// stop the controls of all the characters
	    		player.stopControls();
		    	enemy1.stopControls();
		    	enemy2.stopControls();
		    	enemy3.stopControls();
		    	enemy4.stopControls();
		    	   
	    		FileChooser fileChooser = new FileChooser();
	    		fileChooser.setTitle("Save Game!");
	    		fileChooser.setInitialDirectory(new File("save"));
	    		File file = fileChooser.showSaveDialog(primaryStage);
	    		String fileName = "";
	    		if(file != null) {
	    			fileName = file.getAbsolutePath();
	    		}
	    		else {
	    			// if file wasn't chosen resume controls
	    			player.controls();
    	   		   	enemy1.controls();
	    		   	enemy2.controls();
	    		   	enemy3.controls();
	    		   	enemy4.controls();
	    			return;
	    		}
	    		
	    		ObjectOutputStream saveGame = new ObjectOutputStream(new FileOutputStream(fileName));
				
	    		// write the objects to the file
	    		saveGame.writeObject(m); // save map
	    		saveGame.writeObject(player); // save the player info
	    	    
	    		System.out.println("Game Saved");
	    	    saveGame.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    	// resume controls after saving the objects to the file
   		    player.controls();
		    enemy1.controls();
		    enemy2.controls();
		    enemy3.controls();
	    	enemy4.controls();
	
	    });
	    
	    // if btnNewGame is pressed, restart the application to start a new game
		btnNewGame.setOnAction( e -> {
			System.out.println( "Restarting app!" );
			primaryStage.close();
			Platform.runLater(() -> new Pacman().start(new Stage()));
        });
	    
		// if load button is pressed
	    load.setOnAction(event ->
	    {
	       try {
	    	   // stop the controls 
	    	   player.stopControls();
	    	   enemy1.stopControls();
	    	   enemy2.stopControls();
	    	   enemy3.stopControls();
	    	   enemy4.stopControls();
	    	   
	    	   FileChooser fileChooser = new FileChooser();
	    	   fileChooser.setTitle("Load Game!");
	    	   fileChooser.setInitialDirectory(new File("save"));
	    	   File selectedFile = fileChooser.showOpenDialog(primaryStage);
	    	   
	    	   String fileName = "";
	    	   if(selectedFile != null) {
	    		   fileName = selectedFile.getPath();
	    	   }
	    	   else {
	    		   // if file wasn't chosen resume controls
	    		   player.controls();
	    		   enemy1.controls();
	    		   enemy2.controls();
	    		   enemy3.controls();
	    		   enemy4.controls();
	    		   return; 
	    	   }
	    	   
	    	   ObjectInputStream loadGame = new ObjectInputStream(new FileInputStream(fileName));
	    	   
	    	   try {
	    		   // read the objects back from the file
	    		   m = (Map)loadGame.readObject();
	    		   player = (Player)loadGame.readObject();

	    		   score.setText(("Score: " + Integer.toString(player.getPoints())));
	    		   
	    		   // add to player
	    		   ImageView imageView = new ImageView("images//pacmanRight.gif");
	    		   player.setImageView(imageView, m.getWidth());
	    		   player.setScene(scene);
	    		   
	    		   player.setMap(m);
	    		   System.out.println("***" + player.getR() + "*" + player.getC());
	    		   
	    		   Enemy enemy1 = new Enemy(m, "images//blue.png", '1');
	    		   Enemy enemy2 = new Enemy(m, "images//pinky.png", '2');
	    		   Enemy enemy3 = new Enemy(m, "images//ghost2.gif", '3');
	    		   Enemy enemy4 = new Enemy(m, "images//redghost.gif", '4');
	    		   enemy1.setCharacters(player, enemy2, enemy3, enemy4);
	    		   enemy2.setCharacters(player, enemy1, enemy3, enemy4);
	    		   enemy3.setCharacters(player, enemy1, enemy2, enemy4);
	    		   enemy4.setCharacters(player, enemy1, enemy2, enemy3);
	    		   player.setEnemies(enemy1, enemy2, enemy3, enemy4);
	    		   
	    		   m.setCharacters(player, enemy1, enemy2, enemy3, enemy4);
	    		   m.setGridPane(map);
	    		   map.getChildren().clear();
	    		   
	    		   m.fillGridPane();
	    		   
	    		   player.controls();
	    		   enemy1.controls();
	    		   enemy2.controls();
	    		   enemy3.controls();
	    		   enemy4.controls();
	    		   
	    		   // if game was originally over, set label back to ""
	    		   if(gameStatus.getText().equals(" GAME OVER") || gameStatus.getText().equals(" YOU WON"))
	    			   gameStatus.setText("");
	    		   
	    		   player.setLabels(score, gameStatus);
	    		   enemy1.setGameStatus(gameStatus);
	    		   enemy2.setGameStatus(gameStatus);
	    		   enemy3.setGameStatus(gameStatus);
	    		   enemy4.setGameStatus(gameStatus);
	    		   System.out.println("Game Loaded");
	    		   
	    		   
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
