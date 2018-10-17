package components;

import components.physics.Collider;
import components.physics.Transform;

public abstract class CollidableObject implements Updatable{
	private static int ID = 0;
	
	public int id;
	public int tag = -1;
	public boolean delete = false;
	
	public static int ENEMY_PROJECTILE	= 0;
	public static int PROJECTILE 		= 1;
	public static int PLAYER 			= 2;
	public static int ENEMY 			= 3;
	public static int WALL				= 4;
	
	public Transform transform;
	public Collider collider;
	
	
	public CollidableObject(Transform transform, Collider collider) {
		id = ID;
		ID++;
		this.transform = transform;
		this.collider = collider;
	}
	public void delete() {
		delete = true;
	}
	
	public abstract void onCollision(CollidableObject obj);
	public abstract void onDelete();

	@Override
	public void tick() {
		transform.tick();
		collider.rotation = transform.rotation;
		collider.setCenterPoint(transform.position);
		collider.setVelocity(transform.velocity);
	}
}
