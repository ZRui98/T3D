package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 
 * @author Rui Z.
 * @about Loads all the images
 */
public class ImageHandler {
	private static ArrayList<int[]> textures;
	private static ArrayList<BufferedImage> images;

	public static void loadImages() {
		textures = new ArrayList<int[]>();
		BufferedImage i = null;
		// 0
		try {
			i = ImageIO.read(new File("res//sprites//wall.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load wall.png");
		}
		// 1
		try {
			i = ImageIO.read(new File("res//sprites//empty_tower.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load empty_tower.png");
		}
		// 2
		try {
			i = ImageIO.read(new File("res//sprites//floor.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load floor.png");
		}

		// 3
		try {
			i = ImageIO.read(new File("res//sprites//ceiling.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load ceiling.png");
		}
		// 4
		try {
			i = ImageIO.read(new File("res//sprites//cannon_tower.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load cannon_tower.png");
		}
		// 5
		try {
			i = ImageIO.read(new File("res//sprites//cannon_projectile.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load cannon_projectile.png");
		}

		// 6
		try {
			i = ImageIO.read(new File("res//sprites//electric_projectile.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load electric_projectile.png");
		}
		// 7
		try {
			i = ImageIO.read(new File("res//sprites//electric_tower.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load electric_tower.png");
		}

		// 8
		try {
			i = ImageIO.read(new File("res//sprites//objective.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load objective.png");
		}
		// 9
		try {
			i = ImageIO.read(new File("res//sprites//spawner_tile.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load spawner_tile.png");
		}
		// 10
		try {
			i = ImageIO.read(new File("res//sprites//alien.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load alien.png");
		}
		// 11
		try {
			i = ImageIO.read(new File("res//sprites//alienw.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load alienw.png");
		}
		// 12
		try {
			i = ImageIO.read(new File("res//sprites//alienw2.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load alienw2.png");
		}
		// 13
		try {
			i = ImageIO.read(new File("res//sprites//frozen_alien.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load frozen_alien.png");
		}
		// 14
		try {
			i = ImageIO.read(new File("res//sprites//frozen_alienw.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load frozen_alienw.png");
		}
		// 15
		try {
			i = ImageIO.read(new File("res//sprites//frozen_alienw2.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load frozen_alienw2.png");
		}
		// 16
		try {
			i = ImageIO.read(new File("res//sprites//spider.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load spider.png");
		}
		// 17
		try {
			i = ImageIO.read(new File("res//sprites//spiderw.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load spiderw.png");
		}
		// 18
		try {
			i = ImageIO.read(new File("res//sprites//spiderw2.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load spiderw2.png");
		}
		// 19
		try {
			i = ImageIO.read(new File("res//sprites//frozen_spider.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load frozen_spider.png");
		}
		// 20
		try {
			i = ImageIO.read(new File("res//sprites//frozen_spiderw.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load frozen_spiderw.png");
		}
		// 21
		try {
			i = ImageIO.read(new File("res//sprites//frozen_spiderw2.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load frozen_spiderw2.png");
		}
		// 22
		try {
			i = ImageIO.read(new File("res//sprites//ice_projectile.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load ice_projectile.png");
		}
		// 23
		try {
			i = ImageIO.read(new File("res//sprites//freezing_tower.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load freezing_tower.png");
		}
		// 24
		try {
			i = ImageIO.read(new File("res//sprites//robot.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load robot.png");
		}
		// 25
		try {
			i = ImageIO.read(new File("res//sprites//robotw.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load robotw.png");
		}
		// 26
		try {
			i = ImageIO.read(new File("res//sprites//robotw2.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load robotw2.png");
		}
		// 27
		try {
			i = ImageIO.read(new File("res//sprites//frozen_robot.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load frozen_robot.png");
		}
		// 28
		try {
			i = ImageIO.read(new File("res//sprites//frozen_robotw.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load frozen_robotw.png");
		}
		// 29
		try {
			i = ImageIO.read(new File("res//sprites//frozen_robotw2.png"));
			int[] tex = new int[i.getHeight() * i.getWidth()];
			for (int p = 0; p < i.getWidth() * i.getHeight(); p++) {
				tex[p] = i.getRGB(p % i.getWidth(), p / i.getWidth());
			}
			textures.add(tex);
		} catch (IOException e) {
			System.out.println("Failed to load frozen_robotw2.png");
		}
		// loading images
		images = new ArrayList<BufferedImage>();
		try {
			i = ImageIO.read(new File("res//images//title_screen.png"));
			images.add(i);
		} catch (IOException e) {
			System.out.println("Failed to load title_screen.png");
		}
		try {
			i = ImageIO.read(new File("res//images//objective_screen.png"));
			images.add(i);
		} catch (IOException e) {
			System.out.println("Failed to load objective_screen.png");
		}
		try {
			i = ImageIO.read(new File("res//images//controls_screen.png"));
			images.add(i);
		} catch (IOException e) {
			System.out.println("Failed to load controls_screen.png");
		}
		try {
			i = ImageIO.read(new File("res//images//inventory_selector.png"));
			images.add(i);
		} catch (IOException e) {
			System.out.println("Failed to load inventory_selector.png");
		}
		try {
			i = ImageIO.read(new File("res//images//ui.png"));
			images.add(i);
		} catch (IOException e) {
			System.out.println("Failed to load ui.png");
		}
		try {
			i = ImageIO.read(new File("res//images//game_over.png"));
			images.add(i);
		} catch (IOException e) {
			System.out.println("Failed to load game_over.png");
		}
		try {
			i = ImageIO.read(new File("res//images//round_button.png"));
			images.add(i);
		} catch (IOException e) {
			System.out.println("Failed to load round_button.png");
		}
		try {
			i = ImageIO.read(new File("res//images//win_screen.png"));
			images.add(i);
		} catch (IOException e) {
			System.out.println("Failed to load win_screen.png");
		}
		try {
			i = ImageIO.read(new File("res//images//credits.png"));
			images.add(i);
		} catch (IOException e) {
			System.out.println("Failed to load credits.png");
		}
	}

	public static BufferedImage getImage(int id) {
		return images.get(id);
	}

	public static int[] getTexture(int id) {
		return textures.get(id);
	}
}