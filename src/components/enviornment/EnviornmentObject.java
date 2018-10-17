package components.enviornment;

import components.CollidableObject;
import components.math.Vector2;
import components.physics.Collider;
import components.physics.Transform;
import graphics.GL.DisplayManager;
import graphics.GL.entities.GraphicalEntity2D;
import graphics.GL.models.TexturedModel;

public abstract class EnviornmentObject extends CollidableObject {

	public Vector2 scale = new Vector2(1, 1);
	
	public GraphicalEntity2D graphicalEntity;
	TexturedModel texturedModel;
	
	public EnviornmentObject(Transform transform, Collider collider, TexturedModel texturedModel) {
		super(transform, collider);
		this.transform= transform;
		this.collider=collider;
		this.texturedModel = texturedModel;
		setUpGraphicsComponents();
	}
	
	public void setUpGraphicsComponents() {
		scale.x = collider.width  / (DisplayManager.WindowBounds.x * 2);
		scale.y = collider.height / (DisplayManager.WindowBounds.y * 2);
		graphicalEntity = new GraphicalEntity2D(texturedModel, transform, scale);
	}
	@Override
	public void tick() {
		super.tick();
		texturedModel.getTexture().tick();
		graphicalEntity.getTexturedModel().setTexture(texturedModel.getTexture());
		graphicalEntity.setScale(scale);
	}
}
