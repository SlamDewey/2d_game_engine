package game.input;

import org.lwjgl.glfw.GLFWScrollCallbackI;

import graphics.GL.DisplayManager;

public class MouseScroll implements GLFWScrollCallbackI {
	
	public static double dx, dy;

	@Override
	public void invoke(long window, double xOffset, double yOffset) {
		dx = xOffset;
		dy = yOffset;
		DisplayManager.CURRENT_SCENE.updateVirtualZoom(dy);
	}
}
