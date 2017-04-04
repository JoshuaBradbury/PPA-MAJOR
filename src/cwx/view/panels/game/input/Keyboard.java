package cwx.view.panels.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Keyboard implements KeyListener {

	public static final Keyboard KEYBOARD = new Keyboard();
	
	private boolean[] keysReleasing = new boolean[0xFFFF];
	private boolean[] keysDown = new boolean[0xFFFF];
	private boolean[] keysPressed = new boolean[0xFFFF];
	
	/**
	 * This class is instantiated as a singleton
	 */
	private Keyboard() {}
	
	/**
	 * Updates the state of the keys
	 */
	public void update() {
		for (int i = 0; i < keysReleasing.length; i++) {
			if (keysReleasing[i]) {
				keysReleasing[i] = false;
				keysDown[i] = false;
			}
			keysPressed[i] = false;
		}
	}

	/**
	 * When a key is pressed
	 */
	@Override
	public void keyPressed(KeyEvent ke) {
		keysDown[ke.getKeyCode()] = true;
		keysPressed[ke.getKeyCode()] = true;
	}

	/**
	 * When a key is released
	 */
	@Override
	public void keyReleased(KeyEvent ke) {
		keysReleasing[ke.getKeyCode()] = true;
	}
	
	/**
	 * Returns if the specified key is currently down
	 * @param keycode The key to check
	 * @return If the key is down
	 */
	public boolean isKeyDown(int keycode) {
		return keysDown[keycode];
	}

	/**
	 * When a key is typed
	 */
	@Override
	public void keyTyped(KeyEvent e) {}
}
