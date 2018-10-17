package graphics.GL.GUI;

import components.math.Vector2f;

public class GUIElement {
	
	private int cornerTexture;
	private int sideTexture;
	private int fillTexture;
	
	private Vector2f position;
	private Vector2f scale;
	public GUIElement(int cornerTexture, int sideTexture, int fillTexture, Vector2f position, Vector2f scale) {
		this.cornerTexture = cornerTexture;
		this.sideTexture = sideTexture;
		this.fillTexture = fillTexture;
		this.position = position;
		this.scale = scale;
	}
	
	public GUIElement(int fillTexture, Vector2f position, Vector2f scale) {
		this.fillTexture = fillTexture;
		sideTexture = cornerTexture = -1;
		this.position = position;
		this.scale = scale;
	}
	
	public int getCornerTexture() {
		return cornerTexture;
	}
	public int getSideTexture() {
		return sideTexture;
	}
	public int getFillTexture() {
		return fillTexture;
	}
	public Vector2f getPosition() {
		return position;
	}
	public Vector2f getScale() {
		return scale;
	}
	
	
	
}
