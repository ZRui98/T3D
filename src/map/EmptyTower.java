package map;

import gui.ImageHandler;

/**
 * @author Rui Z.
 * @about a tower that can be turned into a cannon, electric, or ice tower
 */
public class EmptyTower extends MapTile {
	public static final int COST = 200;

	public EmptyTower() {
		super(ImageHandler.getTexture(1), true);
	}

	public void update() {

	}

}
