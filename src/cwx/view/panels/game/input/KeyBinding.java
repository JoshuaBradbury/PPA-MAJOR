package cwx.view.panels.game.input;

import java.util.HashMap;
import java.util.Map;

public class KeyBinding {

	public static final int KEYBOARD_SIZE = 0xFFFF;

	private static Map<String, Integer> keyBindings = new HashMap<String, Integer>();

	private static boolean[] pressing = new boolean[KEYBOARD_SIZE];
	private static boolean[] down = new boolean[KEYBOARD_SIZE];
	private static boolean[] releasing = new boolean[KEYBOARD_SIZE];

	/**
	 * Binds the specified key as the specified function
	 * @param function The function of the binding
	 * @param key The key that is bound
	 */
	public static void bindKey(String function, int key) {
		keyBindings.put(function, key);
	}

	/**
	 * Returns if the specified key is down
	 * @param key The key to be checked
	 * @return If the specified key is down
	 */
	public static boolean isKeyDown(int key) {
		return Keyboard.KEYBOARD.isKeyDown(key);
	}

	/**
	 * Returns the key bound to the specified function
	 * @param function The function to check
	 * @return The key bound to the function
	 */
	public static int getBinding(String function) {
		return keyBindings.get(function);
	}

	/**
	 * Updates the state of the keys so we know if they are pressing down or releasing
	 */
	public static void update() {
		for (int i = 0; i < KEYBOARD_SIZE; i++) {
			boolean kd = isKeyDown(i);
			if (!pressing[i] && !down[i] && kd) {
				pressing[i] = true;
			} else if (pressing[i] && !down[i] && kd) {
				pressing[i] = false;
				down[i] = true;
			} else if (down[i] && !releasing[i] && !kd) {
				down[i] = false;
				releasing[i] = true;
			} else if (!down[i] && releasing[i] && !kd)
				releasing[i] = false;

			if (pressing[i] && releasing[i]) releasing[i] = false;
			if (releasing[i] && down[i]) down[i] = false;
			if (pressing[i] && down[i]) down[i] = false;
		}
	}

	/**
	 * Returns if the specified binding is currently down
	 * @param function The function to check
	 * @return If the function is down
	 */
	public static boolean isBindingDown(String function) {
		int id = getBinding(function);
		return down[id];
	}
}
