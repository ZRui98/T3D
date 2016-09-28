package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * @author Rui Z.
 * @about controls all of the updating / rendering of the in game menu
 */
public class GSInGameMenu {
	private String[] options = { "Main Menu", "Return", "Exit" };
	private int selectedOption;

	public void update() {
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
				MainPanel.changeGameState(GameState.MENU);
			} else if (selectedOption == 1)
				MainPanel.changeGameState(GameState.RUNNING);
			else if (selectedOption == 2)
				MainPanel.changeGameState(GameState.CLOSE);
		}

	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		for (int i = 0; i < options.length; i++) {
			if (i == selectedOption) {
				g.setFont(MainPanel.pixelFont.deriveFont(80f));
				g.drawString(">" + options[i],
						MainPanel.SCREEN_WIDTH / 2 - 110, i * 50
								+ MainPanel.SCREEN_HEIGHT / 3);
			} else {
				g.setFont(MainPanel.pixelFont.deriveFont(60f));
				g.drawString(options[i], MainPanel.SCREEN_WIDTH / 2 - 85, i
						* 50 + MainPanel.SCREEN_HEIGHT / 3);
			}
		}
	}
}
