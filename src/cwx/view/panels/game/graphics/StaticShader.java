package cwx.view.panels.game.graphics;

import cwx.math.MathUtil;
import cwx.math.Matrix4f;
import cwx.math.Vector3f;

public class StaticShader extends Shader {

	private static final String VERTEX_FILE = "assets/shaders/shader.vert", FRAGMENT_FILE = "assets/shaders/shader.frag";
	
	private int transformationMatrixUniform, viewMatrixUniform, projectionMatrixUniform, colourUniform, selectedUniform;
	
	/**
	 * Initialises the StaticShader object
	 */
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	/**
	 * Loads all the uniform locations
	 */
	public void getAllUniformLocations() {
		transformationMatrixUniform = super.getUniformLocation("transformationMatrix");
		viewMatrixUniform = super.getUniformLocation("viewMatrix");
		projectionMatrixUniform = super.getUniformLocation("projectionMatrix");
		colourUniform = super.getUniformLocation("colour");
		selectedUniform = super.getUniformLocation("selected");
	}
	
	/**
	 * Loads the Transformation Matrix into the Shader
	 * @param matrix The Transformation Matrix
	 */
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(transformationMatrixUniform, matrix);
	}
	
	/**
	 * Loads the Projection Matrix into the Shader
	 * @param matrix The Projection Matrix to load into the Shader
	 */
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(projectionMatrixUniform, matrix);
	}
	
	/**
	 * Loads the View Matrix of the specified Camera into the Shader
	 * @param camera The Camera to generate the View Matrix from
	 */
	public void loadViewMatrix(Camera camera) {
		super.loadMatrix(viewMatrixUniform, MathUtil.getViewMatrix(camera));
	}
	
	/**
	 * Loads the Colour value into the Shader
	 * @param colour The Colour to render with
	 */
	public void loadColour(Vector3f colour) {
		super.loadVector3f(colourUniform, colour);
	}
	
	/**
	 * Loads the value selected into the Shader
	 * @param selected A boolean
	 */
	public void loadSelected(boolean selected) {
		super.loadFloat(selectedUniform, selected ? 1.0f : 0.0f);
	}
	
	/**
	 * Binds the attributes
	 */
	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "inTexCoord");
		super.bindAttribute(2, "normal");
	}

}
