package cwx.view.panels.game.main;

import cwx.math.Vector2f;
import cwx.math.Vector3f;

public class SlidePart extends Part {

	private Vector3f lastMousePos;
	private boolean grabbed;
	private byte direction;
	private Vector2f bounds;
	
	public static final byte X = 0x01, Y = 0x02, Z = 0x04;
	
	/**
	 * Initialises the SliderPart object
	 * @param direction The direction Vector
	 * @param bounds The limits of the slide as a Vector
	 * @param translation The translation Vector
	 * @param rotation The rotation Vector
	 * @param scale The scale Vector
	 * @param visible Whether the Part is visible or not
	 * @param action The PartAction of the Part
	 */
	public SlidePart(byte direction, Vector2f bounds, Vector3f translation, Vector3f rotation, Vector3f scale, boolean visible, PartAction action) {
		super(translation, rotation, scale, visible, action);
		this.direction = direction;
		this.bounds = bounds;
	}
	
	/**
	 * Starts the slide action
	 * @param lastMousePos The last position of the Mouse
	 */
	public void startSlide(Vector3f lastMousePos) {
		this.lastMousePos = lastMousePos.clone();
		grabbed = true;
	}
	
	/**
	 * Whether the Part is grabbed or not
	 * @return If the Part is grabbed
	 */
	public boolean isGrabbed() {
		return grabbed;
	}
	
	/**
	 * Updates the position of the Slide based on the new location of the mouse
	 * @param mousePosition The new position of the Mouse
	 */
	public void updateMousePos(Vector3f mousePosition) {
		if (grabbed) {
			this.getTranslation().add(mousePosition.clone().subtract(lastMousePos).multiply(direction & X, direction & Y, direction & Z));
			if ((direction & X) > 0 && this.getTranslation().getX() < bounds.getX())
				getTranslation().setX(bounds.getX());
			if ((direction & X) > 0 && this.getTranslation().getX() > bounds.getY())
				getTranslation().setX(bounds.getY());
			
			if ((direction & Y) > 0 && this.getTranslation().getY() < bounds.getX())
				getTranslation().setY(bounds.getX());
			if ((direction & Y) > 0 && this.getTranslation().getY() > bounds.getY())
				getTranslation().setY(bounds.getY());
			
			if ((direction & Z) > 0 && this.getTranslation().getZ() < bounds.getX())
				getTranslation().setZ(bounds.getX());
			if ((direction & Z) > 0 && this.getTranslation().getZ() > bounds.getY())
				getTranslation().setZ(bounds.getY());
		}
		
		lastMousePos = mousePosition.clone();
	}
	
	/**
	 * Stops the slide action
	 */
	public void stopSlide() {
		grabbed = false;
	}
}
