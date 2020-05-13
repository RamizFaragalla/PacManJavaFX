import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("serial")
public class Empty implements Item, Serializable {
	private transient Image image;	// an image of a black color
	private transient ImageView imageView;
	
	public Empty(double w) {
		image = new Image("images//empty.png");
		setImageView(new ImageView(image));
		imageView.setFitWidth(w);
        imageView.setFitHeight(w);
	}

	/**
	 * accessor method to get the ImageView object
	 * @param none
	 * @return imageView an ImageView object
	 */
	public ImageView getImageView() {
		return imageView;
	}

	/**
	 * mutator for changing the ImageView object
	 * @param imageView the imageView to set
	 * @return void
	 */
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

}
