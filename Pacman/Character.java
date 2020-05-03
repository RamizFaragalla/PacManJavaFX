import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Character {
	private Image image;
	private ImageView imageView;
	private int r;
	private int c;
	
	public Character(String path, int r, int c, double w) {
		image = new Image(path);
		setImageView(new ImageView(image));
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
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	/**
	 * @return the xPos
	 */
	public int getR() {
		return r;
	}

	/**
	 * @param xPos the xPos to set
	 */
	public void setR(int xPos) {
		this.r = xPos;
	}

	/**
	 * @return the yPos
	 */
	public int getC() {
		return c;
	}

	/**
	 * @param yPos the yPos to set
	 */
	public void setC(int yPos) {
		this.c = yPos;
	}
}
