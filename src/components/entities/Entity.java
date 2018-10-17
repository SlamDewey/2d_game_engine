package components.entities;

import components.GameObject;
import components.physics.Collider;
import components.physics.Transform;
import graphics.GL.models.TexturedModel;

public abstract class Entity extends GameObject {
	public Entity(Transform transform, Collider collider, TexturedModel texturedModel) {
		super(transform, collider, texturedModel);
	}
}
