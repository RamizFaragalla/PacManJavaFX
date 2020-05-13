import javafx.scene.image.ImageView;

public interface Item {
	/**
	 * accessor to get the ImageView object
	 * @param none
	 * @return an ImageView object
	 */
	public ImageView getImageView();

	/**
	 * mutator to change the ImageView object
	 * @param imageView the imageView to set
	 * @return void
	 */
	public void setImageView(ImageView imageView);
}
