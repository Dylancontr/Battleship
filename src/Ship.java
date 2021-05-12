import java.io.Serializable;
public class Ship implements Serializable{
    
    private boolean sunk;//status of ship
    private ShipPart[] size;//size of ship
    private int id;//id of ship
    private int length;//length of size
    
    //takes in two ints, they are the size of the ship and the id of the ship
    public Ship(int l, int i){
        size = new ShipPart[l];
        for(int j = 0; j < l; j++){
            size[j] = new ShipPart(i);
        }
        length = l;
        sunk = false;
        id = i;
    }

    //Gives each shipPart their id
    public void setShip(){
        for(int i = 0; i < size.length; i++){
            size[i].setShipID(id);
        }
    }

    //checks if ship is sunk by seeing if each ShipPart has been hit
    public boolean isSunk(){
        //checking each ShipPart
        for(int i = 0; i < size.length; i++){
            if(!size[i].getHit()) return false;
        }
        //tells Ship it has been sunk
        sunk = true;
        return true;
    }

    //Getters/Accessors
    public ShipPart getShipPart(int i){
        return size[i];
    }

    public boolean getSunk(){
        return sunk;
    }

    public int getLength(){
        return length;
    }

}
