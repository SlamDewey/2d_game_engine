package game;

import components.math.Vector2;
import game.scene.Scene;

public class Game {

	public static boolean RUNNING = false;

	public static Vector2 GAME_BOUNDS;
	public static Vector2 WORLD_CENTER;

	public static GameState STATE;

	private static MathThread mThread;
	public Scene scene;

	public Game(int WorldWidth, int WorldHeight) {
		GAME_BOUNDS = new Vector2(WorldWidth, WorldHeight);
		WORLD_CENTER = new Vector2(GAME_BOUNDS.x / 2, GAME_BOUNDS.y / 2);
		STATE = GameState.SETUP;
	}

	public static void init() {
		STATE = GameState.INITIALIZATION;
		mThread = new MathThread("Math");
	}

	public synchronized void start() {
		mThread.start();
		RUNNING = true;
	}

	public synchronized void stop() {
		RUNNING = false;
		try {
			mThread.join();
		} catch (Exception e) {
			System.err.println("Could not join Math Thread in Game!");
			e.printStackTrace();
		}

	}
}
