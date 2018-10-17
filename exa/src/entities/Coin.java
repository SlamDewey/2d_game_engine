package entities;

import components.CollidableObject;
import components.GameObject;
import components.math.Vector2;
import components.physics.EllipseCollider;
import components.physics.Transform;
import graphics.GL.models.TexturedModel;
import resources.Sounds;

public class Coin extends GameObject {

	public Coin(Vector2 position, TexturedModel texturedModel) {
		super(new Transform(position, 1), new EllipseCollider(position, 15, 15), texturedModel);
	}

	@Override
	public void onCollision(CollidableObject obj) {
		if (obj.tag == CollidableObject.PLAYER) {
			delete();
		}
	}

	@Override
	public void onDelete() {
		Sounds.COIN.playFromStart();
	}

}
