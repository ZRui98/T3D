package entities;

import gui.GSRunning;
import gui.ImageHandler;
import raycasting.Raycasting;

/**
 * @author Rui Z.
 * @about basic projectile
 */
public class CannonProjectile extends Projectile {

	public final static int SIZE = 32;

	public CannonProjectile(int x, int y, int moveDirection) {
		super(x, y, 7, moveDirection, SIZE, 20,
				(SIZE + GSRunning.TILE_SIZE) / 2, ImageHandler.getTexture(5));
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
				e.takeDamage(super.getDamage());
			}
			super.markForRemoval();
		}
	}
}
