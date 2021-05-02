public class HumanPlayer extends Player{

    public HumanPlayer(){
        super();
    }
    
    
    @Override
    public boolean placeShips(int r, int c, char d) {
        return super.setShipCoor(r,c,d);
    }

    @Override
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
                return " sunk Destroyer";
            case 2:
                return " sunk Submarine";
            case 3:
                return " sunk Cruiser";
            case 4:
                return " sunk Battleship";
            case 5:
                return " sunk Carrier";
            default:
                return " sunk ship number " + hitItem;
        }

    }

}