package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JOptionPane;

import algorithms.AStar;
import entities.Enemy;
import entities.Player;
import entities.Projectile;
import entities.VisibleEntity;
import map.CannonTower;
import map.EmptyTower;
import map.FloorTile;
import map.Map;
import map.ObjectiveTile;
import map.SpawnerTile;
import map.WallTile;
import raycasting.Ray;
import raycasting.Raycasting;

/**
 * @author Rui Z.
 * @about controls all of the updating / rendering of the main game
 */
public class GSRunning {

	public static float[] betaPreRendered;
	private int[] screenPixels;
	// precalculates the distance to each row on the lower half of the screen
	private float[] preRenderedZRows;
	private BufferedImage buffer;
	public static final int TILE_SIZE = 64;

	// building mode variables
	private boolean buildingMode;
	private int size;

	Player p;

	private Map level;

	public GSRunning() {
		Raycasting.initializeTables();
		// preloading array used to correct fisheye effect, to correct the
		// slanted distance
		betaPreRendered = new float[MainPanel.SCREEN_WIDTH + 1];
		float deg;
		for (int i = -Raycasting.DEG30; i <= Raycasting.DEG30; i++) {
			deg = (float) Math.toRadians(i * Raycasting.angleBetweenRays);
			betaPreRendered[i + Raycasting.DEG30] = (float) Math.cos(deg);
		}
		preRenderedZRows = new float[MainPanel.SCREEN_HEIGHT / 2];
		// the middle row is infinitely far away, so it is set at the maximum
		// value
		preRenderedZRows[0] = Float.MAX_VALUE;
		for (int i = 1; i < MainPanel.SCREEN_HEIGHT / 2; i++)
			// using similar triangles to find the distance to the row (head on)
			// dist to row x= dist to projection plane* tile_size/(2*row)
			preRenderedZRows[i] = (float) (277.0 * (TILE_SIZE) / i);
		buffer = new BufferedImage(MainPanel.SCREEN_WIDTH, MainPanel.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		screenPixels = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
	}

	public void setLevel() {
		buildingMode = true;
		level = new Map(17, 17);
		AStar.setWorld(level);
		p = new Player(TILE_SIZE / 2 + level.getObjectiveX() * TILE_SIZE,
				TILE_SIZE / 2 + level.getObjectiveY() * TILE_SIZE);
		level.spawnPlayer(p);
		int spawnerX;
		int spawnerY;
		do {
			spawnerX = (int) (Math.random() * (level.getX() - 2)) + 1;
			spawnerY = (int) (Math.random() * (level.getY() - 2)) + 1;
		} while (Math.abs(spawnerX - level.getObjectiveX()) < 4 && Math.abs(spawnerY - level.getObjectiveY()) < 4);
		SpawnerTile spawner = new SpawnerTile(spawnerX, spawnerY, level);
		level.setTile(spawner, spawner.getX(), spawner.getY());
		// building mode configurations
		int sizeX = MainPanel.SCREEN_WIDTH / level.getX();
		int sizeY = MainPanel.SCREEN_HEIGHT / level.getY();
		if (sizeX < sizeY) {
			size = sizeX;
		} else {
			size = sizeY;
		}
		level.initializeRound();
	}

	private boolean finishedBuilding = false;

	public void update() {
		if (level.complete()) {
			MainPanel.changeGameState(GameState.WON);
			return;
		}
		if (InputHandler.getKeyState(KeyEvent.VK_ESCAPE)) {
			InputHandler.lockKeyState(KeyEvent.VK_ESCAPE);
			MainPanel.changeGameState(GameState.INGAMEMENU);
		}
		if (level.roundComplete())
			if (!finishedBuilding)
				buildingMode = true;
		if (buildingMode) {
			if (InputHandler.getKeyState(KeyEvent.VK_ENTER)) {
				InputHandler.lockKeyState(KeyEvent.VK_ENTER);
				buildingMode = false;
				finishedBuilding = true;
				p.setLocation(TILE_SIZE / 2 + level.getObjectiveX() * TILE_SIZE,
						TILE_SIZE / 2 + level.getObjectiveY() * TILE_SIZE);
			}
			if (InputHandler.getDraggedState() || InputHandler.getClickedState()) {
				int xIndex = InputHandler.getMouseX() / size;
				int yIndex = InputHandler.getMouseY() / size;
				if (level.isValidSpot(xIndex, yIndex))
					if (InputHandler.getButton() == MouseEvent.BUTTON1 && p.getResource() >= 200
							&& level.getTile(xIndex, yIndex) instanceof FloorTile) {

						level.setTile(new EmptyTower(), xIndex, yIndex);
						// checking if with the current level layout there is a
						// possible path.
						if (AStar.findPath(level.getSpawnX(), level.getSpawnY(), level.getObjectiveX(),
								level.getObjectiveY()).isEmpty()) {
							level.setTile(new FloorTile(), xIndex, yIndex);
							JOptionPane.showMessageDialog(null, "Path For Enemies is Blocked!");
							InputHandler.lockMouseState();
						} else {
							p.changeResource(-EmptyTower.COST);
						}
					} else if (InputHandler.getButton() == MouseEvent.BUTTON3
							&& level.getTile(xIndex, yIndex) instanceof EmptyTower) {
						level.setTile(new FloorTile(), xIndex, yIndex);
						p.changeResource(EmptyTower.COST);
					}

			}
		} else {
			if (InputHandler.getKeyState(KeyEvent.VK_Q)) {
				p.setInventory(-1);
				InputHandler.lockKeyState(KeyEvent.VK_Q);
			}
			if (InputHandler.getKeyState(KeyEvent.VK_E)) {
				InputHandler.lockKeyState(KeyEvent.VK_E);
				p.setInventory(1);
			}
			if (InputHandler.getMouseX() >= 448 && InputHandler.getMouseX() <= 640 && InputHandler.getMouseY() >= 250
					&& InputHandler.getMouseY() <= 314 && InputHandler.getClickedState()) {
				InputHandler.lockMouseState();
				level.startNextRound();
				finishedBuilding = false;
			}
			if (level.getObjectiveHealth() <= 0)
				MainPanel.changeGameState(GameState.GAMEOVER);
			p.isMovingForwards = InputHandler.getKeyState(KeyEvent.VK_W);
			p.isMovingBackwards = InputHandler.getKeyState(KeyEvent.VK_S);
			p.turningLeft = InputHandler.getKeyState(KeyEvent.VK_A);
			p.turningRight = InputHandler.getKeyState(KeyEvent.VK_D);
			if (InputHandler.getKeyState(KeyEvent.VK_SPACE)) {
				p.shoot();
			}
			// sin = y, cos = x
			// standard
			// sin+|sin+ cos-|cos+
			// sin-|sin- cos-|cos+
			//
			// java
			// 90 sin-|sin- cos-|cos+
			// ___sin+|sin+ cos-|cos+
			/**
			 * In order to obtain this setup with the Cartesian grid (in order
			 * to match java co ords of down +, up-, right+, left-), you must
			 * use the -angle of the standard cos/sin operations, hence the
			 * DEG360-playerAngle
			 */
			p.update();
			level.update();
		}
	}

	// array created by wall rendering that also helps with rendering sprites.
	// stores distance to wall at column of index on SCREEN
	float[] zBuffer = new float[MainPanel.SCREEN_WIDTH];

	public void renderWalls() {
		int angle = p.playerAngle + Raycasting.DEG30;
		if (angle < 0)
			angle += Raycasting.DEG360;
		else if (angle > Raycasting.DEG360)
			angle -= Raycasting.DEG360;
		// casts a ray for each column of the screen
		for (int col = 0; col < MainPanel.SCREEN_WIDTH; col++) {
			// correct distance = distorted distance * cos(BETA)
			// used to find the x component of the ray cast onto the projection
			// screen
			// BETA= angle in the 60 deg of pov. Can use col as index
			Ray r = Raycasting.castRay(p, angle);
			float correctDistance = r.getLength() * betaPreRendered[col];
			zBuffer[col] = correctDistance;
			// using similar triangles, find the slice height
			// slice height = actual height/distance to slice * distance to
			// screen
			int wallHeight = (int) Math.ceil((38200) / correctDistance);
			int texCol = 0;
			// calculating the column of the wall relative to the new height
			if (r.isHorizontal()) {
				texCol = (int) Math.abs(r.gethY() % TILE_SIZE * wallHeight / TILE_SIZE);
			} else {
				texCol = (int) Math.abs(r.getvX() % TILE_SIZE * wallHeight / TILE_SIZE);
			}
			// renders wall slice
			if (wallHeight < Integer.MAX_VALUE && wallHeight >= 0) {
				renderImageSlice(wallHeight, texCol, col, TILE_SIZE, level
						.getTile((int) r.getShortestX() / TILE_SIZE, (int) r.getShortestY() / TILE_SIZE).getSprite());
			}
			// renders floor below wall slice if the column has space for it
			if (wallHeight < MainPanel.SCREEN_HEIGHT)
				// starts below the wall column pixel
				for (int i = (MainPanel.SCREEN_HEIGHT + wallHeight) / 2; i < MainPanel.SCREEN_HEIGHT; i++) {
					// finds the distance on the x-y plane (parallel to floor)
					// from the player to the texture pixel location in the
					// world
					float currentDist = preRenderedZRows[i - MainPanel.SCREEN_HEIGHT / 2]
							/ Raycasting.cosPreRendered[Math.abs(p.playerAngle - angle)];
					float xPos = (p.x + (currentDist * Raycasting.cosPreRendered[angle]));
					float yPos = (p.y - (currentDist * Raycasting.sinPreRendered[angle]));
					int xTex = (int) xPos % TILE_SIZE;
					int yTex = (int) yPos % TILE_SIZE;
					if (yTex < 0)
						yTex += TILE_SIZE;
					if (xTex < 0)
						xTex += TILE_SIZE;
					int[] currentTileMap = level.getTile((int) xPos / TILE_SIZE, (int) yPos / TILE_SIZE).getSprite();
					screenPixels[i * MainPanel.SCREEN_WIDTH + col] = currentTileMap[yTex * TILE_SIZE + xTex];
					// draws the floor pixels at the same time, since the x* y
					// co ordinates are already calculated
					screenPixels[MainPanel.SCREEN_WIDTH * MainPanel.SCREEN_HEIGHT - i * MainPanel.SCREEN_WIDTH
							+ col] = ImageHandler.getTexture(3)[yTex * TILE_SIZE + xTex];
				}
			angle--;
			if (angle < 0)
				angle += Raycasting.DEG360;
			else if (angle > Raycasting.DEG360)
				angle -= Raycasting.DEG360;
		}
	}

	public void renderSprite(VisibleEntity e) {
		// sprite rendering
		float x = e.x - p.x;
		float y = e.y - p.y;
		float angleBetweenSprite = e.getAngleBetween();
		if (angleBetweenSprite < 45 && angleBetweenSprite > -45) {
			float spriteOnScreen = 30 + angleBetweenSprite;
			float dist = (float) (Math.sqrt(x * x + y * y) * (float) Math.cos(Math.toRadians(spriteOnScreen - 30)));
			int spriteSize = (int) (Math.ceil(38200 / dist));
			int startColumn = (int) ((spriteOnScreen / Raycasting.angleBetweenRays)) - spriteSize / 2;
			int endColumn = startColumn + spriteSize;
			int shift = 0;
			if (startColumn < 0) {
				shift = Math.abs(startColumn);
				startColumn = 0;
			}
			if (endColumn >= MainPanel.SCREEN_WIDTH)
				endColumn = MainPanel.SCREEN_WIDTH;
			for (int i = startColumn; i < endColumn; i++) {
				if (zBuffer[i] > dist) {
					if (e instanceof Enemy)
						renderImageSlice(spriteSize, i - startColumn + shift, i, TILE_SIZE, ((Enemy) e).getSprite());
					else if (e instanceof Projectile)
						renderImageSlice(spriteSize, i - startColumn + shift, i, TILE_SIZE, e.getSprite());
				}
			}
		}

	}

	/**
	 * Scales the image given the old height and the new height
	 * 
	 * @param imageHeight
	 *            the new height of the image
	 * @param imageCol
	 *            the column on the new image to scale
	 * @param screenCol
	 *            the column on the screen to render onto
	 * @param originalHeight
	 *            the original height of the image
	 * @param originalImage
	 *            the integer array that stores the rgb data for each pixel
	 */
	public void renderImageSlice(int imageHeight, int imageCol, int screenCol, int originalHeight,
			int[] originalImage) {
		float scale = (float) imageHeight / originalHeight;
		int start = 0;
		int end = imageHeight;

		int screenStart = (MainPanel.SCREEN_HEIGHT - imageHeight) / 2;
		// height of column is larger than the screen
		if (imageHeight > MainPanel.SCREEN_HEIGHT) {
			start = (imageHeight - MainPanel.SCREEN_HEIGHT) / 2;
			end = start + MainPanel.SCREEN_HEIGHT;
			screenStart = 0;
		}
		for (int i = start; i < end; i++) {
			int x = i * imageHeight + imageCol;
			int newIndex = (int) (x % imageHeight / scale) + originalHeight * (int) (x / (imageHeight * scale));
			if (newIndex >= 0) {
				blendPixel(originalImage[newIndex], (screenStart + (i - start)) * MainPanel.SCREEN_WIDTH + screenCol);
			}
		}
	}

	/**
	 * Performs Alpha Compositing on the pixel co ordinate selected
	 * 
	 * @param x
	 *            the x position of the pixel on screen
	 * @param y
	 *            the y position of the pixel on screen
	 * @param newData
	 *            the foreground pixel that you are blending onto the background
	 */
	public void blendPixel(int pixel, int i) {
		int a1 = (pixel >> 24) & 0xff;
		int r1 = (pixel >> 16) & 0xff;
		int g1 = (pixel >> 8) & 0xff;
		int b1 = pixel & 0xff;
		int a2 = (screenPixels[i] >> 24) & 0xff;
		int r2 = (screenPixels[i] >> 16) & 0xff;
		int g2 = (screenPixels[i] >> 8) & 0xff;
		int b2 = screenPixels[i] & 0xff;

		int an = a1 + (a2 * (255 - a1) / 255);
		int rn = (r1 * a1 / 255) + (r2 * a2 * (255 - a1) / (255 * 255));
		int gn = (g1 * a1 / 255) + (g2 * a2 * (255 - a1) / (255 * 255));
		int bn = (b1 * a1 / 255) + (b2 * a2 * (255 - a1) / (255 * 255));
		screenPixels[i] = (an << 24) + (rn << 16) + (gn << 8) + bn;
	}

	public void render(Graphics g) {
		if (buildingMode) {
			for (int i = 0; i < level.getY(); i++)
				for (int j = 0; j < level.getX(); j++) {
					if (level.getTile(j, i) instanceof EmptyTower) {
						g.setColor(Color.GRAY);
						g.fillRect(j * size, i * size, size, size);
					} else if (level.getTile(j, i) instanceof WallTile) {
						g.setColor(Color.GREEN);
						g.fillRect(j * size, i * size, size, size);
					} else if (level.getTile(j, i) instanceof SpawnerTile) {
						g.setColor(Color.RED);
						g.fillRect(j * size, i * size, size, size);
					} else if ((level.getTile(j, i) instanceof ObjectiveTile)) {
						g.setColor(Color.BLUE);
						g.fillRect(j * size, i * size, size, size);
					} else if ((level.getTile(j, i) instanceof CannonTower)) {
						g.setColor(Color.ORANGE);
						g.fillRect(j * size, i * size, size, size);
					}
					g.setColor(Color.GREEN);
					g.drawRect(j * size, i * size, size, size);
				}

			g.setFont(MainPanel.pixelFont.deriveFont(30f));
			g.drawString("RESOURCES: " + p.getResource(), 400, 30);
			g.drawString("PROTECT THE BLUE ", 400, 70);
			g.drawString("SQUARE FROM ENEMIES ", 400, 100);
			g.drawString("COMING FROM THE RED ", 400, 130);
			g.drawString("SQUARE.", 400, 160);
			g.drawString("LEFT CLICK TO PLACE A", 400, 190);
			g.drawString("TOWER BLOCK. RIGHT ", 400, 220);
			g.drawString("CLICK TO REMOVE IT.", 400, 250);
			g.drawString("PRESS ENTER TO START!", 400, 370);
			g.setColor(Color.RED);
			g.drawString("BE CAREFUL!", 400, 280);
			g.drawString("YOU CAN ONLY DO THIS", 400, 310);
			g.drawString("ONCE PER ROUND!", 400, 340);

		} else {
			// clears screen
			for (int i = 0; i < MainPanel.SCREEN_WIDTH * MainPanel.SCREEN_HEIGHT; i++)
				screenPixels[i] = 0;

			renderWalls();
			int pIdx = 0;
			int eIdx = 0;
			while (pIdx < level.getNumProjectiles() && eIdx < level.getNumEnemies()) {
				if (level.getProjectile(pIdx).getDistToPlayer() > level.getEnemy(eIdx).getDistToPlayer()) {
					renderSprite(level.getProjectile(pIdx));
					pIdx++;
				} else {
					renderSprite(level.getEnemy(eIdx));
					eIdx++;
				}
			}
			while (eIdx < level.getNumEnemies()) {
				renderSprite(level.getEnemy(eIdx));
				eIdx++;
			}
			while (pIdx < level.getNumProjectiles()) {
				renderSprite(level.getProjectile(pIdx));
				pIdx++;
			}
			g.drawImage(buffer, 0, 0, MainPanel.SCREEN_WIDTH, MainPanel.SCREEN_HEIGHT, null);

			for (int i = 0; i < level.getY(); i++) {
				for (int j = 0; j < level.getX(); j++) {
					g.setColor(Color.GREEN);
					g.drawRect(450 + j * 8, i * 8, 8, 8);
					if (level.getTile(j, i) instanceof EmptyTower) {
						g.setColor(Color.GRAY);
						g.fillRect(450 + j * 8, i * 8, 8, 8);
					} else if (level.getTile(j, i) instanceof SpawnerTile) {
						g.setColor(Color.RED);
						g.fillRect(450 + j * 8, i * 8, 8, 8);
					} else if (level.getTile(j, i) instanceof ObjectiveTile) {
						g.setColor(Color.BLUE);
						g.fillRect(450 + j * 8, i * 8, 8, 8);
					} else if (level.getTile(j, i) instanceof CannonTower) {
						g.setColor(Color.ORANGE);
						g.fillRect(450 + j * 8, i * 8, 8, 8);
					} else if (level.getTile(j, i) instanceof WallTile) {
						g.fillRect(450 + j * 8, i * 8, 8, 8);
					}
				}

			}
			g.fillOval(450 + (p.x - TILE_SIZE / 2) * 8 / TILE_SIZE, (p.y - TILE_SIZE / 2) * 8 / TILE_SIZE, 8, 8);
			g.setColor(Color.RED);
			g.fillRect(216, 25, (int) (197 * level.getObjectiveHealth() / 1000f), 21);
			// ui
			g.drawImage(ImageHandler.getImage(4), 0, 0, null);
			g.drawImage(ImageHandler.getImage(3), p.getInventory() * 50 + MainPanel.SCREEN_WIDTH / 2 - 150, 320, null);
			g.setFont(MainPanel.pixelFont.deriveFont(30f));
			g.drawString("RESOURCES: " + p.getResource(), 450, MainPanel.SCREEN_HEIGHT / 2 - 25);
			g.drawString("ROUND " + (level.getCurrentRound() + 1) + "/" + level.maxNumberOfRounds(), 450,
					MainPanel.SCREEN_HEIGHT / 2 + 5);
			if (level.roundComplete()) {
				g.drawImage(ImageHandler.getImage(6), MainPanel.SCREEN_WIDTH - 192, 250, null);
			}

		}
	}
}