package game.scene;

public class Key {
	int obj1, obj2;
	
	public Key(int obj1, int obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	
	public boolean matches(int id1, int id2) {
		return (obj1 == id1 && obj2 == id2) || (obj2 == id1 && obj1 == id2);
	}
}
