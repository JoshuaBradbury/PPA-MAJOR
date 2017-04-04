package cwx.view.panels.game.main;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import cwx.math.AABB;
import cwx.math.MathUtil;
import cwx.math.Matrix4f;
import cwx.math.Vector2f;
import cwx.math.Vector3f;
import cwx.view.panels.game.graphics.Loader;
import cwx.view.panels.game.graphics.OpenGLScreen;
import cwx.view.panels.game.graphics.RawModel;
import cwx.view.panels.game.graphics.StaticShader;
import cwx.view.panels.game.graphics.Text;

public class PuzzleCube {

	private static RawModel cube;
	private HashMap<String, Part> parts;
	private Part selected;
	private Vector3f rotation, scale, location;
	private Vector3f colour;
	private Text text;

	/**
	 * Initialises the PuzzleCube object
	 * @param translation The Translation Vector
	 * @param rotation The rotation Vector
	 * @param scale The scale Vector
	 */
	public PuzzleCube(Vector3f translation, Vector3f rotation, Vector3f scale) {
		this.location = translation;
		this.rotation = rotation;
		this.scale = scale;
		text = new Text("Can you solve... The Cube???", new Font("Tahoma", Font.PLAIN, 100), Color.RED);
		colour = new Vector3f(1.0f, 1.0f, 1.0f);
		parts = new HashMap<String, Part>();
		loadModel();
		addParts();
	}

