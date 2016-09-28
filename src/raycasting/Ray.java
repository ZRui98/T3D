package raycasting;

/**
 * 
 * @author Rui Z.
 * @about Class that stores all data for a ray cast by the raycasting algorithm
 */
public class Ray {
	// the shortest distance out of the 2 rays
	private float shortestDistance;
	// contains the x distance of the shortest distance
	private float shortestX;
	// contains the y distance of the shortest distance
	private float shortestY;
	// 0-vertical 1-horizontal
	private boolean isHorizontal;

	// y distance of horizontal ray cast
	private float horizontalYDistance;
	// x distance of horizontal ray cast
	private float horizontalXDistance;
	// y distance of vertical ray cast
	private float verticalYDistance;
	// y distance of vertical ray cast
	private float verticalXDistance;

	public Ray(float horizontalDistance, float horizontalXDistance,
			float horizontalYDistance, float verticalDistance,
			float verticalXDistance, float verticalYDistance) {
		this.verticalXDistance = verticalXDistance;
		this.verticalYDistance = verticalYDistance;
		this.horizontalXDistance = horizontalXDistance;
		this.horizontalYDistance = horizontalYDistance;
		if (horizontalDistance < verticalDistance) {
			shortestDistance = horizontalDistance;
			this.shortestX = horizontalXDistance;
			this.shortestY = horizontalYDistance;
			isHorizontal = true;
		} else {
			shortestDistance = verticalDistance;
			this.shortestX = verticalXDistance;
			this.shortestY = verticalYDistance;
			isHorizontal = false;
		}
	}

	public float gethX() {
		return this.horizontalXDistance;
	}

	public float gethY() {
		return this.horizontalYDistance;
	}

	public float getvX() {
		return this.verticalXDistance;
	}

	public float getvY() {
		return this.verticalYDistance;
	}

	public float getLength() {
		return shortestDistance;
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public float getShortestX() {
		return shortestX;
	}

	public float getShortestY() {
		return shortestY;
	}
}
