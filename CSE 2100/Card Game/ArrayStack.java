public class ArrayStack<E> implements GenericStack<E> {
	public static final int CAPACITY = 10;				// Create an inital size of 10.
	private E[] data;									// Store the data in a generic array of data.
	private int t = -1;									// Set the counter to -1.
	public ArrayStack() { this(CAPACITY); }				// Create an array stack of size 10.
	public ArrayStack(int capacity) {
		data = (E[]) new Object[capacity];
	}
	public int size() { return (t + 1); }				// Return the size of the ArrayStack.
	public boolean isEmpty() { return (t == -1); }		// If t = -1, then it's empty.
	public void push(E e) throws IllegalStateException {	// When an element is pushed into the ArrayStack...
		if (size() == data.length) throw new IllegalStateException("Stack is full");	// make sure there's space.
		data[++t] = e;										// if there is, then increment t first, access data[t+1], and set it to e.
	}
	public E top() {									// Return the element that's on top.
		if (isEmpty()) return null;
		return data[t];
	}
	public E pop() {									// Remove the element that's on top.
		if (isEmpty()) return null;
		E answer = data[t];
		data[t] = null;
		t--;
		return answer;
	}
}