package cwx.view.panels.game.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jogamp.opengl.util.texture.Texture;

import cwx.math.Vector3f;
import cwx.util.ImageUtil;
import cwx.util.OpenGLUtil;

public class Text {

	public static TextShader shader;
	private static ArrayList<Texture> textures;
	private static RawModel model;
	
	private Texture texture;
	private Vector3f scale;
	
	private Font font;
	private Color colour;
	private String text;
	
	static {
		textures = new ArrayList<Texture>();
	}
	
	/**
	 * Loads the model
	 */
	private static void loadModel() {
		model = Loader.loadToVAO(new float[] {
				-1f, -1f, -1f,
				 1f, -1f, -1f,
				-1f,  1f, -1f,
				 1f,  1f, -1f
		}, new int[] {
				0, 1, 2,
				1, 3, 2
		}, new float[] {
				0, 1,
				1, 1,
				0, 0,
				1, 0
		}, new float[] {
				0, 0, 0,
				0, 0, 0,
				0, 0, 0,
				0, 0, 0
		});
	}
	
	/**
	 * Creates the shader
	 */
	public static void createShader() {
		shader = new TextShader();
	}
	
	/**
	 * Initialises the Text object
	 * @param text The text in the object
	 * @param font The font of the object
	 * @param colour The colour of the object
	 */
	public Text(String text, Font font, Color colour) {
		this.text = text;
		this.font = font;
		this.colour = colour;
		loadModel();
		loadTextIntoTexture();
	}
	
	/**
	 * Sets the text of the object
	 * @param text The new text of the object
	 */
	public void setText(String text) {
		this.text = text;
		loadTextIntoTexture();
	}
	
	/**
	 * Sets the Font of the object
	 * @param font The new font
	 */
	public void setFont(Font font) {
		this.font = font;
		loadTextIntoTexture();
	}
	
	/**
	 * Sets the Color of the object
	 * @param colour The new Color of the object
	 */
	public void setColour(Color colour) {
		this.colour = colour;
		loadTextIntoTexture();
	}
	
	/**
	 * Deletes the Textures
	 */
	public static void destroyTextures() {
		shader = null;
		for (Texture texture : textures) {
			texture.destroy(OpenGLUtil.getGL());
		}
		textures.clear();
	}
	
	/**
	 * Generates the Texture object based on the text, font and colour
	 */
	private void loadTextIntoTexture() {
		if (texture != null) {
			textures.remove(texture);
			texture.destroy(OpenGLUtil.getGL());
		}
		
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		int width = image.getGraphics().getFontMetrics(font).stringWidth(text), height = image.getGraphics().getFontMetrics(font).getHeight() * 2;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = image.getGraphics();
		
		g.setColor(colour);
		g.setFont(font);
		g.drawString(text, 0, g.getFontMetrics().getHeight());
		
		texture = ImageUtil.loadImageFromBufferedImage(image);
		
		g.dispose();
		
		textures.add(texture);
		
		scale = new Vector3f(width / 500f, height / 500f, 1f);
	}
	
	/**
	 * Returns the Shader of the Text
	 * @return The TextShader
	 */
	public static TextShader getShader() {
		return shader;
	}
	
	/**
	 * Returns the Texture of the Text
	 * @return The Texture
	 */
	public Texture getTexture() {
		return texture;
	}
	
	/**
	 * Returns the Model of the Text
	 * @return The RawModel
	 */
	public RawModel getModel() {
		return model;
	}
	
	/**
	 * Returns the Scale of the Text
	 * @return The Vector
	 */
	public Vector3f getScale() {
		return scale;
	}
}
