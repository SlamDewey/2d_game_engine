package components.physics;

import components.Updatable;
import components.math.Vector2;

public class Transform implements Updatable {
	
	public double mass;
	public Vector2 acceleration	= new Vector2();
	public Vector2 force		= new Vector2();
	public Vector2 position 	= new Vector2();
	public Vector2 velocity		= new Vector2();

	public double rotVelocity = 0d, rotation = 0d;
	
	public Transform(double x, double y, double mass) {
		position = new Vector2(x, y);
	}
	public Transform(Vector2 position, double mass) {
		this.position.setValues(position);
		this.mass = mass;
	}

	@Override
	public void tick() {
		rotation += rotVelocity;
//		if (rotation > Math.PI)
//			rotation -= Math.PI * 2;
//		if (rotation < -Math.PI)
//			rotation += Math.PI * 2;
		acceleration.setValues(force.x / mass, force.y / mass);
		velocity.add(acceleration);
		position.add(velocity);
	}
}
