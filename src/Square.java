import javafx.scene.shape.Rectangle;
import java.io.Serializable;
/**
 * class represents the square unit a special rectangle with all its sides the same length
 * takes 6 ints:x coordinate, y coordinate, size of lengths, a row, and a column
 * @author Dylan, Evgeniya, Ivan
 */
public class Square extends Rectangle implements Comparable<Square>, Serializable{
    
    private int row;
    private int col;

    /**
	 * Non-default constructor
	 * calls parent rectangle constructor
	 * @param r - row which holds where is square placed
	 * @param c - column which holds where  is square placed
	 * @param x = the X coordinate of the upper-left corner of the Rectangle.
	 * @param y = The Y coordinate of the upper-left corner of the Rectangle.
	 * @param s = the width and height of the Rectangle.	 
	 */
   
    public Square(int x, int y, int s, int r, int c){
        super(x,y,s,s);
        row = r;
        col = c;
    }

    /**
	 * @return  row
	 */
    public int getRow(){
        return row;
    }

    /**
	 * @return  column
	 */
    public int getCol(){
        return col;
    }
    /**
     * compare 2 squares location
	 * @return 1 if other Square is directly north
	 * @return 2 if other Square is directly south
	 * @return 3 if other Square is directly west
	 * @return 4 if other Square is directly east
	 * @return 0 if its same square
	 * @return -1 if other Square is in  direction
	 */
   
    @Override
    public int compareTo(Square other) {
        if(col == other.getCol()){

            if(row == other.getRow()) return 0;

            if(row > other.getRow()) return 3;
            else if(row < other.getRow()) return 4;

            else return -1;
            
        }else if(row == other.getRow()){

            if(col > other.getCol()) return 1;
            else if(col < other.getCol()) return 2;

            else return -1;
            
        }

        return -1;
    }
}