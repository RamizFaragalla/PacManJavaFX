import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Player extends Character {
	private int points;
	private boolean stop;
	private transient Label score;
	private transient Label gameStatus;
	private Enemy enemy1, enemy2, enemy3, enemy4;
	private transient Scene scene;
	private char dir = 'R';
	private PlayerTimer timer = new PlayerTimer();
	
	public Player(Map m) {
		super(m, "images//pacmanRight.gif", 0, 0);
		setPoints(0);
		stop = false;
	}
	
	public void setEnemies(Enemy one, Enemy two, Enemy three, Enemy four) {
		enemy1 = one;
		enemy2 = two;
		enemy3 = three;
		enemy4 = four;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	/**
	 * @return the points
	 */
	public int getPoints() {
		return (int) points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void setLabels(Label score, Label gameStatus) { //Set score and gamestatus
		this.score = score;
		this.gameStatus = gameStatus;
	}
	
	public Label getGameStatus() {
		return gameStatus;
	}
	
	public void stopControls() {
		stop = true;
		timer.stop();
	}
	
	
	public void won() {
		gameStatus.setText("YOU WON");
		gameStatus.setTextFill(Color.GREEN);
	}
	
	public void stillPlaying() {
		gameStatus.setText("");
	}
	
	private class PlayerTimer extends AnimationTimer implements Serializable{
		char grid[][] = getMap().getGrid();
		private long prevTime = 0;
		
		public void handle(long now) {
			long dt = now - prevTime;
			
			if(dt > 0.1e9) {
				String orientation;
				int r = getR();
				int c = getC();
				char direction = dir;
				
				prevTime = now;
				// up
				if(direction == 'U') {
					orientation = "pacmanUp.gif";
	                if(grid[r-1][c]=='S')
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
						stop = true;
						gameStatus.setText("GAME OVER");
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
						stop = true;
						gameStatus.setText("GAME OVER");
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
						stop = true;
						gameStatus.setText("GAME OVER");
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
						stop = true;
						gameStatus.setText("GAME OVER");
					}
					
				}
				score.setText(("Score: " + Integer.toString(getPoints())));
			}
		}
	}
	
	public void controls() {
		
		stop = false;

		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			timer.start();
			//findPlayer();
			int r = getR();
			int c = getC();	
			System.out.println("****Player: " + r + " " + c);
			
			if(stop) {
				key.consume(); // so key won't be passed to other event listeners.
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
