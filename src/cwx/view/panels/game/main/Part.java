package cwx.view.panels.game.main;

import cwx.math.AABB;
import cwx.math.Vector3f;

public class Part {

	protected Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	private boolean visible;
	private PartAction action;
	private Vector3f colour;
	private AABB aabb;
	
	/**
	 * Initialises the Part object
	 * @param translation The translation of the Part
	 * @param rotation The rotation of the Part
	 * @param scale The scale of the Part
	 * @param visible If the Part is visible or not
	 * @param action The action of the Part
	 */
	public Part(Vector3f translation, Vector3f rotation, Vector3f scale, boolean visible, PartAction action) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
		this.visible = visible;
		this.action = action;
		aabb = new AABB();
		aabb.setScale(scale);
		if (action != null)
			action.setPart(this);
	}
	
	/**
	 * Returns the action of the Part
	 * @return The PartAction
	 */
	public PartAction getPartAction() {
		return action;
	}
	
	/**
	 * Returns the colour of the Part
	 * @return The colour vector
	 */
	public Vector3f getColour() {
		return colour;
	}
	
	/**
	 * Sets the colour of the Part
	 * @param colour The new colour of the Part
	 */
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
	/**
	 * Sets if the Part is visible or not
	 * @param visible The new visibility of the Part
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Returns if the Part is visible or not
	 * @return The visibility of the Part
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Returns the action of the Part
	 * @return The PartAction
	 */
	public PartAction getAction() {
		return action;
	}
	
	/**
	 * Returns the translation of the Part
	 * @return The translation Vector
	 */
	public Vector3f getTranslation() {
		return translation;
	}
	
	/**
	 * Returns the rotation of the Part
	 * @return The rotation Vector
	 */
	public Vector3f getRotation() {
		return rotation;
	}
	
	/**
	 * Returns the scale of the Part
	 * @return The scale Vector
	 */
	public Vector3f getScale() {
		return scale;
	}
	
	/**
	 * Returns the Axis-Aligned Bounding Box of the Part
	 * @return The AABB
	 */
	public AABB getAABB() {
		return aabb;
	}
}
