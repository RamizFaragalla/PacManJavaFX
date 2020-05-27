import java.io.Serializable;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

@SuppressWarnings("serial")
public class Player extends Character {
	private int points;						// score counter
	private boolean stop;					// stops the key events
	private transient Label score;			// GUI label for displaying the score
	private transient Label gameStatus;		// GUI label for displaying Game Over / Game Won
	private Enemy enemy1, enemy2, enemy3, enemy4;	// the enemies in the game
	private transient Scene scene;			// used for the key events
	private char dir;						// dir Pacman is moving in
	private PlayerTimer timer;				// PlayerTimer object for continuous movement 
	private boolean isEasy = true;			// current difficulty, starts as easy
	
	public Player(Map m) {
		super(m, "images//pacmanRight.gif", 0, 0);
		setPoints(0);
		stop = false;
		dir = 'D';		// initial direction of Pacman is down
		timer = new PlayerTimer();
	}
	
	/**
	 * mutator to set all the enemies on the map
	 * @param one Enemy object
	 * @param two Enemy object
	 * @param three Enemy object
	 * @param four Enemy object
	 * @return void
	 */
	public void setEnemies(Enemy one, Enemy two, Enemy three, Enemy four) {
		enemy1 = one;
		enemy2 = two;
		enemy3 = three;
		enemy4 = four;
	}
	
	/**
	 * mutator to get the Scene that's being used
	 * @param scene a Scene object
	 * @return void
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	/**
	 * accessor to get the score so far
	 * @param none
	 * @return points an int
	 */
	public int getPoints() {
		return (int) points;
	}
	
	/**
	 * mutator for changing the score
	 * @param points an int
	 * @return void
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * mutator for changing the two labels (score and gameStatus)
	 * @param score a Label object
	 * @param gameStatus a Label object
	 * @return void
	 */
	public void setLabels(Label score, Label gameStatus) {
		this.score = score;
		this.gameStatus = gameStatus;
	}
	
	/**
	 * accessor to get the gameStatus
	 * @param none
	 * @return gameStatus a Label object
	 */
	public Label getGameStatus() {
		return gameStatus;
	}
	
	/**
	 * method stops Pacman from moving
	 * @param none
	 * @return void
	 */
	public void stopControls() {
		stop = true;	// stops key events
		timer.stop();	// stops the timer
	}
	
	// this method makes the difficulty easy
	public void easy() {
		isEasy = true;
	}
	
	// this method makes the difficulty hard
	public void hard() {
		isEasy = false;
	}
	

	// returns the current difficulty of the game
	public Boolean isEasy() {
		return isEasy;
	}
	
	/**
	 * method changes the gameStatus label to "YOU WON"
	 * @param none
	 * @return void
	 */
	public void won() {
		gameStatus.setText(" YOU WON");
		gameStatus.setTextFill(Color.GREEN);
	}
	
	/**
	 * an inner class that extends AnimationTimer
	 * this class is responsible for making Pacman move forward by itself until it hits a wall 
	 * @author Ramiz
	 */
	private class PlayerTimer extends AnimationTimer implements Serializable{
		private char grid[][] = getMap().getGrid();	// 2D char array (map)
		private long prevTime = 0;
		
