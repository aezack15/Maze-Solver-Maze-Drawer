import java.io.*;
//YOU MAY NOT IMPORT java.util.* !!!

/**
 * Super-useful class you should fully understand and not change!
 */
public class Maze implements Serializable{

  /** Field for representing left - This should actually be an enum */
  public static final int LEFT = 3;
  /** Field for representing right - This should actually be an enum */
  public static final int RIGHT = 1;
  /** Field for representing up - This should actually be an enum */
  public static final int UP = 0;
  /** Field for representing down - This should actually be an enum */
  public static final int DOWN = 2;

  private Space[][] map;
  private int width,height;

  /** This class is used heavily within Maze, but understanding it isn't
   * important for you!
   */
  private class Space implements Serializable{

    //array of walls, and neighbors
    private boolean walls[];
    private Space neighbors[];

    /************
      0
      ---
      3 |   | 1
      ---
      2
     ************/



    //the height and width of this space
    private int height;
    private int width;

    //the visit flag for this space
    private boolean visit;

    /**
     * Create a Space with four walls and no neighbors
     *
     * @param h the row
     * @param w the column
     */
    public Space(int h, int w){

      walls =  new boolean[] {true,true,true,true};

      neighbors = new Space[] {null,null,null,null};

      visit=false;
      height=h;
      width=w;
    }

    /**
     * Replace the neighbors array with the entered array
     *
     * @param n
     */
    public void setNeighbors(Space n[]){
      for(int i=0;i<4;i++)
        neighbors[i] = n[i];

    }

    /**
     * Get all the neighbors
     */
    public Space[] getNeighbors(){
      return neighbors;
    }

    /** Return if it has been visited */
    public boolean isVisited(){
      return visit;
    }

    /** Make this Space visited */
    public void setVisited(){
      visit=true;
    }

    /** Make this Space unvisited */
    public void setUnvisited(){
      visit=false;
    }

    /** Remove the wall in the specified direction */
    public void removeWall(int direction){
      walls[direction]=false;
      if(neighbors[direction] != null){
        neighbors[direction].removeWall( (direction+2) % 4, true);
      }
    }

    /** special case of remove for the start and end walls */
    private void removeWall(int direction, boolean first){
      walls[direction]=false;
    }

    /**check if there is a wall in the specified direction */
    public boolean hasWall(int direction){
      return walls[direction];
    }

    /**check if there is a wall above*/
    public boolean hasUpWall(){
      return walls[UP];
    }

    /**check if there is a wall below*/
    public boolean hasDownWall(){
      return walls[DOWN];
    }

    /**check if there is a wall to the left*/
    public boolean hasLeftWall(){
      return walls[LEFT];
    }

    /**check if there is a wall to the right*/
    public boolean hasRightWall(){
      return walls[RIGHT];
    }

    /**return the number of surounding walls*/
    public int numWalls(){
      int n=0;
      for(int i=0;i<4;i++)
        if(walls[i])
          n++;
      return n;
    }

  }

  /**
   * Create a Maze object of the given height and width
   *
   * @param height  The height of the maze.  Must be positive.
   * @param width   The width of the maze.  Must be positive.
   */
  public Maze(int height, int width){
    if (height<=0)
      throw new IllegalArgumentException(
          "height must be positive. Entered: "+height);
    if (width<=0)
      throw new IllegalArgumentException(
          "width must be positive. Entered: "+width);

    //create the map
    map = new Space[height][width];
    this.height = height;
    this.width = width;

    setupMap();
  }

  private void setupMap(){
    //initialize array
    for(int h=0;h<height;h++)
      for(int w=0;w<width;w++)
        map[h][w] = new Space(h,w);

    //set neighbors
    for(int h=0;h<height;h++){
      for(int w=0;w<width;w++){
        Space neighbors[] = new Space[4];

        neighbors[0] = h-1 >= 0 ? map[h-1][w] : null;
        neighbors[2] = h+1 < height ?  map[h+1][w] : null;

        neighbors[3] = w-1 >= 0 ? map[h][w-1] : null;
        neighbors[1] = w+1 < width ? map[h][w+1] : null;

        map[h][w].setNeighbors(neighbors);
      }
    }

  }

  /**
   * Returns the height of the maze
   */
  public int getHeight(){
    return height;
  }

  /**
   * Returns the width of the maze
   */
  public int getWidth(){
    return width;
  }

  //remove a wall from index (h,w) at the directions
  //LEFT, RIGHT, UP, DOWN
  /**
   * Remove a wall from the given coordinate in the directions
   * Maze.LEFT, Maze.RIGHT, Maze.UP or Maze.DOWN
   *
   * @param coord  a Coordinate describing the location of the wall to be
   * removed
   * @param direction  which wall to remove from coord. Must be Maze.UP,
   * Maze.DOWN, Maze.LEFT, or Maze.RIGHT
   */
  public void removeWall(Coord coord, int direction){
    removeWall(coord.row,coord.col,direction);
  }
  private void removeWall(int row, int col, int direction){
    map[row][col].removeWall(direction);
  }

