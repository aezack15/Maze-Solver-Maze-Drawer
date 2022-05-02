import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.*;

/**
 * TODO: Implement the remainder of this main function!
 */
public class DrawMaze{

  /** Clears the screen - you don't need to worry about this */
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }  

  /** Prints the Maze so you can see progress */
  public static void progress(Maze maze){
    clearScreen();
    System.out.println(maze);
    //Slows it down so you can track
   /* try{
      TimeUnit.MILLISECONDS.sleep(65);
    }catch (InterruptedException e) {
      System.exit(1);
    } */
  }

  public static void main(String args[]){

    int height=0,width=0;
    Random rnd=null;
    String outputFileName = "";

    if(args.length < 3){
      System.out.println("ERROR: invalid option");
      System.out.println("java DrawMaze <out.ser> <height> <width> [seed]");
      System.out.println("out.ser  : seriliazed output file");
      System.out.println("height   : height of maze");
      System.out.println("width    : width of maze");
      System.out.println("seed     : Optional seed value for randomizing maze");
      System.exit(1);
    }else{
      outputFileName = args[0];
      height = Integer.parseInt(args[1]);
      width = Integer.parseInt(args[2]);
    }

    if(args.length > 3){
      rnd = new Random(Integer.parseInt(args[3]));
    }else{
      rnd = new Random(0);
    }



    //Create the Maze at the height and width
    Maze maze= new Maze(height,width);

    //initialize start, upper left, and finnish bottom right
    Coord start=new Coord(0,0);
    Coord end=new Coord(height-1,width-1);
    Coord poppedCoord = new Coord(0,0); //temporary coord for the loop
    maze.removeWall(start,Maze.LEFT);
    maze.removeWall(end,Maze.RIGHT);
    boolean IamDone = false;

    //TODO: Complete the Draw Maze!
    // 
    // Basic Algorithm:
    // init: push the start index on a stack, mark it as visited
    LinkedList<Coord> mazeList = new LinkedList<Coord>();
    maze.visit(start); //show that the first coord is visited
    mazeList.push(start); //put start on the front of the list
    
    while(mazeList.size() != 0)
    {
    	//System.out.println("mazeList: " + mazeList.toString());
    	poppedCoord = mazeList.pop(); //pop index off the stack
    	if((maze.getUnvisitedNeighbors(poppedCoord)) != null) //checks to see if the peeked coordinate has any unvisited neighbors	
    	{
    		while((maze.getUnvisitedNeighbors(poppedCoord)).size() == 0)
    		{
    			poppedCoord = mazeList.pop();
    			if(poppedCoord.row == 0 && poppedCoord.col == 0) //if i've popped all the way to the beginning of the maze, I'm done
    			{
    				IamDone = true;
    				break;
    			}
    		}
    		if(IamDone != true)
    		{
			int r = rnd.nextInt((maze.getUnvisitedNeighbors(poppedCoord)).size());
			Coord g = maze.getUnvisitedNeighbors(poppedCoord).get(r); //choose the random coord that was confirmed as unvisited
			
			if(g.row > poppedCoord.row)
			{
				maze.removeWall(poppedCoord, Maze.DOWN);
			}
			else if(g.row < poppedCoord.row)
			{
				maze.removeWall(poppedCoord, Maze.UP);
			}
			else if(g.col > poppedCoord.col)
			{
				maze.removeWall(poppedCoord, Maze.RIGHT);
			}
			else if(g.col < poppedCoord.col)
			{
				maze.removeWall(poppedCoord, Maze.LEFT);
			}
			maze.visit(g); //mark it as visited
			mazeList.push(poppedCoord); //push the current index onto the stack
			mazeList.push(g); //push the next index on the stack
			}
		else
		{
			break; //I've hit my end criteria and have drawn the maze
		}
    	}
    		progress(maze);
    } //end While

    //Writes maze to output file once done
    clearScreen();
    maze.unvisitAll();
    System.out.println(maze);
    maze.toFile(outputFileName);
  }
}

