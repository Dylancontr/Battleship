public class HumanPlayer extends Player{

    public HumanPlayer(){
        super();
    }
    
    //calls setshipcoor with 2 int parameters and a character parameter that speficy a row, column, and direction
    @Override
    public boolean placeShips(int r, int c, char d) {
        return setShipCoor(r,c,d);
    }

    //calls shoot other and determines what to do
    @Override
    public String takeTurn(Player other, int r, int c) {
        
        int hitItem = other.shoot(r,c);

        //chooses reaction based on hitItem that was determined by hit function
        //cases 1-5 are the name of ship IDs they have lenghts: 2,3,3,4,5
        //any additional ships will just return their id unless added later
        //any non ships should be specified by a number
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