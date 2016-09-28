package entities;

import gui.ImageHandler;

/**
 * @author Rui Z.
 * @about enemy that moves twice as fast, but has less hp
 */
public class BugEnemy extends Enemy {

	public BugEnemy(int x, int y) {
		super(180, x, y, 4, 60, 0.1f, ImageHandler.getTexture(16), ImageHandler
				.getTexture(17), ImageHandler.getTexture(18), ImageHandler
				.getTexture(19), ImageHandler.getTexture(20), ImageHandler
				.getTexture(21));
	}

}
