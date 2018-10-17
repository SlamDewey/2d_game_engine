package resources;

import graphics.GL.Loader;
import graphics.GL.textures.AnimatedModelTexture;
import graphics.GL.textures.ModelTexture;

public class Textures {
	
	public static ModelTexture STRETCH_VISUALIZER;
	public static ModelTexture TEST_TEXTURE;
	public static ModelTexture JOEY_MASSA_FACE;
	public static ModelTexture BORDERED_BLACK;
	public static ModelTexture COIN;
	
	public static ModelTexture[] forAnimation;
	
	public static AnimatedModelTexture ANIMATED;
	
	public static void init() {
		STRETCH_VISUALIZER  = new ModelTexture(Loader.loadTexture("STRETCH_VISUALIZER"));
		TEST_TEXTURE		= new ModelTexture(Loader.loadTexture("TEST_TEXTURE"));
		JOEY_MASSA_FACE		= new ModelTexture(Loader.loadTexture("JOEY_MASSA_FACE"));
		BORDERED_BLACK		= new ModelTexture(Loader.loadTexture("BORDERED_BLACK"));
		COIN				= new ModelTexture(Loader.loadTexture("COIN"));
		
		
		
		forAnimation = new ModelTexture[3];
				forAnimation[0] = STRETCH_VISUALIZER;
				forAnimation[1] = TEST_TEXTURE;
				forAnimation[2] = JOEY_MASSA_FACE; 
		
		ANIMATED 			= new AnimatedModelTexture(forAnimation, 0.5);
	}
}
