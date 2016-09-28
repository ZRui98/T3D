package entities;

import map.Map;
import raycasting.Raycasting;

/**
 * @author Rui Z.
 * @about the class that stores all the data for an enemy that can be drawn on
 *        the screen
 */
public class VisibleEntity extends Entity implements Comparable<VisibleEntity> {
	private float angleBetweenPlayerAndEntity;
	private float distToPlayer;
	private int[] imgArray;

	public VisibleEntity(int x, int y, int moveSpeed, int size, int[] img) {
		super(x, y, moveSpeed, size);
		this.imgArray = img;
	}

	@Override
	public void setWorld(Map world) {
		super.setWorld(world);
		calculateAngleBetweenSprite();
	}

	public void calculateAngleBetweenSprite() {
		Player p = world.getPlayer();
		float dx = super.x - p.x;
		float dy = super.y - p.y;

		distToPlayer = (float) Math.sqrt(dx * dx + dy * dy);
		// angle of sprite in cartesian plane
		// relative angle of sprite
		float entityAngle = (float) Math.toDegrees(Math.atan(dy / dx));
		float tempAngle = Math.abs(entityAngle);
		float viewAngle = 1000;
		// finding related accute angle
		if (dx <= 0 && dy <= 0)
			viewAngle = 180 - tempAngle;
		else if (dx >= 0 && dy <= 0)
			viewAngle = tempAngle;
		else if (dx <= 0 && dy >= 0)
			viewAngle = 180 + tempAngle;
		else if (dx >= 0 && dy >= 0)
			viewAngle = 360 - tempAngle;
		float angleBetweenSprite = p.playerAngle * Raycasting.angleBetweenRays
				- viewAngle;
		// deals with case of playerAngle being close to 360/0, and enemy being
		// close to 0/360
		// resulting with angles within range of FOV, but results in angles of
		// 330+ and -330.
		if (angleBetweenSprite >= 330) {
			angleBetweenSprite -= 360;
		} else if (angleBetweenSprite <= -330) {
			angleBetweenSprite += 360;
		}
		this.angleBetweenPlayerAndEntity = angleBetweenSprite;
	}

	public float getAngleBetween() {
		return angleBetweenPlayerAndEntity;
	}

	public float getDistToPlayer() {
		return distToPlayer;
	}

	public int[] getSprite() {
		return imgArray;
	}

	@Override
	public int compareTo(VisibleEntity e) {
		if (this.distToPlayer < e.getDistToPlayer()) {
			return 1;
		} else if (this.distToPlayer > e.getDistToPlayer()) {
			return -1;
		}
		return 0;

	}
}
