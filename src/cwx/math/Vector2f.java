package cwx.math;

/**
 * A simplified representation of a 2D Vector of floats
 */

public class Vector2f {

	private float x, y;

	/**
	 * The default constructor to initialise the variables
	 */
	public Vector2f() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * A constructor that takes in a value of each the x and y
	 * @param x The new x value
	 * @param y The new y value
	 */
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the value of the x component
	 * @return The value of x
	 */
	public float getX() {
		return x;
	}

	/**
	 * Returns the value of the y component
	 * @return The value of y
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of the x component
	 * @param x The new value of x
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Sets the value of the y component
	 * @param y The new value of y
	 */
	public void setY(float y) {
		this.y = y;
	}
}
