import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class Pacman extends Application{
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
		 // Set the stage title.
	      primaryStage.setTitle("Pacman");
	      
	      // Show the window.
	      primaryStage.show();
	}
}	