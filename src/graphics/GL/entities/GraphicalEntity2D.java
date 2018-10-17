package graphics.GL.entities;

import components.math.Vector2;
import components.physics.Transform;
import graphics.GL.models.TexturedModel;

public class GraphicalEntity2D extends GraphicalEntity {

	public GraphicalEntity2D(TexturedModel model, Transform transform, Vector2 scale) {
		super(model, transform.position, (float) transform.rotation, (float) scale.x, (float) scale.y);
	}
	
	public void setScale(Vector2 scale) {
		setXScale((float) scale.x);
		setYScale((float) scale.y);
	}
}
