package components;

import components.math.Vector2;
import components.physics.Collider;
import components.physics.Transform;
import game.Game;
import graphics.GL.DisplayManager;
import graphics.GL.entities.GraphicalEntity2D;
import graphics.GL.models.TexturedModel;

public abstract class GameObject extends CollidableObject {
	
	public GraphicalEntity2D graphicalEntity;
	public TexturedModel texturedModel;
	public Vector2 scale = new Vector2(1, 1);
	
	public GameObject(Transform transform, Collider collider, TexturedModel texturedModel) {
		super(transform, collider);
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
		if(		Game.GAME_BOUNDS.x < transform.position.x ||
			   -Game.GAME_BOUNDS.x > transform.position.x ||
			    Game.GAME_BOUNDS.y < transform.position.y ||
			   -Game.GAME_BOUNDS.y > transform.position.y)
			delete();
		texturedModel.getTexture().tick();
		graphicalEntity.getTexturedModel().setTexture(texturedModel.getTexture());
		graphicalEntity.setScale(scale);
	}
}
