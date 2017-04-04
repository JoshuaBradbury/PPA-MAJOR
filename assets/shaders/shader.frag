#version 130

in vec3 surfaceNormal;
in vec2 texCoord;
in vec3 viewDir;

uniform vec3 colour;
uniform float selected;

float random(vec2 vec) {
	return fract(sin(dot(vec, vec2(12.9898, 78.233))) * 43758.5453);
}

void main(void) {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitDir = normalize(viewDir);
	
	vec3 col = vec3(1.0);
	
	vec3 finalColour = colour;
	
	if (selected > 0.0) {
		finalColour = vec3(1.0) - finalColour;
	} else {
		finalColour *= random(texCoord);
	}
	
	gl_FragColor = vec4((col * finalColour) - (dot(unitNormal, unitDir) * 0.2), 1.0);
}