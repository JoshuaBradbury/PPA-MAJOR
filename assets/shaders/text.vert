#version 130

in vec3 position;
in vec2 textureCoord;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec2 texCoord;

void main(void) {
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldPosition;
	texCoord = vec2(clamp(position.x, 0, 1), clamp(position.y, 0, 1));
	texCoord = textureCoord;
}