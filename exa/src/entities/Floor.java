package entities;

import components.CollidableObject;
import components.GameObject;
import components.enviornment.EnviornmentObject;
import components.math.Vector2;
import components.physics.BoxCollider;
import components.physics.Transform;
import graphics.GL.models.TexturedModel;

public class Floor extends EnviornmentObject {

	public Floor(Vector2 pos, int width, int height, double rotation, TexturedModel texturedModel) {
		super(new Transform(pos, 1), new BoxCollider(pos, width, height), texturedModel);
		transform.rotation = rotation;
		tag = GameObject.WALL;
	}

	@Override
	public void onCollision(CollidableObject obj) {
		if (obj.transform.position.y > transform.position.y) {
			obj.transform.position.add(obj.collider.MTV);
			if (obj.tag == CollidableObject.PLAYER) {
				obj.transform.force.add(0, 1);
				obj.transform.velocity.y = 0;
			}
		}
	}

	@Override
	public void onDelete() {

	}

}
