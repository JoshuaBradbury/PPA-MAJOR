package cwx.view.panels.game.graphics;

import cwx.math.MathUtil;
import cwx.math.Matrix4f;
import cwx.math.Vector3f;

public class TextShader extends Shader {

	private static final String VERTEX_FILE = "assets/shaders/text.vert", FRAGMENT_FILE = "assets/shaders/text.frag";
	
	private int transformationMatrixUniform, viewMatrixUniform, projectionMatrixUniform, colourUniform;
	
	/**
	 * Initialises the TextShader object
	 */
	public TextShader() {
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
	 * Binds the attributes
	 */
	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoord");
	}
}
