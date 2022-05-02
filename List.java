public interface List<T>{

    /**
       Return the element at index i in the list.

       @param i the index to get at
       @return the value of the elemnt at the inde
       @exception IndexOutOFBoundsException Exception thrown if the
       index is out of the bounds of the list
     **/
    public T get(int i) throws IndexOutOfBoundsException;


    /**
       Set the value of the element at index i to e

       @param i the index to set at
       @param e the value to set 
       @return the old value of the element set
       @exception IndexOutOFBoundsException Error if the index i is
       out of the bounds of the list
     **/
    public T set(int i, T e) throws IndexOutOfBoundsException;

    /**
       Add an element e to the list at the index i

       @param i the index to add to
       @param e the value to add

       @exception IndexOutOFBoundsException Exception thrown if the
       index is out of the bounds of the list or more than one away
       from the end of the list
     **/
    public void add(int i, T e) throws IndexOutOfBoundsException;

    /**
       Remove an element from the list at index i and return it

       @param i the index to be removed
       @return the element removed
       
       @exception IndexOutOFBoundsException Exception thrown if the
       index is out of the bounds of the list
    **/
    public T remove(int i)   throws IndexOutOfBoundsException;


    /**
       Returns the number of elements in the list

       @return the number of elements in the list
    **/
    public int size();
}
