import java.io.Serializable;

public class ShipPart implements IEntity, Serializable{
    
    private boolean hit;//tells if part has been struck
    private int shipID;//tells what ship it is a part of

    //takes in and int that holds the id of the ship
    public ShipPart(int id){
        hit = false;
        shipID = id;
    }

    //returns if it has been hit
    public boolean getHit(){
        return hit;
    }
    
    //Tells part it has been struck
    //returns id to tell calling function which ship has been struck
    public int beHit(){
        hit = true;
        return shipID;
    }
    
    //set Ship id, takes int
    public void setShipID(int id){
        shipID = id;
    }


}
