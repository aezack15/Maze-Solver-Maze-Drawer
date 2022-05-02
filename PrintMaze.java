/** Loogit the maze! */
public class PrintMaze{

  //Print out the a serialized maze file
  /** Requires an argument for which .ser file to read */
  public static void main(String args[]){

    Maze m=null;
    String inputFileName="";

    if(args.length < 1 ||  args[0].equals("-h")){
      System.out.println("java PrintMaze <out.ser>");
      System.out.println("out.ser   : serilized maze stored in output");
      System.exit(1);
    }else{
      inputFileName=args[0];
    }

    m = Maze.fromFile(inputFileName);
    System.out.println(m);
  }
}