  private void visit(int row, int col){
    map[row][col].setVisited();
  }
  /**
   * Mark a coordinate as visited
   *
   * @param coord the Coord to be marked as visited
   */
  public void visit(Coord coord){
    visit(coord.row,coord.col);
  }

  /**
   * Mark all coordinates as unvisited
   */
  public void unvisitAll(){
    for(int h=0;h<height;h++){
      for(int w=0;w<width;w++){
        map[h][w].setUnvisited();
      }
    }
  }

  //check if a index (h,w) is visited
  private boolean isVisited(Coord coord){
    return map[coord.row][coord.col].isVisited();
  }

  //return a List of index pairs of all the neighbors of the index (h,w)
  private List<Coord> getNeighbors(Coord coord){
    List<Coord> list = new LinkedList<Coord>();
    int row=coord.row;
    int col=coord.col;

    Space[] neighbors = map[coord.row][coord.col].getNeighbors();

    for(int i=0;i<neighbors.length;i++){
      if(neighbors[i] != null){
        if(i == UP)
          list.add(list.size(), new Coord(row-1,col));
        if(i == DOWN)
          list.add(list.size(), new Coord(row+1,col));
        if(i == RIGHT)
          list.add(list.size(), new Coord(row,col+1));
        if(i == LEFT)
          list.add(list.size(), new Coord(row,col-1));
      }
    }

    return list;

  }

  /**
   * Return a list of all neighbors (ie, of the four surrounding coordinates)
   * of which are unvisited
   *
   * @param coord The coordinate whose neighbors are being checked
   */
  public List<Coord> getUnvisitedNeighbors(Coord coord){
    List<Coord> list = new LinkedList<Coord>();
    int row=coord.row;
    int col=coord.col;

    Space[] neighbors = map[row][col].getNeighbors();

    for(int i=0;i<neighbors.length;i++){
      if(neighbors[i] != null && ! neighbors[i].isVisited()){
        Coord c = null;
        if(i == UP){
          c = new Coord(row-1,col);
        } else if(i == DOWN){
          c = new Coord(row+1, col);
        } else if(i == RIGHT){
          c = new Coord(row,col+1);
        } else if(i == LEFT){
          c = new Coord(row,col-1);
        }

        if(c!= null)
          list.add(list.size(),c);
      }
    }

    return list;
  }

  /**
   * Return a list of all neighbors of coord which are unvisited and reachable
   * (no wall between them)
   *
   * @param coord The coordinate whose neighbors are being checked
   */
  public List<Coord> getReachableUnvisitedNeighbors(Coord coord){
    int row=coord.row;
    int col=coord.col;
    List<Coord> list = new LinkedList<Coord>();
    Space[] neighbors = map[row][col].getNeighbors();

    for(int i=0;i<neighbors.length;i++){
      if(neighbors[i] != null && ! neighbors[i].isVisited()){
        Coord c = null;
        if(i == UP  && ! map[row][col].hasUpWall() ){
          c = new Coord(row-1,col);
        } else if(i == DOWN && ! map[row][col].hasDownWall() ){
          c = new Coord(row+1, col);
        } else if(i == RIGHT  && ! map[row][col].hasRightWall() ){
          c = new Coord(row,col+1);
        } else if(i == LEFT  && ! map[row][col].hasLeftWall() ){
          c = new Coord(row,col-1);
        }

        if( c != null)
          list.add(list.size(),c);
      }
    }
    return list;
  }

