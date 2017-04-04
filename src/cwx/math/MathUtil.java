package cwx.math;

import cwx.view.panels.game.graphics.Camera;

/**
 * A class that provides some basic Math Utility methods
 */
public class MathUtil {
	
	/**
	 * Returns the Transformation Matrix based on the provided location rotation and scale
	 * @param location The location of the matrix
	 * @param rotation The rotation of the matrix
	 * @param scale The scale of the matrix
	 * @return The Transformation Matrix formed of the above components
	 */
	public static Matrix4f getTransformationMatrix(Vector3f location, Vector3f rotation, Vector3f scale) {
		// Creates an identity Matrix
		Matrix4f matrix = Matrix4f.getIdentity();
		
		// Performs the operations on the matrix
		return matrix.translateMatrix(location).rotateMatrix(new Vector3f(1, 0, 0), rotation.getX()).rotateMatrix(new Vector3f(0, 1, 0), rotation.getY()).rotateMatrix(new Vector3f(0, 0, 1), rotation.getZ()).scaleMatrix(scale);
	}
	
	/**
	 * Returns the View Matrix of the specified Camera
	 * @param camera The Camera that will be used to produce the View Matrix
	 * @return The View Matrix of the Camera
	 */
	public static Matrix4f getViewMatrix(Camera camera) {
		// Creates an identity Matrix
		Matrix4f matrix = Matrix4f.getIdentity();
		
		// Stores the vectors to simplify the next line
		Vector3f location = camera.getLocation(), rotation = camera.getRotation();
		
		// Performs the operations on the matrix
		return matrix.rotateMatrix(new Vector3f(1, 0, 0), rotation.getX()).rotateMatrix(new Vector3f(0, 1, 0), rotation.getY()).rotateMatrix(new Vector3f(0, 0, 1), rotation.getZ()).translateMatrix(location.clone().setX(-location.getX()).setY(-location.getY()));
	}
}
