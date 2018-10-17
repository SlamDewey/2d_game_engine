package game.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import components.math.Vector2;
import graphics.GL.DisplayManager;

public class Cursor extends GLFWCursorPosCallback {
	
	public static Vector2 Position = new Vector2();
	
	@Override
	public void invoke(long window, double xPos, double yPos) {
		Position.setValues(xPos - (DisplayManager.WIDTH / 2), (DisplayManager.HEIGHT / 2) - yPos);
	}

}
