import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable{
    
    private int row;
    private int col;

    //They're coordinates for the grid. I don't think I need to explain more than that.
    public Coordinates(int r, int c){
        row = r;
        col = c;
    }

    //getters/accessors
    public int getRow(){
        return row;
   }

   public int getCol(){
       return col;
   }

   //setters/mutators
   public void setRow(int r){
       row = r;
   }

   public void setCol(int c){
       col = c;
   }

   //copied from Square compareTo
   @Override
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
