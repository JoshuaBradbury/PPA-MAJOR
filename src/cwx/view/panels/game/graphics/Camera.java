package cwx.view.panels.game.graphics;

import cwx.math.Vector3f;

/**
 * A class to represent the Camera the world is viewed through
 */

public class Camera {
	
	private Vector3f location;
	private Vector3f rotation;
	
	/**
	 * Initialises the Camera with the location and rotation of it
	 * @param location The location of the Camera
	 * @param rotation The rotation of the Camera
	 */
	public Camera(Vector3f location, Vector3f rotation) {
		this.location = location;
		this.rotation = rotation;
		update();
	}
	
	/**
	 * Returns the location of the Camera
	 * @return The location of the Camera
	 */
	public Vector3f getLocation() {
		return location;
	}
	
	/**
	 * Returns the rotation of the Camera
	 * @return The rotation of the Camera
	 */
	public Vector3f getRotation() {
		return rotation;
	}
	
	/**
	 * Updates the location of the Camera so that it is pointing at the origin in a sphere of
	 * radius 4 around the centre
	 */
	public void update() {
		location.setX(-4f * (float) Math.sin(Math.toRadians(rotation.getY() % 360f)) * (float) Math.cos(Math.toRadians(rotation.getX() % 360f)));
		location.setZ(-4f * (float) Math.cos(Math.toRadians(rotation.getY() % 360f)) * (float) Math.cos(Math.toRadians(rotation.getX() % 360f)));
		location.setY( 4f * (float) Math.sin(Math.toRadians(rotation.getX() % 360f)));
	}
}
