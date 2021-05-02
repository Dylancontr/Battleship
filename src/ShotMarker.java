public class ShotMarker implements IEntity{

    private int row;
    private int col;

    public ShotMarker(){
        
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public void setRow(int r) {
        row = r;
        
    }

    @Override
    public void setCol(int c) {
        col = c;
        
    }
    
}
