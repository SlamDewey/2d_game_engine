package game.scene;

import java.util.ArrayList;

import components.CollidableObject;
import components.GameObject;
import components.LinkedListNode;
import components.Updatable;
import components.enviornment.EnviornmentObject;
import components.math.Vector2;
import components.math.Vector3f;
import components.physics.Transform;
import game.scene.spacial.AABB;
import game.scene.spacial.Point;
import game.scene.spacial.QuadTree;
import graphics.GL.DisplayManager;

public class Scene implements Updatable {

	public boolean isActiveScene = false;
	private boolean isVirtualZoomEnabled = false;

	public Vector2 GameBounds;

	public Transform focus;
	public LinkedListNode<GameObject> firstEntity;
	public LinkedListNode<GameObject> lastEntity;

	public ArrayList<EnviornmentObject> enviornment = new ArrayList<EnviornmentObject>();

	private ArrayList<Key> collisionKeys = new ArrayList<Key>();

	public QuadTree map;
	public Vector2 CameraOffset = new Vector2();
	public double rot = 0.0d;

	/*
	 * Create a new Scene with no reference transform
	 */
	public Scene(Vector2 GameBounds) {
		focus = new Transform(0, 0, 0);
		this.GameBounds = GameBounds;
		firstEntity = lastEntity = null;
		setUpQuadTree();
	}

	/*
	 * Create a new Scene with a reference transform
	 */
	public Scene(Transform focus, Vector2 GameBounds) {
		this.focus = focus;
		this.GameBounds = GameBounds;
		firstEntity = lastEntity = null;
		setUpQuadTree();
	}

	/*
	 * Create a new Scene with a reference transform belonging to a GameObject
	 * add that gameObject to the LinkedList as the first Node
	 */
	public Scene(GameObject focusObject, Vector2 GameBounds) {
		this.focus = focusObject.transform;
		this.GameBounds = GameBounds;
		firstEntity = new LinkedListNode<GameObject>(focusObject);
		setUpQuadTree();
	}

	// set Map = empty Quad Tree
	public void setUpQuadTree() {
		double w = GameBounds.x;
		double h = GameBounds.y;
		AABB bounds = new AABB(new Vector2(), w, h);
		map = new QuadTree(bounds);
	}

	public void resetQuad() {
		map.reset();
	}

	/*
	 * @see components.Updatable#tick()
	 * 
	 * //updating Set the rotation of the camera to the rotation of the focus
	 * transform Then cycle through each gameObject in the LinkedList of all
	 * Objects if a GameObject is marked for deletion, delete it. otherwise
	 * update that GameObject and if necessary add it to the Spacial
	 * Partitioning Grid, so it can be checked for collisions.
	 * 
	 * //collision detection Check all objects in the grid for collisions, then
	 * remove that object from the grid.
	 * 
	 */
	Vector3f position;
	Point p;
	AABB bounds;
	
	@Override
	public void tick() {
		setRotation(focus.rotation);

		/*
		 * for Each GameObject
		 */
		LinkedListNode<GameObject> cursor = firstEntity;
		while (cursor != null) {
			if (cursor.getContent().delete) {
				cursor.getContent().onDelete();
				if (cursor.last != null) {
					cursor = cursor.last;
					cursor.next.delete();
				} 
				else
					firstEntity = cursor.next;
			} 
			else {
				GameObject cur = cursor.getContent();
				cur.tick();
				position = getPosition(cursor).getPercentageOf(DisplayManager.WindowBounds).toVector3f();
				cur.graphicalEntity.setPosition(position);
				cur.graphicalEntity.setZRot((float) getRotation(cur.transform));
				bounds = AABB.OOBBtoAABB(cur.collider.hitbox, cur.transform.position, cur.transform.rotation);
				p = new Point(bounds, cur);
				map.insert(p);
			}
			cursor = cursor.next;
		}
		/*
		 * For each enviornment object
		 */
		for (EnviornmentObject env : enviornment) {
			if (env.delete) {
				enviornment.remove(env);
				break;
			}
			env.tick();
			position = getPosition(env.transform).getPercentageOf(DisplayManager.WindowBounds).toVector3f();
			env.graphicalEntity.setPosition(position);
			env.graphicalEntity.setZRot((float) getRotation(env.transform));
			bounds = AABB.OOBBtoAABB(env.collider.hitbox, env.transform.position, env.transform.rotation);
			p = new Point(bounds, env);
			map.insert(p);
		}
		
		// collision detection
		CollidableObject a, b;
		int col = 50, row = 50;
		double dc = GameBounds.x / col;
		double dr = GameBounds.y / row;
		double mapHW = map.boundary.hWidth;
		double mapHH = map.boundary.hHeight;
		for (int y = 0; y < col * 2; y++)
			for (int x = 0; x < row * 2; x++) {
				bounds = new AABB(new Vector2((dr * x * 1.5) + dr - mapHW, (dc * y * 1.5) + dc - mapHH), dc, dr);
				ArrayList<Point> p = map.getPointsInBounds(bounds);
				for (Point cur : p) {
					int ind = p.indexOf(cur);
					for (int i = 1; i < p.size() - ind; i++) {
						Point cur2 = p.get(i + ind);
						a = cur.obj;
						b = cur2.obj;
						if (a.tag != b.tag) {
							boolean m = false;
							for (Key key : collisionKeys)
								if (key.matches(a.id, b.id))
									m = true;
							if (!m)
								if (a.collider.isCollidingWith(b.collider)) {
									a.onCollision(b);
									b.onCollision(a);
									collisionKeys.add(new Key(a.id, b.id));
								}
						}
					}
				}
			}
		collisionKeys.clear();
		resetQuad();
	}

