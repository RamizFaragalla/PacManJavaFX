import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("serial")
public abstract class Character implements Serializable{
	private transient Image image;
	private transient ImageView imageView;
	private int r;
	private int c;
	
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
	
	public abstract void controls();
	public abstract void stopControls();
	
	public Map getMap() {
		return m;
	}
	
	public void setMap(Map m) {
		this.m = m;
	}
	
	//public abstract void controls(Scene scene);
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
