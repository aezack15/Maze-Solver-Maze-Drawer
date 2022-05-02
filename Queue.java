public interface Queue<T>{

  /**
   * Add an element to the back of the Queue
   * @param e The element to be added
   */
  public void enqueue(T e);

  /**
   * Removes an element from the front of the Queue
   * @return the element
   * @throws IndexOutOfBoundsException if Queue is empty
   */
  public T dequeue() throws IndexOutOfBoundsException;

  /**
   * Returns, but does not remove, the element at the front of the Queue
   * @return the element
   * @throws IndexOutOfBoundsException if Queue is empty
   */
  public T peek() throws IndexOutOfBoundsException;

  /**
   * Returns the number of elements in the Queue
   *
   * @return the number of elements in the Queue
   **/
  public int size();

}
