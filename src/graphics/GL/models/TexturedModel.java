package graphics.GL.models;

import graphics.GL.textures.ModelTexture;

public class TexturedModel {
	private RawModel model;
	private ModelTexture texture;
	
	public TexturedModel(RawModel model, ModelTexture texture) {
		this.model = model;
		this.texture = texture;
	}
	
	public void setTexture(ModelTexture texture) {
		this.texture = texture;
	}
	
	public RawModel getRawModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}
}
