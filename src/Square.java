import javafx.scene.shape.Rectangle;

public class Square extends Rectangle implements Comparable<Square>{
    
    private int row;
    private int col;

    public Square(int x, int y, int s, int r, int c){
        super(x,y,s,s);
        row = r;
        col = c;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

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
