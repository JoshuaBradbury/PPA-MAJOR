package cwx.math;

public class Vector3f {

	private float x, y, z;

	/**
	 * The default constructor that initialises the variables
	 */
	public Vector3f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	/**
	 * A simple constructor that sets all 3 values to the same value
	 * @param i The value of all the components
	 */
	public Vector3f(float i) {
		this.x = i;
		this.y = i;
		this.z = i;
	}

	/**
	 * A simple constructor that sets all 3 values to their own values
	 * @param x The x value
	 * @param y The y value
	 * @param z The z value
	 */
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the x component
	 * @return the x component
	 */
	public float getX() {
		return x;
	}

	/**
	 * Returns the y component
	 * @return the y component
	 */
	public float getY() {
		return y;
	}

	/**
	 * Returs the z component
	 * @return z component
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the x component
	 * @param x The new x component
	 * @return The vector
	 */
	public Vector3f setX(float x) {
		this.x = x;
		return this;
	}
	
	/**
	 * Sets the y component
	 * @param y The new y component
	 * @return The vector
	 */
	public Vector3f setY(float y) {
		this.y = y;
		return this;
	}

	/**
	 * Sets the z component
	 * @param z The new z component
	 * @return The vector
	 */
	public Vector3f setZ(float z) {
		this.z = z;
		return this;
	}

	/**
	 * Adds the vector to a specified vector
	 * @param loc The vector to add
	 * @return The vector with the additions
	 */
	public Vector3f add(Vector3f loc) {
		return add(loc.x, loc.y, loc.z);
	}

	/**
	 * Adds the specified values to the vector
	 * @param x The x value to add
	 * @param y The y value to add
	 * @param z The z value to add
	 * @return The vector with the additions
	 */
	public Vector3f add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * Subtracts the vector to a specified vector
	 * @param loc The vector to subtract
	 * @return The vector with the subtractions
	 */
	public Vector3f subtract(Vector3f loc) {
		return subtract(loc.x, loc.y, loc.z);
	}

	/**
	 * Subtracts the specified values to the vector
	 * @param x The x value to subtract
	 * @param y The y value to subtract
	 * @param z The z value to subtract
	 * @return The vector with the subtractions
	 */
	public Vector3f subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	/**
	 * Multiplies the components by the specified values
	 * @param x The value to multiply the x component by
	 * @param y The value to multiply the y component by
	 * @param z The value to multiply the z component by
	 * @return The vector after the multiplications
	 */
	public Vector3f multiply(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	/**
	 * Returns a new clone of the Vector
	 * @return The clone
	 */
	@Override
	public Vector3f clone() {
		return new Vector3f(x, y, z);
	}
}
