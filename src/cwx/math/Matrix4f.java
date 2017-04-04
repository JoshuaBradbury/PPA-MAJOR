package cwx.math;

import java.nio.FloatBuffer;

import cwx.view.panels.game.graphics.OpenGLScreen;

/**
 * A class that represents a 4x4 Matrix of floats
 */
public class Matrix4f {

	// The actual floats
	private float[] matrix;
	
	/**
	 * The default constructor that just initialises the array - private as it is not used outside of this class
	 */
	private Matrix4f() {
		matrix = new float[16];
	}
	
	/**
	 * Returns a new identity Matrix
	 * @return The identity Matrix
	 */
	public static Matrix4f getIdentity() {
		// Creates a new Matrix
		Matrix4f matrix = new Matrix4f();
		
		// Fills the diagonal with 1s
		matrix.set(0, 0, 1);
		matrix.set(1, 1, 1);
		matrix.set(2, 2, 1);
		matrix.set(3, 3, 1);
		
		return matrix;
	}
	
	/**
	 * Returns a new translation Matrix
	 * @param translation The translation of the Matrix
	 * @return The translation Matrix
	 */
	public static Matrix4f getTranslationMatrix(Vector3f translation) {
		// Creates a new identity Matrix
		Matrix4f identity = Matrix4f.getIdentity();
		
		// Sets the values of the Matrix with the values of the translation
		identity.set(3, 0, identity.get(3, 0) + translation.getX());
		identity.set(3, 1, identity.get(3, 1) + translation.getY());
		identity.set(3, 2, identity.get(3, 2) + translation.getZ());
		
		return identity;
	}
	
	/**
	 * Returns the translation Vector of the Matrix
	 * @return The trainslation Vector of the Matrix
	 */
	public Vector3f getTranslationVector() {
		return new Vector3f(get(3, 0), get(3, 1), get(3, 2));
	}
	
	/**
	 * Returns the rotation Matrix after the rotation around the specified axis by the specified amount of degrees
	 * @param axis The axis which the rotation is performed around
	 * @param rotationInDegrees The roation in degrees
	 * @return The rotation Matrix
	 */
	//Right handed system - flip sign of sin to be Left handed
	public static Matrix4f getRotationMatrix(Vector3f axis, float rotationInDegrees) {
		Matrix4f identity = Matrix4f.getIdentity();
		
		float cos = (float) Math.cos(Math.toRadians(rotationInDegrees)),
			  sin = (float) Math.sin(Math.toRadians(rotationInDegrees));
		
		if (axis.getX() != 0) {
			Matrix4f xIdentity = Matrix4f.getIdentity();
			xIdentity.set(1, 1,  cos);
			xIdentity.set(2, 1, -sin);
			xIdentity.set(1, 2,  sin);
			xIdentity.set(2, 2,  cos);
			identity = identity.multiply(xIdentity);
		} else if (axis.getY() != 0) {
			Matrix4f yIdentity = Matrix4f.getIdentity();
			yIdentity.set(0, 0,  cos);
			yIdentity.set(2, 0,  sin);
			yIdentity.set(0, 2, -sin);
			yIdentity.set(2, 2,  cos);
			identity = identity.multiply(yIdentity);
		} else if (axis.getZ() != 0) {
			Matrix4f zIdentity = Matrix4f.getIdentity();
			zIdentity.set(1, 1,  cos);
			zIdentity.set(2, 1, -sin);
			zIdentity.set(1, 2,  sin);
			zIdentity.set(2, 2,  cos);
			identity = identity.multiply(zIdentity);
		}
		return identity;
	}
	
	/**
	 * Returns the scale Matrix
	 * @param scale The scale of the matrix
	 * @return The scale Matrix
	 */
	public static Matrix4f getScaleMatrix(Vector3f scale) {
		Matrix4f identity = getIdentity();
		identity.set(0, 0, scale.getX());
		identity.set(1, 1, scale.getY());
		identity.set(2, 2, scale.getZ());
		return identity;
	}
	
	/**
	 * Returns the projection Matrix
	 * @param fov The Filed Of View
	 * @param zFar The near clipping plane on the z axis
	 * @param zNear The far clipping plane on the z axis
	 * @return The projection Matrix
	 */
	public static Matrix4f getProjectionMatrix(float fov, float zFar, float zNear) {
		float aspectRatio = (float) OpenGLScreen.WIDTH / (float) OpenGLScreen.HEIGHT, zDiff = zFar - zNear, zSum = zFar + zNear;
		Matrix4f projection = new Matrix4f();
		projection.set(0, 0, (float) (1 / (Math.tan(Math.toRadians(fov / 2)))) / aspectRatio);
		projection.set(1, 1, 1 / (float) (Math.tan(Math.toRadians(fov / 2))));
		projection.set(2, 2, -zSum / zDiff);
		projection.set(2, 3, -1);
		projection.set(3, 2, -(2 * zFar * zNear) / zDiff);
		return projection;
	}
	
