package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * 
 * @author Rui Z.
 * @about Class that contains the canvas & builds the frame
 */
public class MainPanel extends Canvas implements Runnable {

	private static GSRunning runningScreen;
	private GSMenu menuScreen;
	private GSInGameMenu igMenuScreen;
	private GSOver gameOverScreen;
	private GSWon winScreen;
	private boolean running;
	private boolean paused = false;
	private InputHandler inputHandler;
	public static Font pixelFont;
	private final JFrame w;
	private BufferStrategy bStrat;

	// GUI constants
	public static final int SCREEN_HEIGHT = 400;
	public static final int SCREEN_WIDTH = 640;
	private static final long serialVersionUID = 1L;
	public static final int SCALE = 1;

	private static GameState state;

	public MainPanel() {
		state = GameState.MENU;
		setBackground(new Color(0, 0, 0, 0));
		setVisible(true);
		w = new JFrame();
		setPreferredSize(new Dimension((int) (SCREEN_WIDTH * SCALE),
				(int) (SCREEN_HEIGHT * SCALE)));
		w.add(this);
		w.pack();
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setLocationRelativeTo(null);
		w.setVisible(true);
		ImageHandler.loadImages();
		inputHandler = new InputHandler();
		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT,
					new File("res//fonts//alterebro-pixel-font.ttf"))
					.deriveFont(Font.BOLD, 40);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addKeyListener(inputHandler);
		addMouseListener(inputHandler);
		addMouseMotionListener(inputHandler);
		setFocusable(true);
		requestFocus();
		menuScreen = new GSMenu();
		runningScreen = new GSRunning();
		igMenuScreen = new GSInGameMenu();
		gameOverScreen = new GSOver();
		winScreen = new GSWon();
		running = true;
	}

	/**
	 * runs the program
	 */
	public static void main(String[] args) {
		new Thread(new MainPanel()).start();
	}

	/**
	 * changes gamestate
	 * 
	 * @param g
	 *            gamestate to switch to
	 */
	public static void changeGameState(GameState g) {
		if (g == GameState.NEWGAME) {
			runningScreen.setLevel();
			state = GameState.RUNNING;
		} else
			state = g;
	}

	/**
	 * updates respective gamestate
	 */
	public void update() {
		switch (state) {

		case RUNNING: {
			runningScreen.update();
			break;
		}
		case MENU: {
			menuScreen.update();
			break;
		}
		case INGAMEMENU: {
			igMenuScreen.update();
			break;
		}
		case WON: {
			winScreen.update();
			break;
		}
		case GAMEOVER: {
			gameOverScreen.update();
			break;
		}
		case CLOSE: {
			running = false;
			break;
		}
		default:
			break;
		}
	}

	/**
	 * draws onto canvas
	 */
	public void render() {
		bStrat = getBufferStrategy();
		if (bStrat == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bStrat.getDrawGraphics();
		g.scale(SCALE, SCALE);
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		switch (state) {

		case RUNNING: {
			runningScreen.render(g);
			g.setFont(pixelFont);
			g.setColor(Color.RED);
			g.drawString("FPS:" + Integer.toString(FPS), 5, 25);
			break;
		}
		case MENU: {
			menuScreen.render(g);
			break;
		}
		case INGAMEMENU: {
			igMenuScreen.render(g);
			break;
		}
		case WON: {
			winScreen.render(g);
			break;
		}
		case GAMEOVER: {
			gameOverScreen.render(g);
			break;
		}
		case CLOSE: {
			break;
		}
		default:
			break;
		}
		g.dispose();
		bStrat.show();
	}

	private double missedFrames, now, past = 0;
	private int FPS;

	@Override
	public void run() {
		// Game loop code from: http://www.koonsolo.com/news/dewitters-gameloop/
		final double BEST_TIME = 1_000_000_000L / 60.0;// ns per desired fps
		past = System.nanoTime();
		double lastCheck = System.nanoTime();
		// Game Loop
		int fps = 0;
		while (running) {
			if (!paused) {
				now = System.nanoTime();
				missedFrames += (now - past) / BEST_TIME;// time passed between
															// the
															// loop in 1/60th to
															// get
															// the
															// amount of frames
															// that
															// was supposed to
															// be
															// rendered
				past = now;

				while (missedFrames >= 1) {// // Updates when the time exceeds
											// 1/60th
											// of a second
					update();
					fps++;
					missedFrames--;
				}
				render();
				while (System.nanoTime() - lastCheck >= 1000000000L) {
					lastCheck += 1000000000L;
					FPS = fps;
					fps = 0;
				}
			}
		}
		w.dispose();
	}
}