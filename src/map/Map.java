package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import entities.Enemy;
import entities.Player;
import entities.Projectile;
import gui.GSRunning;

/**
 * 
 * @author Rui Z.
 * @about Stores all the data for the map or level that the player is currently
 *        on
 */
public class Map {
	// map data
	private MapTile[][] map;
	// list of enemies in map
	private ArrayList<Enemy> eList = new ArrayList<Enemy>();
	// list of projectiles in map
	private ArrayList<Projectile> pList = new ArrayList<Projectile>();
	// list of towers in map
	private ArrayList<CannonTower> towerList = new ArrayList<CannonTower>();
	private Player player;
	private ObjectiveTile objective;
	SpawnerTile spawner;

	public Map(int x, int y) {
		// setting walls
		map = new MapTile[y][x];
		for (int i = 0; i < y; i++)
			for (int j = 0; j < x; j++)
				map[i][j] = new FloorTile();
		for (int i = 0; i < y; i++) {
			map[i][0] = new WallTile();
			map[i][x - 1] = new WallTile();
		}
		for (int i = 0; i < x; i++) {
			map[0][i] = new WallTile();
			map[y - 1][i] = new WallTile();
		}
		// random generation of objective tile
		int s = (int) (Math.random() * 4);
		int objectiveX = 0;
		int objectiveY = 0;
		if (s == 0) {
			objectiveX = 0;
			objectiveY = (int) (Math.random() * (map.length - 2)) + 1;
		} else if (s == 1) {
			objectiveY = 0;
			objectiveX = (int) (Math.random() * (map[0].length - 2)) + 1;
		} else if (s == 2) {
			objectiveX = map[0].length - 1;
			objectiveY = (int) (Math.random() * (map.length - 2)) + 1;
		} else {
			objectiveY = map.length - 1;
			objectiveX = (int) (Math.random() * (map[0].length - 2)) + 1;
		}
		objective = new ObjectiveTile(objectiveX, objectiveY);
		setTile(objective, objectiveX, objectiveY);
	}

