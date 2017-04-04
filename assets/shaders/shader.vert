#version 130

in vec3 position;
in vec2 inTexCoord;
in vec3 normal;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec3 surfaceNormal;
out vec2 texCoord;
out vec3 viewDir;

void main(void) {
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldPosition;
	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	viewDir = vec3(1.0) - normalize(viewMatrix * vec4(1.0)).xyz;
	texCoord = inTexCoord;
}