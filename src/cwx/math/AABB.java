package cwx.math;

/**
 * A class to represent an AABB (Axis-Aligned Bounding Box)
 */
public class AABB {

	/**
	 * The centre of the AABB and half of the widths in each direction
	 */
	private Vector3f centre = new Vector3f(0, 0, 0), halfWidths = new Vector3f(0);

	/**
	 * Sets the half widths of the AABB to the scale
	 * @param scale The scale of the new box
	 */
	public void setScale(Vector3f scale) {
		halfWidths = scale.clone();
	}
	
	/**
	 * Sets the centre of the AABB
	 * @param centre The centre of the new box
	 */
	public void setCentre(Vector3f centre) {
		this.centre = centre.clone();
	}
	
	/**
	 * Returns the Centre of the AABB
	 * @return The centre of the AABB
	 */
	public Vector3f getCentre() {
		return centre;
	}

	/**
	 * Checks if the AABB is colliding with the specified AABB
	 * @param aabb The other AABB that is being checked for collision
	 * @return Whether or not the two AABBs collide
	 */
	public boolean isColliding(AABB aabb) {
		// Stores the centres to simplify the names
		Vector3f centre = getCentre(), aabbCentre = aabb.getCentre();
		// If the distance between the centres in the x direction is greater than the half widths in the x direction
		// Then they are not colliding with each other
		if (Math.abs(centre.getX() - aabbCentre.getX()) > (halfWidths.getX() + aabb.halfWidths.getX()))
			return false;
		// Same as above for Y and Z
		if (Math.abs(centre.getY() - aabbCentre.getY()) > (halfWidths.getY() + aabb.halfWidths.getY()))
			return false;
		if (Math.abs(centre.getZ() - aabbCentre.getZ()) > (halfWidths.getZ() + aabb.halfWidths.getZ()))
			return false;

		return true;
	}
}