	/**
	 * hardcoded rounds
	 */
	public void initializeRound() {
		// round 1
		spawner.addEnemy('a', 0);
		spawner.addEnemy('s', 0);
		spawner.addEnemy('s', 0);
		spawner.addEnemy('s', 0);
		spawner.addEnemy('a', 0);
		spawner.addEnemy('a', 0);
		// round 2
		spawner.addEnemy('s', 1);
		spawner.addEnemy('a', 1);
		spawner.addEnemy('a', 1);
		spawner.addEnemy('s', 1);
		spawner.addEnemy('a', 1);
		spawner.addEnemy('r', 1);
		spawner.addEnemy('a', 1);
		spawner.addEnemy('r', 1);
		// round 3
		spawner.addEnemy('a', 2);
		spawner.addEnemy('s', 2);
		spawner.addEnemy('r', 2);
		spawner.addEnemy('s', 2);
		spawner.addEnemy('s', 2);
		spawner.addEnemy('s', 2);
		spawner.addEnemy('a', 2);
		spawner.addEnemy('a', 2);
		spawner.addEnemy('a', 2);
		spawner.addEnemy('r', 2);
		// round 4
		spawner.addEnemy('a', 3);
		spawner.addEnemy('a', 3);
		spawner.addEnemy('a', 3);
		spawner.addEnemy('s', 3);
		spawner.addEnemy('s', 3);
		spawner.addEnemy('s', 3);
		spawner.addEnemy('r', 3);
		spawner.addEnemy('r', 3);
		spawner.addEnemy('a', 3);
		spawner.addEnemy('s', 3);
		spawner.addEnemy('s', 3);
		spawner.addEnemy('s', 3);
		spawner.addEnemy('r', 3);
		spawner.addEnemy('a', 3);
		// round 5
		spawner.addEnemy('a', 4);
		spawner.addEnemy('a', 4);
		spawner.addEnemy('a', 4);
		spawner.addEnemy('s', 4);
		spawner.addEnemy('s', 4);
		spawner.addEnemy('s', 4);
		spawner.addEnemy('r', 4);
		spawner.addEnemy('r', 4);
		spawner.addEnemy('a', 4);
		spawner.addEnemy('s', 4);
		spawner.addEnemy('s', 4);
		spawner.addEnemy('s', 4);
		spawner.addEnemy('r', 4);
		spawner.addEnemy('a', 4);
		spawner.addEnemy('a', 4);
		spawner.addEnemy('s', 4);
		// round 6
		spawner.addEnemy('a', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('r', 5);
		spawner.addEnemy('r', 5);
		spawner.addEnemy('a', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('a', 5);
		spawner.addEnemy('a', 5);
		spawner.addEnemy('r', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		spawner.addEnemy('s', 5);
		// round 7
		spawner.addEnemy('s', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('a', 6);
		spawner.addEnemy('a', 6);
		spawner.addEnemy('a', 6);
		spawner.addEnemy('r', 6);
		spawner.addEnemy('a', 6);
		spawner.addEnemy('a', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('a', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('r', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('s', 6);
		spawner.addEnemy('s', 6);
		// round 8
		spawner.addEnemy('s', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('a', 7);
		spawner.addEnemy('a', 7);
		spawner.addEnemy('a', 7);
		spawner.addEnemy('r', 7);
		spawner.addEnemy('a', 7);
		spawner.addEnemy('a', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('a', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('r', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('s', 7);
		spawner.addEnemy('s', 7);
	}

	/**
	 * Checks if current spot is within bounds
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return
	 */
	public boolean isValidSpot(int x, int y) {
		if (x >= map[0].length || y >= map.length || x < 0 || y < 0)
			return false;
		return true;
	}

	/**
	 * Sets the player in the current map
	 * 
	 * @param p
	 *            player to add
	 */
	public void spawnPlayer(Player p) {
		p.setWorld(this);
		this.player = p;
	}

	/**
	 * adds an enemy to this map
	 * 
	 * @param eToAdd
	 *            enemy to add
	 */
	public void addEnemy(Enemy eToAdd) {
		eToAdd.setWorld(this);
		eToAdd.findPath();
		eList.add(eToAdd);
	}

	/**
	 * returns an enemy from the list of enemies
	 * 
	 * @param idx
	 *            the index of the enemy in the array
	 * @return the specified enemy
	 */
	public Enemy getEnemy(int idx) {
		return eList.get(idx);
	}

	/**
	 * @return the total number of enemies in the map
	 */
	public int getNumEnemies() {
		return eList.size();
	}

	/**
	 * Adds a projectile to the world
	 * 
	 * @param pToAdd
	 *            the projectile to add
	 */
	public void addProjectile(Projectile pToAdd) {
		pToAdd.setWorld(this);
		pList.add(pToAdd);
	}

	/**
	 * returns a projectile from the list of projectiles
	 * 
	 * @param idx
	 *            the index of the projectile in the array
	 * @return the specified projectile
	 */
	public Projectile getProjectile(int idx) {
		return pList.get(idx);
	}

	/**
	 * 
	 * @return the total number of projectiles in the level
	 */
	public int getNumProjectiles() {
		return pList.size();
	}

	/**
	 * @param x
	 *            the x location of the tile
	 * @param y
	 *            the y location of the tile
	 * @return the MapTile of specified coordinates
	 */
	public MapTile getTile(int x, int y) {
		return map[y][x];
	}

	/**
	 * @return the player in this level
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the x size of this map
	 */
	public int getX() {
		return map[0].length;
	}

	/**
	 * @return the y size of this map
	 */
	public int getY() {
		return map.length;
	}

	/**
	 * Sets the tile at specified co ordinates
	 * 
	 * @param t
	 *            the tile to set in the map
	 * @param x
	 *            the x coordinate of the tile to add
	 * @param y
	 *            the y coordinate of the tile to add
	 */
	public void setTile(MapTile t, int x, int y) {
		if (getTile(x, y).equals(t))
			return;
		// removing a tower because the specified co ord currently holds a tower
		if (getTile(x, y) instanceof CannonTower) {
			towerList.remove(getTile(x, y));
		}
		// if tile to add is a tower, add it to the list
		if (t instanceof CannonTower) {
			towerList.add((CannonTower) t);
		}
		// sets the spawner in this map
		if (t instanceof SpawnerTile) {
			spawner = (SpawnerTile) t;
		}
		map[y][x] = t;
	}

	/**
	 * @return the health of the current objective in the level
	 */
	public float getObjectiveHealth() {
		return objective.getHealth();
	}

	/**
	 * @since the objective tile is in a wall, we must return a location that
	 *        would be able to walk to if the x co ord is in a wall
	 * @return the X location of the Objective
	 */
	public int getObjectiveX() {
		if (objective.getX() == 0)
			return 1;
		if (objective.getX() == getX() - 1)
			return objective.getX() - 1;
		return objective.getX();
	}

	/**
	 * @since the objective tile is in a wall, we must return a location that
	 *        would be able to walk to if the y co ord is in a wall
	 * @return the Y location of the Objective
	 */
	public int getObjectiveY() {
		if (objective.getY() == 0)
			return 1;
		if (objective.getY() == getY() - 1)
			return objective.getY() - 1;
		return objective.getY();
	}

	/**
	 * @return the spawn x location of the level
	 */
	public int getSpawnX() {
		return spawner.getX();
	}

	/**
	 * @return the spawn x location of the level
	 */
	public int getSpawnY() {
		return spawner.getY();
	}

	/**
	 * @return if the current round is complete or not
	 */
	public boolean roundComplete() {
		return spawner.roundComplete() && eList.isEmpty();
	}

	/**
	 * @return if level is complete
	 */
	public boolean complete() {
		return spawner.doneSpawning() && eList.isEmpty();
	}

	/**
	 * start spawning the next round of enemies
	 */
	public void startNextRound() {
		spawner.spawnNextRound();
	}

	/**
	 * @return the total number of rounds in the level
	 */
	public int maxNumberOfRounds() {
		return spawner.totalNumberOfRounds();
	}

	/**
	 * @return the round that the level is currently on
	 */
	public int getCurrentRound() {
		return spawner.getCurrentRound();
	}

	/**
	 * updates the map
	 */
	public void update() {
		spawner.update();
		for (Enemy e : eList) {
			e.update();
			if (e.x / GSRunning.TILE_SIZE == getObjectiveX() && e.y / GSRunning.TILE_SIZE == getObjectiveY()) {

			}
		}
		Iterator<Enemy> eIter = eList.iterator();
		while (eIter.hasNext()) {
			if (eIter.next().shouldBeRemoved()) {
				eIter.remove();
				player.changeResource(250);
			}
		}

		for (Projectile p : pList) {
			p.update();
		}
		Iterator<Projectile> pIter = pList.iterator();
		while (pIter.hasNext()) {
			if (pIter.next().shouldBeRemoved()) {
				pIter.remove();
			}
		}
		for (CannonTower t : towerList) {
			t.update();
		}
		Collections.sort(pList);
		Collections.sort(eList);
	}

	/**
	 * Does aoe damage
	 * 
	 * @param x
	 *            x coord of splash damage center
	 * @param y
	 *            y coord of splash damage center
	 * @param dmg
	 *            the damage that is to be taken
	 * @param radius
	 *            the radius of the splash damage
	 */
	public void damage(int x, int y, float dmg, int radius) {
		int radius2 = radius * radius;
		for (int i = 0; i < getNumEnemies(); i++) {
			Enemy e = getEnemy(i);
			int distance2 = ((x - e.x) * (x - e.x) + (y - e.y) * (y - e.y));
			if (distance2 <= radius2) {
				e.takeDamage(dmg);
			}
		}
	}

	/**
	 * deal damage to the objective
	 * 
	 * @param damage
	 *            the damage that the objective has taken
	 */
	public void damageObjective(float damage) {
		objective.takeDamage(damage);
	}

}
