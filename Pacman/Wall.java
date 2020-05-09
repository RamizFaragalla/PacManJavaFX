import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("serial")
public class Wall implements Item, Serializable{
	private transient Image image;
	private transient ImageView imageView;
	
	public Wall(double w) {
		image = new Image("images//wall.png");
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
