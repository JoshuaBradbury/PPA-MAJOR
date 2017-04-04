package cwx.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

/**
 * A utility class to load images
 */
public class ImageUtil {

	private static GL2 gl;
	
	private ImageUtil() {}
	
	/**
	 * Sets the GL2 object
	 * @param gl The GL2 object to set
	 */
	public static void setGL(GL2 gl) {
		ImageUtil.gl = gl;
	}
	
	/**
	 * Loads a Texture from a file
	 * @param path The location of the image file
	 * @return The Texture object
	 */
	public static Texture loadImageFromFile(String path) {
		try {
			return TextureIO.newTexture(new File(path), false);
		} catch (GLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Loads a Texture from a BufferedImage
	 * @param image The BufferedImage to load into a Texture
	 * @return The Texture object
	 */
	public static Texture loadImageFromBufferedImage(BufferedImage image) {
		return AWTTextureIO.newTexture(gl.getGLProfile(), image, false);
	}
	
	/**
	 * Loads a BufferedImage from a file
	 * @param path The location of the image file
	 * @return The BufferedImage
	 */
	public static BufferedImage loadBufferedImageFromFile(String path) {
		try {
		return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
