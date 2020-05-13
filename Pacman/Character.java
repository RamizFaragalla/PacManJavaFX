import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("serial")
public abstract class Character implements Serializable {
	private transient Image image;	// image of the character
	private transient ImageView imageView;	// image view for GridPane
	private int r;	// row position for the character
	private int c;	// column position for the character
	
	private Map m;
	
	public Character(Map m, String path, int r, int c) {
		this.m = m;
		image = new Image(path);
		imageView = new ImageView(image);
		imageView.setFitWidth(m.getWidth());
        imageView.setFitHeight(m.getWidth());
		setR(r);
		setC(c);
	}
	
	/**
	 * method allows for the movement of a character on the map
	 */
	public abstract void controls();	// starts the controls of the character
	
	/**
	 * method stops the movement of a character
	 */
	public abstract void stopControls();	// stops the controls of the character
	
	/**
	 * accessor method for the Map object
	 * @param none
	 * @return m an object of the Map class
	 */
	public Map getMap() {
		return m;
	}
	
	/**
	 * mutator method for the Map object
	 * @param m an object of the Map class
	 * @return void
	 */
	public void setMap(Map m) {
		this.m = m;
	}
	
	/**
	 * accessor method for the ImageView object
	 * @param none
	 * @return imageView an ImageView object
	 */
	public ImageView getImageView() {
		return imageView;
	}

	/**
	 * mutator method for the ImageView object
	 * @param imageView imageView to set
	 * @param w a double, the width and height of imageView
	 * @return void
	 */
	public void setImageView(ImageView imageView, double w) {
		this.imageView = imageView;
		imageView.setFitWidth(w);
        imageView.setFitHeight(w);
	}

	/**
	 * accessor for the row position
	 * @param none
	 * @return r an int for the row position 
	 */
	public int getR() {
		return r;
	}

	/**
	 * mutator for the row position
	 * @param row an int to set the row position
	 * @return void
	 */
	public void setR(int row) {
		this.r = row;
	}

	/**
	 * accessor for the column position
	 * @param none
	 * @return c an int for the column position
	 */
	public int getC() {
		return c;
	}

	/**
	 * mutator for the column position 
	 * @param column an int to set the column position
	 * @return void
	 */
	public void setC(int column) {
		this.c = column;
	}
}
