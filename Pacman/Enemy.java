import java.io.Serializable;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

@SuppressWarnings("serial")
public class Enemy extends Character {
	
	private char id;
	private EnemyControls timer = new EnemyControls();
	private Player player;
	private Enemy otherEnemy1;
	private Enemy otherEnemy2;
	private Enemy otherEnemy3;
	private transient Label gameStatus;
	
	public Enemy(Map m, String path, char id) {
		super(m, path, 0, 0);
		this.setID(id);
	}
	
	public void setCharacters(Player player, Enemy otherEnemy1, Enemy otherEnemy2, Enemy otherEnemy3) {
		this.player = player;
		this.otherEnemy1 = otherEnemy1;
		this.otherEnemy2 = otherEnemy2;
		this.otherEnemy3 = otherEnemy3;
		gameStatus = player.getGameStatus();
	}
	

	/**
	 * @return the id
	 */
	public char getID() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setID(char id) {
		this.id = id;
	}
	
	public void controls() {
		timer.start();
	}
	
	public void stopControls() {
		timer.stop();
	}
	
	private class EnemyControls extends AnimationTimer implements Serializable{
		char grid[][] = getMap().getGrid();
		private long prevTime = 0;
		private char currPos = 'E';
		
		public void handle(long now) {
			long dt = now - prevTime;
			
			if(dt > 0.3e9) {
				int r = getR();
				int c = getC();
				char direction = randomValidDirection();
				
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
	
	private char randomValidDirection() {
		int r = getR();
		int c = getC();
		char direction = 'z';
		int randomNum;	// [1, 4], up, down, left, right
		
		// W, 1, 2, P
		char grid[][] = getMap().getGrid();

		while(true) {
			randomNum = (int)(Math.random()*4) + 1;

			if(randomNum == 1) {	//up
				if(grid[r-1][c] == 'S' || grid[r-1][c] == 'B' || grid[r-1][c] == 'E') {
					direction = 'U';
					break;
				}
				
				//else if(grid[][] == 'P') stop timers stop keyboard even
				else if(grid[r-1][c] == 'P') {
					stopControls();
					otherEnemy1.stopControls();
					otherEnemy2.stopControls();
					otherEnemy3.stopControls();
					player.stopControls();
					gameStatus.setText("GAME OVER");
					direction = 'z';
					break;
				}
				
				else continue;
			}
			
			else if(randomNum == 2) { // down
				if(grid[r+1][c] == 'S' || grid[r+1][c] == 'B' || grid[r+1][c] == 'E') {
					direction = 'D';
					break;
				}
				
				else if(grid[r+1][c] == 'P') {
					stopControls();
					otherEnemy1.stopControls();
					otherEnemy2.stopControls();
					otherEnemy3.stopControls();
					player.stopControls();
					gameStatus.setText("GAME OVER");
					direction = 'z';
					break;
				}
				else continue;
			}
			
			else if(randomNum == 3) { // left
				if(grid[r][c-1] == 'S' || grid[r][c-1] == 'B' || grid[r][c-1] == 'E') {
					direction = 'L';
					break;
				}
				
				else if(grid[r][c-1] == 'P') {
					stopControls();
					otherEnemy1.stopControls();
					otherEnemy2.stopControls();
					otherEnemy3.stopControls();
					player.stopControls();
					gameStatus.setText("GAME OVER");
					direction = 'z';
					break;
				}
				
				else continue;
			}
			
			else { // right
				if(grid[r][c+1] == 'S' || grid[r][c+1] == 'B' || grid[r][c+1] == 'E') {
					direction = 'R';
					break;
				}
				
				else if(grid[r][c+1] == 'P') {
					stopControls();
					otherEnemy1.stopControls();
					otherEnemy2.stopControls();
					otherEnemy3.stopControls();
					player.stopControls();
					gameStatus.setText("GAME OVER");
					direction = 'z';
					break;
				}
				
				else continue;
			}
		}
		
		return direction;
	}

}
