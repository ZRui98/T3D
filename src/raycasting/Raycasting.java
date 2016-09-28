package raycasting;

import entities.Player;
import gui.GSRunning;
import gui.MainPanel;

/**
 * 
 * @author Rui Z.
 * @author Algorithm & concept from permadi.com/tutorial/raycast/rayc1.html
 * @about calculates the distance from a certain point to the wall in a certain
 *        direction/ angle
 */
public class Raycasting {

	// Using standard Cartesian Plane in the format of
	// Q2Q1 180-0
	// Q3Q4 180-360
	// technique of trigonometric raycasting to find distance to walls from
	// permadi.com/tutorial/raycast/rayc1.html
	// prerendered table of tan, sin, and cosine values from 0-360 in intervals
	// of 60/640
	public static float[] tanPreRendered;
	public static float[] sinPreRendered;
	public static float[] cosPreRendered;
	// the pov of the camera is 60 degrees
	// predefined values based on pov/SCREEN_WIDTH, which is 60 degrees/640
	// columns
	public static final int DEG30 = MainPanel.SCREEN_WIDTH / 2;
	public static final int DEG360 = MainPanel.SCREEN_WIDTH * 6;
	public static final int DEG180 = DEG360 / 2;
	public static final int DEG90 = DEG180 / 2;
	public static final int DEG270 = DEG90 * 3;

	public final static float angleBetweenRays = 60F / MainPanel.SCREEN_WIDTH;

	public static void initializeTables() {
		// Loading pre-rendered values for sin, cos, and tan.
		tanPreRendered = new float[DEG360 + 1];
		sinPreRendered = new float[DEG360 + 1];
		cosPreRendered = new float[DEG360 + 1];
		float deg;
		for (int i = 0; i < DEG360 + 1; i++) {
			deg = (float) Math.toRadians(i * angleBetweenRays) + 0.0001F;
			cosPreRendered[i] = (float) Math.cos(deg);
			sinPreRendered[i] = (float) Math.sin(deg);
			tanPreRendered[i] = (float) Math.tan(deg);
		}

	}

