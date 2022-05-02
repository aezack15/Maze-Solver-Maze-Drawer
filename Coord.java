/** Class for keeping track of a (row,column) pair without getting them
 * accidentally mixed up */
public class Coord {
  /** The row of the Coord - because it's final, no danger in making it public
   * and dispensing with getters */
  public final int row;
  
  /** The column of the Coord - because it's final, no danger in making it public
   * and dispensing with getters */
  public final int col;

 /**
  * Build a row, column pair
  * @param row the row
  * @param col the column
  */
  public Coord (int row, int col) {
    this.row=row;
    this.col=col;
  }

  public String toString() {
    return "("+row+","+col+")";
  }

  public boolean equals(Object o) {
    if (o instanceof Coord) {
      Coord other = (Coord)o;
      return (row==other.row) && (col==other.col);
    }
    return false;
  }
}
