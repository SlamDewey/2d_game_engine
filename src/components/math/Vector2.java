package components.math;


public class Vector2 {
	public double x, y;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vector2(){
		x = y = 0.0;
	}
	
	//Calculate-able Factors
	public double magnitude() {
		if (x == 0 && y == 0)				//if at (0, 0) then Math.sqrt() gives (NaN, NaN)
			return 0;
		return Math.sqrt((x * x) + (y * y));
	}
	
	/*
	 * Calculates the angle of this vector to the positive X axis.
	 * returns the calculated angle in radians
	 */
	public double rotation() {
		if (x == 0 && y == 0) return 0;		//skip calculations if (0, 0)
		if (y == 0) return (x > 0) ? 0 : Math.PI;
		if (x == 0) return (y > 0) ? Math.PI / 2 : Math.PI * 1.5;
		double rotation = Math.acos(x / magnitude());
		if (y < 0) rotation = -rotation;
		return rotation;
	}
	
	/*
	 * Check if the given vector lies at some point within
	 * a circle, where the circle has a midpoint of this vector
	 * and a radius of 'discrepancy'
	 */
	public boolean equals(Vector2 v, double discrepancy) {
		Vector2 subbed = minus(v);
		return (subbed.magnitude() < discrepancy);
	}

	/*
	 * All methods that don't alter the values of this Vector2 Object
	 */
	public Vector2 getNormal() {
		return new Vector2(y, -x);
	}
	public double dot(final Vector2 v) {
		return (x * v.x) + (y * v.y);
	}
	public double cross(final Vector2 v) {
		return (x * v.y) - (y * v.x);
	}
	public Vector2 minus(final Vector2 v) {
		return new Vector2(this.x - v.x, this.y - v.y);
	}
	public Vector2 minus(final double x, final double y) {
		return new Vector2(this.x - x, this.y - y);
	}
	public Vector2 plus(final Vector2 v) {
		return new Vector2(this.x + v.x, this.y + v.y);
	}
	public Vector2 plus(final double x, final double y) {
		return new Vector2(this.x + x, this.y + y);
	}	
	
	/*
	 * All methods that alter the values of this Vector2 Object
	 */
	public Vector2 subtract(final double x, final double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	public Vector2 subtract(final Vector2 v) {
		return subtract(v.x, v.y);
	}
	public Vector2 add(final double x, final double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	public Vector2 add(final Vector2 v) {
		return add(v.x, v.y);
	}
	public Vector2 setValues(final double x, final double y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector2 setValues(final Vector2 v) {
		return setValues(v.x, v.y);
	}
	public Vector2 normalize() {
		double mag = magnitude();
		x = x / mag;
		y = y / mag;
		return this;
	}
	public Vector2 rotateBy(double rotation) {
		double x, y;
		x = this.x * Math.cos(rotation) - this.y * Math.sin(rotation);
		y = this.x * Math.sin(rotation) + this.y * Math.cos(rotation);
		setValues(x, y);
		return this;
	}
	public Vector2 negate() {
		x = -x;
		y = -y;
		return this;
	}
	
	
	public Vector3f toVector3f() {
		return new Vector3f((float) x, (float) y, 0f);
	}
	
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "X: " + x + ", Y: " + y;
	}
	public Vector2 getPercentageOf(Vector2 sizeVector) {
		return new Vector2(x / sizeVector.x, y / sizeVector.y);
	}
}
