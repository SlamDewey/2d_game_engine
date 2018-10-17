package components;

public class LinkedListNode<T> {

	private T t;
	public LinkedListNode<T> last = null;
	public LinkedListNode<T> next = null;
	
	//constructor
	public LinkedListNode(T t) {
		this.t = t;
	}
	
	//methods
	public void delete() {
		if (last != null)
			last.next = (next);
		if (next != null)
			next.last = (last);
	}
	public void deleteFromHereToEnd() {
		last.next = (null);
	}
	public void addAfter(LinkedListNode<T> node) {
		if (node == null) return;
		node.next = (next);
		if (next != null)
			next.last = (node);
		next = node;
		node.last = (this);
	}
	public void addBefore(LinkedListNode<T> node) {
		if (node == null) return;
		node.last = (last);
		if (last != null)
			last.next = (node);
		last = node;
		node.next = (this);
	}
	
	public void setContent(T t) {
		this.t = t;
	}
	public T getContent() {
		return t;
	}
}
