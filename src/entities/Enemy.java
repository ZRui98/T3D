package entities;

import java.awt.Point;
import java.util.Stack;

import algorithms.AStar;
import gui.GSRunning;

/**
 * @author Rui Z.
 * @about basic enemy class
 */
public class Enemy extends VisibleEntity {
	public float hp;
	private Stack<Point> path;
	private boolean isWalking;
	private int[][] walkingAnimation;
	private int[] frozenSprite;
	private int[][] frozenAnimation;
	private float damage;
	private boolean isFrozen;

	public Enemy(int hp, int x, int y, int moveSpeed, int size, float damage,
			int[] img, int[] img2, int[] img3, int[] img4, int[] img5,
			int[] img6) {
		super(x, y, moveSpeed, size, img);
		this.hp = hp;
		this.damage = damage;
		walkingAnimation = new int[2][];
		walkingAnimation[0] = img2;
		walkingAnimation[1] = img3;
		frozenAnimation = new int[2][];
		frozenSprite = img4;
		frozenAnimation[0] = img5;
		frozenAnimation[1] = img6;
	}

	private long lastTick;
	private int animationTick;

	@Override
	/**
	 *  @return the sprite given conditions on status/ animation tick
	 */
	public int[] getSprite() {
		if (isFrozen) {
			if (!isWalking)
				return frozenSprite;
			else if (System.currentTimeMillis() - lastTick >= 1000) {
				lastTick = System.currentTimeMillis();
				animationTick = ++animationTick % frozenAnimation.length;
			}
			return frozenAnimation[animationTick];
		}
		if (!isWalking)
			return super.getSprite();
		else if (System.currentTimeMillis() - lastTick >= 500) {
			lastTick = System.currentTimeMillis();
			animationTick = ++animationTick % walkingAnimation.length;
		}
		return walkingAnimation[animationTick];
	}

	/**
	 * freezes the enemy
	 */
	public void freeze() {
		if (!isFrozen) {
			isFrozen = true;
			super.setMoveSpeed(super.getMoveSpeed() / 2);
		}
	}

	public void findPath() {
		isWalking = true;
		path = AStar.findPath((int) (x / GSRunning.TILE_SIZE),
				(int) (y / GSRunning.TILE_SIZE), super.getWorld()
						.getObjectiveX(), super.getWorld().getObjectiveY());
	}

	public void takeDamage(float dmg) {
		hp -= dmg;
		if (hp <= 0)
			super.markForRemoval();
	}

	/**
	 * updates the enemies variable, as well as any damage it does
	 */
	public void update() {
		// calculates variables used for rendering.
		super.calculateAngleBetweenSprite();
		// following the path found by the A* algorithm
		if (!path.isEmpty()) {
			if (path.peek().x == x && path.peek().y == y) {
				path.pop();
			} else {
				int xMove = 0;
				int yMove = 0;
				if (x < path.peek().x) {
					xMove++;
				} else if (x > path.peek().x) {
					xMove--;
				}
				if (y < path.peek().y) {
					yMove++;
				} else if (y > path.peek().y) {
					yMove--;
				}
				super.move(xMove * super.getMoveSpeed(),
						yMove * super.getMoveSpeed());
			}
			super.calculateAngleBetweenSprite();
		} else {
			isWalking = false;
		}
		// if the enemy is in range to damage the objective
		if (x / GSRunning.TILE_SIZE == world.getObjectiveX()
				&& y / GSRunning.TILE_SIZE == world.getObjectiveY()) {
			world.damageObjective(damage);
		}
	}

}
