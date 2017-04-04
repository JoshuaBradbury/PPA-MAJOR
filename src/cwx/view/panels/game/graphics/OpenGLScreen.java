package cwx.view.panels.game.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import cwx.math.Matrix4f;
import cwx.util.OpenGLUtil;
import cwx.view.panels.GamePanel;
import cwx.view.panels.game.input.Keyboard;
import cwx.view.panels.game.input.Mouse;

public class OpenGLScreen implements GLEventListener {

	private GLCanvas canvas;

	private GamePanel panel;

	public static int HEIGHT = 600, WIDTH = 800;

	public static final float FOV = 70.0f, Z_FAR = 1000.0f, Z_NEAR = 0.1f;

	/**
	 * Initialises the OpenGLScreen object
	 * @param panel The GamePanel the screen is in
	 */
	public OpenGLScreen(GamePanel panel) {
		this.panel = panel;

		GLProfile glprofile = GLProfile.get(GLProfile.GL2);

		GLCapabilities glCapabilities = new GLCapabilities(glprofile);

		canvas = new GLCanvas(glCapabilities);

		canvas.setSize(WIDTH, HEIGHT);

		canvas.addKeyListener(Keyboard.KEYBOARD);
		canvas.addMouseListener(Mouse.MOUSE);
		canvas.addMouseMotionListener(Mouse.MOUSE);
		canvas.addGLEventListener(this);
	}

	/**
	 * Returns the Canvas
	 * @return The canvas object
	 */
	public GLCanvas getCanvas() {
		return canvas;
	}

	/**
	 * Initialises the rendering code
	 */
	public void renderInit() {
		OpenGLUtil.glClearColor(0, 0, 0, 1);
		OpenGLUtil.glEnable(GL2.GL_DEPTH_TEST);
		OpenGLUtil.glEnable(GL2.GL_TEXTURE_2D);
		OpenGLUtil.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Renders the specified Text object using the specified transformation Matrix
	 * @param model The Text to render
	 * @param matrix The Matrix representing the Texts transformation
	 */
	public void renderText(Text text, Matrix4f matrix) {
		Text.getShader().start();
		OpenGLUtil.glBindVertexArray(text.getModel().getVaoID());
		OpenGLUtil.glEnableVertexAttribArray(0);
		OpenGLUtil.glEnableVertexAttribArray(1);

		Text.getShader().loadTransformationMatrix(matrix);

		OpenGLUtil.glActiveTexture(GL3.GL_TEXTURE0);
		text.getTexture().bind(OpenGLUtil.getGL());

		OpenGLUtil.glDrawElements(GL3.GL_TRIANGLES, text.getModel().getVertexCount(), GL3.GL_UNSIGNED_INT, 0);

		OpenGLUtil.glBindTexture(GL3.GL_TEXTURE_2D, 0);
		OpenGLUtil.glDisableVertexAttribArray(2);
		OpenGLUtil.glDisableVertexAttribArray(0);
		OpenGLUtil.glBindVertexArray(0);
		Text.getShader().stop();
	}

	/**
	 * Renders the specified Model using the specified transformation Matrix and the specified Shader
	 * @param model The RawModel to render
	 * @param matrix The Matrix representing the Models transformation
	 * @param shader The Shader to render the Model with
	 */
	public void renderModel(RawModel model, Matrix4f matrix, StaticShader shader) {
		OpenGLUtil.glBindVertexArray(model.getVaoID());
		OpenGLUtil.glEnableVertexAttribArray(0);
		OpenGLUtil.glEnableVertexAttribArray(1);
		OpenGLUtil.glEnableVertexAttribArray(2);

		shader.loadTransformationMatrix(matrix);

		OpenGLUtil.glDrawElements(GL2.GL_TRIANGLES, model.getVertexCount(), GL2.GL_UNSIGNED_INT, 0);

		OpenGLUtil.glDisableVertexAttribArray(2);
		OpenGLUtil.glDisableVertexAttribArray(1);
		OpenGLUtil.glDisableVertexAttribArray(0);
		OpenGLUtil.glBindVertexArray(0);
	}

	/**
	 * When the screen is initialised
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		OpenGLUtil.setGL(gl);

		panel.initResources();

		OpenGLUtil.glViewport(0, 0, WIDTH, HEIGHT);

		OpenGLUtil.glEnable(GL2.GL_CULL_FACE);
		OpenGLUtil.glCullFace(GL2.GL_BACK);

		drawable.setAutoSwapBufferMode(false);
	}

	/**
	 * When the screen is disposed
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {}

	/**
	 * Called when the Screen is ready to render
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		panel.render();
		drawable.swapBuffers();
	}

	/**
	 * Resizes the screen when a reshape event occurs
	 * @param drawable The drawable object of the screen
	 * @param x The new x of the screen
	 * @param y The new y of the screen
	 * @param width The new width of the screen
	 * @param height The new height of the screen
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		WIDTH = width;
		HEIGHT = height;

		OpenGLUtil.glViewport(x, y, width, height);
		panel.setProjectionMatrix(Matrix4f.getProjectionMatrix(OpenGLScreen.FOV, OpenGLScreen.Z_FAR, OpenGLScreen.Z_NEAR));
		canvas.revalidate();
	}
}