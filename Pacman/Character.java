import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Character {
	private Image image;
	private ImageView imageView;
	private int xPos;
	private int yPos;
	
	public Character(String path, int x, int y) {
		image = new Image(path);
		setImageView(new ImageView(image));
		setxPos(x);
		setyPos(y);
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
	public int getxPos() {
		return xPos;
	}

	/**
	 * @param xPos the xPos to set
	 */
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * @return the yPos
	 */
	public int getyPos() {
		return yPos;
	}

	/**
	 * @param yPos the yPos to set
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}