	/**
	 * Returns the Matrix as a FloatBuffer
	 * @return The FloatBuffer
	 */
	public FloatBuffer getAsFloatBuffer() {
		// Generates and fills the buffer
		FloatBuffer buffer = FloatBuffer.allocate(16);
		for (int i = 0; i < matrix.length; i++) {
			buffer.put(matrix[i]);
		}
		
		// Flips the buffer so that it is ready to be read from
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Returns the Matrix scaled by the specified Vector
	 * @param scale The Vector to scale the Matrix by
	 * @return The scaled Matrix
	 */
	public Matrix4f scaleMatrix(Vector3f scale) {
		Matrix4f mat = getScaleMatrix(scale);
		return multiply(mat);
	}
	
	/**
	 * Rotates the Matrix around the specified axis
	 * @param axis The axis of rotation
	 * @param rotationInDegrees The rotation in degrees
	 * @return The rotated Matrix
	 */
	public Matrix4f rotateMatrix(Vector3f axis, float rotationInDegrees) {
		Matrix4f mat = getRotationMatrix(axis, rotationInDegrees);
		return multiply(mat);
	}
	
	/**
	 * Translates the Matrix
	 * @param translation The Vector to translate the Matrix by
	 * @return The translated Matrix
	 */
	public Matrix4f translateMatrix(Vector3f translation) {
		Matrix4f mat = getTranslationMatrix(translation);
		return multiply(mat);
	}
	
	/**
	 * Adds the Matrix to the specified Matrix
	 * @param mat The Matrix to add
	 * @return The sum of the two Matricies as a new Matrix
	 */
	public Matrix4f add(Matrix4f mat) {
		Matrix4f matrixOut = new Matrix4f();
		
		for (int i = 0; i < 16; i++) {
			matrixOut.matrix[i] = matrix[i] + mat.matrix[i];
		}
		
		return matrixOut;
	}
	
	/**
	 * Multiplies the Matrix by the specified Matrix
	 * @param mat The Matrix to multiply it with
	 * @return The product of the Matricies
	 */
	public Matrix4f multiply(Matrix4f mat) {
		Matrix4f outMatrix = new Matrix4f();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				Vector4f mult = getRow(y).multiply(mat.getCol(x));
				outMatrix.set(x, y, mult.getX() + mult.getY() + mult.getZ() + mult.getW());
			}
		}
		return outMatrix;
	}
	
	/**
	 * Returns the specified row of the Matrix
	 * @param y The row to get
	 * @return The specified row
	 */
	private Vector4f getRow(int y) {
		float[] row = new float[4];
		for (int x = 0; x < 4; x++) {
			row[x] = get(x, y);
		}
		return new Vector4f(row[0], row[1], row[2], row[3]);
	}
	
	/**
	 * Returns the specified column of the Matrix
	 * @param x The column to get
	 * @return The specified column
	 */
	private Vector4f getCol(int x) {
		float[] col = new float[4];
		for (int y = 0; y < 4; y++) {
			col[y] = get(x, y);
		}
		return new Vector4f(col[0], col[1], col[2], col[3]);
	}
	
	/**
	 * Returns a clone of the Matrix
	 * @return A clone of the Matrix
	 */
	@Override
	public Matrix4f clone() {
		Matrix4f mat = new Matrix4f();
		for (int i = 0; i < 16; i++) {
			mat.matrix[i] = matrix[i];
		}
		return mat;
	}
	
	/**
	 * Sets the specified value in the Matrix
	 * @param x The x coord in the Matrix
	 * @param y The y coord in the Matrix
	 * @param value The value to set
	 */
	//Column Major
	public void set(int x, int y, float value) {
		matrix[y + x * 4] = value;
	}
	
	/**
	 * Returns the specified value in the Matrix
	 * @param x The x coord in the Matrix
	 * @param y The y coord in the Matrix
	 * @return The value from the Matrix
	 */
	public float get(int x, int y) {
		return matrix[y + x * 4];
	}

	/**
	 * Multiplies the Matrix by the Vector
	 * @param vec4 The Vector to multiply the Matrix by
	 * @return The Multiplied Matrix
	 */
	public Vector4f multiply(Vector4f vec4) {
		return new Vector4f(vec4.getX() * get(0, 0) + vec4.getY() * get(1, 0) + vec4.getZ() * get(2, 0) + vec4.getW() * get(3, 0),
							vec4.getX() * get(0, 1) + vec4.getY() * get(1, 1) + vec4.getZ() * get(2, 1) + vec4.getW() * get(3, 1),
							vec4.getX() * get(0, 2) + vec4.getY() * get(1, 2) + vec4.getZ() * get(2, 2) + vec4.getW() * get(3, 2),
							vec4.getX() * get(0, 3) + vec4.getY() * get(1, 3) + vec4.getZ() * get(2, 3) + vec4.getW() * get(3, 3));
	}
}
