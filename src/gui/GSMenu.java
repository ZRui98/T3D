package gui;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * @author Rui Z.
 * @about controls all of the updating / rendering of the main menu
 */
public class GSMenu {
	private String[] options = { "Start", "Credits", "Instructions", "Exit" };
	private int selectedOption;
	private boolean instructionsOpen;
	private boolean creditsOpen;
	private int instructionPage;

	public void update() {
		if (instructionsOpen) {
			if (InputHandler.getKeyState(KeyEvent.VK_LEFT)
					|| InputHandler.getClickedState()
					&& InputHandler.getMouseX() >= 20
					&& InputHandler.getMouseX() <= 70
					&& InputHandler.getMouseY() >= 345
					&& InputHandler.getMouseY() <= 365) {
				InputHandler.lockMouseState();
				InputHandler.lockKeyState(KeyEvent.VK_LEFT);
				instructionsOpen = false;
			}
			if (InputHandler.getKeyState(KeyEvent.VK_RIGHT)
					|| InputHandler.getClickedState()
					&& InputHandler.getMouseX() >= 515
					&& InputHandler.getMouseX() <= 615
					&& InputHandler.getMouseY() >= 345
					&& InputHandler.getMouseY() <= 365) {
				InputHandler.lockMouseState();
				InputHandler.lockKeyState(KeyEvent.VK_RIGHT);
				instructionPage = (instructionPage + 1) % 2;
			}
		} else if (creditsOpen) {
			if (InputHandler.getKeyState(KeyEvent.VK_ENTER)) {
				InputHandler.lockKeyState(KeyEvent.VK_ENTER);
				creditsOpen = false;
			}
		} else {
			if (InputHandler.getKeyState(KeyEvent.VK_W)
					|| InputHandler.getKeyState(KeyEvent.VK_UP)) {
				InputHandler.lockKeyState(KeyEvent.VK_W);
				InputHandler.lockKeyState(KeyEvent.VK_UP);
				selectedOption = (selectedOption + options.length - 1)
						% options.length;
			} else if (InputHandler.getKeyState(KeyEvent.VK_S)
					|| InputHandler.getKeyState(KeyEvent.VK_DOWN)) {
				InputHandler.lockKeyState(KeyEvent.VK_S);
				InputHandler.lockKeyState(KeyEvent.VK_DOWN);
				selectedOption = (selectedOption + 1) % options.length;
			} else if (InputHandler.getKeyState(KeyEvent.VK_ENTER)) {
				InputHandler.lockKeyState(KeyEvent.VK_ENTER);
				if (selectedOption == 0) {
					MainPanel.changeGameState(GameState.NEWGAME);
				} else if (selectedOption == 1) {
					creditsOpen = true;
				} else if (selectedOption == 2) {
					instructionsOpen = true;
				} else if (selectedOption == 3)
					MainPanel.changeGameState(GameState.CLOSE);
			}

		}
	}

	public void render(Graphics g) {
		if (instructionsOpen) {
			if (instructionPage == 0)
				g.drawImage(ImageHandler.getImage(1), 0, 0, null);
			else if (instructionPage == 1)
				g.drawImage(ImageHandler.getImage(2), 0, 0, null);
		} else if (creditsOpen) {
			g.drawImage(ImageHandler.getImage(8), 0, 0, null);
		} else {
			g.drawImage(ImageHandler.getImage(0), 0, 0, null);
			g.setColor(Color.RED);
			for (int i = 0; i < options.length; i++) {
				if (i == selectedOption) {
					g.setFont(MainPanel.pixelFont.deriveFont(80f));
					g.drawString(">" + options[i],
							MainPanel.SCREEN_WIDTH / 2 - 110, i * 50
									+ MainPanel.SCREEN_HEIGHT / 5 * 2 + 50);
				} else {
					g.setFont(MainPanel.pixelFont.deriveFont(60f));
					g.drawString(options[i], MainPanel.SCREEN_WIDTH / 2 - 85, i
							* 50 + MainPanel.SCREEN_HEIGHT / 5 * 2 + 50);
				}
			}
		}
	}
}
