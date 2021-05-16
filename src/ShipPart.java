import java.io.Serializable;
/**
 * class represents the  Ship part and  
 * @author Dylan,Evgeniya, Ivan
 */

public class ShipPart implements IEntity, Serializable{
    
    private boolean hit;
    private int shipID;

    /**
  	 * Non-default constructor
  	 * @param id = the id of the ship
  	 * hit initially = false pointing that part hasn't been struck
  	 * */
    public ShipPart(int id){
        hit = false;
        shipID = id;
    }

    /**
   	 * @return  boolean if Ship part been hit or not
   	 */
    public boolean getHit(){
        return hit;
    }
    
    /**
     * Tells part it has been struck  
   	 * @return  shipId to tell calling function which ship has been struck
   	 */
    public int beHit(){
        hit = true;
        return shipID;
    }
    
    /**
	 * Sets Ship id
	 * @param id = part of the ship
	 */
    public void setShipID(int id){
        shipID = id;
    }


}