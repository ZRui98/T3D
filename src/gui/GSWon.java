package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * @author Rui Z.
 * @about controls all of the updating / rendering of the in winning screen
 */
public class GSWon {

	public void update() {
		if (InputHandler.getKeyState(KeyEvent.VK_ENTER)) {
			InputHandler.lockKeyState(KeyEvent.VK_ENTER);
			MainPanel.changeGameState(GameState.MENU);
		}
	}

	public void render(Graphics g) {
		g.drawImage(ImageHandler.getImage(7), 0, 0, null);
		g.setFont(MainPanel.pixelFont.deriveFont(80f));
		g.setColor(Color.WHITE);
		g.drawString("VICTORY", MainPanel.SCREEN_WIDTH / 2 - 100, 100);
		g.setFont(MainPanel.pixelFont.deriveFont(30f));
		g.drawString("Congratulations!", MainPanel.SCREEN_WIDTH / 2 - 230, 190);
		g.drawString("You have successfully defended the objective!",
				MainPanel.SCREEN_WIDTH / 2 - 230, 230);
		g.setColor(Color.RED);
		g.drawString("PRESS ENTER KEY TO RETURN TO MENU",
				MainPanel.SCREEN_WIDTH / 2 - 180, 350);
	}
}
