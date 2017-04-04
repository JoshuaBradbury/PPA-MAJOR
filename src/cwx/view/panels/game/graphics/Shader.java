package cwx.view.panels.game.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.jogamp.opengl.GL3;

import cwx.math.Matrix4f;
import cwx.math.Vector3f;
import cwx.util.OpenGLUtil;

public abstract class Shader {

	private int programID, vertexShaderID, fragmentShaderID;
	
	/**
	 * Initialises the Shader object
	 * @param vertexFile The location of the Vertex Shader code
	 * @param fragmentFile The location of the Fragment Shader code
	 */
	public Shader(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL3.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL3.GL_FRAGMENT_SHADER);
		programID = OpenGLUtil.glCreateProgram();
		OpenGLUtil.glAttachShader(programID, vertexShaderID);
		OpenGLUtil.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		OpenGLUtil.glLinkProgram(programID);
		OpenGLUtil.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	/**
	 * Binds the Shader for use in rendering
	 */
	public void start() {
		OpenGLUtil.glUseProgram(programID);
	}
	
	/**
	 * Unbinds the Shader
	 */
	public void stop() {
		OpenGLUtil.glUseProgram(0);
	}
	
	/**
	 * Releases all the resources the Shader had
	 */
	public void cleanup() {
		stop();
		OpenGLUtil.glDetachShader(programID, vertexShaderID);
		OpenGLUtil.glDetachShader(programID, fragmentShaderID);
		OpenGLUtil.glDeleteShader(fragmentShaderID);
		OpenGLUtil.glDeleteShader(vertexShaderID);
		OpenGLUtil.glDeleteProgram(programID);
	}
	
	/**
	 * An abstract method Shaders will override to abstract away the binding of Attributes
	 */
	public abstract void bindAttributes();
	
	/**
	 * Binds the specified Attribute to the specified name in the Shader
	 * @param attribute The Attribute to bind
	 * @param variableName The name of the Attribute in the Shader
	 */
	public void bindAttribute(int attribute, String variableName) {
		OpenGLUtil.glBindAttribLocation(programID, attribute, variableName);
	}
	
	/**
	 * An abstract method Shaders will override to abstract away the loading of the uniform locations
	 */
	public abstract void getAllUniformLocations();
	
	/**
	 * Returns the location of the Uniform with the specified name
	 * @param uniformName The name of the Uniform
	 * @return The location of the Uniform
	 */
	public int getUniformLocation(String uniformName) {
		return OpenGLUtil.glGetUniformLocation(programID, uniformName);
	}
	
	/**
	 * Loads the specified Matrix into the Uniform at the specified location
	 * @param location The location of the Uniform
	 * @param matrix The Matrix to load
	 */
	public void loadMatrix(int location, Matrix4f matrix) {
		OpenGLUtil.glUniformMatrix4fv(location, false, matrix.getAsFloatBuffer());
	}
	
	/**
	 * Loads the specified Vector into the Uniform at the specified location
	 * @param location The location of the Uniform
	 * @param vector The Vector to load
	 */
	public void loadVector3f(int location, Vector3f vector) {
		OpenGLUtil.glUniform3f(location, vector.getX(), vector.getY(), vector.getZ());
	}
	
	/**
	 * Loads the specified float into the Uniform at the specified location
	 * @param location The location of the Uniform
	 * @param val The value to load
	 */
	public void loadFloat(int location, float val) {
		OpenGLUtil.glUniform1f(location, val);
	}
	
	/**
	 * Loads the Shader from a file and into the GPU
	 * It reads all the code from the file, loads it onto the GPU and
	 * compiles it and then links it to the Program
	 * @param file The location of the Shader file
	 * @param type The type of the Shader
	 * @return The ID of the Shader
	 */
	private static int loadShader(String file, int type) {
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] shaderSource = new String[lines.size()];
		for (int i = 0; i < shaderSource.length; i++) shaderSource[i] = lines.get(i);
		
		int shaderID = OpenGLUtil.glCreateShader(type);
		OpenGLUtil.glShaderSource(shaderID, shaderSource);
		OpenGLUtil.glCompileShader(shaderID);
		
		if (OpenGLUtil.glGetShaderi(shaderID, GL3.GL_COMPILE_STATUS) == GL3.GL_FALSE) {
			System.out.println(OpenGLUtil.glGetShaderInfoLog(shaderID, 500));
		}
		return shaderID;
	}
}
