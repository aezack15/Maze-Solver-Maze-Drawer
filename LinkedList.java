public class LinkedList<T> implements List<T>,Queue<T>,Stack<T>{

  private class Node{
    private T data;
    private Node next;

    public Node(T d){
      data = d;
      next=null;
    }

    public T getData(){ return data;}
    public void setData(T d){ data = d;}
    public Node getNext(){ return next;}
    public void setNext(Node n){ next=n;}
  }

  private Node head;
  private Node tail;
  private int size;

  public LinkedList(){
    head=null;
    tail=null;
    size=0;
  }



  // Public facing list interface methods
  //
  // Each method calls a private method that is implemented
  // recursively. You should be able to follow this code.

  public T get(int i){
    return get(head,i);
  }
  public T set(int i, T e){
    return set(head,i,e);
  }
  public void add(int i, T e){
    head = add(head,i,e); //note that this returns a Node because
    //the head might have changed!
    if (tail==null)
      tail=head;
    size++;
  }

  public T remove(int i){

    T data = get(i);       //retrieve the data we are remove first!

    head = remove(head,i); //note that this returns a Node because
    //the head might have changed!
    if (head==null)
      tail=null;
    size--;

    return data;
  }

  public int size(){
    return size;
  }

  public String toString(){
    return "["+toString(head)+" ]";
  }

  /*************************************************************
   * Private recursive methods
   *
   * You get these for free --- good examples for later, maybe?
   **************************************************************/

  private T get(Node n, int i){
    if( i < 0){
      throw new IndexOutOfBoundsException();
    }
    if(n==null){
      throw new IndexOutOfBoundsException();
    }

    if(i==0){
      return n.getData();
    }

    return get(n.getNext(),i-1);

  }


  private T set(Node n, int i, T e){
    if( i < 0){
      throw new IndexOutOfBoundsException();
    }

    if(n==null){
      throw new IndexOutOfBoundsException();
    }

    if(i==0){
      n.setData(e);
      return e;
    }

    return set(n.getNext(),i-1,e);

  }



  private Node add(Node n, int i, T e){
    if( i < 0){
      throw new IndexOutOfBoundsException();
    }

    if(n==null && i > 0){
      throw new IndexOutOfBoundsException();
    }

    if(i==0){
      Node newNode = new Node(e);
      newNode.setNext(n);
      if (n==null)
        tail=newNode;
      return newNode;
    }

    n.setNext(add(n.getNext(),i-1,e));
    return n;
  }


  private Node remove(Node n, int i){
    if( i < 0){
      throw new IndexOutOfBoundsException();
    }


    if(n==null){
      throw new IndexOutOfBoundsException();
    }

    if(i==0){
      return n.getNext();
    }

    n.setNext(remove(n.getNext(),i-1));
    if (n.getNext()==null)
      tail=n;
    return n;
  }


  private String toString(Node n){
    if(n==null) return "";
    return " "+n.getData().toString()+toString(n.getNext());
  }


  //********* Stack Interface *********/

  public void push(T e){
    //TODO
    Node n = new Node(e);
    if(head == null)
    {
    	head = n;
    	this.size += 1;
    }
    else
    {
	n.setNext(head);
	head = n;
	this.size += 1;
    }
  }
  public T pop() throws IndexOutOfBoundsException{ 
    //TODO
    if(head != null){
    	Node node = head;
    	head = head.getNext();
    	node.setNext(null);
    	this.size -= 1;
    	return node.getData();
    } 
    return null;
  }

  //********* Queue Interface *********/
  public void enqueue (T e){ //adds an element to the back of the queue
    //TODO
    Node node = new Node(e);
    if(this.size > 0)
    {
    	this.tail.setNext(node);
	this.tail = node;
	this.size += 1;
    } 
    else
    {
	this.head = node;
    	this.tail = node;
    	this.size += 1;
    }
  }
  public T dequeue() throws IndexOutOfBoundsException{ //removes an element from the front of the queue
    //TODO
    if(this.head != null)
    {
    	Node node = new Node(this.head.getData());
    	this.head = this.head.getNext();
    	this.size -= 1;
    	return node.getData();
    }
    return null;
  }


  //********** Shared Peek ******/
  public T peek() throws IndexOutOfBoundsException{ //looks at an element at the front
    //TODO
    if(head == null){
    	return null;
    } else{
    	return head.getData();
    }
  }

}

