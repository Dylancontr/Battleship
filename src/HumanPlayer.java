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

        if(hitItem == 0) return "Miss";
        if(hitItem == 1) return "Hit";
        return "You sunk ship number " + hitItem;
    }

}