		/**
		 * method is executed every millisecond, I think!
		 * @param now long, the current time
		 * @return void
		 */
		public void handle(long now) {
			long dt = now - prevTime;
			
			if(dt > 0.1e9) {		// is executed only every 0.1 of a second
				String orientation;	// orientation of Pacman (image path)
				int r = getR();		// row position of player
				int c = getC();		// col position of player
				char direction = dir;	// direction from key board event
				
				prevTime = now;
				// up
				if(direction == 'U') {			// if direction is UP
					orientation = "pacmanUp.gif";
	                if(grid[r-1][c]=='S')		// if there is a small coin to the right of Pacman
	                { //if it is a small dot
	                    grid[r][c]='E';
	                    grid[r-1][c]='P';
	                    setR(r-1);
	                    setPoints(getPoints() + 5);
	                    getMap().update(r, c, grid[r][c], r-1, c, grid[r-1][c], orientation);
	                }
	                
					else if(grid[r-1][c] == 'B') { // if big dot
	                    grid[r][c]='E';
	                    grid[r-1][c]='P';
	                    setR(r-1);
	                    setPoints(getPoints() + 10);
	                    getMap().update(r, c, grid[r][c], r-1, c, grid[r-1][c], orientation);
					}
					
					else if(grid[r-1][c] == 'E') { // if empty space
	                    grid[r][c]='E';
	                    grid[r-1][c]='P';
	                    setR(r-1);
	                    getMap().update(r, c, grid[r][c], r-1, c, grid[r-1][c], orientation);
					}
	                
					else if(grid[r-1][c] > '0' && grid[r-1][c] < '9') {
						// enemy ahead
						enemy1.stopControls();
						enemy2.stopControls();
						enemy3.stopControls();
						enemy4.stopControls();
						stopControls();
						gameStatus.setText(" GAME OVER");
						gameStatus.setTextFill(Color.RED);
					}
				}
				
				// down
				else if(direction == 'D') {
					orientation = "pacmanDown.gif";
	                if(grid[r+1][c]=='S')
	                { //if it is a small dot
	                    grid[r][c]='E';
	                    grid[r+1][c]='P';
	                    setR(r+1);
	                    setPoints(getPoints() + 5);
	                    getMap().update(r, c, grid[r][c], r+1, c, grid[r+1][c], orientation);
	                }
	                
					else if(grid[r+1][c] == 'B') { // if big dot
	                    grid[r][c]='E';
	                    grid[r+1][c]='P';
	                    setR(r+1);
	                    setPoints(getPoints() + 10);
	                    getMap().update(r, c, grid[r][c], r+1, c, grid[r+1][c], orientation);
					}
					
					else if(grid[r+1][c] == 'E') { // if empty space
	                    grid[r][c]='E';
	                    grid[r+1][c]='P';
	                    setR(r+1);
	                    getMap().update(r, c, grid[r][c], r+1, c, grid[r+1][c], orientation);
					}
	                
					else if(grid[r+1][c] > '0' && grid[r+1][c] < '9') {
						// enemy ahead
						enemy1.stopControls();
						enemy2.stopControls();
						enemy3.stopControls();
						enemy4.stopControls();
						stopControls();
						gameStatus.setText(" GAME OVER");
						gameStatus.setTextFill(Color.RED);
					}
				}
				
				// left
				else if(direction == 'L') {
					orientation = "pacmanLeft.gif";
	                if(grid[r][c-1]=='S')
	                { //if it is a small dot
	                    grid[r][c]='E';
	                    grid[r][c-1]='P';
	                    setC(c-1);
	                    setPoints(getPoints() + 5);
	                    getMap().update(r, c, grid[r][c], r, c-1, grid[r][c-1], orientation);
	                }
	                
					else if(grid[r][c-1] == 'B') { // if big dot
						grid[r][c] = 'E';
						grid[r][c-1] = 'P';
						setC(c-1);
						setPoints(getPoints() + 10);
						getMap().update(r, c, grid[r][c], r, c-1, grid[r][c-1], orientation);
					}
					
					else if(grid[r][c-1] == 'E') { // if empty space
						grid[r][c] = 'E';
						grid[r][c-1] = 'P';
						setC(c-1);
						getMap().update(r, c, grid[r][c], r, c-1, grid[r][c-1], orientation);
					}
	                
					else if(grid[r][c-1] > '0' && grid[r][c-1] < '9') {
						// enemy ahead
						enemy1.stopControls();
						enemy2.stopControls();
						enemy3.stopControls();
						enemy4.stopControls();
						stopControls();
						gameStatus.setText(" GAME OVER");
						gameStatus.setTextFill(Color.RED);
					}
				}
				
				// right
				else if(direction == 'R'){
					orientation = "pacmanRight.gif";
					
					if(grid[r][c+1] == 'S') { // if small dot
						grid[r][c] = 'E';
						grid[r][c+1] = 'P';
						setC(c+1);
						setPoints(getPoints() + 5);
						getMap().update(r, c, grid[r][c], r, c+1, grid[r][c+1], orientation);
					}
					
					else if(grid[r][c+1] == 'B') { // if big dot
						grid[r][c] = 'E';
						grid[r][c+1] = 'P';
						setC(c+1);
						setPoints(getPoints() + 10);
						getMap().update(r, c, grid[r][c], r, c+1, grid[r][c+1], orientation);
					}
					
					else if(grid[r][c+1] == 'E') { // if empty space
						grid[r][c] = 'E';
						grid[r][c+1] = 'P';
						setC(c+1);
						getMap().update(r, c, grid[r][c], r, c+1, grid[r][c+1], orientation);
					}
					
					else if(grid[r][c+1] > '0' && grid[r][c+1] < '9') {
						// enemy ahead
						enemy1.stopControls();
						enemy2.stopControls();
						enemy3.stopControls();
						enemy4.stopControls();
						stopControls();
						gameStatus.setText(" GAME OVER");
						gameStatus.setTextFill(Color.RED);
					}
					
				}
				score.setText(("Score: " + Integer.toString(getPoints())));	// update score label
			}
		}
	}
	
	/**
	 * key event from the user
	 * @param none
	 * @return void
	 */
	public void controls() {
		timer.start();	// start the timer
		stop = false;

		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			
			if(stop) {	// if true then key event is stopped 
				key.consume();
				return;
			}
			
			if(key.getCode() == KeyCode.RIGHT) {
				dir = 'R';
				
				System.out.println("Right Key Pressed");
				System.out.println("Points so far: " + getPoints());
			}
			
			else if(key.getCode()==KeyCode.LEFT) {
				dir = 'L';
				
				System.out.println("Left Key Pressed");
				System.out.println("Points so far: " + getPoints());
			}
			
			else if(key.getCode()==KeyCode.DOWN) {
				dir = 'D';
				
				System.out.println("Down Key Pressed");
				System.out.println("Points so far: " + getPoints());
			}
			
			else if(key.getCode()==KeyCode.UP) {
				dir = 'U';
                	
				System.out.println("Up Key Pressed");
				System.out.println("Points so far: " + getPoints());
			}
			
		});
	}
}
