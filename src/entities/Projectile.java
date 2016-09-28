package entities;

/**
 * @author Rui Z.
 * @about an entity that is moving around that can damage an enemy
 */
public abstract class Projectile extends VisibleEntity {

	private int moveDirection;
	private float dmg;

	public Projectile(int x, int y, int moveSpeed, int moveDirection, int size,
			float dmg, int radius, int[] img) {
		super(x, y, moveSpeed, size, img);
		this.dmg = dmg;
		this.moveDirection = moveDirection;
	}

	/**
	 * checks for collisions with entities as well as walls
	 */
	@Override
	public boolean move(int xMovement, int yMovement) {
		boolean hit = !super.move(xMovement, yMovement);
		int idx = 0;
		while (hit == false && idx < super.getWorld().getNumEnemies()) {
			Enemy e = super.getWorld().getEnemy(idx);
			if (x - super.getSize() / 2 < e.x + e.getSize() / 2
					&& x + super.getSize() / 2 > e.x - e.getSize() / 2
					&& y - super.getSize() / 2 < e.y + e.getSize() / 2
					&& y + super.getSize() / 2 > e.y - e.getSize() / 2) {

				hit = true;
			}
			idx++;
		}
		return hit;
	}

	/**
	 * @return the enemy that was hit, if any
	 */
	public Enemy getHitEnemy() {
		int idx = 0;
		while (idx < super.getWorld().getNumEnemies()) {
			Enemy e = super.getWorld().getEnemy(idx);
			if (x - super.getSize() / 2 < e.x + e.getSize() / 2
					&& x + super.getSize() / 2 > e.x - e.getSize() / 2
					&& y - super.getSize() / 2 < e.y + e.getSize() / 2
					&& y + super.getSize() / 2 > e.y - e.getSize() / 2) {

				return super.getWorld().getEnemy(idx);
			}
			idx++;
		}
		return null;
	}

	public int getMoveDirection() {
		return moveDirection;
	}

	public float getDamage() {
		return dmg;
	}

	public abstract void update();

}
