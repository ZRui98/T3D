package map;

import gui.ImageHandler;

/**
 * @author Rui Z.
 * @about an empty tile
 */
public class FloorTile extends MapTile {

	public FloorTile() {
		super(ImageHandler.getTexture(2), false);
	}

}
