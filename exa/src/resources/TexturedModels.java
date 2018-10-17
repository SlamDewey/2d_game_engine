package resources;

import graphics.GL.models.TexturedModel;

public class TexturedModels {

	public static TexturedModel playerShot;
	public static TexturedModel enemyShot;
	public static TexturedModel PLAYER;
	public static TexturedModel WALL;
	public static TexturedModel COIN;
	
	public static void init() {
		playerShot 	= new TexturedModel(Models.ELLIPSE, Textures.STRETCH_VISUALIZER);
		enemyShot 	= new TexturedModel(Models.ELLIPSE, Textures.JOEY_MASSA_FACE);
		PLAYER 		= new TexturedModel(Models.BOX	  , Textures.ANIMATED);
		WALL		= new TexturedModel(Models.BOX	  , Textures.BORDERED_BLACK);
		COIN		= new TexturedModel(Models.ELLIPSE, Textures.COIN);
	}
}