  /**
   * Serialize the Maze object to the given file
   *
   * @param filename the file to be created.
   */
  public void toFile(String filename){
    try{
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
      out.writeObject(this);
      out.close();
    }catch(IOException e){
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Create a Maze object from a serialized one stored to a file
   *
   * @param filename the file to be read from
   */
  public static Maze fromFile(String filename){
    Maze m = null;
    try{
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
      m = (Maze) in.readObject();
      in.close();
    }catch(IOException e){
      e.printStackTrace();
      System.exit(1);
    }catch(ClassNotFoundException e){
      e.printStackTrace();
      System.exit(1);
    }
    return m;
  }

  /**
   * Returns the Maze as a nice unicode String to be printed
   */
  public String toString(){
    String s = "";


    String ul_bow = "\u250F";//┏
    String ur_bow = "\u2513";//┓
    String bl_bow = "\u2517";//┗
    String br_bow = "\u251B";//┛
    String hdash  = "\u2501";//━
    String vdash  = "\u2503";//┃
    String u_tee  = "\u2533";//┳
    String b_tee  = "\u253B";//┻
    String l_tee  = "\u2523";//┣
    String r_tee  = "\u252B";//┫
    String tee    = "\u254B";//┿

    String hwall = hdash+hdash+hdash+hdash;
    String hspace= "    ";
    for(int h=0;h<height;h++){
      for(int w=0;w<width;w++){
        if(h==0){ //top row
          if(w==0){
            if(map[h][w].hasLeftWall() && map[h][w].hasUpWall())
              s+= ul_bow;
            if(!map[h][w].hasLeftWall() && map[h][w].hasUpWall())
              s+= hdash;
            if(map[h][w].hasLeftWall() && !map[h][w].hasUpWall())
              s+= vdash;
          }

          s+= map[h][w].hasUpWall() ? hwall : hspace;

          if( w < width -1){
            s+= map[h][w].hasRightWall() ? u_tee : hdash;
          }else{
            if(map[h][w].hasRightWall() && map[h][w].hasUpWall())
              s+= ur_bow;
            if(!map[h][w].hasRightWall() && map[h][w].hasUpWall())
              s+= hdash;
            if(map[h][w].hasRightWall() && !map[h][w].hasUpWall())
              s+= vdash;
          }
        }else{ //middle row
          if(w==0){
            s += map[h][w].hasUpWall() ? l_tee : vdash ;
          }

          s+= map[h][w].hasUpWall() ? hwall : hspace;


          if( w == width-1){
            s += map[h][w].hasUpWall() ? r_tee : vdash;
          }else if( w < width - 1){

            int r = 0;
            r |= ! map[h][w].hasUpWall() ?      0x1000 : 0x00;
            r |= ! map[h][w].hasRightWall() ?   0x0100 : 0x00;
            r |= ! map[h][w+1].hasUpWall() ?    0x0010 : 0x00;
            r |= ! map[h-1][w].hasRightWall() ? 0x0001 : 0x00;

            //System.out.printf("0x%04x",r);

            if(r  == 0x0000){
              s+=tee;
            }else if (r == 0x1000){
              s+=l_tee;
            }else if (r == 0x1100){
              s+=bl_bow;
            }else if (r == 0x1010){
              s+=vdash;
            }else if (r == 0x1001){
              s+=ul_bow;
            }else if (r == 0x0100){
              s+=b_tee;
            }else if (r == 0x0110){
              s+=br_bow;
            }else if (r == 0x0101){
              s+=hdash;
            }else if (r == 0x0010){
              s+=r_tee;
            }else if (r == 0x0011){
              s+= ur_bow;
            }else if (r == 0x0001){
              s+=u_tee;
            }else{
              s+=" ";
            }
            //System.out.println(s.charAt(s.length()-1));
          }

        }
      }
      s+="\n";

      //interior
      for(int w=0;w<width;w++){
        s+= map[h][w].hasLeftWall() ? vdash : " ";
        s+= map[h][w].isVisited() ? "  *" : "   ";
        s+= w == width-1 && map[h][w].hasRightWall() ? " "+vdash : " ";
      }
      s+="\n";

      //bottom
      if(h==height-1){
        for(int w=0;w<width;w++){
          if(w == 0){
            if(map[h][w].hasLeftWall() &&  map[h][w].hasDownWall())
              s+= bl_bow;
            if(!map[h][w].hasLeftWall() &&  map[h][w].hasDownWall())
              s += hdash;
            if(map[h][w].hasLeftWall() && ! map[h][w].hasDownWall())
              s += vdash;
          }

          s += map[h][w].hasDownWall() ? hwall : hspace;

          if( w < width - 1){
            s += map[h][w].hasRightWall() ? b_tee : hdash;
          }else{
            if(map[h][w].hasRightWall() && map[h][w].hasDownWall())
              s+= br_bow;
            if(!map[h][w].hasRightWall() && map[h][w].hasDownWall())
              s+=hdash;
            if(map[h][w].hasRightWall() && !map[h][w].hasDownWall())
              s+=vdash;
          }
        }
      }

    }
    s+="\n";
    return s;


  }



  public static void main(String args[]){


    Maze m = new Maze(10,10);
    System.out.println(m);

    m = new Maze(10,10);
    for(int j=0;j<10;j++){
      for(int i=0;i<10;i++){
        m.visit(i,j);
      }
    }
    System.out.println(m);

    System.out.println();

    m.removeWall(0,0,LEFT);

    for(int j=0;j<10;j++){
      for(int i=0;i<9;i++){
        if( j % 2 == 0){
          m.removeWall(j,i, RIGHT);
        }else{
          m.removeWall(j,9-i, LEFT);
        }
        m.visit(j,i);
        m.removeWall(j,(j%2 == 0) ? 9 : 0, DOWN);
      }

    }

    System.out.println(m);

    m = new Maze(10,10);

    for(int j=0;j<10;j++){
      for(int i=0;i<10;i++){
        if( j % 2 == 0){
          m.removeWall(i,j, DOWN);
        }else{
          m.removeWall(9-i,j, UP);
        }
      }

    }

    System.out.println(m);

  }

}
