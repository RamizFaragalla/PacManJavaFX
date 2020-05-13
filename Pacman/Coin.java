import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("serial")
public class Coin implements Item, Serializable {
	private transient Image image;	// the image of the wall
	private transient ImageView imageView;
	
	public Coin(String path, double w) {
		image = new Image(path);
		setImageView(new ImageView(image));
		imageView.setFitWidth(w);
        imageView.setFitHeight(w);
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
	 * @param imageView an ImageView object
	 * @return void
	 */
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

}
