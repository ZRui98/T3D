package entities;

import gui.ImageHandler;

/**
 * @author Rui Z.
 * @about enemy with much more health than the others
 */
public class RobotEnemy extends Enemy {

	public RobotEnemy(int x, int y) {
		super(400, x, y, 2, 60, 0.3f, ImageHandler.getTexture(24), ImageHandler
				.getTexture(25), ImageHandler.getTexture(26), ImageHandler
				.getTexture(27), ImageHandler.getTexture(28), ImageHandler
				.getTexture(29));
	}

}
