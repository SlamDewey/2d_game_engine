package graphics.GL.toolbox;

import components.math.Matrix4f;
import components.math.Vector2f;
import components.math.Vector3f;

public class Maths {
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(	Vector3f translation, 
														float rx, float ry, float rz, 
														float xScale, float yScale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate(rx, new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate(ry, new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate(rz, new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(xScale, yScale, 1), matrix, matrix);
		return matrix;
	}
	public static int randInt(int min, int max) {
		return (min + (int) (Math.random() * ((max - min) + 1)));
	}
}
