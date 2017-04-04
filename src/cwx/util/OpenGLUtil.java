package cwx.util;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import cwx.math.MathUtil;
import cwx.math.Matrix4f;
import cwx.math.Vector2f;
import cwx.math.Vector3f;
import cwx.view.panels.game.graphics.Camera;
import cwx.view.panels.game.graphics.OpenGLScreen;

public final class OpenGLUtil {

	private OpenGLUtil() {}

	private static GL2 gl;
	private static GLU glu;

	/**
	 * Returns the GL2 object
	 * @return The GL2 object
	 */
	public static GL2 getGL() {
		return gl;
	}

	/**
	 * Sets the GL2 object
	 * @param gl The GL2 object
	 */
	public static void setGL(GL2 gl) {
		OpenGLUtil.gl = gl;
		ImageUtil.setGL(gl);
		glu = new GLU();
	}

	/**
	 * Returns the world coordinates of the mouse cursor at the specified mouse position based on the camera
	 * @param camera The camera the world is viewed through
	 * @param mouse The mouse position
	 * @return The world position of the cursor
	 */
	public static Vector3f getWorldCoordinatesFromScreenCoordinates(Camera camera, Vector2f mouse) {
		IntBuffer viewport = IntBuffer.allocate(4);
		FloatBuffer modelview = MathUtil.getViewMatrix(camera).getAsFloatBuffer();
		FloatBuffer projection = Matrix4f.getProjectionMatrix(OpenGLScreen.FOV, OpenGLScreen.Z_FAR, OpenGLScreen.Z_NEAR).getAsFloatBuffer();
		FloatBuffer winZ = FloatBuffer.allocate(1);
		FloatBuffer pos = FloatBuffer.allocate(3);
		
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport);

		int winX = (int) mouse.getX();
		int winY = (int) (viewport.get(3) - mouse.getY());
		
		gl.glReadPixels(winX, winY, 1, 1, GL2.GL_DEPTH_COMPONENT, GL2.GL_FLOAT, winZ);
		glu.gluUnProject(winX, winY, winZ.get(0), modelview, projection, viewport, pos);
		
