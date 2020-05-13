import java.io.Serializable;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

@SuppressWarnings("serial")
public class Enemy extends Character {
	
	private char id;			// enemy name (1, 2, etc)
	private EnemyTimer timer;	// timer object to allow for enemy movement 
	private Player player;		// Pacman
	private Enemy otherEnemy1;	// other enemies on the map
	private Enemy otherEnemy2;
	private Enemy otherEnemy3;
	private transient Label gameStatus;	
	
	public Enemy(Map m, String path, char id) {
		super(m, path, 0, 0);
		this.setID(id);
		timer = new EnemyTimer();
	}
	
	/**
	 * mutator to set all the characters on the map
	 * @param player a Player object
	 * @param otherEnemy1 an Enemy object
	 * @param otherEnemy2 an Enemy object
	 * @param otherEnemy3 an Enemy object
	 * @retun void
	 */
	public void setCharacters(Player player, Enemy otherEnemy1, Enemy otherEnemy2, Enemy otherEnemy3) {
		this.player = player;
		this.otherEnemy1 = otherEnemy1;
		this.otherEnemy2 = otherEnemy2;
		this.otherEnemy3 = otherEnemy3;
		gameStatus = player.getGameStatus();
	}
	
	/**
	 * accessor method to change the gameStatus label
	 * @param g Label object
	 * @return void
	 */
	public void setGameStatus(Label g) {
		gameStatus = g;
	}

	/**
	 * accessor method to get the enemy ID (name)
	 * @param none
	 * @return id an int
	 */
	public char getID() {
		return id;
	}

	/**
	 * mutator to change the enemy ID
	 * @param id an int
	 * @return void
	 */
	public void setID(char id) {
		this.id = id;
	}
	
	/**
	 * method starts the enemy timer
	 * @param none
	 * @return void
	 */
	public void controls() {
		timer.start();
	}
	
	/**
	 * method stops the timer
	 * @param none
	 * @return void
	 */
	public void stopControls() {
		timer.stop();
	}
	
	/**
	 * EnemyTimer inner class
	 * responsible for the enemy's movement 
	 * @author Ramiz
	 */
	private class EnemyTimer extends AnimationTimer implements Serializable{
		private char grid[][] = getMap().getGrid();	// 2D char array
		private long prevTime = 0;
		private char currPos = 'E';
		
		/**
		 * method is being called continuously 
		 * @param now a long, current time
		 * @return void
		 */
		public void handle(long now) {
			long dt = now - prevTime;
			
			if(dt > 0.3e9) {	// only runs every 0.3 of a second
				int r = getR();	// enemy location
				int c = getC();
				char direction = randomValidDirection();	// random valid direction that the enemy will move in
				
				prevTime = now;
				// up
				if(direction == 'U') {
					grid[r][c] = currPos;
					currPos = grid[r-1][c];
					grid[r-1][c] = id;
					setR(r-1);
					getMap().update(r, c, grid[r][c], r-1, c, grid[r-1][c], "");
				}
				
				// down
				else if(direction == 'D') {
					grid[r][c] = currPos;
					currPos = grid[r+1][c];
					grid[r+1][c] = id;
					setR(r+1);
					getMap().update(r, c, grid[r][c], r+1, c, grid[r+1][c], "");
				}
				
				// left
				else if(direction == 'L') {
					grid[r][c] = currPos;
					currPos = grid[r][c-1];
					grid[r][c-1] = id;
					setC(c-1);
					getMap().update(r, c, grid[r][c], r, c-1, grid[r][c-1], "");
				}
				
				// right
				else if(direction == 'R'){
					grid[r][c] = currPos;
					currPos = grid[r][c+1];
					grid[r][c+1] = id;
					setC(c+1);
					getMap().update(r, c, grid[r][c], r, c+1, grid[r][c+1], "");
				}
			}
		}
	}
	
	/**
	 * method chooses a random valid direction for the enemy to move in
	 * a valid direction is a direction without a wall or another enemy
	 * @param none
	 * @return direction a char
	 */
	private char randomValidDirection() {
		int r = getR();	// position of enemy
		int c = getC();
		char direction = 'z';
		int randomNum;	// [1, 4], up, down, left, right
		
		// W, 1, 2, P
		char grid[][] = getMap().getGrid();	// 2D char array (map)

		while(true) {
			randomNum = (int)(Math.random()*4) + 1;	// pick a random number

			if(randomNum == 1) {	//up
				if(grid[r-1][c] == 'S' || grid[r-1][c] == 'B' || grid[r-1][c] == 'E') {
					direction = 'U';
					break;
				}
				
				//else if(grid[][] == 'P') stop timers stop keyboard even
				else if(grid[r-1][c] == 'P') {	// collision
					// stop all the controls, Game Over
					stopControls();
					otherEnemy1.stopControls();
					otherEnemy2.stopControls();
					otherEnemy3.stopControls();
					player.stopControls();
					gameStatus.setText(" GAME OVER");
					gameStatus.setTextFill(Color.RED);
					break;
				}
				
				else continue;
			}
			
			else if(randomNum == 2) { // down
				if(grid[r+1][c] == 'S' || grid[r+1][c] == 'B' || grid[r+1][c] == 'E') {
					direction = 'D';
					break;
				}
				
				else if(grid[r+1][c] == 'P') {	// collision
					// stop controls, Game Over
					stopControls();
					otherEnemy1.stopControls();
					otherEnemy2.stopControls();
					otherEnemy3.stopControls();
					player.stopControls();
					gameStatus.setText(" GAME OVER");
					gameStatus.setTextFill(Color.RED);
					break;
				}
				else continue;
			}
			
			else if(randomNum == 3) { // left
				if(grid[r][c-1] == 'S' || grid[r][c-1] == 'B' || grid[r][c-1] == 'E') {
					direction = 'L';
					break;
				}
				
				else if(grid[r][c-1] == 'P') {	// collision
					// stop controls, Game Over
					stopControls();
					otherEnemy1.stopControls();
					otherEnemy2.stopControls();
					otherEnemy3.stopControls();
					player.stopControls();
					gameStatus.setText("GAME OVER");
					gameStatus.setTextFill(Color.RED);
					break;
				}
				
				else continue;
			}
			
			else { // right
				if(grid[r][c+1] == 'S' || grid[r][c+1] == 'B' || grid[r][c+1] == 'E') {
					direction = 'R';
					break;
				}
				
				else if(grid[r][c+1] == 'P') {	// collision
					// stop controls, Game Over
					stopControls();
					otherEnemy1.stopControls();
					otherEnemy2.stopControls();
					otherEnemy3.stopControls();
					player.stopControls();
					gameStatus.setText(" GAME OVER");
					gameStatus.setTextFill(Color.RED);
					break;
				}
				
				else continue;
			}
		}
		
		return direction;
	}

}
