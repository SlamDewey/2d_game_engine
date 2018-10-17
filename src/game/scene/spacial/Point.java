package game.scene.spacial;

import components.CollidableObject;

public class Point {
	public AABB bounds;
	public CollidableObject obj;
	public Point(AABB bounds, CollidableObject obj) {
		this.bounds = bounds;
		this.obj = obj;
	}
}
