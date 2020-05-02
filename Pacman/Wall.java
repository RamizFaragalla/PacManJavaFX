import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall {
	private Image image;
	private ImageView imageView;
	
	public Wall(String path) {
		image = new Image(path);
		setImageView(new ImageView(image));
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
}
