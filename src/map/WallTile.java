package map;

import gui.ImageHandler;

/**
 * @author Rui Z.
 * @about an solid tile without any special atributes
 */
public class WallTile extends MapTile {

	public WallTile() {
		super(ImageHandler.getTexture(0), true);
	}

}
