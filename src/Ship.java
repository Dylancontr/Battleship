import java.io.Serializable;
/**
 * class represents the Ship which consists of Ship parts 
 * @author  Dylan,Evgeniya, Ivan
 */
public class Ship implements Serializable{
	   
    private boolean sunk;
    private ShipPart[] size;
    private int id;
    private int length;
    
    /**
  	 * Non-default constructor
  	 * @param l= length of size of  the Ship
  	 * @param i= id of the Ship
  	 * creates size of the Ship
  	 * sunk is  false initially
  	 * */
    public Ship(int l, int i){
        size = new ShipPart[l];
        for(int j = 0; j < l; j++){
            size[j] = new ShipPart(i);
        }
        length = l;
        sunk = false;
        id = i;
    }

    /**
   	 * Go through size of the ship  and gives each shipPart their id
   	 * @param id = part of the ship
   	 */
    public void setShip(){
        for(int i = 0; i < size.length; i++){
            size[i].setShipID(id);
        }
    }

    /**
   	 * Checks if  ship is sunk by checking if each  ShipPart of the ship has been hit
   	 * @return false if ship is not sunk otherwise true
   	 */
    public boolean isSunk(){
       
        for(int i = 0; i < size.length; i++){
            if(!size[i].getHit()) 
            	return false;
        } 
        sunk = true;
        return true;
    }

    /**
   	 * @return  Ship part identification
   	 */
    public ShipPart getShipPart(int i){
        return size[i];
    }
    /**
   	 * @return  true if ship is sunk
   	 */
    public boolean getSunk(){
        return sunk;
    }
    
    /**
   	 * @return  length of the size
   	 */
    public int getLength(){
        return length;
    }

}