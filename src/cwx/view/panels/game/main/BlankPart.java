package cwx.view.panels.game.main;

import cwx.math.Vector3f;

public class BlankPart extends Part {
	
	/**
	 * Initialises the BlankPart object
	 * @param translation The translation Vector
	 * @param rotation The rotation Vector
	 * @param scale The scale Vector
	 * @param visible Whether the Part is visible or not
	 */
	public BlankPart(Vector3f translation, Vector3f rotation, Vector3f scale, boolean visible) {
		super(translation, rotation, scale, visible, null);
	}
}
