package entities;



import static org.lwjgl.glfw.GLFW.*;

import components.CollidableObject;
import components.entities.Entity;
import components.math.Vector2;
import components.physics.BoxCollider;
import components.physics.Transform;
import game.MathThread;
import game.input.Keyboard;
import resources.Sounds;
import resources.TexturedModels;

public class Player extends Entity {

	private double playerSpeed = 10.0;
	private Vector2 gravity = new Vector2(0, -1);
	
	private boolean canJump = false;
	private boolean canShoot = false;
	
	private int shotsPerSec = 10;
	private int shotWait = (int) (MathThread.UPS_CAP / shotsPerSec);
	private int shotTick = 0;
	
	public Player(Vector2 position) {
		super(	new Transform(position, 1),
				new BoxCollider(position, 50, 50),
				TexturedModels.PLAYER);
		tag = CollidableObject.PLAYER;
		transform.force.setValues(gravity);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		shotTick++;

		if (shotTick > shotWait) {
			canShoot = true;
		}
		
		double dx, dy;
		dx = dy = 0.0d;
		if (Keyboard.keys[GLFW_KEY_D])
			dx += 1.0;
		if (Keyboard.keys[GLFW_KEY_A])
			dx -= 1.0;
		if (Keyboard.keys[GLFW_KEY_SPACE] && canJump) {
			dy += 10;
			canJump = false;
			Sounds.JUMP.playFromStart();
		}
		transform.force.setValues(gravity.x, dy + gravity.y);
		transform.velocity.x = dx * playerSpeed;
	}
	
	@Override
	public void onCollision(CollidableObject obj) {
		if (obj.tag == CollidableObject.WALL && obj.transform.position.y < transform.position.y) {
			canJump = true;
		}
	}
	@Override
	public void onDelete() {
		
	}
}
