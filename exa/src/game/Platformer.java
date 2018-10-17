package game;


import components.math.Vector2;
import components.physics.Collider;
import entities.Ball;
import entities.Coin;
import entities.Floor;
import entities.Player;
import game.scene.Scene;
import graphics.GL.DisplayManager;
import resources.Models;
import resources.Sounds;
import resources.TexturedModels;
import resources.Textures;
import sound.Sound;

public class Platformer extends Game {

	public Platformer(int GameWidth, int GameHeight) {
		super(GameWidth, GameHeight);
		init(); 
		
		scene = new Scene(GAME_BOUNDS);
		scene.enableVirtualZoom();
		
		Vector2 position = new Vector2(0, 0);
		
		Floor floor = new Floor(position, 5000, 50, 0, TexturedModels.WALL);
		position.setValues(1000, 100);
		Floor ceiling = new Floor(position, 500, 50, 0, TexturedModels.WALL);
		Coin[] coins = new Coin[10];
		for (int i = 0; i < coins.length; i++) {
			position.setValues(i * 100 + 100, 250);
			coins[i] = new Coin(position, TexturedModels.COIN);
		}
		position.setValues(-200, 200);
		Ball ball = new Ball(position, 50, 50, TexturedModels.enemyShot);
		
		position.setValues(0, 100);
		Player player = new Player(position);
		scene.addEnviornment(floor);
		scene.addEnviornment(ceiling);
		scene.addEntity(ball);
		scene.addEntity(player);
		for (Coin coin : coins) {
			scene.addEntity(coin);
		}
		scene.setFocus(player.transform);
	}
	
	public static void init() {
		Game.init();
		Models.init();
		Sounds.init();
		Textures.init();
		TexturedModels.init();
	}
	
	
	public static void main(String[] args) {
		Sound.volume = -20;
		Collider.COLLISION_METHOD = Collider.CollisionMethod.SAT;
		DisplayManager.WIDTH = 1280;
		DisplayManager.HEIGHT = 720;
		DisplayManager.setFullscreen(false);
		DisplayManager dm = new DisplayManager();
		dm.start("ENGINE TEST");
		Platformer.init();
		Game game = new Platformer(5000, 5000);
		dm.game = game;
		dm.loop();
	}

}