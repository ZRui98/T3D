package entities;

import gui.ImageHandler;

/**
 * @author Rui Z.
 * @about projectile that follows the closest enemy
 */
public class ElectricProjectile extends Projectile {

	private long startingTime;
	public final static int SIZE = 32;

	public ElectricProjectile(int x, int y, int moveDirection) {
		super(x, y, 1, moveDirection, SIZE, 0.75f, SIZE / 2, ImageHandler
				.getTexture(6));
		startingTime = System.currentTimeMillis();
	}

	public void update() {
		super.calculateAngleBetweenSprite();
		if (System.currentTimeMillis() - startingTime < 5000) {
			if (world.getNumEnemies() > 0) {
				Enemy closestEnemy = world.getEnemy(0);
				for (int i = 1; i < world.getNumEnemies(); i++) {
					Enemy e = world.getEnemy(i);
					// calculating Manhattan distance
					if (Math.abs(e.x - super.x) + Math.abs(e.y - super.y) < Math
							.abs(closestEnemy.x - super.x)
							+ Math.abs(closestEnemy.y - super.y))
						closestEnemy = e;
				}
				int xMove = 0;
				int yMove = 0;
				if (closestEnemy.x < super.x) {
					xMove--;
				} else if (closestEnemy.x > super.x) {
					xMove++;
				}
				if (closestEnemy.y < super.y) {
					yMove--;
				} else if (closestEnemy.y > super.y) {
					yMove++;
				}
				super.move(xMove * super.getMoveSpeed(),
						yMove * super.getMoveSpeed());
				world.damage(x, y, super.getDamage(), super.getSize());
			}
		} else {
			super.markForRemoval();
		}
	}

}
