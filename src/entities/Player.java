package entities;

import gui.GSRunning;
import map.CannonTower;
import map.ElectricTower;
import map.EmptyTower;
import map.FreezingTower;
import raycasting.Ray;
import raycasting.Raycasting;

/**
 * @author Rui Z.
 * @about class that stores all values and has all methods for the player
 */
public class Player extends Entity {
	public int playerAngle = Raycasting.DEG90;
	public int turningAngle = 20;
	public boolean isMovingForwards = false;
	public boolean isMovingBackwards = false;
	public boolean turningLeft = false;
	public boolean turningRight = false;
	private int inventorySlot = 0;
	private int resource;

	public Player(int x, int y) {
		super(x, y, 5, 32);
		resource = 4000;
	}

	public int getResource() {
		return resource;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void changeResource(int resource) {
		this.resource += resource;
	}

	public void setInventory(int shift) {
		inventorySlot += shift;
		if (inventorySlot < 0)
			inventorySlot += 6;
		if (inventorySlot >= 6)
			inventorySlot -= 6;
	}

	public int getInventory() {
		return inventorySlot;
	}

	// timer variable that stores the last shot time
	private long lastShot = 0;

	/**
	 * function that is called when a player uses an option in their inventory
	 */
	public void shoot() {
		long now = System.currentTimeMillis();
		if (inventorySlot == 0) {
			if (now - lastShot > 400) {
				super.getWorld().addProjectile(new CannonProjectile(x, y, playerAngle));
				lastShot = now;
			}
		} else if (inventorySlot == 1) {
			if (now - lastShot > 2000) {
				super.getWorld().addProjectile(new IceProjectile(x, y, playerAngle));
				lastShot = now;
			}
		} else if (inventorySlot == 2 && resource >= CannonTower.COST) {
			Ray r = Raycasting.castRay(this, this.playerAngle);
			int xidx = (int) (r.getShortestX() / GSRunning.TILE_SIZE);
			int yidx = (int) (r.getShortestY() / GSRunning.TILE_SIZE);
			if (super.getWorld().getTile(xidx, yidx) instanceof EmptyTower) {
				super.getWorld().setTile(new CannonTower(super.getWorld(), xidx, yidx), xidx, yidx);
				resource -= CannonTower.COST;
			}
		} else if (inventorySlot == 3 && resource >= ElectricTower.COST) {
			Ray r = Raycasting.castRay(this, this.playerAngle);
			int xidx = (int) (r.getShortestX() / GSRunning.TILE_SIZE);
			int yidx = (int) (r.getShortestY() / GSRunning.TILE_SIZE);
			if (super.getWorld().getTile(xidx, yidx) instanceof EmptyTower) {
				super.getWorld().setTile(new ElectricTower(super.getWorld(), xidx, yidx), xidx, yidx);
				resource -= ElectricTower.COST;
			}
		} else if (inventorySlot == 4 && resource >= FreezingTower.COST) {
			Ray r = Raycasting.castRay(this, this.playerAngle);
			int xidx = (int) (r.getShortestX() / GSRunning.TILE_SIZE);
			int yidx = (int) (r.getShortestY() / GSRunning.TILE_SIZE);
			if (super.getWorld().getTile(xidx, yidx) instanceof EmptyTower) {
				super.getWorld().setTile(new FreezingTower(super.getWorld(), xidx, yidx), xidx, yidx);
				resource -= FreezingTower.COST;
			}
		} else if (inventorySlot == 5) {
			Ray r = Raycasting.castRay(this, this.playerAngle);
			int xidx = (int) (r.getShortestX() / GSRunning.TILE_SIZE);
			int yidx = (int) (r.getShortestY() / GSRunning.TILE_SIZE);
			if (super.getWorld().getTile(xidx, yidx) instanceof CannonTower) {
				if (super.getWorld().getTile(xidx, yidx) instanceof ElectricTower) {
					resource += ElectricTower.COST / 2;
				} else if (super.getWorld().getTile(xidx, yidx) instanceof FreezingTower) {
					resource += FreezingTower.COST / 2;
				} else {
					resource += CannonTower.COST / 2;
				}
				super.getWorld().setTile(new EmptyTower(), xidx, yidx);
			}
		}
	}

	/**
	 * @about updates player movement
	 */
	public void update() {
		int xMovement = 0, yMovement = 0;
		xMovement = (int) (Raycasting.cosPreRendered[(playerAngle)] * super.getMoveSpeed());
		yMovement = (int) (Raycasting.sinPreRendered[Raycasting.DEG360 - (playerAngle)] * super.getMoveSpeed());
		if (isMovingBackwards) {
			xMovement = -xMovement;
			yMovement = -yMovement;
		}
		if (isMovingForwards || isMovingBackwards)
			super.move(xMovement, yMovement);
		if (turningLeft) {
			playerAngle += turningAngle;
		} else if (turningRight) {
			playerAngle -= turningAngle;
		}

		if (playerAngle < 0)
			playerAngle += Raycasting.DEG360;
		else if (playerAngle > Raycasting.DEG360)
			playerAngle -= Raycasting.DEG360;

	}
}
