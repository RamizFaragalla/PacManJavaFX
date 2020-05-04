public class Player extends Character {
	private int points;
	public Player(double w) {
		super("images//pacmanRight.gif", 0, 0, w);
		setPoints(0);
	}
	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}


}