	/**
	 * Adds the initial parts such as the actual cubes and the first buttons for the first level
	 */
	public void addParts() {
		parts.put("cube", new BlankPart(new Vector3f(0), new Vector3f(0), new Vector3f(1), true));

		parts.put("Button1", new Part(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.25f, 0.1f, 0.25f), true, new PartAction(this) {
			@Override
			public void triggered() {
				parts.remove("Button1");
				text.setText("Good... but where did it go?");
				parts.get("Button2").setVisible(true);
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));

		parts.put("Button2", new Part(new Vector3f(0.0f, -1.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.25f, 0.1f, 0.25f), false, new PartAction(this) {
			@Override
			public void triggered() {
				parts.remove("Button2");
				text.setText("Almost but not quite, keep chasing >:)");
				parts.get("Button3").setVisible(true);
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));

		parts.put("Button3", new Part(new Vector3f(0.0f, 0.0f, 1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.25f, 0.25f, 0.1f), false, new PartAction(this) {
			@Override
			public void triggered() {
				parts.remove("Button3");
				text.setText("haha child's play");
				parts.get("Button4").setVisible(true);
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));

		parts.put("Button4", new Part(new Vector3f(1.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.1f, 0.25f, 0.25f), false, new PartAction(this) {
			@Override
			public void triggered() {
				parts.remove("Button4");
				add4Buttons();
				text.setText("Well done for making it to the next level, it doesn't mean its easier");
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));

		TextPart part = new TextPart(text, new Vector3f(0, 2, 0), new Vector3f(0), new Vector3f(1), true, null);
		part.setColour(new Vector3f(1, 0, 0));
		parts.put("text", part);
	}

	/**
	 * Performs the changes for the third level which includes splitting the Cube into parts and turning some into slides
	 */
	public void splitIntoParts() {
		parts.put("slidePart", new SlidePart(SlidePart.X, new Vector2f(-1, 1), new Vector3f(0, -0.5f, 0.5f), new Vector3f(0), new Vector3f(1.0f, 0.5f, 0.5f), true, null));

		parts.remove("cube");
		parts.put("cubeTopFront", new BlankPart(new Vector3f(0, 0.5f, 0.5f), new Vector3f(0), new Vector3f(1.0f, 0.5f, 0.5f), true));
		parts.put("cubeTopBack", new BlankPart(new Vector3f(0, 0.5f, -0.5f), new Vector3f(0), new Vector3f(1.0f, 0.5f, 0.5f), true));
		parts.put("cubeBottomBack", new BlankPart(new Vector3f(0, -0.5f, -0.5f), new Vector3f(0), new Vector3f(1.0f, 0.5f, 0.5f), true));

		parts.put("centreButton", new Part(new Vector3f(0), new Vector3f(0), new Vector3f(0.5f), true, new PartAction(this) {
			@Override
			public void triggered() {
				parts.remove("cubeTopBack");
				parts.remove("centreButton");
				addNewSlideAndButton();
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));
	}

	private boolean finalAnimRunning, gameOver;
	private float finalAnim = 0.1f;

	/**
	 * Adds the final button that ends the game
	 */
	public void addNewSlideAndButton() {
		parts.put("slidePart", new SlidePart(SlidePart.X, new Vector2f(-1, 1), new Vector3f(0, 0.5f, -0.5f), new Vector3f(0), new Vector3f(1.0f, 0.5f, 0.5f), true, null));

		parts.put("centreButton", new Part(new Vector3f(0, 0, -0.5f), new Vector3f(0), new Vector3f(0.5f, 0.5f, 0.25f), true, new PartAction(this) {
			@Override
			public void triggered() {
				parts.remove("centreButton");
				parts.remove("slidePart");
				finalAnimRunning = true;
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));
	}

	/**
	 * Returns the selected Part
	 * @return The Part that is selected
	 */
	public Part getSelected() {
		return selected;
	}

	private byte valueSet = 0;
	private int score = 0;

	/**
	 * Adds the 5 buttons used in the second level puzzle
	 */
	public void add4Buttons() {
		colour = new Vector3f(1, 0, 0);
		parts.put("part1", new Part(new Vector3f(1.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.1f, 0.25f, 0.25f), true, new PartAction(this) {
			@Override
			public void triggered() {
				if ((valueSet & 0x01) == 0) {
					score += 1;
					score *= 3;
					valueSet |= 0x01;
					text.setText(score + "");
				}
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));

		parts.put("part2", new Part(new Vector3f(-1.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.1f, 0.25f, 0.25f), true, new PartAction(this) {
			@Override
			public void triggered() {
				if ((valueSet & 0x02) == 0) {
					score += 1;
					score *= 5;
					valueSet |= 0x02;
					text.setText(score + "");
				}
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));

		parts.put("part3", new Part(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.25f, 0.1f, 0.25f), true, new PartAction(this) {
			@Override
			public void triggered() {
				if ((valueSet & 0x04) == 0) {
					score += 1;
					score *= 2;
					valueSet |= 0x04;
					text.setText(score + "");
				}
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));

		parts.put("part4", new Part(new Vector3f(0.0f, 0.0f, 1.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.25f, 0.25f, 0.1f), true, new PartAction(this) {
			@Override
			public void triggered() {
				if ((valueSet & 0x08) == 0) {
					score += 1;
					score *= 4;
					valueSet |= 0x08;
					text.setText(score + "");
				}
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));

		parts.put("part5", new Part(new Vector3f(0.0f, -1.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.25f, 0.1f, 0.25f), true, new PartAction(this) {
			@Override
			public void triggered() {
				if (score == 205) {
					parts.remove("part1");
					parts.remove("part2");
					parts.remove("part3");
					parts.remove("part4");
					parts.remove("part5");
					colour = new Vector3f(0, 1, 0);
					cube.splitIntoParts();
					text.setText("New level time! Hold space to move objects...");
				} else {
					score = 0;
					valueSet = 0;
					text.setText(score + "");
				}
			}

			@Override
			public boolean shouldTrigger() {
				return selected == part;
			}
		}));
	}

	/**
	 * Returns the Colour of the cube
	 * @return The Colour Vector
	 */
	public Vector3f getColour() {
		return colour;
	}

	/**
	 * Returns the rotation Vector
	 * @return The rotation of the cube
	 */
	public Vector3f getRotation() {
		return rotation;
	}

	/**
	 * Returns the scale Vector
	 * @return The scale of the cube
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Returns the translation Vector
	 * @return The translation of the cube
	 */
	public Vector3f getTranslation() {
		return location;
	}

	/**
	 * Loads the cube model
	 */
	private static void loadModel() {
		cube = Loader.loadToVAO(new float[] {
				-1f, -1f, 1f, 1f, -1f, 1f, -1f, 1f, 1f, 1f, 1f, 1f, -1f, -1f, -1f, 1f, -1f, -1f, -1f, 1f, -1f, 1f, 1f, -1f,

				-1f, 1f, -1f, 1f, 1f, -1f, -1f, 1f, 1f, 1f, 1f, 1f, -1f, -1f, -1f, 1f, -1f, -1f, -1f, -1f, 1f, 1f, -1f, 1f,

				1f, -1f, -1f, 1f, 1f, -1f, 1f, -1f, 1f, 1f, 1f, 1f, -1f, -1f, -1f, -1f, 1f, -1f, -1f, -1f, 1f, -1f, 1f, 1f
		}, new int[] {
				0, 1, 2, 3, 2, 1, 6, 5, 4, 5, 6, 7,

				10, 9, 8, 9, 10, 11, 12, 13, 14, 15, 14, 13,

				16, 17, 18, 19, 18, 17, 22, 21, 20, 21, 22, 23
		}, new float[] {
				0.0f, 0.0f, 1.0f, 0.0f, 0.1f, 0.1f, 1.0f, 1.0f,

				0.0f, 0.0f, 1.0f, 0.0f, 0.1f, 0.1f, 1.0f, 1.0f,

				0.0f, 0.0f, 1.0f, 0.0f, 0.1f, 0.1f, 1.0f, 1.0f,

				0.0f, 0.0f, 1.0f, 0.0f, 0.1f, 0.1f, 1.0f, 1.0f,

				0.0f, 0.0f, 1.0f, 0.0f, 0.1f, 0.1f, 1.0f, 1.0f,

				0.0f, 0.0f, 1.0f, 0.0f, 0.1f, 0.1f, 1.0f, 1.0f
		}, new float[] {
				0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f,

				0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f,

				1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f, -1f, 0f, 0f
		});
	}

	private AABB mouseAABB = new AABB();

	/**
	 * Updates the mouse AABB based on its new position
	 * @param vec3 The position of the Mouse
	 * @param down Whether the mouse is down or not
	 */
	public void setMousePos(Vector3f vec3, boolean down) {
		if (!down) {
			mouseAABB.setCentre(vec3);
			mouseAABB.setScale(new Vector3f(0.1f));
		}
		if (parts.containsKey("slidePart")) {
			if (down && !((SlidePart) parts.get("slidePart")).isGrabbed() && selected == parts.get("slidePart"))
				((SlidePart) parts.get("slidePart")).startSlide(vec3);
			else if (!down && ((SlidePart) parts.get("slidePart")).isGrabbed())
				((SlidePart) parts.get("slidePart")).stopSlide();
			((SlidePart) parts.get("slidePart")).updateMousePos(vec3);
		}
	}

	/**
	 * Renders the cube
	 * @param screen The Screen to render it to
	 * @param staticShader The Shader to render the cube with
	 */
	public void render(OpenGLScreen screen, StaticShader staticShader) {
		selected = null;

		if (finalAnimRunning) {
			if (finalAnim > 20000.0f) {
				finalAnimRunning = false;
				gameOver = true;
				parts.remove("cubeTopFront");
				parts.remove("cubeBottomBack");
			} else {
				finalAnim *= 1.03f;

			}
		}

		if (gameOver) {
			text.setText("Game Over, You Win!");
		}
		for (String partName : parts.keySet()) {
			Part part = parts.get(partName);
			if (part.isVisible()) {
				Matrix4f partMatrix = MathUtil.getTransformationMatrix(part.getTranslation(), part.getRotation(), part.getScale());
				part.getAABB().setCentre(part.getTranslation());

				if (part.getAABB().isColliding(mouseAABB) && selected == null && !(part instanceof BlankPart)) {
					staticShader.start();
					staticShader.loadSelected(true);
					staticShader.stop();
					selected = part;
				} else {
					staticShader.start();
					staticShader.loadSelected(false);
					staticShader.stop();
				}
				if (part instanceof TextPart) {
					if (part.getColour() != null)
						Text.getShader().loadColour(part.getColour());
					screen.renderText(((TextPart) part).getText(), partMatrix);
				} else {
					staticShader.start();
					if (part.getColour() != null)
						staticShader.loadColour(part.getColour());
					else
						staticShader.loadColour(colour);
					screen.renderModel(cube, MathUtil.getTransformationMatrix(new Vector3f(0), new Vector3f(0, finalAnim, 0), new Vector3f(1)).clone().multiply(partMatrix), staticShader);
					staticShader.stop();
				}
			}
		}
	}
}
