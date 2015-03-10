/**
 *An ordered collection of items, where items are both added and removed
 * exclusively from the front.
 * <p>Bugs: none known
 *
 * @authors sobin
 */
public class Stack<E> implements StackADT<E>
{
	// size of the stack
	private int size;
	// point to the top element of the stack
	private ListNode<E> top;
	/**
	 * Stack constructor initializes the stack for knight path app.
	 * An ordered collection of items, where items are both added and removed
	 * exclusively from the front.
	 */
	public Stack()
	{
		size = 0;
		top = null;
	}
	/**
	 * Returns the size of the stack.
	 *
	 * @return the size of the stack
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Checks if the stack is empty.
	 * @return true if stack is empty; otherwise false
	 */
	public boolean isEmpty()
	{
		return (size == 0);
	}
	/**
	 * Returns (but does not remove) the top item of the stack.
	 *
	 * @return the top item of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public E peek() throws EmptyStackException
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		return top.getData();
	}
	/**
	 * Pushes the given item onto the top of the stack.
	 *
	 * @param item the item to push onto the stack
	 */
	public void push(E item)
	{
		ListNode<E> newNode = new ListNode<E>(item, top);
		top = newNode;
		size++;
	}
	/**
	 * Pops the top item off the stack and returns it.
	 *
	 * @return the top item of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public E pop() throws EmptyStackException
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		E oldElement = top.getData();
		top = top.getNext();
		size--;
		return oldElement;
	}
	/**
	 * Returns a string representation of the stack.
	 * For printing when debugging your implementation.
	 * Format 1 item per line from top to bottom.
	 *
	 * @return a string representation of the stack
	 */
	public String toString()
	{
		String s = "["; //s is a string to make output look as specified in
		//program specs should appear ex: [1,1]
		ListNode<E> tmp = top;
		if (size >= 1)
		{
			s += top.getData();
			tmp = tmp.getNext();
		}
		if (size > 1)
		{
			while (tmp != null)
			{
				s += "," + tmp.getNext();
				tmp = tmp.getNext();
			}
		}
		s += "]";
		return s;
	}
}
