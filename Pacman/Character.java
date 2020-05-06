import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("serial")
public abstract class Character implements Serializable{
	private transient Image image;
	private transient ImageView imageView;
	private int r;
	private int c;
	
	public Character(String path, int r, int c, double w) {
		image = new Image(path);
		imageView = new ImageView(image);
		imageView.setFitWidth(w);
        imageView.setFitHeight(w);
		setR(r);
		setC(c);
	}
	
	
	/**
	 * @return the imageView
	 */
	public ImageView getImageView() {
		return imageView;
	}

	/**
	 * @param imageView the imageView to set
	 */
	public void setImageView(ImageView imageView, double w) {
		this.imageView = imageView;
		imageView.setFitWidth(w);
        imageView.setFitHeight(w);
	}

	/**
	 * @return the row position
	 */
	public int getR() {
		return r;
	}

	/**
	 * 
	 * @param row set the row position
	 */
	public void setR(int row) {
		this.r = row;
	}

	/**
	 * @return the column position
	 */
	public int getC() {
		return c;
	}

	/**
	 * 
	 * @param column set the column position
	 */
	public void setC(int column) {
		this.c = column;
	}
}
