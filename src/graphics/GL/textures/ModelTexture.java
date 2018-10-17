package graphics.GL.textures;

import components.Updatable;

public class ModelTexture implements Updatable {
	protected int textureID;
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getTextureID() {
		return textureID;
	}

	@Override
	public void tick() {
		
	}
}
