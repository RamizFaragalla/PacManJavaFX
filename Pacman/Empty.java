import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Empty implements Item {
	private Image image;
	private ImageView imageView;
	
	public Empty(double w) {
		image = new Image("images//empty.png");
		setImageView(new ImageView(image));
		imageView.setFitWidth(w);
        imageView.setFitHeight(w);
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
