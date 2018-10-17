package game.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback {
	public static boolean[] keys = new boolean[500];
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (key < keys.length && key > -1)
			keys[key] = action != GLFW.GLFW_RELEASE;
	}
}
