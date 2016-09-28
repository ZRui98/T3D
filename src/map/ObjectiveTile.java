package map;

import gui.ImageHandler;

/**
 * @author Rui Z.
 * @about the objective tile that the player must defend
 */
public class ObjectiveTile extends MapTile {

	private float objectiveHealth;
	private int x;
	private int y;

	public ObjectiveTile(int x, int y) {
		super(ImageHandler.getTexture(8), true);
		this.x = x;
		this.y = y;
		objectiveHealth = 1000;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void takeDamage(float dmg) {
		objectiveHealth -= dmg;
	}

	public float getHealth() {
		return objectiveHealth;
	}

}
