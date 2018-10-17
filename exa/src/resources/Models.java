package resources;

import components.math.Vector2;
import components.physics.BoxCollider;
import components.physics.Collider;
import components.physics.EllipseCollider;
import graphics.GL.DisplayManager;
import graphics.GL.models.RawModel;

public class Models {

	public static RawModel BOX;
	public static RawModel ELLIPSE;

	private static Collider collider;

	public static void init() {
		
		collider = new BoxCollider(new Vector2(), 2, 2);
		BOX = DisplayManager.loader.loadToVAO(collider.getVertexPositions(), collider.getTextureCoords(),
				collider.getIndices());
		collider = new EllipseCollider(new Vector2(), 1, 1);
		ELLIPSE = DisplayManager.loader.loadToVAO(collider.getVertexPositions(), collider.getTextureCoords(),
				collider.getIndices());
	}
}
