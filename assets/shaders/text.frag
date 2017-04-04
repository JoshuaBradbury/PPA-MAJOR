#version 130

in vec2 texCoord;

uniform sampler2D texture2d;
uniform vec3 colour;

void main(void) {
	vec4 col = texture(texture2d, texCoord);
	if (col.x == 0 && col.y == 0 && col.z == 0) return;
	gl_FragColor = col;
}