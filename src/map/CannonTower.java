package map;

import entities.CannonProjectile;
import entities.Projectile;
import gui.GSRunning;
import gui.ImageHandler;
import raycasting.Raycasting;

/**
 * Basic tower that shoots a basic projectile on all 4 sides
 * 
 * @author Rui
 *
 */
public class CannonTower extends MapTile {
	private int xIndex;
	private int yIndex;
	private Map world;
	public static final int COST = 500;

	public CannonTower(Map world, int xIndex, int yIndex) {
		super(ImageHandler.getTexture(4), true);
		this.world = world;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}

	public CannonTower(Map world, int xIndex, int yIndex, int[] img) {
		super(img, true);
		this.world = world;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}

	public boolean eastOpen() {
		return !world.getTile(xIndex + 1, yIndex).isSolid();
	}

	public boolean westOpen() {
		return !world.getTile(xIndex - 1, yIndex).isSolid();
	}

	public boolean northOpen() {
		return !world.getTile(xIndex, yIndex - 1).isSolid();
	}

	public boolean southOpen() {
		return !world.getTile(xIndex, yIndex + 1).isSolid();
	}

	public void shootProjectile(Projectile p) {
		world.addProjectile(p);
	}

	public int getXIndex() {
		return xIndex;
	}

	public int getYIndex() {
		return yIndex;
	}

	public Map getWorld() {
		return world;
	}

	private long lastShot = 0;

	public void update() {
		long now = System.currentTimeMillis();
		if (now - lastShot > 3000) {
			lastShot = now;

			// shoots on all 4 sides
			if (westOpen()) {
				shootProjectile(new CannonProjectile(xIndex
						* GSRunning.TILE_SIZE - CannonProjectile.SIZE, yIndex
						* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2,
						Raycasting.DEG180));
			}

			if (eastOpen()) {
				shootProjectile(new CannonProjectile((xIndex + 1)
						* GSRunning.TILE_SIZE + CannonProjectile.SIZE, yIndex
						* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2, 0));
			}

			if (southOpen()) {
				shootProjectile(new CannonProjectile((xIndex)
						* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2,
						(yIndex + 1) * GSRunning.TILE_SIZE
								+ CannonProjectile.SIZE, Raycasting.DEG270));
			}

			if (northOpen()) {
				shootProjectile(new CannonProjectile((xIndex)
						* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE / 2, yIndex
						* GSRunning.TILE_SIZE - CannonProjectile.SIZE,
						Raycasting.DEG90));
			}
		}
	}
}
