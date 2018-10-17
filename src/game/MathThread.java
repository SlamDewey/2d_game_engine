package game;

import graphics.GL.DisplayManager;

public class MathThread extends Thread implements Runnable {
	
	public static int UPS_CAP = 60;	// # of times to update object positions per second
	public static int UPS = 0;
	public static int LAST_UPS = 0;
	
	long lastTime = 0;
	long updateInterval = 1000000000 / UPS_CAP;
	long lastTime2 = 0;
	long updateInterval2 = 1000000000;
	
	public MathThread(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		while (Game.RUNNING) {
			long now = System.nanoTime();
			if (now - lastTime > updateInterval) {
				lastTime = now;
				UPS++;
				if (DisplayManager.CURRENT_SCENE != null)
					DisplayManager.CURRENT_SCENE.tick();
			}
			if (now - lastTime2 > updateInterval2) {
				lastTime2 = now;
				LAST_UPS = UPS;
				UPS = 0;
				System.out.println(LAST_UPS);
			}
		}
	}
}
