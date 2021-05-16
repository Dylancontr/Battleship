/**
 * class represents the  Human Player  
 * @author  Dylan, Evgeniya, Ivan
 */
public class HumanPlayer extends Player{
    /**
     * default-constructor
     * calls parent  Player constructor 
     **/
    public HumanPlayer(){
        super();
    }
    
    @Override
    /**
     * @return ship coordinates(row, column, direction)
     */
    public boolean placeShips(int r, int c, char d) {
        return setShipCoor(r,c,d);
    }

    
    @Override
    /**
     * calls shoot other and determines what to do
     * chooses reaction based on hitItem that was determined by hit function
     * any additional ships will just return their id unless added later
     * @return -2 if square already shot
     * @return -1 if miss
     * @return 0 if hit
     * @return 1-5 are the name of ship IDs that have length of 1,2,3,4,5
     * @return by default a number of any non ships should be specified by a number
     */
    public String takeTurn(Player other, int r, int c) {
        
        int hitItem = other.shoot(r,c);
        
        switch(hitItem){
            case -1:
                return "Miss";
            case 0:
                return "Hit";
            case -2:
                return "Area already shot";
            case 1:
                return "sunk Destroyer";
            case 2:
                return "sunk Submarine";
            case 3:
                return "sunk Cruiser";
            case 4:
                return "sunk Battleship";
            case 5:
                return "sunk Carrier";
            default:
                return "sunk ship number " + hitItem;
        }

    }

}
