package graphics.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;

import components.GameObject;
import components.LinkedListNode;
import components.enviornment.EnviornmentObject;
import components.math.Vector2;
import game.Game;
import game.MathThread;
import game.input.Cursor;
import game.input.Keyboard;
import game.input.Mouse;
import game.input.MouseScroll;
import game.scene.Scene;
import graphics.GL.GUI.GUIElement;
import graphics.GL.GUI.GUIRenderer;

public class DisplayManager {

	public static int WIDTH;
	public static int HEIGHT;
	public static final int FPS_CAP = 100;
	public static float VIRTUAL_ZOOM = -1.0f;
	public static Scene CURRENT_SCENE;
	public static Vector2 WindowBounds;

	private static boolean isFullscreen = false;

	private long window;
	public Game game;

	public static Loader loader;

	public void start(String title) {
		init(title);
		setWindowBounds();

		Keyboard kInput = new Keyboard();
		Mouse mInput = new Mouse();
		Cursor cInput = new Cursor();
		MouseScroll sInput = new MouseScroll();

		glfwSetKeyCallback(window, kInput);
		glfwSetMouseButtonCallback(window, mInput);
		glfwSetCursorPosCallback(window, cInput);
		glfwSetScrollCallback(window, sInput);
	}

	private void init(String title) {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

		window = glfwCreateWindow(WIDTH, HEIGHT, title, isFullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);

		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			if (!isFullscreen) {
				GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
				glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2,
						(vidmode.height() - pHeight.get(0)) / 2);
			}
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		GLCapabilities caps = GL.createCapabilities();
		GL.setCapabilities(caps);

		loader = new Loader();
	}

	public void loop() {

		GUIRenderer guiRenderer = new GUIRenderer(loader);
		MasterRenderer renderer = new MasterRenderer();

		List<GUIElement> guis = new ArrayList<GUIElement>();
		// guis.add(gui);

		game.start();

//		while (MathThread.LAST_UPS != MathThread.UPS_CAP)
//			System.out.println("INITIALIZING GAME" + Game.STATE);

		setScene(game.scene);

		LinkedListNode<GameObject> cursor;

		while (!glfwWindowShouldClose(window)) {
			cursor = CURRENT_SCENE.firstEntity;
			while (cursor != null) {
				renderer.processEntity(cursor.getContent().graphicalEntity);
				cursor = cursor.next;
			}
			for (EnviornmentObject env : CURRENT_SCENE.enviornment) {
				renderer.processEntity(env.graphicalEntity);
			}
			renderer.render();
			guiRenderer.render(guis);
			glfwPollEvents();
			glfwSwapBuffers(window);
		}
		game.stop();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		glfwDestroyWindow(window);
	}

	private void setScene(Scene scene) {
		if (CURRENT_SCENE == null)
			CURRENT_SCENE = scene;
		else {
			CURRENT_SCENE.isActiveScene = false;
			CURRENT_SCENE = scene;
		}
		CURRENT_SCENE.isActiveScene = true;
	}

	public void setWindowBounds() {
		float FOV = Renderer.FOV;
		float aspectRatio = (float) WIDTH / (float) HEIGHT;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		WindowBounds = new Vector2(x_scale * WIDTH, y_scale * HEIGHT);
	}

	public static boolean isFullscreen() {
		return isFullscreen;
	}

	public static void setFullscreen(boolean isFullscreen) {
		DisplayManager.isFullscreen = isFullscreen;
	}

}
