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
	private final double CELL_WIDTH = 20.0;
	private final int ROWS = 13;	// 21 13
	private final int COLS = 25;	// 19 25
	private char[][] grid;
	private transient GridPane map;
	private Player player;
	private Enemy enemy1, enemy2, enemy3, enemy4;
	private Item item;
	
	public Map() {
		grid = new char[ROWS][COLS];
		map = new GridPane();
		//cells = new ImageView[ROWS][COLS];
		fillGrid();
		//fillCells();
	}
	
	public void setCharacters(Player player, Enemy enemy1, Enemy enemy2, Enemy enemy3, Enemy enemy4) {
		this.player = player;
		this.enemy1 = enemy1;
		this.enemy2 = enemy2;
		this.enemy3 = enemy3;
		this.enemy4 = enemy4;
	}
	
	public void setGridPane(GridPane g) {
		map = g;
		
	}
	
	public void fillGridPane() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {

				if(grid[i][j] == 'W') {
					item = new Wall(CELL_WIDTH);
                    map.add(item.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == 'S') {
					item = new Coin("images//smalldot.png", CELL_WIDTH);
                    map.add(item.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == 'B') {
					item = new Coin("images//whitedot.png", CELL_WIDTH);
                    map.add(item.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == '1') {
					enemy1.setR(i);
					enemy1.setC(j);
                    map.add(enemy1.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == '2') {
					enemy2.setR(i);
					enemy2.setC(j);
                    map.add(enemy2.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == '3') {
					enemy3.setR(i);
					enemy3.setC(j);
                    map.add(enemy3.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == '4') {
					enemy4.setR(i);
					enemy4.setC(j);
                    map.add(enemy4.getImageView(), j+1, i+1);
				}
				
				else if(grid[i][j] == 'P') {
					player.setR(i);
					player.setC(j);
					System.out.println(player.getR() + " " + player.getC());
                    map.add(player.getImageView(), j+1, i+1);
				}
				
				else {
					item = new Empty(CELL_WIDTH);
//                    imageView.setX((double)j * m.getWidth());
//                    imageView.setY((double)i * m.getWidth());
//                    imageView.setFitWidth(m.getWidth());
//                    imageView.setFitHeight(m.getWidth());
                    map.add(item.getImageView(), j+1, i+1);
				}
			}
		}
	}
	
	public GridPane getGridPane() {
		return map;
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
			input = new Scanner(new File("level2.txt"));
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
	
	public boolean won() {
		for(int i = 0; i < getRows(); i++) {
			for(int j = 0; j < getCols(); j++) {
				if(grid[i][j] == 'S' || grid[i][j] == 'B')
					return false;
			}
		}
		return true;
	}
	
	public void update(int r1, int c1, char a, int r2, int c2, char b, String orientation) {
		updateHelper(r1, c1, a, orientation);
		updateHelper(r2, c2, b, orientation);
		if(won()) {
			enemy1.stopControls();
			enemy2.stopControls();
			player.stopControls();
			
			player.won();
		}
		
//		else
//			player.stillPlaying();
	}
	
	@SuppressWarnings("static-access")
	private void updateHelper(int i, int j, int x, String orientation) {
		
//		map.getChildren().remove(j+1, i+1);
		//remove node
		for (Node node : map.getChildren()) {
		    if (map.getColumnIndex(node) == (j+1) && map.getRowIndex(node) == (i+1)) {
		        map.getChildren().remove(node);
		        break;
		    }
		}
		///////////////////////////////////////////////change item classes to ()
		if(x == 'S') {
			item = new Coin("images//smalldot.png", CELL_WIDTH);
            map.add(item.getImageView(), j+1, i+1);
		}
		
		else if(x == 'B') {
			item = new Coin("images//whitedot.png", CELL_WIDTH);
            map.add(item.getImageView(), j+1, i+1);
		}
		
		else if(x == '1') {
			enemy1.setR(i);
			enemy1.setC(j);
            map.add(enemy1.getImageView(), j+1, i+1);
		}
		
		else if(x == '2') {
			enemy2.setR(i);
			enemy2.setC(j);
            map.add(enemy2.getImageView(), j+1, i+1);
		}
		
		else if(x == '3') {
			enemy3.setR(i);
			enemy3.setC(j);
            map.add(enemy3.getImageView(), j+1, i+1);
		}
		
		else if(x == '4') {
			enemy4.setR(i);
			enemy4.setC(j);
            map.add(enemy4.getImageView(), j+1, i+1);
		}
		
		else if(x == 'P'){
			Image image = new Image("images/" + orientation);
			player.setImageView(new ImageView(image), CELL_WIDTH);
			player.setR(i);
			player.setC(j);
			System.out.println(player.getR() + " " + player.getC());
            map.add(player.getImageView(), j+1, i+1);
		}
		
		else {
			item = new Empty(CELL_WIDTH);
            map.add(item.getImageView(), j+1, i+1);
		}
		
	}
	
}
