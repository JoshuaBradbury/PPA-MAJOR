package cwx.view.panels.game.main;

import cwx.math.Vector3f;
import cwx.view.panels.game.graphics.Text;

public class TextPart extends Part {

	private Text text;
	
	/**
	 * Initialises the TextPart object
	 * @param text The Text object of the Part
	 * @param translation The translation of the Part
	 * @param rotation The rotation of the Part
	 * @param scale The scale of the Part
	 * @param visible Whether the Part is visible or not
	 * @param action The PartAction of the Part
	 */
	public TextPart(Text text, Vector3f translation, Vector3f rotation, Vector3f scale, boolean visible, PartAction action) {
		super(translation, rotation, scale, visible, action);
		this.text = text;
	}

	/**
	 * Returns the Text object of the Part
	 * @return The Text Object
	 */
	public Text getText() {
		return text;
	}
	
	/**
	 * Returns the Scale of the Part
	 * @return The scale of the Text object instead of the actual Part
	 */
	@Override
	public Vector3f getScale() {
		return text.getScale();
	}
}
