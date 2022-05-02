public interface Stack<T>{

  /**
   * Add an element to the top of the Stack
   * @param e The element to be added
   */
  public void push(T e);
  
  /**
   * Removes an element from the top of the Stack
   * @return the element
   * @throws IndexOutOfBoundsException if Stack is empty
   */
  public T pop() throws IndexOutOfBoundsException;

  /**
   * Returns, but does not remove, the element at the top of the Stack
   * @return the element
   * @throws IndexOutOfBoundsException if Stack is empty
   */
  public T peek() throws IndexOutOfBoundsException;

  /**
   * Returns the number of elements in the Stack
   *
   * @return the number of elements in the Stack
   **/
  public int size();    
}
