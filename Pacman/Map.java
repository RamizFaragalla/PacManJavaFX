import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

@SuppressWarnings("serial")
public class Map implements Serializable {
	private final double CELL_WIDTH = 20.0;		// width and height of the ImageView
	private final int ROWS = 13;	// 21 13	// row count of 2D array and GridPane
	private final int COLS = 25;	// 19 25	// col count of 2D array and GridPane
	private char[][] grid;						// 2D char array
	private transient GridPane map;				// GridPane of ImageViews
	private Player player;						// Pacman
	private Enemy enemy1, enemy2, enemy3, enemy4;	// 4 enemies 
	private Item item;							// items like wall and coins 
	
	public Map() {
		grid = new char[ROWS][COLS];
		map = new GridPane();
		//cells = new ImageView[ROWS][COLS];
		fillGrid();
		//fillCells();
	}
	
	/**
	 * method sets the characters of the map
	 * @param player a Player object, Pacman
	 * @param enemy1 an Enemy object 
	 * @param enemy2 an Enemy object
	 * @param enemy3 an Enemy object
	 * @param enemy4 an Enemy object
	 * @return void
	 */
	public void setCharacters(Player player, Enemy enemy1, Enemy enemy2, Enemy enemy3, Enemy enemy4) {
		this.player = player;
		this.enemy1 = enemy1;
		this.enemy2 = enemy2;
		this.enemy3 = enemy3;
		this.enemy4 = enemy4;
	}
	
	/**
	 * mutator method for changing the GridPane
	 * @param g a GridPane object
	 * @return void
	 */
	public void setGridPane(GridPane g) {
		map = g;
		
	}
	