	public void enableVirtualZoom() {
		isVirtualZoomEnabled = true;
	}

	public void disableVirtualZoom() {
		isVirtualZoomEnabled = false;
	}

	public boolean isVirtualZoomEnabled() {
		return isVirtualZoomEnabled;
	}

	public void updateVirtualZoom(double dy) {
		if (isVirtualZoomEnabled) {
			DisplayManager.VIRTUAL_ZOOM += dy * 0.015f * 10;
			if (DisplayManager.VIRTUAL_ZOOM > -0.15f)
				DisplayManager.VIRTUAL_ZOOM = -0.15f;
		}
	}

	/*
	 * Find the last entity in the LinkedList of objects, Create a new Node with
	 * the specified GameObject Add that GameObject after the current lastEntity
	 * of the list
	 */
	public void addEntity(GameObject gameObject) {
		setLastEntity();
		LinkedListNode<GameObject> toAdd = new LinkedListNode<GameObject>(gameObject);
		if (firstEntity == null) {
			firstEntity = toAdd;
			return;
		}
		lastEntity.addAfter(toAdd);
		lastEntity = toAdd;
	}

	public void addEnviornment(EnviornmentObject envObj) {
		enviornment.add(envObj);
	}

	/*
	 * Find the position of the game object held by the specified node, and
	 * return that coordinate as a new coordinate relative to the
	 * Transform.position vector of the 'focus' transform.
	 */
	public Vector2 getPosition(LinkedListNode<GameObject> node) {
		if (focus.position.equals(new Vector2(), 0))
			return node.getContent().transform.position;
		return getPosition(node.getContent().transform);
	}

	public Vector2 getPosition(Transform transform) {
		Vector2 sub = transform.position.minus(focus.position);
		double relPos = sub.magnitude();
		double angle = sub.rotation() + rot;
		return new Vector2(relPos * Math.cos(angle) + CameraOffset.x, relPos * Math.sin(angle) + CameraOffset.y);
	}

	/*
	 * Return the relative rotation of the object held by the specified node.
	 * The rotation is relative to this scene, and therefore the focus
	 * transform.
	 */
	public double getRotation(Transform transform) {
		if (transform == focus)
			return 0;
		// return 0;
		return (Math.PI) + transform.rotation + rot;
	}

	/*
	 * Find the specified GameObject in the LinkedList of all GameObjects and
	 * mark it for deletion. If this method cannot find the specified GameObject
	 * in its list, it will do nothing.
	 */
	public void delete(GameObject gameObject) {
		LinkedListNode<GameObject> cursor = firstEntity;
		while (cursor.getContent() != gameObject)
			cursor = cursor.next;
		if (gameObject == cursor.getContent())
			cursor.delete();
	}

	/*
	 * Returns an int referencing the total number of GamemObjects this scene
	 * manages
	 */
	public int getObjectCount() {
		return getObjectCount(firstEntity);
	}

	public int getObjectCount(LinkedListNode<GameObject> first) { // mainly for
																	// debug
																	// purposes
		int count = 0;
		LinkedListNode<GameObject> last = first;
		while (last != null) {
			last = last.next;
			count++;
		}
		return count;
	}

	// setters
	public void setFocus(Transform focus) {
		this.focus = focus;
	}

	/*
	 * Cycle through all Linked Nodes, until hitting a node where node.next ==
	 * null i.e. the last node. Then set the 'lastEntity' variable to reference
	 * that node.
	 */
	public void setLastEntity() {
		if (firstEntity == null)
			return;
		LinkedListNode<GameObject> last = firstEntity;
		while (last.next != null)
			last = last.next;
		lastEntity = last;
	}

	public void setCameraOffset(Vector2 CameraOffset) {
		this.CameraOffset = CameraOffset;
	}

	public void setRotation(double rot) {
		this.rot = rot;
	}
}
