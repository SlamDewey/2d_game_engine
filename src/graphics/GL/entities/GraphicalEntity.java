package graphics.GL.entities;

import components.math.Vector2;
import components.math.Vector3f;
import graphics.GL.models.TexturedModel;

public class GraphicalEntity {
	
	private TexturedModel model;
	private Vector3f position;
	protected float rot_x = 0f, rot_y = 0f, rot_z = 0f;
	protected float xScale, yScale;
	
	public GraphicalEntity(TexturedModel model, Vector3f position, float rot_x, float rot_y, float rot_z, float xScale,
			float yScale) {
		super();
		this.model = model;
		this.position = position;
		this.rot_x = rot_x;
		this.rot_y = rot_y;
		this.rot_z = rot_z;
		this.xScale = xScale;
		this.yScale = yScale;
	}
	public GraphicalEntity(TexturedModel model, Vector2 position, float rot_z, float xScale, float yScale) {
		this.model = model;
		this.position = new Vector3f((float) position.x, (float) position.y, 0);
		this.rot_z = rot_z;
		this.xScale = xScale;
		this.yScale = yScale;
	}
	
	public TexturedModel getTexturedModel() {
		return model;
	}
	public void setTexturedModel(TexturedModel model) {
		this.model = model;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position.setX(position.getX());
		this.position.setY(position.getY());
		this.position.setZ(position.getZ());
	}
	public float getXRot() {
		return rot_x;
	}
	public void setXRot(float rot_x) {
		this.rot_x = rot_x;
	}
	public float getYRot() {
		return rot_y;
	}
	public void setYRot(float rot_y) {
		this.rot_y = rot_y;
	}
	public float getZRot() {
		return rot_z;
	}
	public void setZRot(float rot_z) {
		this.rot_z = rot_z;
	}
	public float getXScale() {
		return xScale;
	}
	public void setXScale(float xScale) {
		this.xScale = xScale;
	}
	public float getYScale() {
		return yScale;
	}
	public void setYScale(float yScale) {
		this.yScale = yScale;
	}
	
	
}
