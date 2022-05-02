import java.util.concurrent.TimeUnit;

public class SolveMaze{

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }  

  /** Prints the Maze so you can see progress */
  public static void progress(Maze maze){
    //Prints the Maze so you can see progress
    clearScreen();
    System.out.println(maze);
    //Slows it down so you can track
    try{
      TimeUnit.MILLISECONDS.sleep(80);
    }catch (InterruptedException e) {
      System.exit(1);
    }
  }

  public static void main(String args[]){

    String inputFileName="";

    if(args.length < 1 ||  args[0].equals("-h")){
      System.out.println("java PrintMaze <out.ser>");
      System.out.println("out.ser   : serilized maze stored in output");
      System.exit(1);
    }else{
      inputFileName=args[0];
    }

    //Create the maze by reading the input file
    Maze maze = Maze.fromFile(inputFileName);
    System.out.println(maze);
    Coord start=new Coord(0,0);
    Coord end = new Coord(maze.getHeight() - 1, maze.getWidth() - 1);
    Coord dequeuedCoord = new Coord(0,0); //temporary coord for the loop
    boolean amIdone = false;
    maze.visit(start); //marked start as visited
    LinkedList<Coord> path = new LinkedList<Coord>();
    LinkedList<Coord> answer = new LinkedList<Coord>();
    path.enqueue(start); //add the start to the queue
    answer.enqueue(start); 

    while(path.size() != 0)
    {
    	progress(maze); //print progress
    	dequeuedCoord = path.dequeue(); //dequeue current index
    	while((maze.getReachableUnvisitedNeighbors(dequeuedCoord)).size() == 0)
    	{
    			System.out.println(path);
    			dequeuedCoord = path.dequeue(); //remove an element from the back of the list
    			if(dequeuedCoord == end) //check to see if I've reached the end
	    		{
	    			amIdone = true;
	    			break;
	    		}	    		
    	}
    	if(amIdone == false)
    	{
    		Coord g = maze.getReachableUnvisitedNeighbors(dequeuedCoord).get(0); //find reachable neighbors from the dequeued coordinate, pick the first one in the list
    		if(g.col == end.col && g.row == end.row) //we reached the exit of the maze
    		{
    			maze.visit(g); //visit that coord
    			answer.enqueue(g); //add the end coord to the answer list
    			amIdone = true;
    			break; //we found the end
    		}
    		else
    		{
	    		maze.visit(g); //visit that coord
	    		path.enqueue(dequeuedCoord);
	    		path.enqueue(g); //enqueue the reachable neighbors coordinate
	    	}
    	}
    	else
    	{
    		break;
    	}
    	    	System.out.println(path);
    }//end while

    //print final result after complete!
    clearScreen();
    System.out.println(maze);
  }
}
