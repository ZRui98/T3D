package map;

import entities.IceProjectile;
import gui.GSRunning;
import gui.ImageHandler;
import raycasting.Raycasting;

/**
 * @author Rui Z.
 * @about similar to the cannon tower, except that it shoots ice projectiles
 */
public class FreezingTower extends CannonTower {
	public static final int COST = 750;

	public FreezingTower(Map world, int xIndex, int yIndex) {
		super(world, xIndex, yIndex, ImageHandler.getTexture(23));
	}

	private long lastShot;

	public void update() {
		long now = System.currentTimeMillis();
		if (now - lastShot > 3000) {
			lastShot = now;

			// shoots on all 4 sides
			if (westOpen()) {

				shootProjectile(new IceProjectile(super.getXIndex()
						* GSRunning.TILE_SIZE - IceProjectile.SIZE,
						super.getYIndex() * GSRunning.TILE_SIZE
								+ GSRunning.TILE_SIZE / 2, Raycasting.DEG180));
			}

			if (eastOpen()) {

				shootProjectile(new IceProjectile((super.getXIndex() + 1)
						* GSRunning.TILE_SIZE + IceProjectile.SIZE,
						super.getYIndex() * GSRunning.TILE_SIZE
								+ GSRunning.TILE_SIZE / 2, 0));
			}
			if (southOpen()) {

				shootProjectile(new IceProjectile((super.getXIndex())
						* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2,
						(super.getYIndex() + 1) * GSRunning.TILE_SIZE
								+ IceProjectile.SIZE, Raycasting.DEG270));
			}

			if (northOpen()) {

				shootProjectile(new IceProjectile((super.getXIndex())
						* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2,
						super.getYIndex() * GSRunning.TILE_SIZE
								- IceProjectile.SIZE, Raycasting.DEG90));
			}
		}

	}
}
