package cwx.view.panels.game.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.jogamp.opengl.GL3;

import cwx.util.OpenGLUtil;

public class Loader {

	private static ArrayList<Integer> vaos = new ArrayList<Integer>(), vbos = new ArrayList<Integer>();
	
	/**
	 * Loads the specified data into a RawModel and returns it
	 * @param positions The positions of the vertices
	 * @param indices The indices of the vertices
	 * @param normals The normals of the vertices
	 * @return The RawModel
	 */
	public static RawModel loadToVAO(float[] positions, int[] indices, float[] normals) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 3, normals);
		bindIndicesBuffer(indices);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	/**
	 * Loads the specified data into a RawModel and returns it
	 * @param positions The positions of the vertices
	 * @param indices The indices of the vertices
	 * @param texCoords The texture coordinates of the vertices
	 * @param normals The normals of the vertices
	 * @return The RawModel
	 */
	public static RawModel loadToVAO(float[] positions, int[] indices, float[] texCoords, float[] normals) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, texCoords);
		storeDataInAttributeList(2, 3, normals);
		bindIndicesBuffer(indices);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	/**
	 * Loads the specified data into a RawModel and returns it
	 * @param positions The positions of the vertices
	 * @param indices The indexes of the vertices
	 * @return The RawModel
	 */
	public static RawModel loadToVAO(float[] positions, int[] indices) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, positions);
		bindIndicesBuffer(indices);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	/**
	 * Creates the VAO ID and returns it
	 * @return The VAO ID
	 */
	private static int createVAO() {
		int vaoID = OpenGLUtil.glGenVertexArrays();
		vaos.add(vaoID);
		OpenGLUtil.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	/**
	 * Creates the VBO ID and returns it
	 * @return The VBO ID
	 */
	private static int createVBO() {
		int vboID = OpenGLUtil.glGenBuffers();
		vbos.add(vboID);
		return vboID;
	}
	
	/**
	 * Cleans up the VAOs and VBOs by deleting them all
	 */
	public static void cleanup() {
		OpenGLUtil.glDeleteVertexArrays(vaos);
		OpenGLUtil.glDeleteBuffers(vbos);
	}
	
	/**
	 * Stores the data in the specified Attribute List
	 * @param attributeList The index of the attribute list
	 * @param size The size of the data
	 * @param data The data to store
	 */
	private static void storeDataInAttributeList(int attributeList, int size, float[] data) {
		int vboID = createVBO();
		OpenGLUtil.glBindBuffer(GL3.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		OpenGLUtil.glBufferData(GL3.GL_ARRAY_BUFFER, buffer, GL3.GL_STATIC_DRAW);
		
		OpenGLUtil.glVertexAttribPointer(attributeList, size, GL3.GL_FLOAT, false, 0, 0);
		
		OpenGLUtil.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Binds a VBO and loads the indices data into it
	 * @param indices The indices of the Model
	 */
	private static void bindIndicesBuffer(int[] indices) {
		int vboID = createVBO();
		OpenGLUtil.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, vboID);
		
		IntBuffer buffer = storeDataInIntBuffer(indices);
		OpenGLUtil.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, buffer, GL3.GL_STATIC_DRAW);
	}
	
	/**
	 * Stores the float array in a FloatBuffer and returns it
	 * @param data The float array
	 * @return The FloatBuffer
	 */
	private static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = FloatBuffer.allocate(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Sotres the int array in an IntBuffer and returns it
	 * @param data The int array
	 * @return The IntBuffer
	 */
	private static IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = IntBuffer.allocate(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Unbinds the VAO
	 */
	private static void unbindVAO() {
		OpenGLUtil.glBindVertexArray(0);
	}
}