	/**
	 * method fills the GridPane with ImageViews from the 2d char array
	 * @param none
	 * @return void
	 */
	public void fillGridPane() {
		for(int i = 0; i < ROWS; i++) {	// outer loop for the rows
			for(int j = 0; j < COLS; j++) {	// inner loop for the cols

				if(grid[i][j] == 'W') {						// if cell has a wall 
					item = new Wall(CELL_WIDTH);			// create a wall object
                    map.add(item.getImageView(), j+1, i+1);	// add its ImageView to GridPane
				}
				
				else if(grid[i][j] == 'S') {							// if cell has a small coin
					item = new Coin("images//coin.png", CELL_WIDTH);	// create a coin object
                    map.add(item.getImageView(), j+1, i+1);				// add its ImageView to GridPane
				}
				
				else if(grid[i][j] == 'B') {							// if cell has a big coin
					item = new Coin("images//bigcoin.png", CELL_WIDTH);	// create a coin object 
                    map.add(item.getImageView(), j+1, i+1);				// add its ImageView to GridPane
				}
				
				else if(grid[i][j] == '1') {					// if cell has enemy 1
					enemy1.setR(i);								// save the position of enemy1
					enemy1.setC(j);
                    map.add(enemy1.getImageView(), j+1, i+1);	// add enemy1's ImageView to the GridPane
				}
				
				else if(grid[i][j] == '2') {					// same with enemy2
					enemy2.setR(i);
					enemy2.setC(j);
                    map.add(enemy2.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == '3') {					// same with enemy3
					enemy3.setR(i);
					enemy3.setC(j);
                    map.add(enemy3.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == '4') {					// same with enemy4
					enemy4.setR(i);	
					enemy4.setC(j);
                    map.add(enemy4.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == 'P') {									// if cell has a Pacman
					player.setR(i);												// save the position of Pacman
					player.setC(j);	
					System.out.println(player.getR() + " " + player.getC());
                    map.add(player.getImageView(), j+1, i+1);					// add it's ImageView to GridPane
				}
				
				else {												// if cell has a blank space
					item = new Empty(CELL_WIDTH);					// create an empty object
                    map.add(item.getImageView(), j+1, i+1);			// add its ImageView to GridPane
				}
			}
		}
	}
	
	/**
	 * accessor method to retrieve the GridPane
	 * @param none
	 * @return map a GridPane object
	 */
	public GridPane getGridPane() {
		return map;
	}
	
	/**
	 * accessor method to get the width, which is equal to height of ImageView
	 * @param none
	 * @return CELL_WIDTH a double 
	 */
	public double getWidth() {
		return CELL_WIDTH;
	}
	
	/**
	 * accessor to get the total number of rows in the map
	 * @param none
	 * @return ROWS an int
	 */
	public int getRows() {
		return ROWS;
	}
	
	/**
	 * accessor to get the total number of cols in the map
	 * @param none
	 * @return COLS an int
	 */
	public int getCols() {
		return COLS;
	}
	
	/**
	 * accessor to get the 2D char array
	 * @param none
	 * @return grid a char[][]
	 */
	public char[][] getGrid() {
		return grid;
	}
	
	/**
	 * method fills the 2D char array from a txt file 
	 * @param none
	 * @return void
	 */
	private void fillGrid() {
		char c;
		Scanner input;
		try {
			input = new Scanner(new File("level2.txt"));
			for(int i = 0; i < ROWS; i++) {
				for(int j = 0; j < COLS; j++) {
					c = input.next().charAt(0);		// get the character at position (i, j)
					grid[i][j] = c;					// store the character in the array
					System.out.print(grid[i][j]);
				}
				
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method checks if the player has won the game
	 * if there are coins still present on the map, then return false
	 * if there are no more coins, then return true (the player won)
	 * @param none
	 * @return boolean did the player win?
	 */
	public boolean won() {
		for(int i = 0; i < getRows(); i++) {
			for(int j = 0; j < getCols(); j++) {
				if(grid[i][j] == 'S' || grid[i][j] == 'B')	// if there is a coin in position (i, j)
					return false;							// player still didn't win
			}
		}
		return true;		// player won
	}
	
	/**
	 * method updates ONLY two cells of the GridPane
	 * @param r1 row position of first cell
	 * @param c1 col position of first cell
	 * @param a character symbol (wall, coin, etc) in first cell
	 * @param r2 row position of second cell
	 * @param c2 col position of second cell
	 * @param b a character symbol in second cell
	 * @param orientation which direction is Pacman facing?
	 * @return void
	 */
	public void update(int r1, int c1, char a, int r2, int c2, char b, String orientation) {
		updateHelper(r1, c1, a, orientation);	// updateHelper updates only one cell of GridPane
		updateHelper(r2, c2, b, orientation);
		if(won()) {	// if the player won
			enemy1.stopControls();	// stop all the controls
			enemy2.stopControls();
			enemy3.stopControls();
			enemy4.stopControls();
			player.stopControls();
			
			player.won();	// call Player's won() function
		}
		
	}
	
	/**
	 * method updates only once cell of the GridPane
	 * @param i	 row position of the cell
	 * @param j	 col position of the cell
	 * @param x character symbol (wall, coin, etc)
	 * @param orientation which direction is Pacman facing?
	 * @return void
	 */
	@SuppressWarnings("static-access")
	private void updateHelper(int i, int j, int x, String orientation) {

		for (Node node : map.getChildren()) {	// delete the ImageView at position (j+1, i+1) in GridPane
		    if (map.getColumnIndex(node) == (j+1) && map.getRowIndex(node) == (i+1)) {
		        map.getChildren().remove(node);
		        break;
		    }
		}
		
		if(x == 'S') {	// if small coin, add a small coin ImageView at the position
			item = new Coin("images//coin.png", CELL_WIDTH);
            map.add(item.getImageView(), j+1, i+1);
		}
		
		else if(x == 'B') {		// if big coin, add a big coin ImageView at the position
			item = new Coin("images//bigcoin.png", CELL_WIDTH);
            map.add(item.getImageView(), j+1, i+1);
		}
		
		else if(x == '1') {		// if enemy1, add an enemy1 ImageView at the position
			enemy1.setR(i);
			enemy1.setC(j);
            map.add(enemy1.getImageView(), j+1, i+1);
		}
		
		else if(x == '2') {		// enemy2
			enemy2.setR(i);
			enemy2.setC(j);
            map.add(enemy2.getImageView(), j+1, i+1);
		}
		
		else if(x == '3') {		// enemy3
			enemy3.setR(i);
			enemy3.setC(j);
            map.add(enemy3.getImageView(), j+1, i+1);
		}
		
		else if(x == '4') {		// enemy4
			enemy4.setR(i);
			enemy4.setC(j);
            map.add(enemy4.getImageView(), j+1, i+1);
		}
		
		else if(x == 'P'){		// if Pacman, add a Pacman ImageView at the position
			Image image = new Image("images/" + orientation);
			player.setImageView(new ImageView(image), CELL_WIDTH);
			player.setR(i);
			player.setC(j);
			System.out.println(player.getR() + " " + player.getC());
            map.add(player.getImageView(), j+1, i+1);
		}
		
		else {					// if blank space, add a blank space ImageView at the position
			item = new Empty(CELL_WIDTH);
            map.add(item.getImageView(), j+1, i+1);
		}
		
	}
	
}
