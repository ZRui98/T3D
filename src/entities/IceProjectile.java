package entities;

import gui.GSRunning;
import gui.ImageHandler;
import raycasting.Raycasting;

/**
 * @author Rui Z.
 * @about projectile that freezes enemies
 */
public class IceProjectile extends Projectile {
	public final static int SIZE = 32;

	public IceProjectile(int x, int y, int moveDirection) {
		super(x, y, 7, moveDirection, SIZE, 20,
				(SIZE + GSRunning.TILE_SIZE) / 2, ImageHandler.getTexture(22));
	}

	public void update() {
		super.calculateAngleBetweenSprite();
		int xMove = (int) (Raycasting.cosPreRendered[super.getMoveDirection()] * super
				.getMoveSpeed());
		int yMove = (int) (Raycasting.sinPreRendered[Raycasting.DEG360
				- super.getMoveDirection()] * super.getMoveSpeed());
		boolean hit = super.move(xMove, yMove);
		if (hit) {
			Enemy e = super.getHitEnemy();
			if (e != null) {
				e.freeze();
			}
			super.markForRemoval();
		}
	}
}
