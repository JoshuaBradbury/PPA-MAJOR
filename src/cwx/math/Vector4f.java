package cwx.math;

public class Vector4f {

	private float x, y, z, w;

	public Vector4f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}
	
	public Vector4f(float i) {
		this.x = i;
		this.y = i;
		this.z = i;
		this.w = i;
	}
	
	public Vector4f(Vector2f vec2, float z, float w) {
		this.x = vec2.getX();
		this.y = vec2.getY();
		this.z = z;
		this.w = w;
	}
	
	public Vector4f(Vector3f vec3, float w) {
		this.x = vec3.getX();
		this.y = vec3.getY();
		this.z = vec3.getZ();
		this.w = w;
	}

	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
	
	public float getW() {
		return w;
	}
	
	public Vector4f multiply(Vector4f loc) {
		return multiply(loc.x, loc.y, loc.z, loc.w);
	}

	public Vector4f multiply(float x, float y, float z, float w) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		this.w *= w;
		return this;
	}
}
