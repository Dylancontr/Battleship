public class ShipPart implements IEntity{
    
    private boolean hit;
    private int shipID;
    private int row;
    private int col;

    public ShipPart(int id){
        hit = false;
        shipID = id;
    }

    public boolean getHit(){
        return hit;
    }
    
    public int beHit(){
        hit = true;
        return shipID;
    }

    public int getShipID(){
        return shipID;
    }

    public void setShipID(int id){
        shipID = id;
    }

    @Override
    public int getRow(){
        return row;
    }

    @Override 
    public int getCol(){
        return col;
    }

    @Override
    public void setRow(int r){
        row = r;
    }

    @Override
    public void setCol(int c){
        col = c;
    }
}
