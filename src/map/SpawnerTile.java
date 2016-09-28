package map;

import java.util.ArrayList;

import entities.AlienEnemy;
import entities.BugEnemy;
import entities.Enemy;
import entities.RobotEnemy;
import gui.GSRunning;
import gui.ImageHandler;

/**
 * @author Rui Z.
 * @about a tile on the floor of the map that the enemies come from
 */
public class SpawnerTile extends MapTile {

	private ArrayList<ArrayList<Enemy>> enemiesToSpawn;
	private Map world;
	private int x;
	private int y;
	private int currentRound;

	public SpawnerTile(int x, int y, Map world) {
		super(ImageHandler.getTexture(9), false);
		this.x = x;
		this.y = y;
		enemiesToSpawn = new ArrayList<ArrayList<Enemy>>();
		this.world = world;
		currentRound = -1;
	}

	/**
	 * Adds enemies into the spawner tile
	 * 
	 * @param e
	 *            the type of enemy to add
	 * @param round
	 *            the round that the enemy will spawn in
	 */
	public void addEnemy(char e, int round) {
		Enemy tempEnemy = null;
		if (e == 'a') {
			tempEnemy = new AlienEnemy(x * GSRunning.TILE_SIZE
					+ GSRunning.TILE_SIZE / 2, y * GSRunning.TILE_SIZE
					+ GSRunning.TILE_SIZE / 2);
		} else if (e == 's') {
			tempEnemy = new BugEnemy(x * GSRunning.TILE_SIZE
					+ GSRunning.TILE_SIZE / 2, y * GSRunning.TILE_SIZE
					+ GSRunning.TILE_SIZE / 2);
		} else if (e == 'r') {
			tempEnemy = new RobotEnemy(x * GSRunning.TILE_SIZE
					+ GSRunning.TILE_SIZE / 2, y * GSRunning.TILE_SIZE
					+ GSRunning.TILE_SIZE / 2);
		}
		while (round >= enemiesToSpawn.size()) {
			enemiesToSpawn.add(new ArrayList<Enemy>());
		}
		enemiesToSpawn.get(round).add(tempEnemy);
	}

	/**
	 * spawns the next round
	 */
	public void spawnNextRound() {
		if (currentRound < enemiesToSpawn.size() && roundComplete())
			currentRound++;
	}

	/**
	 * @return if the spawner is out of enemies to spawn
	 */
	public boolean doneSpawning() {
		return currentRound >= enemiesToSpawn.size() - 1
				&& enemiesToSpawn.get(currentRound).size() == 0;
	}

	/**
	 * @return whether the current round is complete or not
	 */
	public boolean roundComplete() {
		if (currentRound >= 0 && currentRound < enemiesToSpawn.size())
			return enemiesToSpawn.get(currentRound).size() == 0;
		return true;
	}

	/**
	 * @return the total number of rounds stored in the spawner
	 */
	public int totalNumberOfRounds() {
		return enemiesToSpawn.size();
	}

	/**
	 * @return the current round that is being spawned
	 */
	public int getCurrentRound() {
		return currentRound;
	}

	// timer variable for the last spawn that occurred
	private long lastSpawn;

	/**
	 * updates the spawner
	 */
	public void update() {
		if (currentRound >= 0 && currentRound < enemiesToSpawn.size()
				&& enemiesToSpawn.get(currentRound).size() > 0) {
			long now = System.currentTimeMillis();
			if (now - lastSpawn > 3000) {
				lastSpawn = now;
				Enemy e = enemiesToSpawn.get(currentRound).remove(0);
				world.addEnemy(e);
			}
		}
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
}
