package datastructure;

public abstract class List<T> {
    public enum ListType {
	LinkedList, ArrayList
    };
    
    public abstract void add(T value);
    public abstract boolean remove(T value);
    public abstract void insertAt(int index, T value);
    public abstract boolean removeAt(int index);
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
	// insert before index if index is out of range, insert at the end
	public void insertAt (int index, T value) {
	    if(head==null) {
		head = new Node<T>(value);
		tail = head;
		head.nextNode = null;
		head.previousNode = null;
	    } else {
		Node<T> node = new Node<T>(value);
		Node<T> p = head;
		for(int i=0;i<index && p!=null;i++,p=p.nextNode)
		    ;
		if (p==null) {
		    tail.nextNode = node;
		    node.previousNode = tail;
		    node.nextNode = null;
		    tail = node;
		} else if (p!=head){
		    node.previousNode = p.previousNode;
		    p.previousNode.nextNode = node;
		    p.previousNode = node;
		    node.nextNode = p;
		} else {
		    node.nextNode = head;
		    head.previousNode = node;
		    node.previousNode = null;
		    head = node;
		} 
	    } 

	}
	public boolean removeAt(int index) {
	    Node<T> node = head;
	    boolean removed = false;
	    
	    if (index==0) {
		return removed;
	    }
	    
	    for(int i=0;i<index && node!=null;i++,node=node.nextNode)
		;
	    if (node==head) {
		head = head.nextNode;
		head.previousNode = null;
		removed = true;
	    } else if (node==tail) {
		tail = tail.previousNode;
		tail.nextNode=null;
	    }
	    else if (node!=null && node!=head) {
		node.previousNode.nextNode = node.nextNode;
		node.nextNode.previousNode = node.previousNode;
		removed = true;
	    }
	    return removed;
	}
	// remove all the nodes that has specific value 
	public boolean remove(T value) {
	    Node<T> node = head;
	    boolean removed = false;
	    while (node != null) {
		Node<T> nextNode = node.nextNode;
		if (node.value.equals(value)) {
		    if (node!=head && node!=tail) {
			node.previousNode.nextNode = node.nextNode;
			node.nextNode.previousNode = node.previousNode;
			node.nextNode = null; // actually no need to set to null here
			node.previousNode = null;
			removed = true;
		    }
		    if (node==head) {
			head = node.nextNode;
			head.previousNode = null;
			node.nextNode = null;
			node.previousNode = null;
			removed = true;
		    }
		    if (node==tail) {
			tail = node.previousNode;
			tail.nextNode = null;
			node.nextNode = null;
			node.previousNode = null;
			removed = true;
		    }

		}
		node = nextNode;
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
	List<Integer> list = List.createList(ListType.LinkedList);//cannot write List<int> list=... autoboxing cannot be used in generic type 
        list.add(1);  //autoboxing to new Integer(1)
	list.add(2);
	list.add(3);
	list.add(4);
	list.add(5);
	list.add(6);
	list.add(7);
	list.add(8);
	list.add(9);
	list.add(7);
	list.insertAt(0,1);
	list.insertAt(100,100);
	list.remove(7);
	
	list.removeAt(0);
	list.removeAt(100);
	
	System.out.println(list);
    }

}
