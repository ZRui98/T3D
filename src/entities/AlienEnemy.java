package entities;

import gui.ImageHandler;

/**
 * @author Rui Z.
 * @about standard enemy with no special attributes
 */
public class AlienEnemy extends Enemy {
	public AlienEnemy(int x, int y) {
		super(220, x, y, 2, 60, 0.2f, ImageHandler.getTexture(10), ImageHandler.getTexture(11),
				ImageHandler.getTexture(12), ImageHandler.getTexture(13), ImageHandler.getTexture(14),
				ImageHandler.getTexture(15));
	}

}
