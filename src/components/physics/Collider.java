package components.physics;

import components.math.Interval;
import components.math.Vector2;

public abstract class Collider {

	public enum CollisionMethod {
		SAT, EDGE_TEST
	}

	public static CollisionMethod COLLISION_METHOD = null;

	public Vector2[] hitbox;

	protected Vector2 position;
	protected Vector2 velocity = new Vector2();
	public double rotation;
	public int width, height;
	public boolean isSolid;

	public Vector2 MTV = new Vector2();

	public enum ColliderType {
		ELLIPSE, BOX, POLYGON
	}

	public ColliderType colliderType;

	public Collider(Vector2 pos, ColliderType colliderType, int width, int height) {
		this.colliderType = colliderType;
		this.width = width;
		this.height = height;
		setCenterPoint(pos);
	}

	public Collider(Vector2 pos, ColliderType colliderType, Vector2[] hitbox) {
		this.colliderType = colliderType;
		this.hitbox = hitbox;
		setCenterPoint(pos);
	}

	public abstract Vector2 getGreatestPointOnPerimeterAt(double angle);

	public abstract Vector2 getGreatestPointOnPerimeterAt(double width, double height, double angle);

	public void setCenterPoint(Vector2 position) {
		this.position = position;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public boolean isCollidingWith(Collider other) {
		Vector2 relPos = position.minus(other.position);
		Vector2[] corners1 = getCorners(hitbox, position, rotation);
		Vector2[] corners2 = getCorners(other.hitbox, other.position, other.rotation);
		if (COLLISION_METHOD == (CollisionMethod.EDGE_TEST)) {
			Vector2 a, b, c, d;
			for (int i = 0; i < hitbox.length; i++) {
				int ip = (i + 1 == hitbox.length) ? 0 : i + 1;
				for (int j = 0; j < other.hitbox.length; j++) {
					int jp = (j + 1 == other.hitbox.length) ? 0 : j + 1;
					a = corners1[i];
					b = corners1[ip];
					c = corners2[j];
					d = corners2[jp];
					Vector2 intPoi = LineIntersect(a, b, c, d);
					if (intPoi.x <= 1 && intPoi.x >= 0 && intPoi.y <= 1 && intPoi.y >= 0)
						return true;
				}
			}
			return false;
		}
		else if (COLLISION_METHOD == (CollisionMethod.SAT)) {
			Vector2[] axes1 = getAxes(corners1);
			Vector2[] axes2 = getAxes(corners2);
			Interval proj1, proj2;
			double overlap = Double.MAX_VALUE;
			Vector2 smallest = new Vector2();
			
			for (Vector2 axis : axes1) {
				proj1 = getProjection(corners1, axis);
				proj2 = getProjection(corners2, axis);
				if (!proj1.overlaps(proj2)) {
					MTV.setValues(0, 0);
					other.MTV.setValues(0, 0);
					return false;
				} else {
					  double o = proj1.getOverlap(proj2);
					  if (proj1.contains(proj2) || proj2.contains(proj1)) {
					    double mins = Math.abs(proj1.getMin() - proj2.getMin());
					    double maxs = Math.abs(proj1.getMax() - proj2.getMax());
					    o += (mins < maxs) ? mins : maxs;
					  }
					  if (o < overlap) {
					    overlap = o;
					    smallest = axis;
					  }
				}
			}
			for (Vector2 axis : axes2) {
				proj1 = getProjection(corners1, axis);
				proj2 = getProjection(corners2, axis);
				if (!proj1.overlaps(proj2)) {
					MTV.setValues(0, 0);
					other.MTV.setValues(0, 0);
					return false;
				} else {
					  double o = proj1.getOverlap(proj2);
					  if (proj1.contains(proj2) || proj2.contains(proj1)) {
					    double mins = Math.abs(proj1.getMin() - proj2.getMin());
					    double maxs = Math.abs(proj1.getMax() - proj2.getMax());
					    o += (mins < maxs) ? mins : maxs;
					  }
					  if (o < overlap) {
					    overlap = o;
					    smallest = axis;
					  }
				}
			}
			if (smallest.dot(relPos) < 0) smallest.negate();
			MTV.setValues(smallest.x * overlap, smallest.y * overlap);
			other.MTV.setValues(MTV);
			return true;
		} 
		throw new IllegalArgumentException("No Collision Detection Method Selected!");
	}

	public Vector2[] getCorners(Vector2[] coords, Vector2 position, double rotation) {
		Vector2[] result = new Vector2[coords.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Vector2().setValues(coords[i]);
			result[i].rotateBy(rotation);
			result[i].add(position);
		}

		return result;
	}

	private Vector2[] getAxes(Vector2[] corners) {
		Vector2[] axes = new Vector2[corners.length / 2];
		Vector2 p;
		for (int i = 0; i < axes.length; i++) {
			p = new Vector2().setValues(corners[i]);
			p.subtract(corners[i + 1 == corners.length ? 0 : i + 1]);
			axes[i] = p.getNormal();
			axes[i].normalize();
		}
		return axes;
	}

	private Interval getProjection(Vector2[] corners, Vector2 axis) {
		double min = axis.dot(corners[0]);
		double max = min;
		for (Vector2 vert : corners) {
			double p = axis.dot(vert);
			if (p < min)
				min = p;
			else if (p > max)
				max = p;
		}
		return new Interval(min, max);
	}

	/*
	 * Check if the line from point a->b intersects any edge in the Collider
	 * specified
	 */
	public static boolean Raycast2D(Vector2 a, Vector2 b, Collider obj) {
		for (int i = 0; i < obj.hitbox.length; i++) {
			int ip = (i + 1 == obj.hitbox.length) ? 0 : i + 1;
			Vector2 intersectPoint = LineIntersect(a, b, obj.hitbox[i].rotateBy(obj.rotation),
					obj.hitbox[ip].rotateBy(obj.rotation));
			if (intersectPoint.x <= 1 && intersectPoint.x >= 0 && intersectPoint.y <= 1 && intersectPoint.y >= 0)
				return true;
		}
		return false;
	}

	/*
	 * This function checks if the lines a->b and c->d are intersecting. It does
	 * so by finding the intersection point of the lines by finding scalar
	 * variables used to multiply the two lines into a matching coordinate i.e.
	 * : a->b must intersect with c->d a->b can be called r, where r is (b - a)
	 * {the relative distance between points a and b} c->d can be called s,
	 * where r is (d - c) {the relative distance between points c and d} If the
	 * lines intersect: a + (someScalarValue1) * r = c + (someScalarValue2) * s
	 * so that the (someScalarValue) is a number between 0 and 1 (inclusive).
	 */
	public static Vector2 LineIntersect(Vector2 a, Vector2 b, Vector2 c, Vector2 d) {
		Vector2 r = (b.minus(a));
		Vector2 s = (d.minus(c));
		double den = r.cross(s);
		double t, u;
		t = c.minus(a).cross(r) / den;
		u = c.minus(a).cross(s) / den;
		return new Vector2(t, u);
	}

	public abstract float[] getVertexPositions();

	public abstract float[] getTextureCoords();

	public abstract int[] getIndices();
}