	public static Ray castRay(Player p, int angle) {
		//
		// CHECKING ALL OF THE VERTICAL INTERSECTIONS
		//
		// Ya,Xa is the addition each time after first point. vxd,vyd
		// controls all points lying on top and bottom sides of grids
		float verticalXDistance, verticalYDistance;
		float Xa, Ya;
		// UP
		// A.y = rounded_down(Py/TILE_SIZE) * (TILE_SIZE) - 1;
		if (angle > 0 && angle < DEG180) {
			verticalYDistance = (p.y / GSRunning.TILE_SIZE)
					* GSRunning.TILE_SIZE - 1;
			// A.x=Px+(Py-A.y)/tan(ALPHA)
			// moves verticalYDistance back to
			// (playerY/TILE_SIZE)*TILE_SIZE.
			// needs to be rounded down(Py-A.y-1), as going negative (Q1-2)
			verticalXDistance = (p.x + ((p.y - verticalYDistance - 1) / tanPreRendered[angle]));
			if (angle == DEG90)// special case used to avoid holes, since
								// the value of playerY-verticalYDistance is
								// guaranteed negative(DEG90 is up)
				verticalXDistance = (p.x - ((p.y - verticalYDistance - 1) / tanPreRendered[angle]));
			Ya = -GSRunning.TILE_SIZE;
		}
		// DOWN
		// A.y = rounded_down(Py/TILE_SIZE) * (TILE_SIZE) + TILE_SIZE;
		else {
			verticalYDistance = (p.y / GSRunning.TILE_SIZE)
					* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE;
			// A.x=Px+(Py-A.y)/tan(ALPHA)
			verticalXDistance = (p.x + ((p.y - verticalYDistance) / tanPreRendered[angle]));
			Ya = GSRunning.TILE_SIZE;
		}
		// Xa=TILE_SIZE/tan(ALPHA)
		Xa = Math.abs(GSRunning.TILE_SIZE / tanPreRendered[angle]);
		// makes it so that if the angle is90=270, the additive is negative
		if (angle > DEG90 && angle < DEG270)
			Xa = -Xa;
		float verticalDistance = 0;
		// ignores cases when distance goes out of map
		if (angle == 0 || angle == DEG180)
			verticalDistance = Float.MAX_VALUE;
		else {
			// repeat, check all points on line until wall is found
			while (true) {
				int verticalXGrid = (int) verticalXDistance
						/ GSRunning.TILE_SIZE;
				int verticalYGrid = (int) verticalYDistance
						/ GSRunning.TILE_SIZE;
				if (!p.getWorld().isValidSpot(verticalXGrid, verticalYGrid)) {
					verticalDistance = Float.MAX_VALUE;
					break;
				}
				if (p.getWorld().getTile(verticalXGrid, verticalYGrid)
						.isSolid()) {
					// PD=ABS(Px-Dx)/cos(ALPHA)=ABS(Py-Dy)/sin(ALPHA)
					verticalDistance = Math.abs((p.x - verticalXDistance)
							/ cosPreRendered[angle]);
					break;
				}
				verticalXDistance += Xa;
				verticalYDistance += Ya;
			}
		}
		//
		// CHECKING ALL OF THE HORIZONTAL INTERSECTIONS
		// Xb & Yb are the addition each time to the horizontal and vertical
		// distances
		// hxd,hyd
		// controls all points lying on top and bottom sides of grids
		float horizontalXDistance, horizontalYDistance;
		float Xb, Yb;
		// LEFT
		// B.x = rounded_down(Px/TILE_SIZE) * (TILE_SIZE) - 1.
		if (angle > DEG90 && angle < DEG270) {
			horizontalXDistance = (p.x / GSRunning.TILE_SIZE)
					* GSRunning.TILE_SIZE - 1;
			// A.y = Py + (Px-A.x)*tan(ALPHA);
			// due to x going negative(Q2 & Q3 are on left side), needs to be
			// rounded
			// down(Px-A.x-1)to force this.
			horizontalYDistance = (p.y + ((p.x - horizontalXDistance - 1) * tanPreRendered[angle]));
			Xb = -GSRunning.TILE_SIZE;
		}
		// RIGHT
		// B.x = rounded_down(Px/TILE_SIZE) * (TILE_SIZE) + TILE_SIZE.
		else {
			horizontalXDistance = (p.x / GSRunning.TILE_SIZE)
					* GSRunning.TILE_SIZE + GSRunning.TILE_SIZE;
			// A.y = Py + (Px-A.x)*tan(ALPHA);
			horizontalYDistance = (p.y + ((p.x - horizontalXDistance) * tanPreRendered[angle]));
			if (angle == 0)// same as special case for DEG90, this case
							// deals with holes at 0 degrees
				horizontalYDistance = (p.y + ((horizontalXDistance - p.x) * tanPreRendered[angle]));
			Xb = GSRunning.TILE_SIZE;
		}

		// Ya=TILE_SIZE*tan(ALPHA)
		Yb = Math.abs(GSRunning.TILE_SIZE * tanPreRendered[angle]);
		// makes it so that if the angle is 0-180, the additive is negative
		if (angle > 0 && angle < DEG180)
			Yb = -Yb;

		// repeat, check all points on line until wall is found
		float horizontalDistance = 0;
		// ignores if case goes out of map
		if (angle == DEG90 || angle == DEG270)
			horizontalDistance = Float.MAX_VALUE;
		else {
			while (true) {
				int horizontalXGrid = (int) horizontalXDistance
						/ GSRunning.TILE_SIZE;
				int horizontalYGrid = (int) horizontalYDistance
						/ GSRunning.TILE_SIZE;
				if (!p.getWorld().isValidSpot(horizontalXGrid, horizontalYGrid)) {
					horizontalDistance = Float.MAX_VALUE;
					break;
				}
				if (p.getWorld().getTile(horizontalXGrid, horizontalYGrid)
						.isSolid()) {
					// PD=ABS(Px-Dx)/cos(ALPHA)=ABS(Py-Dy)/sin(ALPHA)
					horizontalDistance = Math.abs((p.y - horizontalYDistance)
							/ sinPreRendered[angle]);
					break;
				}
				horizontalXDistance += Xb;
				horizontalYDistance += Yb;
			}
		}
		return new Ray(horizontalDistance, horizontalXDistance,
				horizontalYDistance, verticalDistance, verticalXDistance,
				verticalYDistance);
	}
}
