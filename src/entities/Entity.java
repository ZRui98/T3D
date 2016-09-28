package entities;

import gui.GSRunning;
import map.Map;
import map.MapTile;

public class Entity {
	public int x;
	public int y;
	public Map world;

	private int moveSpeed;
	private int size;
	public boolean isMovingNorth;
	public boolean isMovingSouth;
	public boolean isMovingEast;
	public boolean isMovingWest;

	private boolean toBeRemoved = false;

	public Entity(int x, int y, int moveSpeed, int size) {
		this.x = x;
		this.y = y;
		this.moveSpeed = moveSpeed;
		this.size = size;
	}

	public void setWorld(Map world) {
		this.world = world;
	}

	public Map getWorld() {
		return world;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int newMoveSpeed) {
		moveSpeed = newMoveSpeed;
	}

	public int getSize() {
		return size;
	}

	/**
	 * Collisions + Movement
	 * 
	 * @about limited to entities that move less than 1 tile, and entities that
	 *        are smaller than 1 tile
	 * @param xMovement
	 *            x amount to move
	 * @param yMovement
	 *            y amount to move
	 * @return if the entity had moved or not
	 */
	public boolean move(int xMovement, int yMovement) {
		double padding = size / 2;
		// uses the 8 possible tiles that could surround an enemy
		// _1 1
		// 1___1
		// 1___1
		// _1 1
		int bx = (int) ((x - padding) / (double) GSRunning.TILE_SIZE);
		int tx = (int) ((x + padding) / (double) GSRunning.TILE_SIZE);
		int ty = (int) ((y - padding) / (double) GSRunning.TILE_SIZE);
		int by = (int) ((y + padding) / (double) GSRunning.TILE_SIZE);
		int bmy = (int) ((y + yMovement + padding) / (double) GSRunning.TILE_SIZE);
		int tmy = (int) ((y + yMovement - padding) / (double) GSRunning.TILE_SIZE);
		int bmx = (int) ((x + xMovement - padding) / (double) GSRunning.TILE_SIZE);
		int tmx = (int) ((x + xMovement + padding) / (double) GSRunning.TILE_SIZE);
		MapTile bottomLeftTile = world.getTile(bx, bmy);
		MapTile bottomRightTile = world.getTile(tx, bmy);
		MapTile leftBottomTile = world.getTile(bmx, by);
		MapTile leftTopTile = world.getTile(bmx, ty);
		MapTile rightBottomTile = world.getTile(tmx, by);
		MapTile rightTopTile = world.getTile(tmx, ty);
		MapTile topLeftTile = world.getTile(bx, tmy);
		MapTile topRightTile = world.getTile(tx, tmy);
		boolean moved = true;
		if (yMovement < 0)
			if (!topLeftTile.isSolid() && !topRightTile.isSolid())
				y += yMovement;
			else {
				moved = false;
			}
		if (yMovement > 0)
			if (!bottomLeftTile.isSolid() && !bottomRightTile.isSolid())
				y += yMovement;
			else {
				moved = false;
			}
		if (xMovement > 0)
			if (!rightTopTile.isSolid() && !rightBottomTile.isSolid())
				x += xMovement;
			else {
				moved = false;
			}
		if (xMovement < 0)
			if (!leftTopTile.isSolid() && !leftBottomTile.isSolid())
				x += xMovement;
			else {
				moved = false;
			}
		return moved;
	}

	public void markForRemoval() {
		toBeRemoved = true;
	}

	public boolean shouldBeRemoved() {
		return toBeRemoved;
	}

}
