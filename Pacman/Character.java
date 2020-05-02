import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Character {
	private Image image;
	private ImageView imageView;
	private int r;
	private int c;
	
	public Character(String path, int r, int c) {
		image = new Image(path);
		setImageView(new ImageView(image));
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
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	/**
	 * @return the xPos
	 */
	public int getR() {
		return r;
	}


	public void setR(int row) {
		this.r = row;
	}

	/**
	 * @return the yPos
	 */
	public int getC() {
		return c;
	}


	public void setC(int column) {
		this.c = column;
	}
}
