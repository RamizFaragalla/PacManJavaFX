@SuppressWarnings("serial")
public class Enemy extends Character {
	
	private int id;
	
	public Enemy(String path, double w, int id) {
		super(path, 0, 0, w);
		this.setID(id);
	}

	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setID(int id) {
		this.id = id;
	}

}