		return new Vector3f(pos.get(0), pos.get(1), pos.get(2));
	}

	/**
	 * Generates the Vertex Array ID and returns it
	 * @return The Vertex Array ID
	 */
	public static int glGenVertexArrays() {
		IntBuffer buffer = IntBuffer.allocate(1);
		gl.glGenVertexArrays(1, buffer);
		return buffer.get(0);
	}

	/**
	 * Binds the specified Vertex Array
	 * @param vaoID The Vertex Array to bind
	 */
	public static void glBindVertexArray(int vaoID) {
		gl.glBindVertexArray(vaoID);
	}

	/**
	 * Generates the Vertex Buffer ID and returns it
	 * @return The Vertex Buffer ID
	 */
	public static int glGenBuffers() {
		IntBuffer buffer = IntBuffer.allocate(1);
		gl.glGenBuffers(1, buffer);
		return buffer.get(0);
	}

	/**
	 * Deletes the specified Vertex Array
	 * @param vao The Vertex Array to delete
	 */
	@SuppressWarnings("serial")
	public static void glDeleteVertexArrays(int vao) {
		glDeleteVertexArrays(new ArrayList<Integer>() {
			{
				add(vao);
			}
		});
	}

	/**
	 * Deletes all the Vertex Arrays specified
	 * @param vaos The array of Vertex Array IDs
	 */
	public static void glDeleteVertexArrays(ArrayList<Integer> vaos) {
		IntBuffer buffer = IntBuffer.allocate(vaos.size());
		for (int vao : vaos) {
			buffer.put(vao);
		}
		buffer.flip();
		gl.glDeleteVertexArrays(vaos.size(), buffer);
	}

	/**
	 * Deletes the specified Vertex Buffer Object
	 * @param vbo The Vertex Buffer Object to delete
	 */
	@SuppressWarnings("serial")
	public static void glDeleteBuffers(int vbo) {
		glDeleteBuffers(new ArrayList<Integer>() {
			{
				add(vbo);
			}
		});
	}

	/**
	 * Deletes all the specified Vertex Buffer Objects
	 * @param vbos The array of Vertex Buffer Object IDs
	 */
	public static void glDeleteBuffers(ArrayList<Integer> vbos) {
		IntBuffer buffer = IntBuffer.allocate(vbos.size());
		for (int vbo : vbos) {
			buffer.put(vbo);
		}
		buffer.flip();
		gl.glDeleteBuffers(vbos.size(), buffer);
	}

	/**
	 * Binds the specified buffer as the specified type
	 * @param target The type of Vertex Buffer
	 * @param vboID The Vertex Buffer Object ID
	 */
	public static void glBindBuffer(int target, int vboID) {
		gl.glBindBuffer(target, vboID);
	}

	/**
	 * Creates the Attribute pointer for the Vertex Array
	 * @param attributeList The attribute list to be pointing to
	 * @param size The size of each data point in bytes
	 * @param type The type of the data
	 * @param normalized Whether the data is normalized or not
	 * @param stride The stride in the data
	 * @param offset The offset in the data
	 */
	public static void glVertexAttribPointer(int attributeList, int size, int type, boolean normalized, int stride, int offset) {
		gl.glVertexAttribPointer(attributeList, size, type, normalized, stride, offset);
	}

	/**
	 * Loads Integer data onto the GPU
	 * @param target The type of Vertex Buffer
	 * @param buffer The data
	 * @param usage The usage of the data
	 */
	public static void glBufferData(int target, IntBuffer buffer, int usage) {
		gl.glBufferData(target, buffer.capacity() * Integer.BYTES, buffer, usage);
	}

	/**
	 * Loads Float data onto the GPU
	 * @param target The type of Vertex Buffer
	 * @param buffer The data
	 * @param usage The usage of the data
	 */
	public static void glBufferData(int target, FloatBuffer buffer, int usage) {
		gl.glBufferData(target, buffer.capacity() * Float.BYTES, buffer, usage);
	}

	/**
	 * Disables the specified Vertex Array
	 * @param i The ID of the Vertex Array
	 */
	public static void glDisableVertexAttribArray(int i) {
		gl.glDisableVertexAttribArray(i);
	}

	/**
	 * Enables the specified Vertex Array
	 * @param i The ID of the Vertex Array
	 */
	public static void glEnableVertexAttribArray(int i) {
		gl.glEnableVertexAttribArray(i);
	}

	/**
	 * Sets the active Texture
	 * @param glTexture The Texture to activate
	 */
	public static void glActiveTexture(int glTexture) {
		gl.glActiveTexture(glTexture);
	}
	
	/**
	 * Binds the specified Texture as the specified type
	 * @param target The type of the image
	 * @param texture The ID of the Texture
	 */
	public static void glBindTexture(int target, int texture) {
		gl.glBindTexture(target, texture);
	}

	/**
	 * Draws the loaded data
	 * @param mode The type of primitives to render
	 * @param count The number of elements
	 * @param type The type of the index data
	 * @param offset The offset in the data
	 */
	public static void glDrawElements(int mode, int count, int type, int offset) {
		gl.glDrawElements(mode, count, type, offset);
	}

	/**
	 * Sets the Viewport of the screen
	 * @param x The x position of the screen
	 * @param y The y position of the screen
	 * @param width The width of the screen
	 * @param height The height of the screen
	 */
	public static void glViewport(int x, int y, int width, int height) {
		gl.glViewport(x, y, width, height);
	}

	/**
	 * Sets the clear colour of the window
	 * @param r The red component of the colour
	 * @param g The green component of the colour
	 * @param b The blue component of the colour
	 * @param a The alpha component of the colour
	 */
	public static void glClearColor(int r, int g, int b, int a) {
		gl.glClearColor(r, g, b, a);
	}

	/**
	 * Enables the specified target
	 * @param target The target to enable
	 */
	public static void glEnable(int target) {
		gl.glEnable(target);
	}

	/**
	 * Clears the specified mask
	 * @param mask The mask to clear
	 */
	public static void glClear(int mask) {
		gl.glClear(mask);
	}

	/**
	 * Creates the Program and returns its ID
	 * @return The ID of the Program
	 */
	public static int glCreateProgram() {
		return gl.glCreateProgram();
	}

	/**
	 * Attaches the specified Shader to the specified Program
	 * @param programID The Program to attach the Shader to
	 * @param shaderID The Shader to attach
	 */
	public static void glAttachShader(int programID, int shaderID) {
		gl.glAttachShader(programID, shaderID);
	}

	/**
	 * Links the specified Program to the pipeline
	 * @param programID The Program to link
	 */
	public static void glLinkProgram(int programID) {
		gl.glLinkProgram(programID);
	}

	/**
	 * Validates the specified Program
	 * @param programID The Program to validate
	 */
	public static void glValidateProgram(int programID) {
		gl.glValidateProgram(programID);
	}

	/**
	 * Sets the Program currently being used
	 * @param programID The Program to use
	 */
	public static void glUseProgram(int programID) {
		gl.glUseProgram(programID);
	}

	/**
	 * Detaches the specified Shader from the specified Program
	 * @param programID The Program which the Shader is attached to
	 * @param shaderID The Shader to detach
	 */
	public static void glDetachShader(int programID, int shaderID) {
		gl.glDetachShader(programID, shaderID);
	}

	/**
	 * Deletes the specified Shader
	 * @param shaderID The Shader to delete
	 */
	public static void glDeleteShader(int shaderID) {
		gl.glDeleteShader(shaderID);
	}

	/**
	 * Deletes the specified Program
	 * @param programID The Program to delete
	 */
	public static void glDeleteProgram(int programID) {
		gl.glDeleteProgram(programID);
	}

	/**
	 * Binds the Attribute location for the specified Program
	 * @param programID The Program
	 * @param index The Attribute Index to bind
	 * @param name The name of the Attribute in the Program
	 */
	public static void glBindAttribLocation(int programID, int index, String name) {
		gl.glBindAttribLocation(programID, index, name);
	}

	/**
	 * Returns the specified Uniform location for the specified Program
	 * @param programID The Program the Uniform is in
	 * @param name The name of the Uniform in the Program
	 * @return The Location of the Uniform
	 */
	public static int glGetUniformLocation(int programID, String name) {
		return gl.glGetUniformLocation(programID, name);
	}

	/**
	 * Loads 16 floats as a Matrix into the specified Uniform location
	 * @param location The location of the uniform
	 * @param transpose Whether the data is transposed or not
	 * @param buffer The data to be loaded
	 */
	public static void glUniformMatrix4fv(int location, boolean transpose, FloatBuffer buffer) {
		gl.glUniformMatrix4fv(location, 1, transpose, buffer);
	}

	/**
	 * Loads 3 floats into the specified Uniform location
	 * @param location The location of the Uniform
	 * @param x The first float
	 * @param y The second float
	 * @param z The third float
	 */
	public static void glUniform3f(int location, float x, float y, float z) {
		gl.glUniform3f(location, x, y, z);
	}

	/**
	 * Loads the specified float to the specified uniform location
	 * @param location The location of the Uniform
	 * @param val The value to load into the Uniform
	 */
	public static void glUniform1f(int location, float val) {
		gl.glUniform1f(location, val);
	}

	/**
	 * Creates the Shader and returns the ID of the Shader
	 * @param type The type of the Shader
	 * @return The ID of the Shader
	 */
	public static int glCreateShader(int type) {
		return gl.glCreateShader(type);
	}

	/**
	 * Loads the source of the Shader onto the GPU
	 * @param shaderID The ID of the Shader
	 * @param shaderSource The source code of the Shader
	 */
	public static void glShaderSource(int shaderID, String[] shaderSource) {
		String source = "";
		for (String line : shaderSource) {
			source += line + "\n";
		}

		gl.glShaderSource(shaderID, 1, new String[] { source }, (int[]) null, 0);
	}

	/**
	 * Compiles the specified Shader
	 * @param shaderID The ID of the Shader to compile
	 */
	public static void glCompileShader(int shaderID) {
		gl.glCompileShader(shaderID);
	}

	/**
	 * Returns the log of the specified shader
	 * @param shaderID The ID of the Shader
	 * @param size The number of bytes to collect
	 * @return The log as a char buffer
	 */
	public static char[] glGetShaderInfoLog(int shaderID, int size) {
		ByteBuffer buffer = ByteBuffer.allocate(size);
		IntBuffer lengthBuffer = IntBuffer.allocate(1);

		gl.glGetShaderInfoLog(shaderID, size, lengthBuffer, buffer);

		byte[] bytes = buffer.array();
		char[] chars = new char[bytes.length];

		for (int i = 0; i < chars.length; i++) {
			chars[i] = (char) bytes[i];
		}

		return chars;
	}

	/**
	 * Returns the info about the specified parameter of the specified Shader
	 * @param shaderID The ID of the Shader
	 * @param target The Parameter of the Shader
	 * @return The information
	 */
	public static int glGetShaderi(int shaderID, int target) {
		IntBuffer buffer = IntBuffer.allocate(1);
		gl.glGetShaderiv(shaderID, target, buffer);
		return buffer.get(0);
	}

	/**
	 * Generates and returns a Texture ID
	 * @return The Texture ID
	 */
	public static int glGenTextures() {
		IntBuffer buffer = IntBuffer.allocate(1);
		gl.glGenTextures(1, buffer);
		return buffer.get(0);
	}

	/**
	 * Loads the image data onto the GPU
	 * @param target The type of image
	 * @param level The number of mipmap levels to generate
	 * @param glFormat The format of the data
	 * @param width The width of the image
	 * @param height The height of the image
	 * @param type The type of data
	 * @param image The image data
	 */
	public static void glTexImage2D(int target, int level, int glFormat, int width, int height, int type, ByteBuffer image) {
		gl.glTexImage2D(target, level, glFormat, width, height, 0, glFormat, type, image);
	}

	/**
	 * Specifies a parameter of how the Texture should be loaded
	 * @param target The type of Texture
	 * @param pname The parameter name
	 * @param parameter The parameter value
	 */
	public static void glTexParameteri(int target, int pname, int parameter) {
		gl.glTexParameteri(target, pname, parameter);
	}

	/**
	 * Deletes the Texture from the GPU
	 * @param textureID The Texture to delete
	 */
	public static void glDeleteTextures(int textureID) {
		IntBuffer buffer = IntBuffer.allocate(1);
		buffer.put(textureID);
		gl.glDeleteTextures(1, buffer);
	}

	/**
	 * Specifies the face to cull
	 * @param mode The face to cull
	 */
	public static void glCullFace(int mode) {
		gl.glCullFace(mode);
	}
}
