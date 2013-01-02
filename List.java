package datastructure;

public abstract class List<T> {
    public enum ListType {
	LinkedList, ArrayList
    };
    
    public abstract void add(T value);
    public abstract boolean remove(T value);
    public abstract boolean contains(T value);
    public abstract T get(int index);
    public abstract int size();
    
    public static <T> List<T> createList(ListType type) {
	switch(type) {
	default:
	    return new LinkedList<T>();
	}
    }

    public static class LinkedList<T> extends List<T> {
	private Node<T> head = null;
	private Node<T> tail = null;
	private int size = 0;
	public void add(T value) {
	    add(new Node<T>(value));
	}
	private void add(Node<T> node) {
	    if (head==null) {
		head = node;
		tail = node;
		node.nextNode = null;
		node.previousNode = null;
	    } else {
		Node<T> prev = tail;
		prev.nextNode = node;
		node.previousNode = prev;
		tail = node;
	    }
	    size++;
	}
	public boolean remove(T value) {
	    Node<T> node = head;
	    boolean removed = false;
	    while (node != null) {
		if (node.value.equals(value)) {
		    if (node!=head && node!=tail) {
			node.previousNode.nextNode = node.nextNode;
			node.nextNode.previousNode = node.previousNode;
			node.nextNode = null;
			node.previousNode = null;
			removed = true;
		    }
		    if (node==head) {
			System.out.println("head!!");
			head = node.nextNode;
			node.nextNode = null;
			node.previousNode = null;
			removed = true;
		    }
		    if (node==tail) {
			System.out.println("tail!!!");
			tail = node.previousNode;
			node.nextNode = null;
			node.previousNode = null;
			removed = true;
		    }

		}
		node = node.nextNode;
	    }
	    return removed;
	}
	public boolean contains(T value) {
	    Node<T> node = head;
	    while(node!=null) {
		if(node.value.equals(value)) {
		    return true;
		}
		node = node.nextNode;
	    }
	    return false;
	}
	public T get(int index) {
	    T result = null;
	    Node<T> node = head;
	    int i=0;
	    while(node!=null && i < index) {
		node = node.nextNode;
		i++;
	    }
	    if (node != null) {
		result = node.value;
	    }
	    return result;
	}
	public int size() {
	    return size;
	}
	public String toString() {
	    StringBuilder builder = new StringBuilder("");
	    Node<T> node = head;

	    while (node!=null) {
		builder.append(node.value).append(", ");
		node = node.nextNode;
	    }
	    return builder.toString();
	}
    }

    /*
      http://programmers.stackexchange.com/questions/81499/when-should-you-use-a-private-inner-class

      Suppose you're building a tree, a list, a graph and so on. Why should you expose the internal details of a node or a cell to the external world?

Anyone using a graph or a list should only rely on its interface, not its implementation, since you may want to change it one day in the future (e.g. from an array-based implementation to a pointer-based one) and the clients using your data structure (each one of them) would have to modify their code in order to adequate it to the new implementation.

Instead, encapsulating the implementation of a node or a cell in a private inner class gives you the freedom to modify the implementation any time you need to, without the clients to be forced to adjust their code consequently, as long as your data structure's interface remains untouched.

Hiding the details of your data structure's implementation also leads to security advantages, because if you want to distribute your class you'll make only the interface file available together with the compiled implementation file and nobody will know if you're actually using arrays or pointers for your implementation, thus protecting your application from some kind of exploitation or, at least, knowledge due to the impossibility to misuse or inspect your code. In addition to practical issues, please do not underestimate the fact that it's an extremely elegant solution in such cases.
     */
    private static class Node<T> {
	private T value = null;
	private Node<T> previousNode = null;
	private Node<T> nextNode = null;
	
	private Node(T value) {
	    this.value = value;
	}
	public String toString(){
	    return "value=" + value + " previous=" + (previousNode!=null ? previousNode.value : "NULL") + " next=" + (nextNode!=null ? nextNode.value:"NULL");
	}
    }
    public static void main(String args[]) {
	List<Integer> list = List.createList(ListType.LinkedList);
        list.add(new Integer(1));
	
	list.remove(new Integer(1));

	System.out.println(list);
    }

}
