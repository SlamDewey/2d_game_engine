package components.physics;

import components.math.Vector2;

public class EllipseCollider extends Collider {
	
	double xRadii, yRadii;
	private int GraphicalVerts = 50;
	
	public EllipseCollider(Vector2 pos, double xRadii, double yRadii) {
		super(pos, ColliderType.ELLIPSE, (int) xRadii * 2, (int) yRadii * 2);
		this.xRadii = xRadii;
		this.yRadii = yRadii;
		int vertices = 8;
		
		hitbox = new Vector2[vertices];
		for (int i = 0; i < hitbox.length; i++) {
			double theta = (i - 1) * ((2 * Math.PI) / vertices);
			hitbox[i] = getGreatestPointOnPerimeterAt(theta);
		}
		setCenterPoint(pos);
	}

	@Override
	public Vector2 getGreatestPointOnPerimeterAt(double angle) {
		return new Vector2(-xRadii * Math.cos(angle), yRadii * Math.sin(angle));
	}

	@Override
	public Vector2 getGreatestPointOnPerimeterAt(double width, double height, double angle) {
		return new Vector2(-(width / 2) * Math.cos(angle), (height / 2) * Math.sin(angle));
	}
	
	@Override
	public float[] getVertexPositions() {
		float[] positions = new float[(GraphicalVerts + 1) * 2];
		double angleChange = ((Math.PI * 2) / GraphicalVerts);
		positions[0] = 0f;
		positions[1] = 0f;
		float ratio = (float) (width / height);
		Vector2 sc;
		for (int i = 2; i < positions.length - 1; i += 2) {
			sc = getGreatestPointOnPerimeterAt(2, 2, ((i / 2) - 1) * angleChange);
			positions[i] 		= (float) (sc.x);
			positions[i + 1] 	= (float) (sc.y / ratio);
		}
		return positions;
	}

	@Override
	public int[] getIndices() {
		int[] indices = new int[(GraphicalVerts) * 3];
		for (int i = 0; i < indices.length - 2; i += 3) {
			indices[i] 		= (i / 3) + 1;
			indices[i + 1] 	= 0;
			indices[i + 2] 	= (i == 0) ? GraphicalVerts : (i / 3);
		}
		return indices;
	}

	@Override
	public float[] getTextureCoords() {
		float[] texCoords = new float[(GraphicalVerts + 1) * 2];
		float angleChange = (float) ((Math.PI * 2) / GraphicalVerts);
		texCoords[0] = 0.5f;
		texCoords[1] = 0.5f;
		Vector2 sc;
		for (int i = 2; i < texCoords.length - 1; i += 2) {
			sc = getGreatestPointOnPerimeterAt(1, 1, ((i / 2) - 1) * angleChange);
			texCoords[i] 		= (float) ((sc.x) + 0.5f);
			texCoords[i + 1] 	= (float) ((sc.y) + 0.5f);
		}
		return texCoords;
	}
}
