import javafx.scene.shape.Rectangle;

public class Square extends Rectangle implements Comparable<Square>{
    
    private int row;//holds row of square
    private int col;//hold column of square

    //A square is a special rectangle with all its sides the same length
    //takes 6 ints:x coordinate, y coordinate, size of lengths, a row, and a column
    //x,y,s call the rectangle function
    public Square(int x, int y, int s, int r, int c){
        super(x,y,s,s);
        row = r;
        col = c;
    }

    //gets row
    public int getRow(){
        return row;
    }

    //gets col
    public int getCol(){
        return col;
    }

    //Tells if other Square is directly north, south, east, west, the same Square, or in some diagnol direction
    //north:1, south:2, west:3, east:4
    //same square:0
    //some diagnol:-1
    @Override
    public int compareTo(Square other) {
        if(col == other.getCol()){

            if(row == other.getRow()) return 0;

            if(row - other.getRow() > 0) return 3;
            else if(row - other.getRow() < 0) return 4;

            else return -1;
            
        }else if(row == other.getRow()){

            if(col - other.getCol() > 0) return 1;
            else if(col - other.getCol() < 0) return 2;

            else return -1;
            
        }

        return -1;
    }
}
