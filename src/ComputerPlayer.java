public class ComputerPlayer extends Player{

    public ComputerPlayer(){
        super();
    }

    @Override
    public boolean placeShips(int r, int c, char d) {
        while(!allShipsSet()){
            r = (int)(Math.random() * 9);
            c = (int)(Math.random() * 9);
            int direction = (int)(Math.random() * 4 + 1);
            switch(direction){
                case 1:
                    d = 'n';
                    break;
                case 2:
                    d = 'e';
                    break;
                case 3:
                    d = 's';
                    break;
                case 4:
                    d = 'w';
                    break;
            }
            setShipCoor(r, c, d);
        }
        return true;
    }

    @Override
    public String takeTurn(Player other, int r, int c) {
        int hitItem;
        do{
            
            r = (int)(Math.random()*9);
            c = (int)(Math.random()*9);
            hitItem = other.shoot(r,c);

        }while(hitItem == -2);

        switch(hitItem){
            case -1:
                return "Miss";
            case 0:
                return "Hit";
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
                return "You sunk ship number " + hitItem;
        }
    }
}
