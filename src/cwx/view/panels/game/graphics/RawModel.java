package cwx.view.panels.game.graphics;

public class RawModel {

	private int vaoID, vertexCount;
	
	/**
	 * Initialises the RawModel
	 * @param vaoID The ID of the VAO the model is stored in
	 * @param vertexCount The number of vertices in the Model
	 */
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	/**
	 * Returns the VAO ID
	 * @return The ID of the VAO
	 */
	public int getVaoID() {
		return vaoID;
	}
	
	/**
	 * Returns the number of vertices in the Model
	 * @return The number of vertices in the Model
	 */
	public int getVertexCount() {
		return vertexCount;
	}
}
