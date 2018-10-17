package components.physics;

import components.math.Vector2;

public class BoxCollider extends Collider {

	public BoxCollider(Vector2 pos, int width, int height) {
		super(pos, ColliderType.BOX, width, height);
		hitbox = new Vector2[4];
		double hWidth = width / 2.0;
		double hHeight = height / 2.0;
		hitbox[0] = new Vector2(-hWidth,  hHeight);
		hitbox[1] = new Vector2(-hWidth, -hHeight);
		hitbox[2] = new Vector2( hWidth, -hHeight);
		hitbox[3] = new Vector2( hWidth,  hHeight);
		setCenterPoint(pos);
	}

	public BoxCollider(Vector2 pos, Vector2[] hitbox) {
		super(pos, ColliderType.BOX, hitbox);
	}

	@Override
	public Vector2 getGreatestPointOnPerimeterAt(double angle) {
		return getGreatestPointOnPerimeterAt(width, height, angle);
	}
	@Override
	public Vector2 getGreatestPointOnPerimeterAt(double width, double height, double angle) {
		double hWidth = width / 2;
		double hHeight = height / 2;
		Vector2 result = new Vector2();
		
		double cornerAngle = new Vector2(hWidth, hHeight).rotation();
		double absAngle = (angle >= 0) ? angle : angle + (Math.PI);
		absAngle = (absAngle < Math.PI / 2) ? absAngle : Math.PI - absAngle;
		
		if (absAngle < cornerAngle)
			result.setValues(hWidth, hWidth * Math.tan(absAngle));
		else
			result.setValues(hHeight * Math.tan((Math.PI / 2) - absAngle), hHeight);
		result.y = -result.y;
		if (angle < 0) {
			result.y = -result.y;
			if (angle < -Math.PI / 2)
				result.x = -result.x;
		}
		else
			if (angle > Math.PI / 2) {
				result.x = -result.x;
			}
		return result;
	}
	
	@Override
	public float[] getVertexPositions() {
		float[] positions = new float[hitbox.length * 2];
		Vector2 max = new Vector2(width / 2, height / 2);
		float ratio = (float) (max.x / max.y);
		Vector2 sc;
		for (int i = 0; i < positions.length - 1; i += 2) {
			sc = hitbox[i / 2].getPercentageOf(max);
			positions[i] 		= (float) (sc.x);
			positions[i + 1] 	= (float) (sc.y / ratio);
		}
		return positions;
	}
	@Override
	public int[] getIndices() {
		int[] indices =  new int[] {
				0, 1, 3,
				3, 1, 2
		};
		return indices;
	}

	@Override
	public float[] getTextureCoords() {
		return new float[] {
			1, 0,
			1, 1,
			0, 1,
			0, 0
		};
	}
}
