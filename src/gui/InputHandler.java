package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public class InputHandler extends MouseAdapter implements KeyListener {
	// an array that checks if the key is off or on(pressed or not)
	private static boolean[] keysPressed = new boolean[123];
	private static int mouseX;
	private static int mouseY;
	private static boolean clicked;
	private static boolean dragged;
	private static int button;
	public static int LEFT_BUTTON = 1;
	public static int MIDDLE_BUTTON = 2;
	public static int RIGHT_BUTTON = 3;

	/**
	 * @param keyCode
	 *            the key
	 * @return if the key is pressed or not
	 */
	public static boolean getKeyState(int keyCode) {
		if (keyCode >= 0 && keyCode < keysPressed.length)
			return keysPressed[keyCode];
		return false;
	}

	public static void lockKeyState(int keyCode) {
		if (keyCode >= 0 && keyCode < keysPressed.length)
			keysPressed[keyCode] = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key >= 0 && key < keysPressed.length)
			keysPressed[key] = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key >= 0 && key < keysPressed.length)
			keysPressed[key] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static int getButton() {
		return button;
	}

	public static boolean getDraggedState() {
		return dragged;
	}

	public static boolean getClickedState() {
		return clicked;
	}

	public static void lockMouseState() {
		clicked = false;
		mouseX = -1;
		mouseY = -1;
	}

	public static int getMouseX() {
		return mouseX;
	}

	public static int getMouseY() {
		return mouseY;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX() / MainPanel.SCALE;
		mouseY = e.getY() / MainPanel.SCALE;
		dragged = true;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (SwingUtilities.isLeftMouseButton(e))
			button = LEFT_BUTTON;
		else if (SwingUtilities.isMiddleMouseButton(e))
			button = MIDDLE_BUTTON;
		else if (SwingUtilities.isRightMouseButton(e))
			button = RIGHT_BUTTON;
		clicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		clicked = false;
		dragged = false;
	}

}
