package game.scene.spacial;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
	
	private static final int CAPACITY = 3;
	
	public AABB boundary;
	
	List<Point> members = new ArrayList<Point>();
	
	QuadTree nw, ne, sw, se;
	
	public QuadTree(AABB boundary) {
		this.boundary = boundary;
		
	}
	
	public boolean insert(Point member) {
		AABB bounds = member.bounds;
		if (!boundary.intersectsAABB(bounds))
			return false;
		if (members.size() <= CAPACITY) {
			members.add(member);
			return true;
		}
		if (nw == null)
			subdivide();
		boolean good = false;
		if (nw.insert(member)) good = true;
		if (ne.insert(member)) good = true;
		if (sw.insert(member)) good = true;
		if (se.insert(member)) good = true;
		return good;
	}
	public void subdivide() {
		double nHWidth = boundary.hWidth / 2;
		double nHHeight = boundary.hHeight/ 2;

		nw = new QuadTree(new AABB(boundary.center.plus(-nHWidth,  nHHeight), nHWidth, nHHeight));
		ne = new QuadTree(new AABB(boundary.center.plus( nHWidth,  nHHeight), nHWidth, nHHeight));
		sw = new QuadTree(new AABB(boundary.center.plus(-nHWidth, -nHHeight), nHWidth, nHHeight));
		se = new QuadTree(new AABB(boundary.center.plus( nHWidth, -nHHeight), nHWidth, nHHeight));
	}
	
	public ArrayList<Point> getPointsInBounds(AABB bounds) {
		
		ArrayList<Point> mems = new ArrayList<Point>();
		if (!bounds.intersectsAABB(boundary))
			return mems;
		
		for(Point cursor : members) {
			if (bounds.intersectsAABB(cursor.bounds))
				mems.add(cursor);
		}
		
		if (nw == null)
			return mems;
		
		
		ArrayList<Point> nwPoints = nw.getPointsInBounds(bounds);
		for (Point cursor : nwPoints)
			mems.add(cursor);
		ArrayList<Point> nePoints = ne.getPointsInBounds(bounds);
		for (Point cursor : nePoints)
			mems.add(cursor);
		ArrayList<Point> swPoints = sw.getPointsInBounds(bounds);
		for (Point cursor : swPoints)
			mems.add(cursor);
		ArrayList<Point> sePoints = se.getPointsInBounds(bounds);
		for (Point cursor : sePoints)
			mems.add(cursor);
		
		return mems;
	}
	
	public void reset() {
		nw = ne = sw = se = null;
		members.clear();
	}
}