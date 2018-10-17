package entities;

import components.CollidableObject;
import components.GameObject;
import components.math.Vector2;
import components.physics.EllipseCollider;
import components.physics.Transform;
import graphics.GL.models.TexturedModel;
import resources.Sounds;

public class Ball extends GameObject {
	
	int count = 0;

	public Ball(Vector2 position, int width, int height, TexturedModel texturedModel) {
		super(	new Transform(position, 1),
				new EllipseCollider(position, width, height),
				texturedModel);
		tag = CollidableObject.ENEMY_PROJECTILE;
	}

	@Override
	public void onCollision(CollidableObject obj) {
		Sounds.BOUNCE.playFromStart();
		if (obj.tag != GameObject.WALL) {
//			obj.transform.velocity.setValues(transform.velocity);
			count++;
		}
		if (count > 10)
			delete();
	}
	@Override
	public void onDelete() {
		Sounds.BOSS_DEFEATED.play();
	}
}