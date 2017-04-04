package cwx.view.panels;

import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.SwingWorker;

import cwx.controller.Controller;
import cwx.math.Matrix4f;
import cwx.math.Vector2f;
import cwx.math.Vector3f;
import cwx.util.OpenGLUtil;
import cwx.view.ViewPanel;
import cwx.view.panels.game.graphics.Camera;
import cwx.view.panels.game.graphics.Loader;
import cwx.view.panels.game.graphics.OpenGLScreen;
import cwx.view.panels.game.graphics.StaticShader;
import cwx.view.panels.game.graphics.Text;
import cwx.view.panels.game.input.KeyBinding;
import cwx.view.panels.game.input.Keyboard;
import cwx.view.panels.game.input.Mouse;
import cwx.view.panels.game.main.PuzzleCube;

public class GamePanel extends ViewPanel {

	private static boolean initialising;

	private static OpenGLScreen screen;
	private Matrix4f projectionMatrix;

	private Camera camera;
	private StaticShader staticShader;
	private PuzzleCube cube;
	private boolean running = true;

	/**
	 * Runs the main game loop of the Panel
	 */
	public void run() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				long lastTime = System.nanoTime();
				long secondTime = System.currentTimeMillis();
				final double ns = 1000000000 / 60;
				double delta = 0;
				int ups = 0, fps = 0;
				while (running) {
					long now = System.nanoTime();
					delta += (now - lastTime) / ns;
					lastTime = now;
					while (delta >= 1) {
						repaint();
						fps++;
						ups++;
						delta--;
					}
					if (System.currentTimeMillis() - secondTime >= 1000) {
						System.out.println("FPS: " + fps + " UPS: " + ups);
						secondTime += 1000;
						fps = 0;
						ups = 0;
					}
				}
				return null;
			}

		}.execute();
	}

	/**
	 * Initialises the GamePanel
	 * @param controller The Controller
	 */
	@Override
	public void init(Controller controller) {
		getView().setLabelText("Loading...");
		screen = new OpenGLScreen(this);
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				GamePanel panel = (GamePanel) evt.getSource();
				GamePanel.screen.getCanvas().setSize(panel.getSize());
			}
		});

		initialising = true;
		add(screen.getCanvas());
		initBindings();
	}

	/**
	 * Initialises the bindings
	 */
	public void initBindings() {
		KeyBinding.bindKey("ACTION", KeyEvent.VK_SPACE);
	}

	/**
	 * Sets the Projection Matrix
	 * @param mat The new projection Matrix
	 */
	public void setProjectionMatrix(Matrix4f mat) {
		projectionMatrix = mat;
		staticShader.start();
		staticShader.loadProjectionMatrix(projectionMatrix);
		staticShader.stop();
		Text.getShader().start();
		Text.getShader().loadProjectionMatrix(projectionMatrix);
		Text.getShader().stop();
	}

	/**
	 * Initialises the resources used by the GamePanel
	 */
	public void initResources() {
		camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));

		staticShader = new StaticShader();
		Text.createShader();

		setProjectionMatrix(Matrix4f.getProjectionMatrix(OpenGLScreen.FOV, OpenGLScreen.Z_FAR, OpenGLScreen.Z_NEAR));

		cube = new PuzzleCube(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		initialising = false;

		getView().setLabelText("Game time");
		run();
	}

	/**
	 * Handles the updates of the game 60 times a second
	 */
	public void update() {
		if (initialising)
			return;

		Keyboard.KEYBOARD.update();
		KeyBinding.update();

		if (Mouse.MOUSE.didMouseMove()) {
			if (Mouse.MOUSE.isMouseDown()) {
				if (!KeyBinding.isBindingDown("ACTION")) {
					camera.getRotation().add(Mouse.MOUSE.getMouseYDiff() / 5.0f, Mouse.MOUSE.getMouseXDiff() / 5.0f, 0);
					camera.update();
				}
			}
			mousePos = OpenGLUtil.getWorldCoordinatesFromScreenCoordinates(camera, new Vector2f(Mouse.MOUSE.getMouseX(), Mouse.MOUSE.getMouseY()));
			cube.setMousePos(mousePos, Mouse.MOUSE.isMouseDown() && KeyBinding.isBindingDown("ACTION"));
		}

		if (Mouse.MOUSE.isMouseReleasing() && cube.getSelected() != null && cube.getSelected().getPartAction() != null) {
			if (cube.getSelected().getPartAction().shouldTrigger()) {
				cube.getSelected().getPartAction().triggered();
			}
		}

		Mouse.MOUSE.update();
	}

	/**
	 * Paints the panel
	 * @param g The graphics object
	 */
	@Override
	public void paint(Graphics g) {
		screen.getCanvas().repaint();
	}

	private Vector3f mousePos = new Vector3f(0.0f);

	/**
	 * Handles the rendering of the panel 60 times a second
	 */
	public void render() {
		if (initialising)
			return;
		screen.renderInit();

		Text.getShader().start();
		Text.getShader().loadViewMatrix(camera);
		Text.getShader().stop();
		staticShader.start();
		staticShader.loadViewMatrix(camera);
		staticShader.stop();
		cube.render(screen, staticShader);
		update();
	}

	/**
	 * Returns the screen object
	 * @return The OpenGLScreen
	 */
	public static OpenGLScreen getScreen() {
		return screen;
	}

	/**
	 * Closes the panel
	 */
	@Override
	public void close() {
		running = false;
		Loader.cleanup();
		staticShader.cleanup();
		Text.getShader().cleanup();
		Text.destroyTextures();
		screen.getCanvas().destroy();
	}

	@Override
	public void update(Observable o, Object arg) {}
}
