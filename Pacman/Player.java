import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

@SuppressWarnings("serial")
public class Player extends Character {
	private int points;
	private boolean stop;
	private transient Label score;
	private transient Label gameStatus;
	private Enemy enemy1, enemy2;
	private transient Scene scene;
	
	public Player(Map m) {
		super(m, "images//pacmanRight.gif", 0, 0);
		setPoints(0);
		stop = false;
	}
	
	public void setEnemies(Enemy one, Enemy two) {
		enemy1 = one;
		enemy2 = two;
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
	
	public void setLabels(Label score, Label gameStatus) {
		this.score = score;
		this.gameStatus = gameStatus;
	}
	
	public Label getGameStatus() {
		return gameStatus;
	}
	
	public void stopControls() {
		stop = true;
	}
	
	
	public void won() {
		gameStatus.setText("YOU WON");
		gameStatus.setTextFill(Color.GREEN);
	}
	
	public void stillPlaying() {
		gameStatus.setText("");
	}
	
	public void controls() {
		stop = false;
		char grid[][] = getMap().getGrid();

		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			
			//findPlayer();
			int r = getR();
			int c = getC();	
			String orientation;
			System.out.println("****Player: " + r + " " + c);
			
			if(stop) {
				key.consume();
				return;
			}
			
			if(key.getCode() == KeyCode.RIGHT) {
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
					stop = true;
					gameStatus.setText("GAME OVER");
				}
				
				System.out.println("Right Key Pressed");
				System.out.println("Points so far: " + getPoints());
			}
			
			else if(key.getCode()==KeyCode.LEFT) {
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
					stop = true;
					gameStatus.setText("GAME OVER");
				}
				
				System.out.println("Left Key Pressed");
				System.out.println("Points so far: " + getPoints());
			}
			
			else if(key.getCode()==KeyCode.DOWN) {
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
					stop = true;
					gameStatus.setText("GAME OVER");
				}
				
				System.out.println("Down Key Pressed");
				System.out.println("Points so far: " + getPoints());
			}
			
			else if(key.getCode()==KeyCode.UP) {
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
					stop = true;
					gameStatus.setText("GAME OVER");
				}
                	
				System.out.println("Up Key Pressed");
				System.out.println("Points so far: " + getPoints());
			}
			
			score.setText(("Score: " + Integer.toString(getPoints())));
			
		});
	}
}
