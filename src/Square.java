import javafx.scene.shape.Rectangle;

public class Square extends Rectangle{
    
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

}
