import java.io.Serializable;
/**
 * class represents Coordinates of Ship part 
 * @author  Dylan,Evgeniya, Ivan
 */
public class Coordinates implements Comparable<Coordinates>, Serializable{
    
    private int row;
    private int col;

    /**
  	 * Non-default constructor
  	 * @param c- column of coordinate
  	 * @param r- row  of coordinate
  	 **/
    public Coordinates(int r, int c){
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
   	 * @return column
   	 */
   public int getCol(){
       return col;
   }

   /**
  	 * @param r  sets row
  	 */
   public void setRow(int r){
       row = r;
   }

   /**
 	 * @param  c- sets column
 	 */
   public void setCol(int c){
       col = c;
   }
   
   /**
    * compare 2 coordinates 
	 * @return 1 if other coordinate is directly north 
	 * @return 2 if other coordinate is directly south
	 * @return 3 if other coordinate is directly west
	 * @return 4 if other coordinate is directly east
	 * @return 0 if its same coordinate
	 * @return -1 if other coordinate is in  direction
	 */
   public int compareTo(Coordinates other){
    if(col == other.getCol()){

        if(row == other.getRow()) return 0;

        else if(row > other.getRow()) return 3;
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
