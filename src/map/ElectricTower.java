package map;

import entities.ElectricProjectile;
import gui.GSRunning;
import gui.ImageHandler;
import raycasting.Raycasting;

/**
 * @author Rui Z.
 * @about similar to the cannon tower, except that it shoots electric
 *        projectiles,
 */
public class ElectricTower extends CannonTower {

	public static final int COST = 2000;

	public ElectricTower(Map world, int xIndex, int yIndex) {
		super(world, xIndex, yIndex, ImageHandler.getTexture(7));
	}

	private long lastShot;

	public void update() {
		if (super.getWorld().getNumEnemies() > 0) {
			long now = System.currentTimeMillis();
			if (now - lastShot > 5000) {
				lastShot = now;

				// shoots on all 4 sides
				if (westOpen()) {

					shootProjectile(new ElectricProjectile(super.getXIndex()
							* GSRunning.TILE_SIZE - ElectricProjectile.SIZE,
							super.getYIndex() * GSRunning.TILE_SIZE
									+ GSRunning.TILE_SIZE / 2,
							Raycasting.DEG180));
				}

				if (eastOpen()) {

					shootProjectile(new ElectricProjectile(
							(super.getXIndex() + 1) * GSRunning.TILE_SIZE
									+ ElectricProjectile.SIZE,
							super.getYIndex() * GSRunning.TILE_SIZE
									+ GSRunning.TILE_SIZE / 2, 0));
				}
				if (southOpen()) {

					shootProjectile(new ElectricProjectile((super.getXIndex())
							* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2,
							(super.getYIndex() + 1) * GSRunning.TILE_SIZE
									+ ElectricProjectile.SIZE,
							Raycasting.DEG270));
				}

				if (northOpen()) {

					shootProjectile(new ElectricProjectile((super.getXIndex())
							* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2,
							super.getYIndex() * GSRunning.TILE_SIZE
									- ElectricProjectile.SIZE, Raycasting.DEG90));
				}
			}
		}
	}
}
