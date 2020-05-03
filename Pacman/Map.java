import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;

public class Map {
	private final double CELL_WIDTH = 20.0;
	private final int ROWS = 21;
	private final int COLS = 19;
	private final char[][] grid;
	private Scanner input;

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
		try {
			input = new Scanner(new File("level.txt"));
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
	
//	private void fillCells() {
//		for(int i = 0; i <= ROWS; i++) {
//			for(int j = 0; j <= COLS; j++) {
//				if(grid[i][j] == 'W') {
//					Wall wall = new Wall();
//					ImageView imageView = wall.getImageView();
//                    imageView.setX((double)j * CELL_WIDTH);
//                    imageView.setY((double)i * CELL_WIDTH);
//                    imageView.setFitWidth(CELL_WIDTH);
//                    imageView.setFitHeight(CELL_WIDTH);
//                    cells[i][j] = imageView;
//				}	
//			}
//		}
//	}
}
