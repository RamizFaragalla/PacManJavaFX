import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;

@SuppressWarnings("serial")
public class Map implements Serializable{
	private final double CELL_WIDTH = 20.0;
	private final int ROWS = 21;
	private final int COLS = 19;
	private char[][] grid;

	//private ImageView[][] cells;
	
	public Map() {
		grid = new char[ROWS][COLS];
		//cells = new ImageView[ROWS][COLS];
		fillGrid();
		//fillCells();
	}
	
	public double getWidth() {
		return CELL_WIDTH;
	}
	
	public int getRows() {
		return ROWS;
	}
	
	public int getCols() {
		return COLS;
	}
	
	public char[][] getGrid() {
		return grid;
	}
	
	private void fillGrid() {
		char c;
		Scanner input;
		try {
			input = new Scanner(new File("Pacman/level.txt"));
			for(int i = 0; i < ROWS; i++) {
				for(int j = 0; j < COLS; j++) {
					c = input.next().charAt(0);
					grid[i][j] = c;
					System.out.print(grid[i][j]);
				}
				
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
