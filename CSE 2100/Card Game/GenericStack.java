public interface GenericStack<E> {
	// Generic implementation of a stack. Defines functions to be implemented.
	int size();
	boolean isEmpty();
	void push (E e);
	E top();
	E pop();
}