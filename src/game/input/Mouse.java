package game.input;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class Mouse implements GLFWMouseButtonCallbackI {

	public static boolean[] buttons = new boolean[25];

	/*
	 * @see org.lwjgl.glfw.GLFWMouseButtonCallbackI#invoke(long, int, int, int)
	 */
	@Override
	public void invoke(long window, int button, int action, int mods) {
		if (button < buttons.length && button > -1)
			buttons[button] = (action != GLFW_RELEASE);
	}
}
