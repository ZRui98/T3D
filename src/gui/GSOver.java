package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * @author Rui Z.
 * @about controls all of the options / rendering of the game over screen
 */
public class GSOver {
	String[] options = { "RESTART", "MAIN MENU", "EXIT" };
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
			if (selectedOption == 0) {
				InputHandler.lockKeyState(KeyEvent.VK_ENTER);
				MainPanel.changeGameState(GameState.NEWGAME);
			} else if (selectedOption == 1) {
				InputHandler.lockKeyState(KeyEvent.VK_ENTER);
				MainPanel.changeGameState(GameState.MENU);
			} else if (selectedOption == 2)
				MainPanel.changeGameState(GameState.CLOSE);
		}
	}

	public void render(Graphics g) {
		g.drawImage(ImageHandler.getImage(5), 0, 0, null);
		g.setColor(Color.RED);
		g.setFont(MainPanel.pixelFont.deriveFont(100f));
		g.drawString("GAME OVER", MainPanel.SCREEN_WIDTH / 2 - 150,
				MainPanel.SCREEN_HEIGHT / 2 - 100);
		for (int i = 0; i < options.length; i++) {
			if (i == selectedOption) {
				g.setFont(MainPanel.pixelFont.deriveFont(80f));
				g.drawString(">" + options[i],
						MainPanel.SCREEN_WIDTH / 2 - 110, i * 50
								+ MainPanel.SCREEN_HEIGHT / 3 + 50);
			} else {
				g.setFont(MainPanel.pixelFont.deriveFont(60f));
				g.drawString(options[i], MainPanel.SCREEN_WIDTH / 2 - 85, i
						* 50 + MainPanel.SCREEN_HEIGHT / 3 + 50);
			}
		}
	}
}
