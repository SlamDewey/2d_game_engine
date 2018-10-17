package game.scene.spacial;

import components.math.Vector2;

public class AABB {
	public Vector2 center;
	public double hWidth, hHeight;
	
	public AABB(Vector2 center, double hWidth, double hHeight) {
		this.center = center;
		this.hWidth = hWidth;
		this.hHeight = hHeight;
	}
	
	public boolean containsPoint(Vector2 point) {
		return (point.x < center.x + hWidth &&
				point.x > center.x - hWidth &&
				point.y < center.y + hWidth &&
				point.y > center.y - hWidth);
	}
	
	public boolean intersectsAABB(AABB other) {
		return !(	center.x + hWidth <= other.center.x - other.hWidth ||
					center.x - hWidth >= other.center.x + other.hWidth ||
					center.y + hHeight <= other.center.y - other.hHeight ||
					center.y - hHeight >= other.center.y + other.hHeight);
	}
	
	public static AABB OOBBtoAABB(Vector2[] hitbox, Vector2 center, double rotation) {
		Vector2[] verts = getCorners(hitbox, rotation);
		double xMax = verts[0].x;
		double yMax = verts[0].y;
		for (int i = 0; i < verts.length; i++) {
			if (verts[i].x > xMax)
				xMax = verts[i].x;
			if (verts[i].y > yMax)
				yMax = verts[i].y;
		}
		return new AABB(center, xMax, yMax);
	}
	private static Vector2[] getCorners(Vector2[] coords, double rotation) {
		Vector2[] result = new Vector2[coords.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Vector2().setValues(coords[i]);
			result[i].rotateBy(rotation);
		}
		return result;
	}
	
	@Override
	public String toString() {
		return center.toString() + " | W: " + hWidth + ", H: " + hHeight;
	}
}
